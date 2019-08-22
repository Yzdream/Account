package com.yz.data.bean;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.yz.data.db.AppDatabase;

@Table(database = AppDatabase.class)
public class LockState {

    @PrimaryKey(autoincrement = true)
    private int id;

    //错误的次数
    @Column
    private Integer count;
    //上次错误的时间
    @Column
    private Long time;
    //锁定时间
    @Column
    private long lockTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCount() {
        return count == null ? 0 : count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTime() {
        return time == null ? 0 : time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }
}
