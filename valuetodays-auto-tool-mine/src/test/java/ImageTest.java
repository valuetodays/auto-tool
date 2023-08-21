import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-08-21
 */
public class ImageTest {
    @Test
    public void a() throws IOException {
        String dir = "X:/mine-for-check/3";
        Collection<File> files = FileUtils.listFiles(new File(dir), null, false);
        for (File file : files) {
            BufferedImage bi = ImageIO.read(file);
            Integer posX = 10;
            Integer posY = 3;
            int rgb = bi.getRGB(posX, posY);
            Color color = new Color(rgb, false);
            System.out.println("x=" + posX + ", y=" + posY + ", rgb=" + color);
//            rgb = bi.getRGB(bi.getWidth() / 2, bi.getHeight() / 2);
//            System.out.println("x=" + bi.getWidth() / 2 + ", y=" + bi.getHeight() / 2 + ", rgb=" + rgb);
        }
    }
}
