import logging
import time
from .utils import cv
from .utils import TemplateMatching
from .utils import BRISKMatching
from .utils import SURFMatching
from .utils import TargetPos
from .utils import get_logger
from .utils import NoMatchPointError

# 超时时间
FIND_TIMEOUT = 20
# 日志
logger = get_logger(__name__)

# 匹配方法
CVSTRATEGY = ["surf", "tpl", "brisk"]
MATCHING_METHODS = {
    "tpl": TemplateMatching,
    "brisk": BRISKMatching,
    "surf": SURFMatching
}


def entrance(img_src, screen_src):
    """
    程序入口，接收两张图片的路径
    :param img_src: 待识别图像路径
    :param screen_src: 原始图像路径
    :return:
    """
    logger.info('开始识别')
    logger.info('接收到参数 {%s} {%s}' % (img_src, screen_src))
    # 待识别图像
    image = Template('check.png', img_src)
    # 原始图像
    screen = Template('origin.png', screen_src)
    logger.info('图像转换完毕')
    find(image, screen)


def find(image, screen, timeout=FIND_TIMEOUT, interval=0.5):
    """
    根据传入的图片，返回合适的坐标
    :param image:
    :param screen:
    :param timeout: 时间限制（默认20s）
    :param interval: 寻找间隔时间（默认0.5s）
    :return:
    """
    start_time = time.time()
    while True:
        if (time.time() - start_time) > timeout:
            raise NoMatchPointError('未找到目标图片坐标 {%s} ' % image)
        else:
            pos = image.match_in(screen)
            if pos:
                print(pos)
                break
            else:
                time.sleep(interval)



class Template(object):
    def __init__(self, filename, file_path, target_pos=TargetPos.MID):
        self.filename = filename
        self.filepath = file_path
        self.target_pos = target_pos

    def match_in(self, screen):
        match_result = self._cv_match(screen)
        logger.info('识别结果为：%s' % match_result)
        if not match_result:
            return None
        focus_pos = TargetPos().getXY(match_result, self.target_pos)
        return focus_pos

    def _cv_match(self, screen):
        image = self._imread()
        screen = cv.imread(screen.filepath)
        ret = None
        for method in CVSTRATEGY:
            func = MATCHING_METHODS.get(method, None)
            ret = self._try_match(func, image, screen)
            if ret:
                return ret

    def _imread(self):
        return cv.imread(self.filepath)

    @staticmethod
    def _try_match(func, *args):
        logging.info('当前识别方法为：%s' % func.__name__)
        ret = func(*args).find_best_result()
        return ret

