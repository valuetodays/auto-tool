package cn.valuetodays.autotool.common.win32;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-26 20:40
 */
public interface Win32Struct {

    @Structure.FieldOrder({"mask", "hItem", "state", "stateMask",
        "pszText", "cchTextMax",
        "iImage", "iSelectedImage", "cChildren", "lParam"})
    public class TVITEM extends Structure {
        public int mask;
//        public WinNT.HANDLE hItem;
        public WinDef.DWORD hItem;
        public int state;
        public int stateMask;
        public char[] pszText;
        public int       cchTextMax;
        public int       iImage;
        public int       iSelectedImage;
        public int       cChildren;
        public WinDef.LPARAM lParam;

    }

}
