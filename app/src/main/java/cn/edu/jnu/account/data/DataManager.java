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
import java.util.List;

public class DataManager {
    private List<Bill> bills;
    private List<Account> accounts;
    static private DataManager dataManager;

    static public final String ACCOUNT_FILE = "accounts.dat";
    static public final String BILL_FILE = "bills.dat";

    private DataManager() {
    }

    static public DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }


    public void addBill(Bill bill) {

    }

    public void updateBill(int billId) {

    }

    public void deleteBill(int billId) {

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
        try {
            FileInputStream fileIn = context.openFileInput(ACCOUNT_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Account>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            FileInputStream fileIn = context.openFileInput(BILL_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Bill>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
