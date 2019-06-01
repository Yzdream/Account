package com.yz.account.manager;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yz.data.bean.Account;

public class BindingManager {

    @BindingAdapter("isVisibility")
    public static void isVisibility(View layout,String type){
        if (Account.SEL.equals(type)){
            layout.setVisibility(View.GONE);
        }else{
            layout.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("isGone")
    public static void isGone(View layout,String type){
        if (Account.SEL.equals(type)){
            layout.setVisibility(View.VISIBLE);
        }else{
            layout.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter("isEnabled")
    public static void isEnabled(EditText editText,String type){
        if (Account.SEL.equals(type)){
            editText.setEnabled(false);
        }else{
            editText.setEnabled(true);
        }
    }

    @BindingAdapter("setText")
    public static void setText(TextView textView, String type){
        if (Account.SEL.equals(type)){
            textView.setText("删除");
        }else{
            textView.setText("保存");
        }
    }
}
