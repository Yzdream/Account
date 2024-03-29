package com.yz.account.base;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yz.account.R;

import java.util.Objects;

public abstract class BaseDialogFragment<mBinding extends ViewDataBinding, mViewModel extends BaseViewModel> extends DialogFragment {

    private View rootView;

    protected float width = 0.8f;

    protected mBinding mBinding;
    protected mViewModel mViewModel;

    private FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.Dialog_style);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getRootViewId(), container, false);
        //创建viewModel
        mViewModel = ViewModelProviders.of(mActivity).get(getViewModelClass());
        //绑定viewModel
        dataBindingSetViewModel();
        if (mBinding == null) {
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            rootView = inflater.inflate(getRootViewId(), container, false);
        } else {
            mBinding.setLifecycleOwner(this);
            rootView = mBinding.getRoot();
        }
        initView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * width), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    protected abstract int getRootViewId();

    protected abstract Class<mViewModel> getViewModelClass();

    protected abstract void dataBindingSetViewModel();

    protected abstract void initView();

    protected View getRootView() {
        return rootView;
    }
}
