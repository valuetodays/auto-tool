package cn.valuetodays.autotool.common.win32;

import cn.valuetodays.autotool.common.win32.dllext.Comctl32Plus;
import cn.valuetodays.autotool.common.win32.dllext.GDI32Plus;
import cn.valuetodays.autotool.common.win32.dllext.User32Plus;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.UINT;
import com.sun.jna.platform.win32.WinUser;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-25 10:57
 */
public final class Win32Utils {
    public static final User32 USER_32 = User32.INSTANCE;
    public static final User32Plus USER_32_PLUS = User32Plus.INSTANCE;
    public static final Kernel32 KERNEL_32 = Kernel32.INSTANCE;
    public static final GDI32 GDI_32 = GDI32.INSTANCE;
    public static final GDI32Plus GDI_32_PLUS = GDI32Plus.INSTANCE;
    public static final Comctl32Plus COMCTL_32_PLUS = Comctl32Plus.INSTANCE;


    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * 发送按键到指定窗口，先 KeyDown 再 KeyUp
     * 要使用PostMessage。不能使用SendMessage
     * <p/>
     * # 如下不生效
     * # win32api.SendMessage(hwnd, win32con.WM_KEYDOWN, win32con.VK_F1, 0)
     * # win32api.SendMessage(hwnd, win32con.WM_KEYUP, win32con.VK_F1, 0)
     *
     * @param hwnd hwnd
     * @param vk   Win32Const.VK_*
     */
    public static synchronized void SendKeyToWindow(HWND hwnd, int vk) {
        SendKeyDownToWindow(hwnd, vk);
        sleep(300);
        SendKeyUpToWindow(hwnd, vk);
    }

    public static synchronized void SendKeyDownToWindow(HWND hwnd, int vk) {
        WinDef.WPARAM wParam = new WinDef.WPARAM(vk);
        WinDef.LPARAM lParam = Win32Const.LPARAM_ZERO;
        USER_32.PostMessage(hwnd, WinUser.WM_KEYDOWN, wParam, lParam);
    }
    public static synchronized void SendKeyUpToWindow(HWND hwnd, int vk) {
        WinDef.WPARAM wParam = new WinDef.WPARAM(vk);
        WinDef.LPARAM lParam = Win32Const.LPARAM_ZERO;
        USER_32.PostMessage(hwnd, WinUser.WM_KEYUP, wParam, lParam);
    }

    /**
     * 传入控件所在对话框中的x,y可以触发点击操作
     * @param hwnd hwnd
     */
    public static synchronized void clickWindow(HWND hwnd, int xPos, int yPos) {
        WinDef.DWORD dword = Win32Utils.makeDwordFromPosition(xPos, yPos);
        // 鼠标左键按下
        USER_32.SendMessage(hwnd, Win32Const.WM_LBUTTONDOWN, Win32Const.WPARAM_MK_LBUTTON, new WinDef.LPARAM(dword.longValue()));
        sleep(20);
        // 鼠标左键抬起
//        USER_32.SendMessage(hwnd, Win32Const.WM_LBUTTONUP, Win32Const.WPARAM_MK_LBUTTON, new WinDef.LPARAM(dword.longValue()));
        USER_32.SendMessage(hwnd, Win32Const.WM_LBUTTONUP, Win32Const.WPARAM_ZERO, new WinDef.LPARAM(dword.longValue()));
    }

    /**
     * 往输入框中设置文本
     * @param hwnd hwnd
     * @param text text
     */
    public static synchronized void SetTextToEditCtrl(HWND hwnd, String text) {
        LPARAM lparamForEmpty = new LPARAM(toNativeValue(stringToPointer2("")));
        USER_32.SendMessage(hwnd, Win32Const.WM_SETTEXT, Win32Const.WPARAM_ZERO, lparamForEmpty);
        sleep(300);
        LPARAM lparamForText = new LPARAM(toNativeValue(stringToPointer2(text)));
        USER_32.SendMessage(hwnd, Win32Const.WM_SETTEXT, Win32Const.WPARAM_ZERO, lparamForText);
        System.out.println("set text " + text + " in window " + hex(hwnd));
    }

