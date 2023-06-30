package cn.edu.jnu.account.data;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class DataManagerTest {
    DataManager dataManager;
    List<Account> accountsBackUp;
    Context context;
    List<Bill> billsBackUp;

    @Before
    public void setUp() throws Exception {
        dataManager = DataManager.getDataManager();
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        accountsBackUp = dataManager.loadAccounts(context);
        billsBackUp = dataManager.loadBills(context);
        dataManager.saveAccounts(context, new ArrayList<>());
        dataManager.saveBills(context, new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        dataManager.saveAccounts(context, accountsBackUp);
        dataManager.saveBills(context,billsBackUp);
    }

//    @Test
//    public void saveAndLoadAccountsTest() {
//        List<Account> accounts = new ArrayList<>();
//
//        Account account = new Account();
//        account.setName("123");
//        account.setRemarks("456");
//        account.setMoney(123.45);
//        accounts.add(account);
//        account = new Account();
//        account.setName("qwe");
//        account.setRemarks("wer");
//        account.setMoney(546.00);
//        accounts.add(account);
//
//        dataManager.saveAccounts(context, accounts);
//        List<Account> accountsRead = dataManager.loadAccounts(context);
//
//        Assert.assertEquals(accounts.size(), accountsRead.size());
//        for (int i=0; i<accounts.size(); i++) {
//            Assert.assertEquals(accounts.get(i).getName(), accountsRead.get(i).getName());
//            Assert.assertEquals(accounts.get(i).getRemarks(), accountsRead.get(i).getRemarks());
//            Assert.assertEquals(accounts.get(i).getMoney(), accountsRead.get(i).getMoney());
//        }
//    }

    @Test
    public void getAccountNamesTest() {
        List<Account> accountList = dataManager.loadAccounts(context);
        List<String> names = dataManager.getAccountNames(accountList);
        List<String> names_gt = new ArrayList<>();

        Iterator<Account> iterator = accountList.iterator();
        while (iterator.hasNext()){
            Account next = iterator.next();
            names_gt.add(next.getName());
        }

        boolean flag = true;
        names.remove(0);                //去除默认账户
        names.remove(names.size()-1);   //去除添加账户
        flag = names.size() != names_gt.size() ? false : true;
        for (int i = 0;i < names_gt.size();i++){
            if (!names.get(i).equals(names_gt.get(i))){
                flag = false;
                break;
            }
        }
        Assert.assertTrue(flag == true);
    }

    @Test
    public void deleteBillTest() {
        List<Bill> billList = dataManager.loadBills(context);
        int billNum = billList.size();

        boolean flag = true;
        if (0 != billNum){
            dataManager.deleteBill(context,billList.get(0),billList);
            flag = billNum != billList.size() + 1 ? false : true;
        }else {
            Bill myBill = new Bill();
            billList.add(myBill);
            dataManager.deleteBill(context,myBill,billList);
            flag = billList.size() != 0 ? false : true;
        }

        Assert.assertTrue(true == flag);
    }

    @Test
    public void getBillsByAccountNameTest() {
        List<String> accountNames = new ArrayList<>();
        List<Bill> bills = dataManager.loadBills(context);

        boolean flag = true;

        if (0 == bills.size()){
            flag = true;
        }else {
            Iterator<Bill> iterator = bills.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next().getAccountName();
                if(0 == dataManager.getBillsByAccountName(next, bills).size()){
                    flag = false;
                    break;
                }
            }
        }
        Assert.assertTrue(true == flag);

    }

//    @Test
//    public void deleteAccountTest() {
//        List<Account> accountList = dataManager.loadAccounts(context);
//        List<String> names = dataManager.getAccountNames(accountList);
//        names.remove(0);
//        names.remove(names.size() - 1);
//        List<Bill> billList = dataManager.loadBills(context);
//        boolean flag = true;
//        if (0 != billList.size()){
//            for (Account acc :
//                    accountList) {
//                dataManager.deleteAccount(context,acc,billList);
//                for (Bill dbill :
//                        billList) {
//                    if (dbill.getAccountName().equals(dbill.getAccountName())) {
//                        flag = false;
//
//                        break;
//                    }
//                }
//
//            }
//            Assert.assertTrue(true == flag);
//        }
//    }

    @Test
    public void getTotalAccountMoneyTest() {
        List<Account> accountList = dataManager.loadAccounts(context);
        double total = 0.0;

        Iterator<Account> iterator = accountList.iterator();
        while (iterator.hasNext()){
            Account next = iterator.next();
            total += next.getMoney();
        }
        String totalAccountMoney = dataManager.getTotalAccountMoney();
        Assert.assertTrue(Math.abs(Double.valueOf(totalAccountMoney) - total) < 0.001);
    }

    @Test
    public void testGetTotalAccountMoneyTest() {
        List<Account> accountList = dataManager.loadAccounts(context);
        String totalAccountMoney = dataManager.getTotalAccountMoney(accountList);
        double total = 0.0;

        Iterator<Account> iterator = accountList.iterator();
        while (iterator.hasNext()){
            Account next = iterator.next();
            total += next.getMoney();
        }
        Assert.assertTrue(Math.abs(Double.valueOf(totalAccountMoney) - total) < 0.001);

    }

    @Test
    public void getIncomeTest() {
        List<Bill> billList = dataManager.loadBills(context);

        double income = 0.0;

        Iterator<Bill> iterator = billList.iterator();
        while (iterator.hasNext()){
            Bill next = iterator.next();
            if (next.getBillClass().equals(Bill.INCOME_CLASS)){
                income += next.getMoney();
            }
        }

        String func_income = dataManager.getIncome(billList);
        Assert.assertTrue(Math.abs(Double.valueOf(func_income) - income) < 0.001);
    }

    @Test
    public void getExpendTest() {
        List<Bill> billList = dataManager.loadBills(context);

        double outcome = 0.0;

        Iterator<Bill> iterator = billList.iterator();
        while (iterator.hasNext()){
            Bill next = iterator.next();
            if (next.getBillClass().equals(Bill.EXPEND_CLASS)){
                outcome += next.getMoney();
            }
        }

        String func_income = dataManager.getExpend(billList);
        Assert.assertTrue(Math.abs(Double.valueOf(func_income) - outcome) < 0.001);
    }

}