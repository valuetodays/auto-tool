package cn.valuetodays.autotool.common.ocr;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * 识别图片中的文字.
 *
 * @author lei.liu
 * @since 2023-05-30 14:33
 */
@Slf4j
public final class TesseractOcrUtils {
    private TesseractOcrUtils() {
    }

    public static Tesseract build(String datapath) {
        Tesseract tesseract = new Tesseract();
        // Tesseract-OCR安装目录下的tessdata
        tesseract.setDatapath(datapath);
        return tesseract;
    }

    /**
     * 建议自行new Tesseract()对象来调用里面的方法
     */
    public static String doOcr(String datapath, File imageFile) {
        Tesseract tesseract = build(datapath);
        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            log.error("error when doOcr()", e);
        }
        return null;
    }

}
