package cn.valuetodays.autotool.llk;

/**
 * 图块.
 *
 * @author lei.liu
 * @since 2023-05-28 11:31
 */
public abstract class BaseTile<T> {
    /**
     * 图片中值（一般是数字，但理论上也可以是其它的，如字符串）
     */
    protected T status;

    public abstract boolean isCleared();

    public final T getStatus() {
        return status;
    }

    public final boolean hasTile() {
        return !isCleared();
    }
    // 新写一个方法，不用重写equals()方法
    public final boolean isEquals(BaseTile<T> other) {
        if (other == null) {
            return false;
        }
        return status.equals(other.status);
    }
}
