package cn.edu.jnu.account.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable, Parcelable {
    public static final int INCOME_CLASS = 0;
    public static final int EXPEND_CLASS = 1;
    private static int num = 0;

    private final int id;
    private int billClass;
    private double money;
    private int type;
    private String accountName;
    private Date time;

    public Bill() {
        this.id = num++;
    }

    public Bill(double money, int type, String account, Date time) {
        this.id = num++;
        this.money = money;
        this.type = type;
        this.accountName = account;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billClass=" + billClass +
                ", money=" + money +
                ", type=" + type +
                ", accountName='" + accountName + '\'' +
                ", time=" + time +
                '}';
    }

    protected Bill(Parcel in) {
        id = in.readInt();
        billClass = in.readInt();
        money = in.readDouble();
        type = in.readInt();
        accountName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(billClass);
        dest.writeDouble(money);
        dest.writeInt(type);
        dest.writeString(accountName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getBillClass() {
        return billClass;
    }

    public void setBillClass(int billClass) {
        this.billClass = billClass;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
