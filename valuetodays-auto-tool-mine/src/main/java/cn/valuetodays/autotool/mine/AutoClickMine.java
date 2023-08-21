package cn.valuetodays.autotool.mine;

import cn.valuetodays.autotool.common.win32.ScreenUtils;
import cn.valuetodays.autotool.common.win32.Win32Utils;
import com.sun.jna.platform.win32.WinDef;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

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

    private static final int tileWidth = 24;
    private static final int tileHeight = 24;
    private static final int leftOffsetToFirstTile = 18;
    private static final int topOffsetToFirstTile = 82;
    private static final int rightOffsetToLastTile = 12;
    private static final int bottomOffsetToLastTile = 12;

    private boolean is1(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return green < 30 && red < 35 && (blue > 235);
    }

    private boolean is2(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (green > 115 && green < 130) && red < 10 && (blue<17);
    }

    private boolean is3(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red > 215) && green < 15 && (blue<30);
    }

    public int getValue(int rgb) {
        if (rgb == new Color(192, 192, 192).getRGB()) {
            return -1; // unknown
        }
        if (is1(rgb)) {
            return 1;
        } else if (is2(rgb)) {
            return 2;
        } else if (is3(rgb)) {
            return 3;
        }
        System.err.println("unknown rgb=" + rgb);
        return -100;
    }

    /**
     * 自动点击（外部调用入口）
     */
    public void doAutoClick() throws IOException {
        System.out.println("doAutoClick() begin");

        String windowTitle = ("扫雷");
        hwnd = Win32Utils.getHwnd(windowTitle);
        if (Objects.isNull(hwnd)) {
            System.out.println("程序未运行");
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
        int totalTilesHeight = clientHeight - topOffsetToFirstTile - bottomOffsetToLastTile;
        int totalTilesWidth = clientWidth - leftOffsetToFirstTile - rightOffsetToLastTile;
        verticalTileNum = totalTilesWidth / tileWidth;
        horizonTileNum = totalTilesHeight / tileHeight;
        System.out.println("tileXNum=" + verticalTileNum + ", tileYNum=" + horizonTileNum);

        tileMap = new IntTile[verticalTileNum][horizonTileNum];
        for (IntTile[] intTiles : tileMap) {
            Arrays.fill(intTiles, IntTile.UNKNOWN);
        }

//

        forceSaveClientAsImage();

/*
        Map<Integer, Integer> intIntMap = new HashMap<>();
        intIntMap.put(-194809, 3);
        intIntMap.put(-324339, 3);
        final int center = 999;
        Map<Integer, List<Triple<Integer, Integer, Integer>>> valueAndPosWithRGBMap = new HashMap<>();
        // 还未点击
        valueAndPosWithRGBMap.put(-1,
            Collections.singletonList(
                Triple.of(0, 0, Color.WHITE.getRGB())
            )
        );
        // 0
        valueAndPosWithRGBMap.put(0,
            Collections.singletonList(
                Triple.of(center, center, -4144960)
            )
        );
        // 1
        valueAndPosWithRGBMap.put(1,
            Collections.singletonList(
                Triple.of(center, center, -16776961)
            )
        );
        // 2
        valueAndPosWithRGBMap.put(2,
            Collections.singletonList(
                Triple.of(center, center, -16744448)
            )
        );
        // 3
        valueAndPosWithRGBMap.put(3,
            Collections.singletonList(
                Triple.of(center, center, -194809)
            )
        );
*/
        reloadTileMap();

/*
        HashSet<String> distinctImageIdList = new HashSet<>(imageIdList);
        int tileCount = mode.getHorizontalTileCount() * mode.getVerticalTileCount(); // 图块数量
        int exceptedTileCategoryCount = tileCount / mode.getTileCountPerCategory(); // 图块种类类数量
        System.out.println("!! distinct: " + distinctImageIdList.size() + ", total: " + imageIdList.size());
        if (exceptedTileCategoryCount != distinctImageIdList.size()) {
            System.err.println("解析tile图片失败，有误识别的图片，建议重新开始一局。");
        }

 */
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
                BufferedImage subimage = clientAsImage.getSubimage(
                    leftOffsetToFirstTile + i * tileWidth + 3, topOffsetToFirstTile + j * tileHeight + 3,
                    tileWidth - 4, tileHeight - 4
                );
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
                        scannedValue = getValue(subimage.getRGB(10, 3));
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
        IntTile toClick = readTile(tileX, tileY);
        if (!toClick.isUnknown()) {
            return;
        }
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
        for (int point1x = 0; point1x < tileMap.length; point1x++) {
            for (int point1y = 0; point1y < tileMap[0].length; point1y++) {
                tryClickTile(point1x, point1y);
                reloadTileMap();
            }
        }
    }

    /**
     * 读取二维数组中的值
     * （注意：不要使用之前获取的值，因为当消除相同图块后，这两个图块的值都已被清空）
     */
    private IntTile readTile(int x, int y) {
        return tileMap[x][y];
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
        int x = leftOffsetToFirstTile + tileX * tileWidth + tileWidth / 2;
        int y = topOffsetToFirstTile + tileY * tileHeight + tileHeight / 2;
        Win32Utils.clickWindow(hwnd, x, y);
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
        int x = leftOffsetToFirstTile + tileX * tileWidth + tileWidth / 2;
        int y = topOffsetToFirstTile + tileY * tileHeight + tileHeight / 2;
        Win32Utils.rightClickWindow(hwnd, x, y);
        reloadTileMap();
        tryClickTile(tileX, tileY);
    }

}
