package com.sjhy.plugin.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 命名工具类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class NameUtils {
    /**
     * 转驼峰命名正则匹配规则
     */
    private static final Pattern TO_HUMP_PATTERN = Pattern.compile("[-_]([a-z0-9])");
    private static final Pattern TO_LINE_PATTERN = Pattern.compile("[A-Z]+");
    private volatile static NameUtils nameUtils;

    /**
     * 私有构造方法
     */
    NameUtils() {
    }

    /**
     * 单例模式
     */
    public static NameUtils getInstance() {
        if (nameUtils == null) {
            synchronized (NameUtils.class) {
                if (nameUtils == null) {
                    nameUtils = new NameUtils();
                }
            }
        }
        return nameUtils;
    }

    /**
     * 首字母大写方法
     *
     * @param name 名称
     * @return 结果
     */
    public String firstUpperCase(String name) {
        return StringUtils.capitalize(name);
    }

    /**
     * 首字母小写方法
     *
     * @param name 名称
     * @return 结果
     */
    public String firstLowerCase(String name) {
        return StringUtils.uncapitalize(name);
    }

    /**
     * 将小驼峰命名转换为大驼峰命名
     *
     * @param name 小驼峰命名的字符串
     * @return 转换后的大驼峰命名字符串
     */
    public String lowerToUpperCamelCase(String name) {
        return StringUtils.lowerToUpperCamelCase(name);
    }

    /**
     * 将大驼峰命名转换为小驼峰命名
     *
     * @param name 大驼峰命名的字符串
     * @return 转换后的小驼峰命名字符串
     */
    public String upperToLowerCamelCase(String name) {
        return StringUtils.upperToLowerCamelCase(name);
    }


    /**
     * 将小驼峰命名转换为下划线大写命名
     *
     * @param name 小驼峰命名的字符串
     * @return 转换后的下划线大写命名字符串
     */
    public String lowerCamelToSnakeCase(String name) {
        return StringUtils.lowerCamelToSnakeCase(name);
    }

    /**
     * 将大驼峰命名转换为下划线大写命名
     *
     * @param name 大驼峰命名的字符串
     * @return 转换后的下划线大写命名字符串
     */
    public String upperCamelToSnakeCase(String name) {
        return StringUtils.upperCamelToSnakeCase(name);
    }


    /**
     * 根据给定的名称生成短别名
     * 此方法用于将输入的名称转换为更短的别名形式，通常用于简化名称或生成易于使用的标识
     *
     * @param name 需要生成别名的原始名称
     * @return 生成的短别名
     */
    public String generateShortAlias(String name) {
        return StringUtils.generateShortAlias(name);
    }

    /**
     * 驼峰转下划线，全小写
     *
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    public String hump2Underline(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = TO_LINE_PATTERN.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            if (matcher.start() > 0) {
                matcher.appendReplacement(buffer, "_" + matcher.group(0).toLowerCase());
            } else {
                matcher.appendReplacement(buffer, matcher.group(0).toLowerCase());
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 通过java全名获取类名
     *
     * @param fullName 全名
     * @return 类名
     */
    public String getClsNameByFullName(String fullName) {
        int genericIdx = fullName.indexOf('<');
        if (genericIdx == -1) {
            return fullName.substring(fullName.lastIndexOf('.') + 1);
        }
        String className = fullName.substring(0, genericIdx);
        return fullName.substring(className.lastIndexOf('.') + 1);
    }

    /**
     * 通过java全名获取类名
     *
     * @param fullName 全名
     * @return 类名
     */
    public String getClsFullNameRemoveGeneric(String fullName) {
        int genericIdx = fullName.indexOf('<');
        if (genericIdx == -1) {
            return fullName;
        }
        return fullName.substring(0, genericIdx);
    }

    /**
     * 下划线中横线命名转驼峰命名（属性名）
     *
     * @param name 名称
     * @return 结果
     */
    public String getJavaName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        // 强转全小写
        name = name.toLowerCase();
        Matcher matcher = TO_HUMP_PATTERN.matcher(name.toLowerCase());
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 下划线中横线命名转驼峰命名（类名）
     *
     * @param name 名称
     * @return 结果
     */
    public String getClassName(String name) {
        return firstUpperCase(getJavaName(name));
    }

    /**
     * 任意对象合并工具类
     *
     * @param objects 任意对象
     * @return 合并后的字符串结果
     */
    public String append(Object... objects) {

        if (objects == null || objects.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Object s : objects) {
            if (s != null) {
                builder.append(s);
            }
        }
        return builder.toString();
    }
}
