package cn.edu.jnu.account.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Bill implements Serializable, Parcelable {
    public static final String  INCOME_CLASS = "收入";
    public static final String EXPEND_CLASS = "支出";
    private static int num = 0;

    private final int id;
    private String billClass;
    private double money;
    private String  type;
    private String accountName;
    private String description;

    public Bill(String billClass, double money, String type, String accountName, String description, Date time) {
        this.id = num++;
        this.billClass = billClass;
        this.money = money;
        this.type = type;
        this.accountName = accountName;
        this.description = description;
        this.time = time;
    }

    private Date time;

    public Bill() {
        this.id = num++;
    }

    public Bill(double money, String  type, String account, Date time) {
        this.id = num++;
        this.money = money;
        this.type = type;
        this.accountName = account;
        this.time = time;

    }


    protected Bill(Parcel in) {
        id = in.readInt();
        billClass = in.readString();
        money = in.readDouble();
        type = in.readString();
        accountName = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(billClass);
        dest.writeDouble(money);
        dest.writeString(type);
        dest.writeString(accountName);
        dest.writeString(description);
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillClass() {
        return billClass;
    }

    public void setBillClass(String billClass) {
        this.billClass = billClass;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String  getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String  getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        String date = "";
        String year = String.valueOf(calendar.get(Calendar.YEAR));					//获取年份
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);					//获取月份
        String day = String.valueOf(calendar.get(Calendar.DATE));					//获取日
        date = year+"-"+month+"-"+day;

        return date;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billClass='" + billClass + '\'' +
                ", money=" + money +
                ", type='" + type + '\'' +
                ", accountName='" + accountName + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
