package cn.valuetodays.autotool.mine;

import cn.valuetodays.autotool.mine.impl.ClassicMine;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link ClickLogic}.
 *
 * @author lei.liu
 * @since 2023-08-21
 */
public class ClickLogicTest {

    @Test
    public void logicByAroundTile() {
        IMine delegate = new ClassicMine();
        AutoClickMine autoClickMine = new AutoClickMine(delegate);
        IntTile[][] tileMap = new IntTile[3][3];
        tileMap[0][0] = IntTile.of(3);
        tileMap[1][0] = IntTile.of(1);
        tileMap[2][0] = IntTile.ZERO;
        tileMap[0][1] = IntTile.MINE;
        tileMap[1][1] = IntTile.of(2);
        tileMap[2][1] = IntTile.of(1);
        tileMap[0][2] = IntTile.UNKNOWN;
        tileMap[1][2] = IntTile.UNKNOWN;
        tileMap[2][2] = IntTile.UNKNOWN;
        autoClickMine.setTileMap(tileMap);
        ClickLogic clickLogic = autoClickMine.getClickLogic();
        autoClickMine.printTileMap();
        List<Point> points = Arrays.asList(
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 0),

            new Point(0, 1),
            new Point(1, 1),
            new Point(2, 1),

            new Point(0, 2),
            new Point(1, 2),
            new Point(2, 2)
        );
        clickLogic.logicByAroundTile(points);
    }
}
