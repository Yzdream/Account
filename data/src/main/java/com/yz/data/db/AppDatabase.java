package com.yz.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    // 数据库名称
    public static final String NAME = "AppDatabase_account";

    // 数据版本
    public static final int VERSION = 2;
}
