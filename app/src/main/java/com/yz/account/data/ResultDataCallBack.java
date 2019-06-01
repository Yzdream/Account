package com.yz.account.data;

public interface ResultDataCallBack<T> {

    void onSuccess(T t);

    void onNoData();

    void onError(Throwable throwable);

}