    /**
     * 发送按键到当前窗口，先 KeyDown 再 KeyUp
     *
     * @param vk Win32Const.VK_*
     */
    public static synchronized void SendKeyToActiveWindow(int vk) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);

        // Press
        input.input.ki.wVk = new WinDef.WORD((vk));
        input.input.ki.dwFlags = new WinDef.DWORD(0);  // keydown

        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

        sleep(300);

        // Release
        input.input.ki.wVk = new WinDef.WORD((vk));
        input.input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP);  // keyup

        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    /**
     * 点击按钮
     * @param hDlg hDlg
     * @param nIDDlgItem nIDDlgItem
     */
    public static void clickButton(HWND hDlg, int nIDDlgItem) {
        // 点击前先激活
        Win32Utils.USER_32_PLUS.SetActiveWindow(hDlg);
        Win32Utils.USER_32.SetFocus(hDlg);
        USER_32_PLUS.SendDlgItemMessageA(hDlg, nIDDlgItem, new UINT(Win32Const.BM_CLICK), Win32Const.WPARAM_ZERO, Win32Const.LPARAM_ZERO);
    }

    /**
     * 点击按钮.
     *
     * 注意：该方法需要查询按钮的“控件id”和“父窗口句柄”。
     *
     * @param hBtn hBtn
     */
    public static void clickButton(HWND hBtn) {
        int ctrlID = USER_32_PLUS.GetDlgCtrlID(hBtn);
        if (0 == ctrlID) {
            return;
        }
        clickButton(USER_32.GetParent(hBtn), ctrlID);
    }

    public static int getLastError() {
        return KERNEL_32.GetLastError();
    }

    private static class CallbackForEnumWindows implements WinUser.WNDENUMPROC {
        private final List<EnumWindowVo> enumWindowList = new ArrayList<>();

        @Override
        public boolean callback(HWND hWnd, Pointer data) {
            EnumWindowVo enumWindowVo = new EnumWindowVo();
            enumWindowVo.setWindowText(WindowUtils.getWindowTitle(hWnd));
            enumWindowVo.setClassName(Win32Utils.USER_32_PLUS.GetClassName2(hWnd));
            enumWindowVo.setHwnd(hWnd);
            enumWindowVo.setAddr(Pointer.nativeValue(hWnd.getPointer()));
            enumWindowVo.setHexAddr(hexLong(enumWindowVo.getAddr()));
            enumWindowList.add(enumWindowVo);
            return true;
        }

        public List<EnumWindowVo> getEnumWindowList() {
            return enumWindowList;
        }
    }

    private Win32Utils() {

    }

    public static List<EnumWindowVo> enumChildWindows(HWND parent) {
        CallbackForEnumWindows callbackForEnumWindows = new CallbackForEnumWindows();
        USER_32.EnumChildWindows(parent, callbackForEnumWindows, null);
        return callbackForEnumWindows.getEnumWindowList();
    }

    public static List<EnumWindowVo> enumWindows() {
        return enumChildWindows(null);
    }

    private static final Charset DEFAULT_CHARSET = StandardCharsets.US_ASCII;

    public static Pointer stringToPointer(String string) {
        // WARNING: assumes ascii-only string
        int size = string.getBytes(DEFAULT_CHARSET).length + 1;
        System.out.println("> stringToPointer " + string + " cost " + size + " bytes");
        Pointer p = new Memory(size);
        p.setString(0, string, DEFAULT_CHARSET.name());
        return p;
    }
    public static Pointer stringToPointer2(String string) {
//        int size = string.getBytes(DEFAULT_CHARSET).length + 1;
        long size = ((long) Native.WCHAR_SIZE) * (string.length() + 1);
//        System.out.println("> stringToPointer2 " + string + " cost " + size + " bytes");
        Pointer m = new Memory(size);
        m.setWideString(0, string);
        return m;
    }


    public static String toNativeString(char[] chars, int length) {
        return Native.toString(Arrays.copyOfRange(chars, 0, length));
    }

    public static long toNativeValue(Pointer pointer) {
        return Pointer.nativeValue(pointer);
    }

    public static long toNativeValue(PointerType pointerType) {
        return toNativeValue(pointerType.getPointer());
    }

    /**
     * 方便调用者使用
     *
     * @param pointerType pointerType
     * @return 类似 0xa23d 的字符串
     */
    public static String hex(PointerType pointerType) {
        return hexPointer(pointerType);
    }

    public static String hexPointer(PointerType pointerType) {
        return hexLong(toNativeValue(pointerType));
    }

    /**
     * 方便调用者使用
     *
     * @param nativeValue nativeValue
     * @return 类似 0xa23d 的字符串
     */
    public static String hex(long nativeValue) {
        return hexLong(nativeValue);
    }

    public static String hexLong(long nativeValue) {
        return "0x" + Long.toHexString(nativeValue);
    }

    // 定义在 Winsock2.h中
    // c/c++中的WORD要对应java中的DWORD. WORD类中没有high/low属性，而DWORD有
    public static DWORD MAKEDWORD(int high, int low) {
        int n = ((high & 0x0000ffff) << 16) + ((low & 0x0000ffff));
//        int a = ((int) (low & 0x000000ff)) |  (((int) ((byte) high)) << 8);
        return new DWORD(n);
    }

    public static DWORD makeDword(int high, int low) {
        return MAKEDWORD(high, low);
    }

    /**
     * @param x x坐标
     * @param y y坐标
     */
    public static DWORD makeDwordFromPosition(int x, int y) {
        return MAKEDWORD(y, x);
    }



}
