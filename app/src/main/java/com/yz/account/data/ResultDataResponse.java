package com.yz.account.data;

public abstract class ResultDataResponse<T> implements ResultDataCallBack<T> {

    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
