package com.yz.account.view;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yz.account.R;
import com.yz.account.base.BaseActivity;
import com.yz.account.databinding.ActivityLockBinding;
import com.yz.account.uitls.ToastHelper;
import com.yz.account.view.activity.MainActivity;
import com.yz.account.viewmodule.LockViewModule;
import com.yz.account.widget.LockView;

public class LockActivity extends BaseActivity<ActivityLockBinding, LockViewModule> {

    private Vibrator vibrator;
    private String lock = "";

    @Override
    protected Class<LockViewModule> getViewModelClass() {
        return LockViewModule.class;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_lock;
    }

    @Override
    protected void dataBindingSetViewModel() {
        mBinding.setModel(mViewModel);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewModel.getLock();
        mViewModel.getLockMutableLiveData().observe(this,lock1 -> {
            if (lock1 == null||TextUtils.isEmpty(lock1.getLock())){
                //新增
                mBinding.lockView.setState(true);
                mBinding.tvTitle.setText(R.string.lock_title);
            }else{
                //验证
                mBinding.tvTitle.setText(R.string.lock_title1);
                mBinding.lockView.setState(false);
                lock = lock1.getLock();
            }
        });
        mBinding.lockView.setOnUnlockListener(new LockView.OnUnlockListener() {
            @Override
            public void addError() {
                mBinding.lockView.reset();
                ToastHelper.showShortToast("至少连接4个点");
            }

            @Override
            public void addLock(String result) {
                mBinding.lockView.reset();
                if (TextUtils.isEmpty(lock)){
                    lock = result;
                    ToastHelper.showShortToast("请再试一次，确定图案密码");
                }else{
                    if (lock.equals(result)){
                        ToastHelper.showShortToast("图案密码设置成功");
                        mViewModel.addLock(result);
                        intentActivity(MainActivity.class);
                        finish();
                    }else{
                        ToastHelper.showShortToast("两次图案密码不一致，请重新绘制新的图案密码");
                    }
                }
            }

            @Override
            public boolean isUnlockSuccess(String result) {
                return lock.equals(result);
            }

            @Override
            public void onSuccess() {
                mBinding.lockView.reset();
                intentActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onFailure() {
                ToastHelper.showShortToast("图案密码错误！");
                mBinding.lockView.reset();
                // 震动效果的系统服务
                if (vibrator==null) {
                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                }
                if (vibrator!=null) {
                    vibrator.vibrate(200);
                }
            }
        });
    }
}
