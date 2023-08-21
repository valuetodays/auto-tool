package cn.valuetodays.autotool.mine;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-08-21
 */
public class ClickLogic {

    private AutoClickMine autoClickMine;

    public ClickLogic(AutoClickMine autoClickMine) {
        this.autoClickMine = autoClickMine;
    }

    @Data
    public static class ValuedPoints {
        private List<Point> points;
        private Integer value;

        public ValuedPoints(List<Point> points, Integer value) {
            this.points = points;
            this.value = value;
        }
    }

    public boolean logicByAroundTile(List<Point> points) {
        // 找最小值，并根据该值判断周边雷
        // 再找下一个最小值，并根据该值判断周边雷
        List<Integer> valueList = points.stream()
            .map(e -> autoClickMine.readTile(e.x, e.y).getValue())
            .collect(Collectors.toList());
        List<Integer> ascValueList = valueList.stream()
            // 查询周围有雷的图块
            .filter(e -> e > 0 && e < 9)
            .distinct()
            .sorted(Comparator.comparingInt(Integer::new))
            .collect(Collectors.toList());
        List<ValuedPoints> valuedPointsList = new ArrayList<>();
        for (Integer ascValue : ascValueList) {
            for (int index = 0; index < valueList.size(); index++) {
                Integer value = valueList.get(index);
                if (value.equals(ascValue)) {
                    Point point = points.get(index);
                    this.logicByAroundTile0(points, valuedPointsList, point.x, point.y);
                    Point pointToMark = tryMarkValuedPointsList(valuedPointsList);
                    if (Objects.nonNull(pointToMark)) {
                        autoClickMine.markTileAsMine(pointToMark.x, pointToMark.y);
                        autoClickMine.printTileMap();
                        return true;
                    }
                    // 去掉已经合并过的
                    valuedPointsList = valuedPointsList.stream()
                        .filter(e -> e.points.size() > 0)
                        .collect(Collectors.toList());
                }
            }
        }
        return false;
    }

    private void logicByAroundTile0(List<Point> points,
                                    List<ValuedPoints> valuedPointsList,
                                    int tileX, int tileY) {
        IntTile tile = autoClickMine.readTile(tileX, tileY);
        if (tile.isMine() || tile.isUnknown() || tile.isZero()) {
            return;
        }
        Integer mineCount = tile.getValue();
        // fixme: 这个borderPoints有问题，不能只从points中获取，要从总的图块[][]中获取
        List<Point> borderPoints = points.stream()
            // 取tileX,tileY周围的图块
            .filter(e -> Math.abs(e.x - tileX) <= 1 && Math.abs(e.y - tileY) <= 1)
            // 排除自身
            .filter(e -> e.x != tileX || e.y != tileY)
            .collect(Collectors.toList());
        List<Point> mineList = borderPoints.stream()
            .filter(e -> autoClickMine.readTile(e.x, e.y).isMine())
            .collect(Collectors.toList());
        // 剩余雷的数量
        int restMineCount = mineCount - mineList.size();
        if (restMineCount <= 0) {
            return;
        }
        // 剩余雷 在 unknownList 中
        List<Point> unknownList = borderPoints.stream()
            .filter(e -> autoClickMine.readTile(e.x, e.y).isUnknown())
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(unknownList)) {
            return;
        }

        if (CollectionUtils.size(unknownList) == 1) {
            unknownList.forEach(e -> autoClickMine.markTileAsMine(e.x, e.y));
        } else {
            valuedPointsList.add(new ValuedPoints(unknownList, restMineCount));
        }
    }

    private Point tryMarkValuedPointsList(List<ValuedPoints> valuedPointsList) {
        int size = CollectionUtils.size(valuedPointsList);
        if (size < 2) {
            return null;
        }
        ValuedPoints first = valuedPointsList.get(0);
        for (int i = 1; i < 2; i++) {
            ValuedPoints valuedPoints = valuedPointsList.get(i);
            Point pointToMark = tryMark(first, valuedPoints);
            if (Objects.nonNull(pointToMark)) {
                return pointToMark;
            }
        }
        return null;
    }

    private Point tryMark(ValuedPoints vp1, ValuedPoints vp2) {
        ValuedPoints longerPoints = vp1;
        ValuedPoints theOtherPoints = vp2;
        if (vp2.points.size() > vp1.points.size()) {
            longerPoints = vp2;
            theOtherPoints = vp1;
        }
        if (longerPoints.points.containsAll(theOtherPoints.points)) {
            List<Point> copyPoints = new ArrayList<>(longerPoints.points);
            copyPoints.removeAll(theOtherPoints.points);
//            longerPoints.value -= theOtherPoints.value;
            if (copyPoints.size() == 1) {
                return copyPoints.get(0);
            }
            theOtherPoints.points.clear();
            theOtherPoints.value = 0;
        }
        return null;
    }

    private void canClear(ValuedPoints vp1, ValuedPoints vp2) {

    }
}
