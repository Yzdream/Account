package com.yz.account.viewmodule;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yz.account.base.BaseViewModel;
import com.yz.account.module.LockModel;
import com.yz.data.bean.Lock;

public class LockViewModule extends BaseViewModel<LockModel> {

    private MutableLiveData<Lock> lockMutableLiveData = new MutableLiveData<>();


    public LockViewModule(@NonNull Application application) {
        super(application);
    }

    @Override
    protected LockModel createModel() {
        return new LockModel();
    }

    public void getLock(){
        Lock lock =  mModel.findLock();
        lockMutableLiveData.setValue(lock);
    }

    public void addLock(String lock){
        Lock lock1 = new Lock();
        lock1.setLock(lock);
        mModel.saveLock(lock1);
    }

    public MutableLiveData<Lock> getLockMutableLiveData(){
        return lockMutableLiveData;
    }
}
