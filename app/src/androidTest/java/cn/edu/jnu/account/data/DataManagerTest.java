package cn.edu.jnu.account.data;


import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class DataManagerTest {
    DataManager dataManager;
    List<Account> accountsBackUp;

    @Before
    public void setUp() throws Exception {
        dataManager = DataManager.getDataManager();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        accountsBackUp = dataManager.loadAccounts(context);
    }

    @After
    public void tearDown() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager.saveAccounts(context, accountsBackUp);
    }

    @Test
    public void saveAndLoadAccountsTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<Account> accounts = new ArrayList<>();

        Account account = new Account();
        account.setName("123");
        account.setRemarks("456");
        account.setMoney(123.45);
        accounts.add(account);
        account = new Account();
        account.setName("qwe");
        account.setRemarks("wer");
        account.setMoney(546.00);
        accounts.add(account);

        dataManager.saveAccounts(context, accounts);
        List<Account> accountsRead = dataManager.loadAccounts(context);

        Assert.assertEquals(accounts.size(), accountsRead.size());
        for (int i=0; i<accounts.size(); i++) {
            Assert.assertEquals(accounts.get(i).getName(), accountsRead.get(i).getName());
            Assert.assertEquals(accounts.get(i).getRemarks(), accountsRead.get(i).getRemarks());
            Assert.assertEquals(accounts.get(i).getMoney(), accountsRead.get(i).getMoney());
        }
    }
}