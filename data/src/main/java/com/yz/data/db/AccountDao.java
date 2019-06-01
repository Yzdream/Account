package com.yz.data.db;


import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.yz.data.bean.Account;
import com.yz.data.bean.Account_Table;

import java.util.List;

public class AccountDao {

    public static boolean saveAccount(Account account) {
        ModelAdapter<Account> modelAdapter = FlowManager.getModelAdapter(Account.class);
        return modelAdapter.save(account);
    }

    public static boolean delAccount(Account account) {
        ModelAdapter<Account> modelAdapter = FlowManager.getModelAdapter(Account.class);
        return modelAdapter.delete(account);
    }

    public static List<Account> findAllById(String typeId){
        return SQLite.select().from(Account.class).where(Account_Table.typeId.eq(typeId)).queryList();
    }



}
