package cn.valuetodays.autotool.common.win32;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;

import static com.sun.jna.platform.win32.WinUser.WM_USER;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-25 12:11
 */
public class Win32Const {

    public static final WPARAM WPARAM_MK_LBUTTON = new WPARAM(Win32Const.MK_LBUTTON);
    public static final WPARAM WPARAM_MK_RBUTTON = new WPARAM(Win32Const.MK_RBUTTON);
    public static final WPARAM WPARAM_ZERO = new WPARAM(0);
    public static final LPARAM LPARAM_ZERO = new LPARAM(0);

    public static final int VK_LBUTTON = 1;
    public static final int VK_RBUTTON = 2;
    public static final int VK_CANCEL = 3;
    public static final int VK_MBUTTON = 4;
    public static final int VK_BACK = 8;
    public static final int VK_TAB = 9;
    public static final int VK_CLEAR = 12;
    public static final int VK_RETURN = 13;
    public static final int VK_SHIFT = 16;
    public static final int VK_CONTROL = 17;
    public static final int VK_MENU = 18;
    public static final int VK_PAUSE = 19;
    public static final int VK_CAPITAL = 20;
    public static final int VK_KANA = 21;
    public static final int VK_HANGEUL = 21;  // old name - should be here for compatibility
    public static final int VK_HANGUL = 21;
    public static final int VK_JUNJA = 23;
    public static final int VK_FINAL = 24;
    public static final int VK_HANJA = 25;
    public static final int VK_KANJI = 25;
    public static final int VK_ESCAPE = 27;
    public static final int VK_CONVERT = 28;
    public static final int VK_NONCONVERT = 29;
    public static final int VK_ACCEPT = 30;
    public static final int VK_MODECHANGE = 31;
    public static final int VK_SPACE = 32;
    public static final int VK_PRIOR = 33;
    public static final int VK_NEXT = 34;
    public static final int VK_END = 35;
    public static final int VK_HOME = 36;
    public static final int VK_LEFT = 37;
    public static final int VK_UP = 38;
    public static final int VK_RIGHT = 39;
    public static final int VK_DOWN = 40;
    public static final int VK_SELECT = 41;
    public static final int VK_PRINT = 42;
    public static final int VK_EXECUTE = 43;
    public static final int VK_SNAPSHOT = 44;
    public static final int VK_INSERT = 45;
    public static final int VK_DELETE = 46;
    public static final int VK_HELP = 47;
    public static final int VK_LWIN = 91;
    public static final int VK_RWIN = 92;
    public static final int VK_APPS = 93;
    public static final int VK_NUMPAD0 = 96;
    public static final int VK_NUMPAD1 = 97;
    public static final int VK_NUMPAD2 = 98;
    public static final int VK_NUMPAD3 = 99;
    public static final int VK_NUMPAD4 = 100;
    public static final int VK_NUMPAD5 = 101;
    public static final int VK_NUMPAD6 = 102;
    public static final int VK_NUMPAD7 = 103;
    public static final int VK_NUMPAD8 = 104;
    public static final int VK_NUMPAD9 = 105;
    public static final int VK_MULTIPLY = 106;
    public static final int VK_ADD = 107;
    public static final int VK_SEPARATOR = 108;
    public static final int VK_SUBTRACT = 109;
    public static final int VK_DECIMAL = 110;
    public static final int VK_DIVIDE = 111;
    public static final int VK_F1 = 112;
    public static final int VK_F2 = 113;
    public static final int VK_F3 = 114;
    public static final int VK_F4 = 115;
    public static final int VK_F5 = 116;
    public static final int VK_F6 = 117;
    public static final int VK_F7 = 118;
    public static final int VK_F8 = 119;
    public static final int VK_F9 = 120;
    public static final int VK_F10 = 121;
    public static final int VK_F11 = 122;
    public static final int VK_F12 = 123;
    public static final int VK_F13 = 124;
    public static final int VK_F14 = 125;
    public static final int VK_F15 = 126;
    public static final int VK_F16 = 127;
    public static final int VK_F17 = 128;
    public static final int VK_F18 = 129;
    public static final int VK_F19 = 130;
    public static final int VK_F20 = 131;
    public static final int VK_F21 = 132;
    public static final int VK_F22 = 133;
    public static final int VK_F23 = 134;
    public static final int VK_F24 = 135;
    public static final int VK_NUMLOCK = 144;
    public static final int VK_SCROLL = 145;
    public static final int VK_LSHIFT = 160;
    public static final int VK_RSHIFT = 161;
    public static final int VK_LCONTROL = 162;
    public static final int VK_RCONTROL = 163;
    public static final int VK_LMENU = 164;
    public static final int VK_RMENU = 165;
    public static final int VK_PROCESSKEY = 229;
    public static final int VK_ATTN = 246;
    public static final int VK_CRSEL = 247;
    public static final int VK_EXSEL = 248;
    public static final int VK_EREOF = 249;
    public static final int VK_PLAY = 250;
    public static final int VK_ZOOM = 251;
    public static final int VK_NONAME = 252;
    public static final int VK_PA1 = 253;
    public static final int VK_OEM_CLEAR = 254;


