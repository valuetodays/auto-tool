package cn.valuetodays.autotool.common.win32.dllext;

import cn.valuetodays.autotool.common.win32.Win32Struct;
import cn.valuetodays.autotool.common.win32.Win32Utils;
import cn.valuetodays.autotool.common.win32.Win32Const;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-26 19:57
 */
public interface Comctl32Plus extends StdCallLibrary, WinUser, WinNT {
    Comctl32Plus INSTANCE = Native.load("comctl32", Comctl32Plus.class, W32APIOptions.DEFAULT_OPTIONS);

    // 是宏，不是方法，需要自行实现
//    #define TVM_GETCOUNT            (TV_FIRST + 5)
//#define TreeView_GetCount(hwnd)  (UINT)SNDMSG((hwnd), TVM_GETCOUNT, 0, 0)
    default int TreeView_GetCount(HWND hwnd) {
        LRESULT lr = Win32Utils.USER_32.SendMessage(hwnd, Win32Const.TVM_GETCOUNT, null, null);
        return lr.intValue();
    }

    default DWORD TreeView_GetNextItem(HWND hwnd, DWORD hitem, int code) {
        LRESULT lr = Win32Utils.USER_32.SendMessage(hwnd, Win32Const.TVM_GETNEXTITEM, new WPARAM(code), new LPARAM(hitem.longValue()));
        return new DWORD(lr.longValue());
    }

    default DWORD TreeView_GetRoot(HWND hwnd) {
        return TreeView_GetNextItem(hwnd, new DWORD(0), Win32Const.TVGN_ROOT);
    }

    default DWORD TreeView_GetSelection(HWND hwnd) {
        return TreeView_GetNextItem(hwnd, new DWORD(0), Win32Const.TVGN_CARET);
    }

    default BOOL TreeView_Select(HWND hwnd, DWORD hitem, int code) {
        LRESULT lr = Win32Utils.USER_32.SendMessage(hwnd, Win32Const.TVM_SELECTITEM, new WPARAM(code), new LPARAM(hitem.longValue()));
        return new BOOL(lr.intValue());
    }

    default BOOL TreeView_SelectItem(HWND hwnd, DWORD hitem) {
        return TreeView_Select(hwnd, hitem, Win32Const.TVGN_CARET);
    }

    default BOOL TreeView_SelectDropTarget(HWND hwnd, DWORD hitem) {
        return TreeView_Select(hwnd, hitem, Win32Const.TVGN_DROPHILITE);
    }

    default BOOL TreeView_SelectSetFirstVisible(HWND hwnd, DWORD hitem) {
        return TreeView_Select(hwnd, hitem, Win32Const.TVGN_FIRSTVISIBLE);
    }

    default DWORD TreeView_GetChild(HWND hwnd, DWORD hitem) {
        return TreeView_GetNextItem(hwnd, hitem, Win32Const.TVGN_NEXT);
    }

    default DWORD TreeView_GetNextSibling(HWND hwnd, DWORD hitem) {
        return TreeView_GetNextItem(hwnd, hitem, Win32Const.TVGN_NEXT);
    }

    default DWORD TreeView_GetFirstVisible(HWND hwnd) {
        return TreeView_GetNextItem(hwnd, new DWORD(0), Win32Const.TVGN_FIRSTVISIBLE);
    }

    default DWORD TreeView_GetNextVisible(HWND hwnd, DWORD hitem) {
        return TreeView_GetNextItem(hwnd, hitem, Win32Const.TVGN_NEXTVISIBLE);
    }


    default BOOL TreeView_GetItem(HWND hwnd, Win32Struct.TVITEM pitem) {
        LRESULT lr = Win32Utils.USER_32.SendMessage(hwnd, Win32Const.TVM_GETITEM, null, new LPARAM(pitem.getPointer().getLong(0)));
        return new BOOL(lr.intValue());
    }

}
