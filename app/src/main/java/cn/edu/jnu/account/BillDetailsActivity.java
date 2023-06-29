package cn.edu.jnu.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.edu.jnu.account.data.Bill;

public class BillDetailsActivity extends AppCompatActivity {

    private static final int RESULT_CODE_CHANGE = 1;
    private static final int RESULT_CODE_NO_CHANGE = 0;

    private Bill bill;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        getBillData();

        init();
    }

    private void getBillData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bill = bundle.getParcelable("bill");
        time = bundle.getString("time");

        try {
            bill.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("bill --> " + bill);
    }

    private void init() {
        initNavigation();
        ((TextView) findViewById(R.id.type_bill_detail)).setText(bill.getType());
        ((TextView) findViewById(R.id.money_bill_detail)).setText(String.valueOf(bill.getMoney()));
        ((TextView) findViewById(R.id.time_bill_detail)).setText(bill.getTime());
        ((TextView) findViewById(R.id.account_bill_detail)).setText(bill.getAccountName());
        ((TextView) findViewById(R.id.description_bill_detail)).setText(bill.getDescription());
    }



    private void initNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar_bill_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(false);
            }
        });
    }

    private void goBack(boolean isChange) {
        Intent intent = new Intent();
        if (isChange) {
            Bundle bundle = new Bundle();
            setResult(RESULT_CODE_CHANGE, intent);
        }
        else
            setResult(RESULT_CODE_NO_CHANGE);
        BillDetailsActivity.this.finish();
    }
}