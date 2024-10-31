package com.sjhy.plugin.tool;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 添加字符串工具类，为了兼容JB的各种产品，尽量不要用第三方工具包
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/08/07 11:52
 */
@SuppressWarnings("WeakerAccess")
public class StringUtils {

    /**
     * 首字母处理方法
     */
    private static final BiFunction<String, Function<Integer, Integer>, String> FIRST_CHAR_HANDLER_FUN = (str, firstCharFun) -> {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = firstCharFun.apply(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        // cannot be longer than the char array
        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        // copy the first codepoint
        newCodePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            // copy the remaining ones
            newCodePoints[outOffset++] = codepoint;
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    };

    /**
     * 判断是空字符串
     *
     * @param cs 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 首字母大写方法
     *
     * @param str 字符串
     * @return 首字母大写结果
     */
    public static String capitalize(final String str) {
        return FIRST_CHAR_HANDLER_FUN.apply(str, Character::toTitleCase);
    }

    /**
     * 首字母小写方法
     *
     * @param str 字符串
     * @return 首字母小写结果
     */
    public static String uncapitalize(final String str) {
        return FIRST_CHAR_HANDLER_FUN.apply(str, Character::toLowerCase);
    }

    /**
     * 将小驼峰命名转换为大驼峰命名
     *
     * @param lowerCamel 小驼峰命名的字符串
     * @return 转换后的大驼峰命名字符串
     */
    public static String lowerToUpperCamelCase(String lowerCamel) {
        if (lowerCamel == null || lowerCamel.isEmpty()) {
            return lowerCamel;
        }

        // 使用 StringBuilder 构建转换后的字符串
        StringBuilder result = new StringBuilder();
        boolean isNextUpperCase = true;

        // 遍历小驼峰命名字符串中的每个字符
        for (char c : lowerCamel.toCharArray()) {
            if (isNextUpperCase) {
                result.append(Character.toUpperCase(c));
                isNextUpperCase = false;
            } else {
                result.append(c);
            }

            // 如果当前字符是小写字母并且下一个字符是大写字母，则下一个字符应为大写
            if (Character.isLowerCase(c) && Character.isUpperCase(c)) {
                isNextUpperCase = true;
            }
        }

        return result.toString();
    }

    /**
     * 将大驼峰命名转换为小驼峰命名
     *
     * @param upperCamel 大驼峰命名的字符串
     * @return 转换后的小驼峰命名字符串
     */
    public static String upperToLowerCamelCase(String upperCamel) {
        if (upperCamel == null || upperCamel.isEmpty()) {
            return upperCamel;
        }

        // 使用 StringBuilder 构建转换后的字符串
        StringBuilder result = new StringBuilder();
        boolean isNextLowerCase = false;

        // 遍历大驼峰命名字符串中的每个字符
        for (char c : upperCamel.toCharArray()) {
            if (isNextLowerCase) {
                result.append(Character.toLowerCase(c));
                isNextLowerCase = false;
            } else {
                result.append(c);
            }

            // 如果当前字符是大写字母并且下一个字符是大写字母，则下一个字符应为小写
            if (Character.isUpperCase(c) && !Character.isUpperCase(result.charAt(result.length() - 1))) {
                isNextLowerCase = true;
            }
        }

        return result.toString();
    }


    /**
     * 将小驼峰命名转换为下划线大写命名
     *
     * @param lowerCamel 小驼峰命名的字符串
     * @return 转换后的下划线大写命名字符串
     */
    public static String lowerCamelToSnakeCase(String lowerCamel) {
        if (lowerCamel == null || lowerCamel.isEmpty()) {
            return lowerCamel;
        }

        // 使用 StringBuilder 构建转换后的字符串
        StringBuilder result = new StringBuilder();

        // 遍历小驼峰命名字符串中的每个字符
        for (int i = 0; i < lowerCamel.length(); i++) {
            char c = lowerCamel.charAt(i);

            // 如果字符是大写字母
            if (Character.isUpperCase(c)) {
                // 在大写字母前插入下划线
                if (i > 0) {
                    result.append('_');
                }
                // 将大写字母转换为小写
                result.append(Character.toLowerCase(c));
            } else {
                // 其他字符直接追加
                result.append(c);
            }
        }

        // 将最终结果转换为大写字母
        return result.toString().toUpperCase();
    }

    /**
     * 将大驼峰命名转换为下划线大写命名
     *
     * @param upperCamel 大驼峰命名的字符串
     * @return 转换后的下划线大写命名字符串
     */
    public static String upperCamelToSnakeCase(String upperCamel) {
        if (upperCamel == null || upperCamel.isEmpty()) {
            return upperCamel;
        }

        // 使用 StringBuilder 构建转换后的字符串
        StringBuilder result = new StringBuilder();

        // 遍历大驼峰命名字符串中的每个字符
        for (int i = 0; i < upperCamel.length(); i++) {
            char c = upperCamel.charAt(i);

            // 如果字符是大写字母
            if (Character.isUpperCase(c)) {
                // 在大写字母前插入下划线
                if (i > 0) {
                    result.append('_');
                }
                // 将大写字母转换为小写
                result.append(Character.toLowerCase(c));
            } else {
                // 其他字符直接追加
                result.append(c);
            }
        }

        // 将最终结果转换为大写字母
        return result.toString().toUpperCase();
    }

    /**
     * 将表名转换为简短别名
     *
     * @param tableName 表名，例如：user_info
     * @return 简短别名，例如：ui
     */
    public static String generateShortAlias(String tableName) {
        // 分割表名中的单词
        String[] words = tableName.split("_");
        StringBuilder shortAlias = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // 取每个单词的首字母
                shortAlias.append(word.charAt(0));
            }
        }

        return shortAlias.toString().toLowerCase();
    }
}
