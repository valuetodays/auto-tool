package cn.valuetodays.autotool.llk;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * 图块中保存的是数字
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IntTile extends BaseTile<Integer> {
    public static final IntTile CLEARED = new IntTile(-1);
    // 四周的墙（边界）归入 CLEARED 处理
    public static final IntTile BORDER = CLEARED;

    public IntTile(int status) {
        this.status = status;
    }

    @Override
    public boolean isCleared() {
        return Objects.equals(status, CLEARED.status);
    }

}
