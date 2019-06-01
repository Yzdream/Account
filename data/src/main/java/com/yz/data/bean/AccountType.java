package com.yz.data.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.yz.data.BR;
import com.yz.data.db.AppDatabase;

import java.util.UUID;

@Table(database = AppDatabase.class)
public class AccountType extends BaseObservable {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column()
    private String typeId;

    @Column()
    private String typeName;

    private boolean isChoose;

    public AccountType() {
        this.typeId = UUID.randomUUID().toString().trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Bindable
    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
        notifyPropertyChanged(BR.choose);
    }
}
