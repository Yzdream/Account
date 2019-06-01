package com.yz.data.bean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.yz.data.db.AppDatabase;

@Table(database = AppDatabase.class)
public class Lock {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String lock;

    public String getLock() {
        return lock == null ? "" : lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
