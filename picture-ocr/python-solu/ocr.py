import requests
import time
import sys
import os
import numpy as np
from io import BytesIO
from PIL import Image
from collections import defaultdict


# 获取验证码的网址
CAPT_URL = 'xxx.com'

# 验证码的保存路径
CAPT_PATH = r'C:\Users\thinker\Desktop\picture'
if not os.path.exists(CAPT_PATH):
    os.mkdir(CAPT_PATH)


def capt_fetch():
    """
    从网站获取验证码，将验证码转为Image对象
    :return: capt Image
    """
    # 下载验证码
    image = requests.get(CAPT_URL)
    #  将二进制的验证码图片写入IO流
    f = BytesIO(image.content)
    # 将验证码转换为Image对象
    capt = Image.open(f)

    return capt


def get_threshold(capt):
    """
    获取一张图片中，像素出现次数最多的像素，作为阈值
    :param capt:
    :return:
    """
    pixel_dict = defaultdict(int)
    # 取得图片长、宽
    rows, cols = capt.size
    for i in range(rows):
        for j in range(cols):
            # 取得这一点的(r,g,b)
            pixel = capt.getpixel((i, j))
            # 以像素做key，出现的次数做value
            pixel_dict[pixel] += 1
    # 取得字典中像素出现最多的次数
    count_max = max(pixel_dict.values())
    # 反转字典，改为以出现次数做key，方便后面取得像素
    pixel_dict_reverse = {v: k for k, v in pixel_dict.items()}
    # 取得出现次数最多的像素
    threshold = pixel_dict_reverse[count_max]
    return threshold


def get_bin_table(threshold):
    """
    按照阈值进行二值化处理
    :param threshold:
    :return:
    """
    table = []
    rate = 0.1
    for i in range(256):
        if threshold * (1 - rate) <= i <= threshold * (1 + rate):
            table.append(1)
        else:
            table.append(0)
    return table


def capt_download():
    """
    下载验证码保存到本地
    :return:
    """
    capt = capt_fetch()
    capt.show()

    text = input("请输入验证码中的字符：")
    # 时间戳做前缀以区分图片
    suffix = str(int(time.time() * 1e3))

    capt.save(CAPT_PATH + "/" + text + "_" + suffix + ".jpg")


def capt_process(capt):
    """
    图像预处理，将验证码图片转为二值型图片，按字符切割
    :param capt: image
    :return: 一个数组包含四个元素，每个元素是一张包含单个字符的二值型图片
    """
    # 转为灰度图
    capt_gray = capt.convert("L")
    # 取得图片阈值
    threshold = get_threshold(capt_gray)
    # 二值化图片
    table = get_bin_table(threshold)
    capt_bw = capt_gray.point(table, "1")
    capt_per_char_list = []
    for i in range(4):
        x = 5 + i * 15
        y = 2
        capt_per_char = capt_bw.crop((x, y, x + 13, y + 24))
        capt_per_char_list.append(capt_per_char)

    return capt_per_char_list


def capt_inference(capt_per_char):
    """
    提取图像特征
    :param capt_per_char: 由单个字符组成的二值型图片
    :return: 包含 capt_per_char中字符的特征
    """
    char_array = np.array(capt_per_char)
    total_pixels = np.sum(char_array)
    cols_pixels = np.sum(char_array, 0)
    rows_pixels = np.sum(char_array, 1)
    char_features = np.append(cols_pixels, rows_pixels)
    char_features = np.append(total_pixels, char_features)
    return char_features.tolist()


def train():
    """
    将预分类的验证码图片集转化为字符特征训练集
    :return: train_labels 验证码字符预分类结果
    :return: train_table  验证码字符特征训练集
    """
    files = os.listdir(CAPT_PATH)

    train_table = []
    train_labels = []
    for f in files:
        # 取出验证码的实际值存入train_labels
        train_labels += list(f.split("_")[0])
        capt = Image.open(CAPT_PATH + "/" + f)
        capt_per_char_list = capt_process(capt)
        for capt_per_char in capt_per_char_list:
            char_features = capt_inference(capt_per_char)
            train_table.append(char_features)

    return train_table, train_labels


def nnc(train_table, test_vec, train_labels):
    """
    根据已知特征矩阵的分类情况，预测未分类的特征向量所属类别
    :param train_table: 预分类的特征矩阵
    :param test_vec: 特征向量， 长度必须与矩阵的列数相等
    :param train_labels: 特征矩阵的类别向量
    :return:
    """
    dist_mat = np.square(np.subtract(train_table, test_vec))
    dist_vec = np.sum(dist_mat, axis=1)
    pos = np.argmin(dist_vec)

    return train_labels[pos]


# def entrance():
#     """
#     方法入口，验证识别效果
#     :return:
#     """
#     test_labels = []
#     # 获取验证码
#     capt = capt_fetch()
#     # 训练
#     train_table, train_labels = train()
#     capt_per_char_list = capt_process(capt)
#     for capt_per_char in capt_per_char_list:
#         char_features = capt_inference(capt_per_char)
#         label = nnc(train_table, char_features, train_labels)
#         test_labels.append(label)
#
#     test_labels = "".join(test_labels)
#     return capt, test_labels


def my_split(capt):
    """
    修改 x 与 y ，查看切割图片
    :param capt: 验证码Image
    :return:
    """
    capt_gray = capt.convert("L")
    # 取得图片阈值
    threshold = get_threshold(capt_gray)
    # 二值化图片
    table = get_bin_table(threshold)
    capt_bw = capt_gray.point(table, "1")
    x = 50
    y = 2
    capt_per_char = capt_bw.crop((x, y, x + 13, y + 24))
    capt_per_char.show()


def read_txt(dir):
    lines = []
    with open(dir, 'r') as f:
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip('\n')
            lines.append(line)
    return lines


# 本地测试
# capt, test_labels = entrance()
# capt.show()
# print(test_labels)

# 下载验证码
# for i in range(200):
#     capt_download()

# 本地测试字符分割
# path = r'C:\Users\thinker\Desktop\1.jpg'
# img = Image.open(path)
# my_split(img)


# 参考修改方法
def entrance():
    """
    :param capt: 待识别验证码路径
    :return: 识别出的验证码
    """
    test_labels = []
    capt = Image.open(sys.argv[1])
    # 训练
    train_table, train_labels = train()
    capt_per_char_list = capt_process(capt)
    for capt_per_char in capt_per_char_list:
        char_features = capt_inference(capt_per_char)
        label = nnc(train_table, char_features, train_labels)
        test_labels.append(label)

    test_labels = "".join(test_labels)
    print(test_labels)


if __name__ == "__main__":
    entrance()



