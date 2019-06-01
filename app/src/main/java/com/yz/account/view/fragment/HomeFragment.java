package com.yz.account.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yz.account.R;
import com.yz.account.base.VpBaseFragment;
import com.yz.account.base.adapter.BindingAdapter;
import com.yz.account.base.adapter.OnItemToucherListener;
import com.yz.account.databinding.FragmentHomeBinding;
import com.yz.account.databinding.ItemAccountBinding;
import com.yz.account.dialog.AddAccountDialog;
import com.yz.account.uitls.EventBusUtils;
import com.yz.account.viewmodule.MainViewModule;
import com.yz.data.bean.Account;
import com.yz.data.event.EventRefresh;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HomeFragment extends VpBaseFragment<FragmentHomeBinding, MainViewModule> {

    public final static String POSITION = "position";

    private BindingAdapter<Account, ItemAccountBinding> accountAdapter;

    private int position = 0;

    public static HomeFragment getInstance(int position) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    protected Class<MainViewModule> getViewModelClass() {
        return MainViewModule.class;
    }

    @Override
    protected void dataBindingSetViewModel() {
        mBinding.setModel(mViewModel);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }
        initRvAccount();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void getEventData(EventRefresh eventRefresh){
        if (getUserVisibleHint()){
            mViewModel.getAccount(position);
            EventBusUtils.cancelEventDelivery(eventRefresh);
        }
    }

    @Override
    protected void prepareFetchData(boolean forceUpdate) {
        super.prepareFetchData(true);
    }

    @Override
    public void loadData() {
        super.loadData();
        mViewModel.getAccount(position);
    }

    private void initRvAccount() {
        accountAdapter = new BindingAdapter<>(getNonNullActivity(),new ArrayList<>(),R.layout.item_account);
        mBinding.rvAccount.setLayoutManager(new LinearLayoutManager(getNonNullActivity()));
        mBinding.rvAccount.setAdapter(accountAdapter);
        accountAdapter.setOnItemToucherListener(new OnItemToucherListener() {
            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
                Account account = accountAdapter.getItem(position);
                account.setType(Account.SEL);
                new AddAccountDialog()
                        .setData(account)
                        .show(getChildFragmentManager(),"SEL");
            }
        });

        mViewModel.getAccountLiveData(position)
                .observe(this,accounts -> accountAdapter.refreshData(accounts));
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }
}
