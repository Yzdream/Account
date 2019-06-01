package com.yz.data.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.yz.data.bean.AccountType;


import java.util.List;

public class AccountTypeDao {

    public static boolean saveAccountType(AccountType accountType) {
        ModelAdapter<AccountType> modelAdapter = FlowManager.getModelAdapter(AccountType.class);
        return modelAdapter.save(accountType);
    }

    public static boolean delAccountType(AccountType accountType) {
        ModelAdapter<AccountType> modelAdapter = FlowManager.getModelAdapter(AccountType.class);
        return modelAdapter.delete(accountType);
    }

    public static List<AccountType> findAll(){
        return SQLite.select().from(AccountType.class).queryList();
    }

}
