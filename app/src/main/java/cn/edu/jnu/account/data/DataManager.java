package cn.edu.jnu.account.data;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataManager {
    private List<Bill> bills;
    private List<Account> accounts;
    private List<String> billTypes;
    static private DataManager dataManager;

    static public final String ACCOUNT_FILE = "accounts.dat";
    static public final String BILL_FILE = "bills.dat";
    static public final String BILL_TYPE_FILE = "billTypes.dat";

    private DataManager() {

    }

    public List<String> getAccountNames(List<Account> accounts) {
        List<String> strli = new ArrayList<>();
        Iterator<Account> iterator = accounts.iterator();
        while(iterator.hasNext()){
            strli.add(iterator.next().getName());
        }
        initAccountList(strli);
        return strli;
    }

    private void initAccountList(List<String> strli) {
        strli.add(0, "默认账户");
        strli.add("添加账户");
    }

    static public DataManager getDataManager() {
        if (null == dataManager) {
            synchronized (DataManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager();

                }
            }
        }
        return dataManager;
    }


    public void addBill(Bill bill) {

    }

    public void updateBill(int billId) {

    }

    public void deleteBill(int billId) {

    }

    public void deleteBill(Context context, Bill bill, List<Bill> billsShow) {
        if (null != this.bills) {
            synchronized (DataManager.class) {
                if (this.bills == null) {
                    this.bills = loadBills(context);
                }
                this.bills.remove(bill);
                billsShow.remove(bill);
            }
        }
        saveBills(context, this.bills);
    }


    public List<Bill> getAllBills() {
        return null;
    }

    public List<Bill> getBillsByDate() {
        return null;
    }

    public List<Bill> getBillsByAccountName(String accountName, List<Bill> bills) {
        List<Bill> billsFilter = new ArrayList<>();
        Log.i("data", accountName);
        for (Bill bill: bills) {
            Log.i("data", bill.getAccountName());
            if (bill.getAccountName().equals(accountName)) {
                billsFilter.add(bill);
            }
        }
        return billsFilter;
    }

    public void addAccount(Account account) {

    }

    public void updateAccount(int accountId) {

    }

    public void deleteAccount(int accountId) {

    }

    public void deleteAccount(Context context, Account account, List<Bill> bills) {
        String accountName = account.getName();
        if (this.bills == null) {
            loadBills(context);
        }

        for (Bill bill: bills) {
            if (bill.getAccountName().equals(accountName)) {
                bill.setAccountName("");
            }
        }
        this.accounts.remove(account);
        saveBills(context, bills);
        saveAccounts(context, this.accounts);
    }

    public List<Account> getAllAccounts() {
        return null;
    }

    public void saveAccounts(Context context, List<Account> accounts) {
        try {
            FileOutputStream fileOut = context.openFileOutput(ACCOUNT_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(accounts);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public List<Account> loadAccounts(Context context) {
        List<Account> data = new ArrayList<>();
        if (this.accounts != null) {
            return this.accounts;
        }
        try {
            FileInputStream fileIn = context.openFileInput(ACCOUNT_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Account>)in.readObject();
            this.accounts = data;
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            return new ArrayList<>();
        }

        return data;
    }

    public void saveBills(Context context, List<Bill> bills) {
        try {
            FileOutputStream fileOut = context.openFileOutput(BILL_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bills);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public List<Bill> loadBills(Context context) {
        List<Bill> data = new ArrayList<>();
        if (this.bills != null) {
            return this.bills;
        }
        try {
            FileInputStream fileIn = context.openFileInput(BILL_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Bill>)in.readObject();
            this.bills = data;
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return new ArrayList<>();
        }

        return data;
    }

    public void saveBilltypes(Context context, List<String> billtypes) {
        try {
            FileOutputStream fileOut = context.openFileOutput(BILL_TYPE_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(billtypes);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public List<String> loadBilltypes(Context context) {
        List<String> data = new ArrayList<>();
        if (this.billTypes != null) {
            return this.billTypes;
        }
        try {
            FileInputStream fileIn = context.openFileInput(BILL_TYPE_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<String>)in.readObject();
            this.billTypes = data;
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getTotalAccountMoney() {
        if (this.accounts == null) {
            return "";
        }
        else {
            double total_money = 0;
            for (Account account : this.accounts) {
                total_money += account.getMoney();
            }
            return String.valueOf(total_money);
        }
    }

    public String getTotalAccountMoney(List<Account> accounts) {
        if (this.accounts == null) {
            return "";
        }
        else {
            double total_money = 0;
            for (Account account : accounts) {
                total_money += account.getMoney();
            }
            return String.valueOf(total_money);
        }
    }

    public String getIncome(List<Bill> bills) {
        double total_money = 0.0;
        Log.i("data", "getIncome: " + bills);;
        for (Bill bill:bills) {
            if (bill.getBillClass().equals(Bill.INCOME_CLASS)) {
                total_money += bill.getMoney();
            }
        }
        return String.valueOf(total_money);
    }

    public String getExpend(List<Bill> bills) {
        double total_money = 0.0;
        for (Bill bill:bills) {
            if (bill.getBillClass().equals(Bill.EXPEND_CLASS)) {
                total_money += bill.getMoney();
            }
        }
        return String.valueOf(total_money);
    }

    public static boolean duplicate(List<Account> accounts,Account account){
        if(null == accounts)
            return false;
        for (Account acc:
             accounts) {
            if (acc.getName().equals(account.getName()))
                return true;
        }
        return false;
    }
}
