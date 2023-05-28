package cn.valuetodays.autotool.common.win32.dllext;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-26 16:12
 */
public interface GDI32Plus extends StdCallLibrary, WinUser, WinNT {
    GDI32Plus INSTANCE = Native.load("gdi32", GDI32Plus.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean BitBlt(WinDef.HDC hdcDest, int nXDest, int nYDest, int nWidth,
                   int nHeight, WinDef.HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                      byte[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                      short[] pixels, WinGDI.BITMAPINFO bi, int usage);

    boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                      int[] pixels, WinGDI.BITMAPINFO bi, int usage);

    int SRCCOPY = 0xCC0020;
}
