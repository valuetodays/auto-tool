package cn.valuetodays.autotool.mine;

import cn.valuetodays.autotool.common.win32.ScreenUtils;
import cn.valuetodays.autotool.common.win32.Win32Utils;
import com.sun.jna.platform.win32.WinDef;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-27 21:42
 */
public class AutoClickMine {
    private static final String basePath = "X:/mine";
    private WinDef.HWND hwnd;
    private IntTile[][] tileMap;
    private BufferedImage clientAsImage;
    private int verticalTileNum;
    private int horizonTileNum;

    private final IMine delegate;

    public AutoClickMine(IMine mine) {
        if (Objects.isNull(mine)) {
            throw new NullPointerException("should not be null");
        }
        this.delegate = mine;
    }

    /**
     * 自动点击（外部调用入口）
     */
    public void doAutoClick() throws IOException {
        System.out.println("doAutoClick() begin");

        String withTitle = delegate.getWithTitle();
        hwnd = Win32Utils.getHwnd(withTitle);
        if (Objects.isNull(hwnd)) {
            System.out.println("程序未运行：" + withTitle);
            return;
        }

        Win32Utils.USER_32.SetForegroundWindow(hwnd);
        Win32Utils.USER_32_PLUS.SetActiveWindow(hwnd);
        Win32Utils.USER_32.BringWindowToTop(hwnd);

        Win32Utils.sleep(1000);

        WinDef.RECT clientRect = new WinDef.RECT();
        Win32Utils.USER_32.GetClientRect(hwnd, clientRect);
        System.out.println("clientRect: " + clientRect);
        if (clientRect.right == 0) {
            System.err.println("window is not active?");
            return;
        }
        WinDef.RECT windowRect = new WinDef.RECT();
        Win32Utils.USER_32.GetWindowRect(hwnd, windowRect);
        System.out.println("windowRect: " + windowRect);
/*
        // 屏幕坐标要转换成客户区的坐标
        WinDef.POINT leftTopPoint = new WinDef.POINT(leftOffsetToFirstTile, topOffsetToFirstTile);
        Win32Utils.USER_32_PLUS.ClientToScreen(hwnd, leftTopPoint);
        System.out.println("leftTopPoint: " + leftTopPoint.x + ", " + leftTopPoint.y);
*/


        forceSaveClientAsImage();
        File fullFile = new File(basePath + "/s0-full.jpg");
        ScreenUtils.saveScreenshotToJpgFile(clientAsImage, fullFile);

        Rectangle rectangle = clientRect.toRectangle();
        int clientWidth = rectangle.width;
        int clientHeight = rectangle.height;
        int totalTilesHeight = delegate.calculateTotalTilesHeight(clientHeight);
        int totalTilesWidth = delegate.calculateTotalTilesWidth(clientWidth);
        verticalTileNum = delegate.calculateVerticalTileNum(totalTilesWidth);
        horizonTileNum = delegate.calculateHorizontalTileNum(totalTilesHeight);
        System.out.println("tileXNum=" + verticalTileNum + ", tileYNum=" + horizonTileNum);

        tileMap = new IntTile[verticalTileNum][horizonTileNum];
        for (IntTile[] intTiles : tileMap) {
            Arrays.fill(intTiles, IntTile.UNKNOWN);
        }

        forceSaveClientAsImage();
        reloadTileMap();

        printTileMap();

        Point p = new Point(1, 1);
//        markTileAsMine(p.x, p.y);
        clickTile(p.x + 1, p.y + 1);
        clickTile(0, 0);

//        for (int i = 0; i < 4; i++) {
//            clickTile(i, 0);
//        }

        autoClick();

        for (int i = 0; i < verticalTileNum*horizonTileNum / 2; i++) {
            autoClick();
            System.out.println("i end " + i);
        }

        System.out.println("doAutoClick() end");
    }

