package com.yz.account.module;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.yz.account.base.BaseRepository;
import com.yz.data.bean.Lock;
import com.yz.data.bean.LockState;

public class LockModel extends BaseRepository {

    public boolean saveLock(Lock lock) {
        deleteAll();
        ModelAdapter<Lock> modelAdapter = FlowManager.getModelAdapter(Lock.class);
        return modelAdapter.save(lock);
    }

    public Lock findLock() {
        return SQLite.select().from(Lock.class).querySingle();
    }

    public void deleteAll() {
        SQLite.delete().from(Lock.class).execute();
    }

    public void saveLockState(LockState lockState) {
        ModelAdapter<LockState> modelAdapter = FlowManager.getModelAdapter(LockState.class);
        if (findLockState()!=null){
            modelAdapter.update(lockState);
            return;
        }
        deleteLockState();
        modelAdapter.save(lockState);
    }

    public LockState findLockState() {
        return SQLite.select().from(LockState.class).querySingle();
    }

    public void deleteLockState() {
        SQLite.delete().from(LockState.class).execute();
    }

}
