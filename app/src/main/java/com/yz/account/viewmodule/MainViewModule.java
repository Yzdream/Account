package com.yz.account.viewmodule;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.yz.account.base.BaseViewModel;
import com.yz.account.data.ResultDataResponse;
import com.yz.account.module.AccountModel;
import com.yz.data.bean.Account;
import com.yz.data.bean.AccountType;

import java.util.ArrayList;
import java.util.List;

public class MainViewModule extends BaseViewModel<AccountModel> {

    private MutableLiveData<List<AccountType>> accountTypeLiveData = new MutableLiveData<>();
    private List<MutableLiveData<List<Account>>> liveDataList = new ArrayList<>();

    private MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();

    public MainViewModule(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AccountModel createModel() {
        return new AccountModel();
    }

    public void getAccountType(){
        mModel.getAccountType(new ResultDataResponse<List<AccountType>>() {
            @Override
            public void onSuccess(List<AccountType> accountTypes) {
                super.onSuccess(accountTypes);
                for (AccountType accountType : accountTypes){
                    MutableLiveData<List<Account>> accountLiveData = new MutableLiveData<>();
                    liveDataList.add(accountLiveData);
                }
                accountTypeLiveData.setValue(accountTypes);
            }
        });
    }

    public void getAccount(int position){
        if (accountTypeLiveData.getValue() == null || position >= accountTypeLiveData.getValue().size()){
            showMsg("数据异常");
            return;
        }

        mModel.getAccountById(accountTypeLiveData.getValue().get(position).getTypeId(), new ResultDataResponse<List<Account>>() {
            @Override
            public void onSuccess(List<Account> accounts) {
                super.onSuccess(accounts);
                isEmpty.setValue(false);
                liveDataList.get(position).setValue(accounts);
            }

            @Override
            public void onNoData() {
                super.onNoData();
                isEmpty.setValue(true);
            }
        });
    }

    public MutableLiveData<List<AccountType>> getAccountTypeLiveData(){
        return accountTypeLiveData;
    }

    public MutableLiveData<List<Account>> getAccountLiveData(int position){
        return liveDataList.get(position);
    }

    public MutableLiveData<Boolean> getIsEmpty(){
        return isEmpty;
    }
}
