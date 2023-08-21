package cn.valuetodays.autotool.mine;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * 图块中保存的是数字.
 *
 *   IntTile应该是不可变对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IntTile extends BaseTile<Integer> {
    public static final IntTile ZERO = new IntTile(0);
    public static final IntTile UNKNOWN = new IntTile(-1);
    public static final IntTile MINE = new IntTile(99);

    public IntTile(int value) {
        this.value = value;
    }

    public static IntTile of(int value) {
        return new IntTile(value);
    }

    @Override
    public boolean isMine() {
        return Objects.equals(value, MINE.value);
    }

    @Override
    public boolean isUnknown() {
        return Objects.equals(value, UNKNOWN.value);
    }
    public boolean isZero() {
        return Objects.equals(value, ZERO.value);
    }

    public boolean isNumber() {
        return value > 0 && value < 9;
    }

}
