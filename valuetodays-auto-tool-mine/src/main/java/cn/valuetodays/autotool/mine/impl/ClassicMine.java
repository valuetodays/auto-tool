package cn.valuetodays.autotool.mine.impl;

import cn.valuetodays.autotool.mine.IMine;
import cn.valuetodays.autotool.mine.IntTile;
import lombok.Data;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-08-21
 */
@Data
public class ClassicMine implements IMine {

    private int tileWidth = 24;
    private int tileHeight = 24;
    private int leftOffsetToFirstTile = 18;
    private int topOffsetToFirstTile = 82;
    private int rightOffsetToLastTile = 12;
    private int bottomOffsetToLastTile = 12;


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
        return (green > 115 && green < 130) && red < 10 && (blue < 17);
    }

    private boolean is3(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red > 215) && green < 15 && (blue < 30);
    }

    private boolean is4(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red == 96) && green == 96 && (blue == 160);
    }

    private boolean is5(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red == 128) && green == 0 && (blue == 0);
    }
    private boolean is6(int rgb) {
        Color color = new Color(rgb, false);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red == 0) && green == 128 && (blue == 128);
    }

    @Override
    public String getWithTitle() {
        return "扫雷";
    }

    @Override
    public int getValue(int rgb) {
        if (rgb == new Color(192, 192, 192).getRGB()) {
            return IntTile.UNKNOWN.getValue(); // unknown
        }
        if (is1(rgb)) {
            return 1;
        } else if (is2(rgb)) {
            return 2;
        } else if (is3(rgb)) {
            return 3;
        } else if (is4(rgb)) {
            return 4;
        } else if (is6(rgb)) {
            return 6;
        }
        System.err.println("unknown rgb=" + rgb);
        return -100;
    }

    @Override
    public BufferedImage getSubimage(BufferedImage clientAsImage, int i, int j) {
        return clientAsImage.getSubimage(
            getLeftOffsetToFirstTile() + i * getTileWidth() + 3,
            getTopOffsetToFirstTile() + j * getTileHeight() + 3,
            getTileWidth() - 4, getTileHeight() - 4
        );
    }
}
