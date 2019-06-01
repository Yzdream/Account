package com.yz.data.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.yz.data.BR;
import com.yz.data.db.AccountDao;
import com.yz.data.db.AppDatabase;


import java.util.UUID;

@Table(database = AppDatabase.class)
public class Account extends BaseObservable {

    public static final String ADD = "0";
    public static final String UPDATE = "1";
    public static final String SEL = "2";

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String accountId;
    @Column
    private String typeId;
    @Column
    private String name;
    @Column
    private String account;
    @Column
    private String password;
    @Column
    private String remark;
    @Column
    private String date;
    @Column
    private boolean isTop;

    private String type;  //0 新增   1 修改  2 查看

    public Account() {
        setAccountId(UUID.randomUUID().toString().trim());
    }

    public Account(String typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId == null ? "" : accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Bindable
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAccount() {
        return account == null ? "" : account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Bindable
    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyPropertyChanged(BR.remark);
    }

    @Bindable
    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
        notifyPropertyChanged(BR.top);
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
}