    public static final int WM_LBUTTONDOWN = 0x201;
    public static final int WM_LBUTTONUP = 514;
    public static final int WM_LBUTTONDBLCLK = 515;
    public static final int WM_RBUTTONDOWN = 516;
    public static final int WM_RBUTTONUP = 517;
    public static final int WM_RBUTTONDBLCLK = 518;
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;
    public static final int WM_MBUTTONDBLCLK = 521;
    public static final int WM_MOUSEWHEEL = 522;
    public static final int WM_MOUSELAST = 522;
    public static final int WM_MOUSEACTIVATE = 0x0021;

    public static final int HTCLIENT = 0x1;

    public static final int MK_LBUTTON = 1;
    public static final int MK_RBUTTON = 2;
    public static final int MK_SHIFT = 4;
    public static final int MK_CONTROL = 8;
    public static final int MK_MBUTTON = 16;

    public static final int WM_SETTEXT = 12;
    public static final int WM_GETTEXT = 13;


    public static final int VK_0 = 49;
    public static final int VK_1 = VK_0 + 1;
    public static final int VK_2 = VK_1 + 1;
    public static final int VK_3 = VK_2 + 1;
    public static final int VK_4 = VK_3 + 1;
    public static final int VK_5 = VK_4 + 1;
    public static final int VK_6 = VK_5 + 1;
    public static final int VK_7 = VK_6 + 1;
    public static final int VK_8 = VK_7 + 1;
    public static final int VK_9 = VK_8 + 1;

    public static final int VK_A = 65;
    public static final int VK_B = VK_A + 1;
    public static final int VK_C = VK_B + 1;
    public static final int VK_D = VK_C + 1;
    public static final int VK_E = VK_D + 1;
    public static final int VK_F = VK_E + 1;
    public static final int VK_G = VK_F + 1;
    public static final int VK_H = VK_G + 1;
    public static final int VK_I = VK_H + 1;
    public static final int VK_J = VK_I + 1;
    public static final int VK_K = VK_J + 1;
    public static final int VK_L = VK_K + 1;
    public static final int VK_M = VK_L + 1;
    public static final int VK_N = VK_M + 1;
    public static final int VK_O = VK_N + 1;
    public static final int VK_P = VK_O + 1;
    public static final int VK_Q = VK_P + 1;
    public static final int VK_R = VK_Q + 1;
    public static final int VK_S = VK_R + 1;
    public static final int VK_T = VK_S + 1;
    public static final int VK_U = VK_T + 1;
    public static final int VK_V = VK_U + 1;
    public static final int VK_W = VK_V + 1;
    public static final int VK_X = VK_W + 1;
    public static final int VK_Y = VK_X + 1;
    public static final int VK_Z = VK_Y + 1;


