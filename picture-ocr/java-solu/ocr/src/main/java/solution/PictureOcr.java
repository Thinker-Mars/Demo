package solution;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static solution.DT.DictOfOcr.*;

/**
 * 验证码处理
 * @Author Cone
 * @Date 2019/9/26 9:46
 */
public class PictureOcr {

    /**
     * 图片去噪点
     * @param picPath
     * @return
     * @throws IOException
     */
    public static void removeBackground(String picPath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(picPath));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isWrite(bufferedImage.getRGB(x, y)) == 1) {
                    bufferedImage.setRGB(x, y, Color.white.getRGB());
                } else {
                    bufferedImage.setRGB(x, y, Color.black.getRGB());
                }
            }
        }
        ImageIO.write(bufferedImage, picType, new File(picPath));
    }

    /**
     * 如果某个像素的三原色值大于所设定的阈值，就将此像素设为白色，即为背景
     * @param colorInt
     * @return
     */
    public static int isWrite(int colorInt) {

        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > DT.DictOfOcr.threshold) {
            return 1;
        }
        return 0;
    }

    /**
     * 裁剪边角
     * @param picPath
     * @throws IOException
     */
    public static void cutPic(String picPath) throws IOException {

        BufferedImage bufferedimage=ImageIO.read(new File(picPath));
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();


        bufferedimage = cropPic(bufferedimage, (cutWidth / 2),0, (width - cutWidth / 2), height);
        bufferedimage = cropPic(bufferedimage,0, (cutHeight / 2),(width - cutWidth), (height - cutHeight / 2));
        ImageIO.write(bufferedimage, picType, new File(picPath));
    }

    /**
     * 根据参数裁剪图片
     * @param bufferedImage
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public static BufferedImage cropPic(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 执行Ocr识别
     * @param picPath
     * @return
     * @throws TesseractException
     */
    public static String executeOcr(String picPath) throws TesseractException {

        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath(tessdataPath);
        //iTesseract.setLanguage("eng");
        //可根据需要引入相关的训练集
        String ocrResult = iTesseract.doOCR(new File(picPath));
        return ocrResult;
    }

    /**
     * 转换图片格式，jpg => png
     * @param picPath
     */
    public static void convertJpgToPng(String picPath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(picPath));
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(newBufferedImage,"png",new File(picPath));
    }
}
