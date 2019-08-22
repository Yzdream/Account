package com.yz.account.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.yz.account.R;
import com.yz.account.base.BaseActivity;
import com.yz.account.base.vpadapter.FragmentViewPagerAdapter;
import com.yz.account.databinding.ActivityMainBinding;
import com.yz.account.dialog.AddAccountDialog;
import com.yz.account.uitls.BarUtils;
import com.yz.account.uitls.CustomClickListener;
import com.yz.account.view.fragment.HomeFragment;
import com.yz.account.viewmodule.MainViewModule;
import com.yz.data.bean.Account;
import com.yz.data.bean.AccountType;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModule> {

    private List<Fragment> mFragments = new ArrayList<>();
    private String typeId = "";

    @Override
    protected Class<MainViewModule> getViewModelClass() {
        return MainViewModule.class;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void dataBindingSetViewModel() {
        mBinding.setModel(mViewModel);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initTool();
        initEmpty();
        initFabAdd();
    }

    private void initFabAdd() {
        mBinding.fabAdd.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                new AddAccountDialog()
                        .setData(new Account(typeId,Account.ADD))
                        .show(getSupportFragmentManager(),"Add");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initAccountData();
        mViewModel.getAccountType();
    }

    private void initTool() {
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mBinding.tvTitle.setText(R.string.app_name);
    }

    private void initEmpty() {
        mViewModel.getIsEmpty()
                .observe(this,isEmpty ->{
                    if (isEmpty == null || !isEmpty) {
                        mBinding.include.setVisibility(View.GONE);
                    }else{
                        mBinding.include.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void initAccountData() {
        mViewModel.getAccountTypeLiveData().observe(this, accountTypes -> {
            if (accountTypes != null) {
                int i = 0;
                for (AccountType accountType : accountTypes) {
                    mFragments.add(HomeFragment.getInstance(i));
                    i++;
                }
                FragmentViewPagerAdapter viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),mFragments);
                mBinding.viewPage.setPagingEnabled(false);
                mBinding.viewPage.setOffscreenPageLimit(1);
                mBinding.viewPage.setAdapter(viewPagerAdapter);
                mBinding.viewPage.setCurrentItem(0);
                typeId = accountTypes.get(0).getTypeId();
                mBinding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        typeId = accountTypes.get(i).getTypeId();
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });

                CommonNavigator commonNavigator = new CommonNavigator(this);
                commonNavigator.setAdjustMode(true);  //ture 即标题平分屏幕宽度的模式
                commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                    @Override
                    public int getCount() {
                        return accountTypes.size();
                    }

                    @Override
                    public IPagerTitleView getTitleView(Context context, int index) {
                        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
//                        colorTransitionPagerTitleView.setBackground(getResources().getDrawable(R.drawable.green_ripple));
                        colorTransitionPagerTitleView.setText(accountTypes.get(index).getTypeName());
                        colorTransitionPagerTitleView.setTextSize(15);
                        //选中时字体颜色
                        colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.white));
                        //未选中时字体颜色
                        colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.gray_b2));
                        colorTransitionPagerTitleView.setOnClickListener(v -> mBinding.viewPage.setCurrentItem(index));
                        return colorTransitionPagerTitleView;
                    }

                    @Override
                    public IPagerIndicator getIndicator(Context context) {
                        LinePagerIndicator indicator = new LinePagerIndicator(context);
                        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                        indicator.setColors(getResources().getColor(R.color.colorAccent));//指示器的颜色
                        return indicator;
                    }
                });
                mBinding.tabLayout.setNavigator(commonNavigator);
                ViewPagerHelper.bind(mBinding.tabLayout, mBinding.viewPage);
                mBinding.tabLayout.onPageSelected(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     /*   //加载menu布局
        getMenuInflater().inflate(R.menu.menu, menu);
        setSearchView(menu);*/
        return super.onCreateOptionsMenu(menu);
    }

    private void setSearchView(Menu menu) {
        //得到SearchView对象，SearchView一些属性可以直接使用，比如：setSubmitButtonEnabled，setQueryHint等
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //如果想单独对SearchView定制，比如需要更换搜索图标等，可以通过一下代码实现。
        //"android:id/search_plate"这个值得到方式：通过跟踪SearchView源码，找到其布局对应的id
        //这边修改的是点击搜索图标后展开界面的背景色，也可以根据不同的id修改对应的控件
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        LinearLayout layout = searchView.findViewById(id);
        layout.setBackground(null);
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        layoutParams.height = BarUtils.getActionBarHeight(this) - 60;
        layout.setLayoutParams(layoutParams);
        layout.setBackground(getResources().getDrawable(R.drawable.white_round_3));
        //搜索框
        int etId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = searchView.findViewById(etId);
        editText.setTextSize(15);
        editText.setGravity(Gravity.CENTER_VERTICAL);
     /*   editText.setHintTextColor(ContextCompat.getColor(this, R.color.gray_E5E5E5));
        editText.setTextColor(ContextCompat.getColor(this, R.color.white));*/
        //搜索图标
        int ivId = searchView.getContext().getResources().getIdentifier("android:id/search_button", null, null);
        ImageView search_button = searchView.findViewById(ivId);
        search_button.setImageResource(R.drawable.icon_search);
      /*  //关闭图标
        int closeId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView icon_close = searchView.findViewById(closeId);
        icon_close.setImageResource(R.drawable.icon_close);*/
    }
}