    /*
    key_map = {
        "0": 49, "1": 50, "2": 51, "3": 52, "4": 53, "5": 54, "6": 55, "7": 56, "8": 57, "9": 58,
            'F1': 112, 'F2': 113, 'F3': 114, 'F4': 115, 'F5': 116, 'F6': 117, 'F7': 118, 'F8': 119,
            'F9': 120, 'F10': 121, 'F11': 122, 'F12': 123, 'F13': 124, 'F14': 125, 'F15': 126, 'F16': 127,
            "A": 65, "B": 66, "C": 67, "D": 68, "E": 69, "F": 70, "G": 71, "H": 72, "I": 73, "J": 74,
            "K": 75, "L": 76, "M": 77, "N": 78, "O": 79, "P": 80, "Q": 81, "R": 82, "S": 83, "T": 84,
            "U": 85, "V": 86, "W": 87, "X": 88, "Y": 89, "Z": 90,
            'BACKSPACE': 8, 'TAB': 9, 'TABLE': 9, 'CLEAR': 12,
            'ENTER': 13, 'SHIFT': 16, 'CTRL': 17,
            'CONTROL': 17, 'ALT': 18, 'ALTER': 18, 'PAUSE': 19, 'BREAK': 19, 'CAPSLK': 20, 'CAPSLOCK': 20, 'ESC': 27,
            'SPACE': 32, 'SPACEBAR': 32, 'PGUP': 33, 'PAGEUP': 33, 'PGDN': 34, 'PAGEDOWN': 34, 'END': 35, 'HOME': 36,
            'LEFT': 37, 'UP': 38, 'RIGHT': 39, 'DOWN': 40, 'SELECT': 41, 'PRTSC': 42, 'PRINTSCREEN': 42, 'SYSRQ': 42,
            'SYSTEMREQUEST': 42, 'EXECUTE': 43, 'SNAPSHOT': 44, 'INSERT': 45, 'DELETE': 46, 'HELP': 47, 'WIN': 91,
            'WINDOWS': 91, 'NMLK': 144,
            'NUMLK': 144, 'NUMLOCK': 144, 'SCRLK': 145
    }

        public static void main(String[] args) {
        char c = 'A';
        while (c <= 'Z') {
            char cur = c;
            c++;
            System.out.println("public static final int VK_"+c+" = VK_"+cur+" + 1;");
        }
    }
    */

    public static final int BM_CLICK = 0x00F5;
    public static final int TB_GETBUTTON = (WM_USER + 23);
    public static final int TB_BUTTONCOUNT = (WM_USER + 24);

    public static final int LVM_FIRST = 0x1000;      // ListView messages
    public static final int TV_FIRST = 0x1100;      // TreeView messages
    public static final int TVM_GETCOUNT = (TV_FIRST + 5);
    public static final int TVM_GETNEXTITEM = (TV_FIRST + 10);
    public static final int TVM_SELECTITEM = (TV_FIRST + 11);
    public static final int TVM_GETITEMA = (TV_FIRST + 12);
    public static final int TVM_GETITEMW = (TV_FIRST + 62);
    // UNICODE
    public static final int TVM_GETITEM = TVM_GETITEMW;

    public static final int TVIF_TEXT               = 0x0001;
    public static final int TVIF_IMAGE              = 0x0002;
    public static final int TVIF_PARAM              = 0x0004;
    public static final int TVIF_STATE              = 0x0008;
    public static final int TVIF_HANDLE             = 0x0010;
    public static final int TVIF_SELECTEDIMAGE      = 0x0020;
    public static final int TVIF_CHILDREN           = 0x0040;
    public static final int TVIF_INTEGRAL           = 0x0080;

    public static final int I_CHILDRENAUTO = (-2);


    public static final int TVGN_ROOT = 0x0000;
    public static final int TVGN_NEXT = 0x0001;
    public static final int TVGN_PREVIOUS = 0x0002;
    public static final int TVGN_PARENT = 0x0003;
    public static final int TVGN_CHILD = 0x0004;
    public static final int TVGN_FIRSTVISIBLE = 0x0005;
    public static final int TVGN_NEXTVISIBLE = 0x0006;
    public static final int TVGN_PREVIOUSVISIBLE = 0x0007;
    public static final int TVGN_DROPHILITE = 0x0008;
    public static final int TVGN_CARET = 0x0009;
    public static final int TVGN_LASTVISIBLE = 0x000A;

    public static final int WM_PARENTNOTIFY        =     0x0210;

}

