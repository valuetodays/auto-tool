package cn.valuetodays.autotool.llk;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 连连看核心算法类  实现连通。
 *
 * 来源于互联网，我稍做修改。
 *
 * @since 2017-11-24 22:46
 */
public class LLKCore {
    /**
     * 连通的总判断方法
     *
     * @param tiles
     * @param firstPoint
     * @param secondPoint
     * @return 连通点的集合; null表示无法连通
     */
    public static List<Point> checkLinked(BaseTile[][] tiles, Point firstPoint, Point secondPoint) {
        List<Point> noCorner = noCorner(tiles, firstPoint, secondPoint);
        if (noCorner != null) {
            return noCorner;
        }
        List<Point> oneCorner = oneCorner(tiles, firstPoint, secondPoint);
        if (oneCorner != null) {
            return oneCorner;
        }

        List<Point> twoCorner = twoCorner(tiles, firstPoint, secondPoint);
        if (twoCorner != null) {
            return twoCorner;
        }
        return null;
    }

    /**
     * 二拐角连通算法
     * @param tiles
     * @param firstPoint
     * @param secondPoint
     * @return 连通点的集合，null代表不能连通
     */
    private static List<Point> twoCorner(BaseTile[][] tiles, Point firstPoint, Point secondPoint) {
        for (int i = 0; i < tiles.length; i++) {
            Point c = new Point(i, firstPoint.y);
            if (tiles[c.x][c.y].isCleared() && canArrived(tiles, firstPoint, c)) {
                List<Point> oneCorner = oneCorner(tiles, c, secondPoint);
                if (oneCorner != null) {
                    oneCorner.add(0, firstPoint);
                    return oneCorner;
                }
            }
        }

        for (int i = 0; i < tiles[0].length; i++) {
            Point c = new Point(firstPoint.x, i);
            if (tiles[c.x][c.y].isCleared() && canArrived(tiles, firstPoint, c)) {
                List<Point> oneCorner = oneCorner(tiles, c, secondPoint);
                if (oneCorner != null) {
                    oneCorner.add(0, firstPoint);
                    return oneCorner;
                }
            }
        }

        return null;
    }

    /**
     * 一拐角连通算法
     * @param tiles
     * @param firstPoint
     * @param secondPoint
     * @return 连通点的集合，null代表不能连通
     */
    private static List<Point> oneCorner(BaseTile[][] tiles, Point firstPoint, Point secondPoint) {
        Point tmp = new Point(firstPoint.x, secondPoint.y);
        List<Point> points = rightAnglePoints(tiles, firstPoint, secondPoint, tmp);
        if (points != null) {
            return points;
        }

        tmp = new Point(secondPoint.x, firstPoint.y);
        points = rightAnglePoints(tiles, firstPoint, secondPoint, tmp);
        if (points != null) {
            return points;
        }

        return null;
    }

    /**
     * 判断三个点是否连通
     * @param tiles
     * @param first
     * @param second
     * @param third
     * @return
     */
    private static List<Point> rightAnglePoints(BaseTile[][] tiles, Point first, Point second, Point third) {
        if (tiles[third.x][third.y].isCleared()
                && canArrived(tiles, first, third)
                && canArrived(tiles, second, third)) {
            List<Point> list = new ArrayList<>();
            list.add(first);
            list.add(third);
            list.add(second);
            return list;
        }

        return null;
    }

    private static List<Point> noCorner(BaseTile[][] tiles, Point firstPoint, Point secondPoint) {
        if (canArrived(tiles, firstPoint, secondPoint)) {
            List<Point> list = new ArrayList<>();
            list.add(firstPoint);
            list.add(secondPoint);
            return list;
        }
        return null;
    }

    /**
     * 判断直线是否可以连通
     *
     * @param tiles
     * @param firstPoint
     * @param secondPoint
     * @return true表示可以连通，false表示不可以连通
     */
    private static boolean canArrived(BaseTile[][] tiles, Point firstPoint, Point secondPoint) {
        if (firstPoint.x == secondPoint.x) {
            for (int i = Math.min(firstPoint.y, secondPoint.y) + 1; i < Math.max(firstPoint.y, secondPoint.y); i++) {
                if (tiles[firstPoint.x][i].hasTile()) {
                    return false;
                }
            }

            return true;
        }

        if (firstPoint.y == secondPoint.y) {
            for (int i = Math.min(firstPoint.x, secondPoint.x) + 1; i < Math.max(firstPoint.x, secondPoint.x); i++) {
                if (tiles[i][firstPoint.y].hasTile()) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
