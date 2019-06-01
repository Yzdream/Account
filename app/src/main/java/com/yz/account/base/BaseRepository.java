package com.yz.account.base;


public class BaseRepository {

    //用于管理网络请求  此项目无需网络
//    private CompositeSubscription mCompositeSubscription;

   /* protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
*/
    public void unSubscribe() {
       /* if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.clear();
        }*/
    }
}
