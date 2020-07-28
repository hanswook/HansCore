package com.mckj.tec_library.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    private static final String ZERO_STR = "0";

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    public static double mul(double value1, int value2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    public static String mulStr(double value1, int value2) {

        return String.valueOf(mul(value1, value2));
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     */
    public static double div(double value1, double value2, int scale) {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            return -1;
        }
        if (value2 <= 0) {
            return -1;
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.divide(b2, scale, RoundingMode.FLOOR).doubleValue();
    }

    public static double div(double value1, double value2) {
        //如果精确范围小于0，抛出异常信息
        if (value1 <= 0) {
            return -1;
        }
        if (value2 <= 0) {
            return -1;
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.divide(b2).doubleValue();
    }


    public static String showStr(double value) {
        return new BigDecimal(String.valueOf(value)).toPlainString();
    }

    public static String showNoZeroStr(double value) {
        //0.0这种等于0的含有多位的小数。没办法进行去尾0操作。单独判断。如果==0 就返回字符串"0"
        if (new BigDecimal(value).compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_STR;
        } else {
            return BigDecimalUtils.showNoZeroTwoFloorStr(value);
        }
    }

    /**
     * 满减活动向下取整拼接
     *
     * @param value
     * @return
     */
    public static String showDiscountStr(double value) {
        return showFloorStr(value, 0);
    }

    /**
     * 满减活动向下取整拼接
     *
     * @param value
     * @return
     */
    public static String showNoZeroDiscountStr(double value) {
        return showNoZeroFloorStr(value, 0);
    }

    /**
     * 满减活动向下取整 1位小数
     *
     * @param value
     * @return
     */
    public static String showOneFloorStr(double value) {
        return showFloorStr(value, 1);
    }

    /**
     * 满减活动向下取整 1位小数
     *
     * @param value
     * @return
     */
    public static String showNoZeroOneFloorStr(double value) {

        //0.0这种等于0的含有多位的小数。没办法进行去尾0操作。单独判断。如果==0 就返回字符串"0"
        if (new BigDecimal(value).compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_STR;
        } else {
            return showNoZeroFloorStr(value, 1);
        }
    }


    /**
     * 满减活动向下取整 2位小数
     *
     * @param value
     * @return
     */
    public static String showNoZeroTwoFloorStr(double value) {

        //0.0这种等于0的含有多位的小数。没办法进行去尾0操作。单独判断。如果==0 就返回字符串"0"
        if (new BigDecimal(value).compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_STR;
        } else {
            return showNoZeroFloorStr(value, 2);
        }
    }

    /**
     * 满减活动向下取整 2位小数
     *
     * @param value
     * @return
     */
    public static String showTwoFloorStr(double value) {
        return showFloorStr(value, 2);
    }

    /**
     * 向下取整拼接 0小数位
     *
     * @param value
     * @return
     */
    public static String showZeroStr(double value) {
        return showFloorStr(value, 0);
    }

    /**
     * 显示取整 保留位数。
     *
     * @param value
     * @param scale
     * @return
     */
    private static String showFloorStr(double value, int scale) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.FLOOR).toPlainString();
    }

    /****
     * 保留一个小数 四舍五入
     * @param value
     * @param scale
     * @return
     */
    public static String showZeroUpStr(double value, int scale) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.UP).toPlainString();
    }


    /**
     * 显示取整 保留位数。
     *
     * @param value
     * @param scale
     * @return
     */
    private static String showNoZeroFloorStr(double value, int scale) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.FLOOR).stripTrailingZeros().toPlainString();
    }

    /**
     * 显示取整 保留位数。
     *
     * @param value
     * @param scale
     * @return
     */
    private static double convertNoZeroFloorDouble(double value, int scale) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * 显示取整 保留位数。
     *
     * @param value
     * @return
     */
    public static double convertNoZeroTwoFloorDouble(double value) {
        return convertNoZeroFloorDouble(value,2);
    }






}
