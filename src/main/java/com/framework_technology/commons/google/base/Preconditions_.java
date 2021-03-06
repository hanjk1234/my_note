package com.framework_technology.commons.google.base;


import com.google.common.collect.Lists;

import static com.google.common.base.Preconditions.*;

/**
 * 前置条件校验
 *
 * @author wei.Li by 14-8-27.
 */
public class Preconditions_ {

    private static void aVoid() {
        String s = "aa";

        //参数校验
        checkArgument(false, "%s should be a type variable.", s);

        //对象为空检验
        checkNotNull(null, "%s is null.", null);

        //状态检测
        checkState(false, "%s is false.", "state");

        //元素属性检验 if (index < 0 || index >= size) { thow Exception}
        checkElementIndex(3, Lists.newArrayList("aa", "bb").size());

        //位置检验，是否越界
        checkPositionIndex(3, Lists.newArrayList("aa", "bb").size());
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            try {
                aVoid();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
