package cn.valuetodays.autotool.llk;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 图块中保存的是字符串
 *
 * @author lei.liu
 * @since 2023-05-28 11:54
 */
public class StringTile extends BaseTile<String> {
    public static final StringTile CLEARED = new StringTile(
        StringUtils.leftPad("", DigestUtils.md5Hex("").length(), "-")
    );

    // 四周的墙（边界）归入 CLEARED 处理
    public static final StringTile BORDER = CLEARED;

    public StringTile(String status) {
        this.status = status;
    }

    @Override
    public boolean isCleared() {
        return CLEARED.status.equals(status);
    }

}
