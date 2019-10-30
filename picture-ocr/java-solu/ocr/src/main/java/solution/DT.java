package solution;


/**
 * 常量设置
 * @Author Cone
 * @Date 2019/9/26 10:39
 */
public class DT {

    public static class DictOfOcr{

        /**
         * 阈值，自定义，根据不同的图片进行设置
         */
        public static int threshold = 160;

        /**
         * 图片裁剪宽度，根据实际情况自行设置
         */
        public static int cutWidth = 2;

        /**
         * 图片裁剪高度，根据实际情况自行设置
         */
        public static int cutHeight = 2;

        /**
         * tessdata是OCR识别所需要的库，包含一些配置文件，及OCR识别所需要的训练集
         * 可根据实际情况修改此包所在的位置，然后在此修改路径即可
         * 我这里是放在了src/main/resources路径下
         * DT.class.getResource("/tessdata/").getPath();
         */
        public static String tessdataPath = "E:\\ideaproject\\ocr\\src\\main\\resources\\tessdata";

        /**
         * 图片文件格式，如png、jpg，在此处进行设置
         * PictureOcr类下有convertJpgToPng方法，可修改图片格式为png
         */
        public static String picType = "png";
    }
}
