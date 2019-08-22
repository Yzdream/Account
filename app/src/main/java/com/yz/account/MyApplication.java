package com.yz.account;

import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;
import com.yz.account.base.CustomApplication;
import com.yz.account.data.SQLCipherHelperImpl;
import com.yz.account.uitls.Density;
import com.yz.account.uitls.ToastHelper;
import com.yz.data.db.AppDatabase;

public class MyApplication extends CustomApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        FlowConfig flowConfig = FlowConfig.builder(this)
                .addDatabaseConfig(new DatabaseConfig.Builder(AppDatabase.class)
                        .openHelper(SQLCipherHelperImpl::new)
                        .build())
                .build();
        FlowManager.init(flowConfig);
        Density.setDensity(this, 375);
        //吐息
        ToastHelper.initToast(this);
        if (0!=(getApplicationInfo().flags&= ApplicationInfo.FLAG_DEBUGGABLE)) {
            Log.e("DEBUG", "程序被修改为可调试状态！！！");
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
        //初始化Logger日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }
}