    private void reloadTileMap() {
        Map<String, Integer> stringIntMap = new HashMap<>();
        stringIntMap.put("6af466a3b34f587a799383e4db8c1439", 0);

        forceSaveClientAsImage();

        for (int i = 0; i < verticalTileNum; i++) {
            for (int j = 0; j < horizonTileNum; j++) {
                IntTile previousState = readTile(i, j);
                if (previousState.isMine() || previousState.isZero()) {
                    continue;
                }
                if (!previousState.isUnknown()) {
                    continue;
                }
                BufferedImage subimage = delegate.getSubimage(clientAsImage, i, j);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
                    ScreenUtils.saveScreenshotToJpgFile(subimage, baos);
                    String md5Hex = DigestUtils.md5Hex(baos.toByteArray());
//                    tileMap[j + 1][i + 1] = new StringTile(md5Hex);
                    File subFile = new File(basePath + "/ss-" + i + "_" + j + "_" + md5Hex + ".jpg");
                    // 测试时可以生成子图片
                    ScreenUtils.saveScreenshotToJpgFile(subimage, subFile);
                    int scannedValue = -1;
                    Integer tmpScannedValue = stringIntMap.get(md5Hex);
                    if (Objects.nonNull(tmpScannedValue)) {
                        scannedValue = tmpScannedValue;
                    } else {
                        scannedValue = delegate.getValue(subimage.getRGB(10, 3));
                    }
                    tileMap[i][j] = IntTile.of(scannedValue);
//                    System.out.println(i + "_" + j + "scannedValue=" + scannedValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void forceSaveClientAsImage() {
        this.clientAsImage = ScreenUtils.getScreenshotOfWindowClient(hwnd);
    }


    /**
     * 尝试点击图块
     */
    private void tryClickTile(int tileX, int tileY) {
//        IntTile toClick = readTile(tileX, tileY);
//        if (!toClick.isUnknown()) {
//            return;
//        }
        int left = tileX - 1;
        int right = tileX + 1;
        int top = tileY - 1;
        int bottom = tileY + 1;

        guessAroundTile(left, top);
        guessAroundTile(tileX, top);
        guessAroundTile(right, top);

        guessAroundTile(left, tileY);
        guessAroundTile(right, tileY);

        guessAroundTile(left, bottom);
        guessAroundTile(tileX, bottom);
        guessAroundTile(right, bottom);
    }

    boolean isInTileMap(int tileX, int tileY) {
        if (tileX <= -1 || tileY <= -1) {
            return false;
        }
        if (tileX >= verticalTileNum || tileY >= horizonTileNum) {
            return false;
        }
        return true;
    }

    private void guessAroundTile(int tileX, int tileY) {
        if (!isInTileMap(tileX, tileY)) {
            return;
        }
        IntTile tile = readTile(tileX, tileY);
        System.out.println("guessAroundTile x" + tileX + ", y=" + tileY + ", value=" + tile.getValue());
        if (tile.isUnknown() || tile.isZero() || tile.isMine()) {
            return;
        }
        Integer mineCount = tile.getValue();

        List<Point> tilesAround = new ArrayList<>(8);
        for (int i = tileX - 1; i <= tileX + 1; i++) {
            for (int j = tileY - 1; j <= tileY + 1 ; j++) {
                if (i == tileX && j == tileY) {
                    continue;
                }
                if (isInTileMap(i, j)) {
                    tilesAround.add(new Point(i, j));
                }
            }
        }
        int mineTiles = (int)tilesAround.stream()
            .filter(e -> readTile(e.x, e.y).isMine())
            .count();
        // 周围的雷都已经确定了，就把周围不是雷的点击了
        if (mineCount == mineTiles) {
            System.out.println("found all mines=" + mineCount);
            tilesAround.stream()
                .filter(e -> readTile(e.x, e.y).isUnknown())
                .forEach(e -> {
                    // 不是雷，左击它
                    String msg = "click non-mine tile(x,y)=" + e.x + "," + e.y
                        + ", because (tileX,tileY)=" + tileX +"," + tileY+ "=" + readTile(tileX, tileY).getValue();
                    System.out.println(msg);
                    clickTile(e.x, e.y);
                });
        } else {
            int unknownTiles = (int)tilesAround.stream()
                .filter(e -> readTile(e.x, e.y).isUnknown())
                .count();
            // 周围的雷都已经确定了，就把周围是雷的标记一下
            if (mineCount == unknownTiles + mineTiles) {
                System.out.println("found mines=" + mineCount);
                tilesAround.stream()
                    .filter(e -> readTile(e.x, e.y).isUnknown())
                    .forEach(e -> {
                        // 是雷，右击标记它
                        String msg = "mark mine tile(x,y)=" + e.x + "," + e.y
                            + ", because (tileX,tileY)=" + tileX +"," + tileY + "=" + readTile(tileX, tileY).getValue();
                        System.out.println(msg);
                        markTileAsMine(e.x, e.y);
                    });
            }
        }

    }

    /**
     * 自动点击
     */
    private void autoClick() {
        reloadTileMap();
        List<Point> points = new ArrayList<>();
        for (int point1x = 0; point1x < tileMap.length; point1x++) {
            for (int point1y = 0; point1y < tileMap[0].length; point1y++) {
                IntTile tile = readTile(point1x, point1y);
                Integer value = tile.getValue();
                if (value > 0 && value < 9 && hasUnknownBorder(tile, point1x, point1y)) {
                    points.add(new Point(point1x, point1y));
                }
            }
        }
        System.out.println("try points=" + StringUtils.join(points, ", "));
        for (Point point : points) {
            reloadTileMap();
            tryClickTile(point.x, point.y);
        }
    }

    /**
     * 周围有未点击/未标记的图块
     * @param x x
     * @param y x
     * @return 是/否
     */
    private boolean hasUnknownBorder(IntTile tile, int x, int y) {
        int left = x - 1;
        int right = x + 1;
        int top = y - 1;
        int bottom = y + 1;

        return Stream.of(
            readNullableTile(left, top),
            readNullableTile(x, top),
            readNullableTile(right, top),

            readNullableTile(left, y),
            readNullableTile(right, y),

            readNullableTile(left, bottom),
            readNullableTile(x, bottom),
            readNullableTile(right, bottom)
        )
        .filter(Objects::nonNull)
        .anyMatch(IntTile::isUnknown);
    }

    /**
     * 读取二维数组中的值
     * （注意：不要使用之前获取的值，因为当消除相同图块后，这两个图块的值都已被清空）
     */
    private IntTile readTile(int x, int y) {
        return tileMap[x][y];
    }

    private IntTile readNullableTile(int x, int y) {
        if (isInTileMap(x, y)) {
            return tileMap[x][y];
        } else {
            return null;
        }
    }


    /**
     * 打印图块二维数组，用于调试
     */
    private void printTileMap() {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                System.out.print(tileMap[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }

    /**
     * 左击图块
     * @param tileX x
     * @param tileY y
     */
    private void clickTile(int tileX, int tileY) {
        IntTile intTile = readTile(tileX, tileY);
        if (!intTile.isUnknown()) {
            System.out.println("tile x,y=" + tileX + "," + tileY + ", value =" + intTile.getValue()
                + ", do not need to click");
            return;
        }
        System.out.println("try click tile(x,y)=" + tileX + "," + tileY);
        Point p = delegate.calculateCenterPosition(tileX, tileY);
        Win32Utils.clickWindow(hwnd, p.x, p.y);
        reloadTileMap();
        tryClickTile(tileX, tileY);
    }
    /**
     * 右击图块，设置成雷，避免误点
     * @param tileX x
     * @param tileY y
     */
    private void markTileAsMine(int tileX, int tileY) {
        readTile(tileX, tileY).setAsMine();
        System.out.println("mark tile(x,y)=" + tileX + "," + tileY);
        Point p = delegate.calculateCenterPosition(tileX, tileY);
        Win32Utils.rightClickWindow(hwnd, p.x, p.y);
        reloadTileMap();
        tryClickTile(tileX, tileY);
    }

}
