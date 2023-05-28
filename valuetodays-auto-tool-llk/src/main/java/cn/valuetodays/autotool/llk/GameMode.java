package cn.valuetodays.autotool.llk;

import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-28 13:12
 */
@Getter
public enum GameMode {
    CHUJI(242 - 1, 174, 60, 75, 12, 7, 4),
    ZHONGJI(1, 1, 1, 1, 1, 1, 1),
    GAOJI(1, 1, 1, 1, 1, 1, 1),
    TEGAO(1, 1, 1, 1, 1, 1, 1);

    // 二维图块左边到窗口左边的距离
    private final int left;
    // 二维图块上边距窗口上边的距离
    private final int top;
    // 图块的宽度
    private final int tileWidth;
    // 图块的高度
    private final int tileHeight;
    // 水平方向图块的个数
    private final int horizontalTileCount;
    // 竖直方向图块的个数
    private final int verticalTileCount;
    // 二给图块中每类图块的数量
    private final int tileCountPerCategory;

    GameMode(int left, int top,
             int tileWidth, int tileHeight,
             int hTileCount, int vTileCount, int tileCountPerCategory) {
        this.left = left;
        this.top = top;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.horizontalTileCount = hTileCount;
        this.verticalTileCount = vTileCount;
        this.tileCountPerCategory = tileCountPerCategory;
    }
}
