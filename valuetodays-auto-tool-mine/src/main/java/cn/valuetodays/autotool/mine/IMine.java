package cn.valuetodays.autotool.mine;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-08-21
 */
public interface IMine {
    String getWithTitle();

    int getTileWidth();
    int getTileHeight();
    int getLeftOffsetToFirstTile();
    int getTopOffsetToFirstTile();
    int getRightOffsetToLastTile();
    int getBottomOffsetToLastTile();

    int getValue(int rgb);

    default int calculateTotalTilesHeight(int clientHeight) {
        return clientHeight - getTopOffsetToFirstTile() - getBottomOffsetToLastTile();
    }
    default int calculateTotalTilesWidth(int clientWidth) {
        return clientWidth - getLeftOffsetToFirstTile() - getRightOffsetToLastTile();
    }

    default int calculateVerticalTileNum(int totalTilesWidth) {
        return totalTilesWidth / getTileWidth();
    }
    default int calculateHorizontalTileNum(int totalTilesHeight) {
        return totalTilesHeight / getTileHeight();
    }

    BufferedImage getSubimage(BufferedImage clientAsImage, int i, int j);

    default Point calculateCenterPosition(int tileX, int tileY) {
        int x = getLeftOffsetToFirstTile() + tileX * getTileWidth() + getTileWidth() / 2;
        int y = getTopOffsetToFirstTile() + tileY * getTileHeight() + getTileHeight() / 2;
        return new Point(x, y);
    }
}
