package cn.valuetodays.autotool.common.win32;

import com.sun.jna.platform.win32.WinDef.HWND;
import lombok.Data;

import java.io.Serializable;

/**
 * EnumWindows()函数的结果保存在此处.
 *
 * @author lei.liu
 * @since 2023-05-25 11:29
 */
@Data
public class EnumWindowVo implements Serializable {
    private HWND hwnd;
    private String windowText;
    private String className;
    private long addr;
    private String hexAddr;
}
