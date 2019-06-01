package com.yz.account.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.yz.account.R;
import com.yz.account.base.BaseDialogFragment;
import com.yz.account.databinding.DialogAddAccountBinding;
import com.yz.account.uitls.CustomClickListener;
import com.yz.account.uitls.EventBusUtils;
import com.yz.account.uitls.TimeUtils;
import com.yz.account.uitls.ToastHelper;
import com.yz.account.viewmodule.MainViewModule;
import com.yz.data.bean.Account;
import com.yz.data.db.AccountDao;
import com.yz.data.event.EventRefresh;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

public class AddAccountDialog extends BaseDialogFragment<DialogAddAccountBinding, MainViewModule> {

    private Account account;
    private ClipboardManager myClipboard;
    private Subscription subscription;

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_add_account;
    }

    @Override
    protected Class<MainViewModule> getViewModelClass() {
        return MainViewModule.class;
    }

    @Override
    protected void dataBindingSetViewModel() {
    }

    @Override
    protected void initView() {
        initClick();
        Logger.i(account.getTypeId());
        mBinding.setData(account);
    }

    private void initClick() {
        mBinding.tvCancel.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                dismiss();
            }
        });

        mBinding.textInputPasswordToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //如果选中，显示密码
                mBinding.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                mBinding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        mBinding.llEditor.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                account.setType(Account.UPDATE);
            }
        });

        mBinding.ivCopyName.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                copy(mBinding.etName);
            }
        });

        mBinding.ivCopyAccount.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                copy(mBinding.etAccount);
            }
        });

        mBinding.ivCopyPwd.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                copy(mBinding.etPwd);
            }
        });

        mBinding.tvSubmit.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                if (Account.SEL.equals(account.getType())){
                    if (AccountDao.delAccount(account)) {
                        EventBusUtils.post(new EventRefresh());
                        dismiss();
                    }else{
                        ToastHelper.showShortToast("数据异常");
                    }
                    return;
                }
                if (TextUtils.isEmpty(account.getName())){
                    ToastHelper.showShortToast("请输入名称");
                    return;
                }
                if (TextUtils.isEmpty(account.getAccount())){
                    ToastHelper.showShortToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(account.getPassword())){
                    ToastHelper.showShortToast("请输入密码");
                    return;
                }
                account.setDate(TimeUtils.getNowDate("yyyy-MM-dd"));
                if (AccountDao.saveAccount(account)){
                    EventBusUtils.post(new EventRefresh());
                    dismiss();
                }else{
                    ToastHelper.showShortToast("保存失败");
                }

            }
        });
    }

    public void copy(EditText view){
        String text = view.getText().toString();
        if (myClipboard == null) {
            myClipboard = (ClipboardManager) getRootView().getContext().getSystemService(CLIPBOARD_SERVICE);
        }
        myClipboard.setPrimaryClip(ClipData.newPlainText("text", text));
        ToastHelper.showShortToast("已复制，15s后自动清除");
        if (subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        subscription = Observable.timer(15, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(aLong -> myClipboard.setPrimaryClip(ClipData.newPlainText("text", "")));
    }

    public AddAccountDialog setData(Account account){
        this.account = account;
        return this;
    }
}
