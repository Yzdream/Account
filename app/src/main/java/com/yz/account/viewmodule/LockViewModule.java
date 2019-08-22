package com.yz.account.viewmodule;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yz.account.base.BaseViewModel;
import com.yz.account.module.LockModel;
import com.yz.data.bean.Lock;
import com.yz.data.bean.LockState;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LockViewModule extends BaseViewModel<LockModel> {

    private MutableLiveData<Lock> lockMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLock = new MutableLiveData<>();
    private MutableLiveData<LockState> lockStateMutableLiveData = new MutableLiveData<>();

    private int lockFailureCount = 0;
    private MutableLiveData<Integer> lockFailureTime = new MutableLiveData<>();

    private Subscription subscription;

    public LockViewModule(@NonNull Application application) {
        super(application);
    }

    @Override
    protected LockModel createModel() {
        return new LockModel();
    }

    public void onLockFailure() {
        lockFailureCount++;
        if (lockFailureCount % 3 == 0) {
            LockState lockState = new LockState();
            lockState.setCount(lockFailureCount);
            lockState.setTime(System.currentTimeMillis());
            Logger.i(lockState.getTime() + "=======1");
            lockState.setLockTime((long) Math.pow(2, (lockFailureCount / 3) - 1) * 15);

            onLock(lockState);
        }
    }

    public void getIsLockState() {
        LockState lockState = mModel.findLockState();
        if (lockState != null && lockState.getLockTime() != 0) {
            onLock(lockState);
        } else {
            mModel.deleteLockState();
            isLock.setValue(false);
        }
    }

    private void onLock(LockState lockState) {
        lockFailureTime.setValue((int) lockState.getLockTime());
        lockStateMutableLiveData.setValue(lockState);
        isLock.setValue(true);

        subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (lockFailureTime.getValue() != null) {
                        lockFailureTime.setValue(lockFailureTime.getValue() - 1);
                        lockStateMutableLiveData.getValue().setLockTime(lockFailureTime.getValue());
                        mModel.saveLockState(lockStateMutableLiveData.getValue());
                        if (lockFailureTime.getValue() == 0) {
                            isLock.setValue(false);
                            if (subscription != null && !subscription.isUnsubscribed()) {
                                subscription.unsubscribe();
                            }
                        }
                    }
                });
    }

    public void onDestory() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void getLock() {
        Lock lock = mModel.findLock();
        lockMutableLiveData.setValue(lock);
    }

    public void addLock(String lock) {
        Lock lock1 = new Lock();
        lock1.setLock(lock);
        mModel.saveLock(lock1);
    }

    public MutableLiveData<Lock> getLockMutableLiveData() {
        return lockMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLock() {
        return isLock;
    }

    public MutableLiveData<Integer> getLockFailureTime() {
        return lockFailureTime;
    }
}
