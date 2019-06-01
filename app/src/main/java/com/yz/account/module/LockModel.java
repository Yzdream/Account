package com.yz.account.module;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.yz.account.base.BaseRepository;
import com.yz.data.bean.Lock;

public class LockModel extends BaseRepository {

    public boolean saveLock(Lock lock){
        deleteAll();
        ModelAdapter<Lock> modelAdapter = FlowManager.getModelAdapter(Lock.class);
        return modelAdapter.save(lock);
    }

    public Lock findLock(){
        return SQLite.select().from(Lock.class).querySingle();
    }

    public  void deleteAll(){
        SQLite.delete().from(Lock.class).execute();
    }



}
