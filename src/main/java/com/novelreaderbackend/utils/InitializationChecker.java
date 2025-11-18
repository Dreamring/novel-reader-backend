package com.novelreaderbackend.utils;

import java.io.File;

/**
 * 初始化检查工匠类
 */
public class InitializationChecker {
    public static void check() {
        //检查数据目录是否创建
        File dbFile = new File(System.getProperty("user.home") + "/.myapp");
        dbFile.getParentFile().mkdirs(); // 确保目录存在
    }
}
