import net.sourceforge.tess4j.TesseractException;
import solution.PictureOcr;

import java.io.IOException;

/**
 * 入口
 * @Author Cone
 * @Date 2019/9/26 9:35
 */
public class Entrance {
    public static void main(String[] args) throws IOException, TesseractException {

        //验证码路径
        String picPath = "C:\\Users\\thinker\\Desktop\\2.png";
        //去噪点
        PictureOcr.removeBackground(picPath);
        //裁剪边角
        PictureOcr.cutPic(picPath);
        //识别
        String ocrResult = PictureOcr.executeOcr(picPath);
        System.out.println("识别结果为：\n" + ocrResult);

    }
}
