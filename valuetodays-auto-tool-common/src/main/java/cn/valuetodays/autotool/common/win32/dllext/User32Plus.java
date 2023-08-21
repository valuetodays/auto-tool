package cn.valuetodays.autotool.common.win32.dllext;

import cn.valuetodays.autotool.common.win32.Win32Const;
import cn.valuetodays.autotool.common.win32.Win32Utils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import java.util.Objects;

/**
 * 有些方法不在 {@link User32}中，就放到这里来.
 *
 * @author lei.liu
 * @since 2023-05-25 13:14
 */
public interface User32Plus extends StdCallLibrary, WinUser, WinNT {
    /**
     * The instance.
     */
    User32Plus INSTANCE = Native.load("user32", User32Plus.class, W32APIOptions.DEFAULT_OPTIONS);

    HWND GetDlgItem(HWND hDlg, int nIDDlgItem);

    /**
     * @param hwnd can be null.
     */
    HWND GetTopWindow(HWND hwnd);

    BOOL SetDlgItemTextA(HWND hDlg, int nIDDlgItem, char[] lpString);

    UINT GetDlgItemText(HWND hdlg, int nIDDlgItem, char[] lpString, int cchMax);

    HWND SetActiveWindow(HWND hWnd);

    LRESULT SendDlgItemMessageA(HWND hDlg, int nIDDlgItem, UINT Msg, WPARAM wParam, LPARAM lParam);

    int GetDlgCtrlID(HWND hWnd);

    /**
     * 获取控件中的文本.
     * 已功能使用的控件：Static
     *
     * @param hdlg       hdlg
     * @param nIDDlgItem nIDDlgItem
     */
    default String GetDlgItemText2(HWND hdlg, int nIDDlgItem) {
        if (Objects.isNull(hdlg)) {
            return null;
        }
        char[] lpString = new char[256];
        UINT uInt = GetDlgItemText(hdlg, nIDDlgItem, lpString, lpString.length);
        return Win32Utils.toNativeString(lpString, uInt.intValue());
    }

    /**
     * 获取输入框中的内容，当GetWindowText() / GetDlgItemText不生效时使用本方法
     * 已功能使用的控件：Edit
     *
     * @param hdlg       hdlg
     * @param nIDDlgItem nIDDlgItem
     */
    default String GetTextInEditCtrl(HWND hdlg, int nIDDlgItem) {
        HWND hwndEdit = Win32Utils.USER_32_PLUS.GetDlgItem(hdlg, nIDDlgItem);

        int n = 1024;
        Pointer pointer = Win32Utils.stringToPointer2(new String(new char[n]));
        LPARAM lparamForText = new LPARAM(Win32Utils.toNativeValue(pointer));
        Win32Utils.USER_32.SendMessage(hwndEdit, Win32Const.WM_GETTEXT, new WPARAM(n), lparamForText);
        return pointer.getWideString(0);
    }

    default String GetClassName2(HWND hwnd) {
        char[] lpClassName = new char[256];
        int length = User32.INSTANCE.GetClassName(hwnd, lpClassName, lpClassName.length);
        return Win32Utils.toNativeString(lpClassName, length);
    }

    default String GetWindowTitle(final HWND hwnd) {
        return WindowUtils.getWindowTitle(hwnd);
    }

    BOOL ClientToScreen(HWND hWnd, POINT lpPoint);

}
