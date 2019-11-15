import logging
import os
# 日志保存在当前项目位置下
filename = os.path.join(os.path.abspath(os.path.join(os.getcwd(), "..")), 'cv.log')


def init_logger():
    # 输出到控制台
    # 默认为root
    # logger = logging.getLogger()
    # formatter = logging.Formatter(
    #     fmt='%(asctime)s => [%(levelname)s]<%(name)s>: %(message)s',
    #     datefmt="[%Y-%m-%d %H:%M:%S]"
    # )
    # handler = logging.StreamHandler()
    # handler.setFormatter(formatter)
    # logger.addHandler(handler)
    # logger.setLevel(logging.DEBUG)

    # 输出到日志
    logging.basicConfig(level=logging.DEBUG,
                        filename=filename,
                        filemode='a',
                        format='%(asctime)s => [%(levelname)s]<%(name)s>: %(message)s',
                        datefmt='[%Y-%m-%d %H:%M:%S]',
                        )


init_logger()


def get_logger(name):
    """
    :param name: 调用方法名
    :return:
    """
    logger = logging.getLogger(name)
    return logger
