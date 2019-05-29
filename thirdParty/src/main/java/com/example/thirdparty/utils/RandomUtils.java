package com.example.thirdparty.utils;

import java.util.*;

/**
*@author Cone
*@Version 2019年5月29日 下午12:52:44
*/
public final class RandomUtils {

	private static final String ALLCHAR
    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String LETTERCHAR
    = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String NUMBERCHAR = "0123456789";
	
	/**
	 * 生成指定范围内的随机数
	 * @param scopeMin
	 * @param scoeMax
	 * @return
	 */
	public static int integer(int scopeMin, int scoeMax) {
		Random random = new Random();
		return (random.nextInt(scoeMax) % (scoeMax - scopeMin + 1) + scopeMin);
	}
	
	/**
	 * 返回固定长度的数字
	 * @param length
	 * @return
	 */
	public static String fixedLengthNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
        }
        return sb.toString();
    }
	
	/**
	 * 返回一个定长的随机字符串(包含大小写字母、数字)
	 * @param length
	 * @return
	 */
	public static String fixedLengthChar(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
	
	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * @param length
	 * @return
	 */
	public static String fixedLengthAlph(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }
	
	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * @param num
	 * @param length
	 * @return
	 */
	public static String generateCharByNum(long num, int length) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (length - strNum.length() >= 0) {
            sb.append(getFixedStringByZero(length - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + length + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }
	
	/**
	 * 随机返回数组中某个元素
	 * @param param
	 * @return
	 */
	public static <T> T randomItem(T[] param) {
        int index = integer(0, param.length);
        return param[index];
    }
	
	/**
	 * 32位UUID
	 * @return
	 */
	public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s.substring(0, 8) + s.substring(9, 13) +
                s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
	
	/**
	 * 根据时间生成15位随机数
	 * @return
	 */
	public static String getRandomStringByTime() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end2 = random.nextInt(99);
        return millis + String.format("%02d", end2);
    }
	
	/**
	 * 生成一个定长的由0组成的字符串
	 * @param length
	 * @return
	 */
	public static String getFixedStringByZero(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}
