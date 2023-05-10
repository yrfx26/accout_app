package cn.edu.jnu.account.bean;

import java.util.Date;

public class bill {
    private double money;
    private int type;
    private String account;
    private Date time;

    public bill() {
    }

    public bill(double money, int type, String account, Date time) {
        this.money = money;
        this.type = type;
        this.account = account;
        this.time = time;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
