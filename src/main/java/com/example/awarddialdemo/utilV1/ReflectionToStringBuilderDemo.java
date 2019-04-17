package com.example.awarddialdemo.utilV1;


import com.example.awarddialdemo.entity.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 适合打印日志是输出参数信息
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-16 17:29
 * @return
 */
public class ReflectionToStringBuilderDemo {
    public static void main(String[] args) {
        Integer num = 1;
        User user = new User();
        user.setUserName("usernameValue");
        user.setPassword("passwordValue");
//        对象及属性一行显示
        System.out.println("num:" + ReflectionToStringBuilder.toString(num));
        System.out.println("user:" + ReflectionToStringBuilder.toString(user));
        System.out.println("user_default_value:" + ReflectionToStringBuilder.toString(user, ToStringStyle.DEFAULT_STYLE));
//       属性换行显示
        System.out.println("num_multi_line:" + ReflectionToStringBuilder.toString(num,ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("user_multi_line:" + ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
//      不显示属性名只显示属性值，同一行显示
        System.out.println("user_no_filed:" + ReflectionToStringBuilder.toString(user,ToStringStyle.NO_FIELD_NAMES_STYLE));
//        对象名称简写
        System.out.println("user_short_prefix:" + ReflectionToStringBuilder.toString(user,ToStringStyle.SHORT_PREFIX_STYLE));
//        只显示属性
        System.out.println("user_simple_style:" + ReflectionToStringBuilder.toString(user,ToStringStyle.SIMPLE_STYLE));
    }
}
