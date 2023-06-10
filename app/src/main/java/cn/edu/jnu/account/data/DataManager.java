package cn.edu.jnu.account.data;


import android.content.Context;

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

    public List<Bill> getBillsByAccountName(String accountName) {
        return null;
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
}
