package com.yz.account.module;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yz.account.base.BaseRepository;
import com.yz.account.data.ResultDataCallBack;
import com.yz.data.bean.Account;
import com.yz.data.bean.AccountType;
import com.yz.data.db.AccountDao;
import com.yz.data.db.AccountTypeDao;

import java.util.List;

public class AccountModel extends BaseRepository {

    public void getAccountType(ResultDataCallBack<List<AccountType>> resultDataCallBack) {
        List<AccountType> accountTypes = SQLite.select().from(AccountType.class).queryList();
        if ( accountTypes.size() > 0) {

        } else {
            AccountType accountType = new AccountType();
            accountType.setTypeName("其他");
            AccountTypeDao.saveAccountType(accountType);

            AccountType accountType2 = new AccountType();
            accountType2.setTypeName("游戏");
            AccountTypeDao.saveAccountType(accountType2);

            AccountType accountType3 = new AccountType();
            accountType3.setTypeName("社交");
            AccountTypeDao.saveAccountType(accountType3);

            AccountType accountType4 = new AccountType();
            accountType4.setTypeName("工作");
            AccountTypeDao.saveAccountType(accountType4);
        }
        if (resultDataCallBack != null) resultDataCallBack.onSuccess(AccountTypeDao.findAll());
    }

    public void getAccountById(String typeId, ResultDataCallBack<List<Account>> resultDataCallBack) {
        List<Account> accounts = AccountDao.findAllById(typeId);
        if ( accounts.size() > 0) {
            if (resultDataCallBack != null) resultDataCallBack.onSuccess(accounts);
        } else {
            if (resultDataCallBack != null) resultDataCallBack.onNoData();
        }
    }

}
