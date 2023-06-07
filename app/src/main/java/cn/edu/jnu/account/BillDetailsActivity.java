package cn.edu.jnu.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.jnu.account.data.Bill;

public class BillDetailsActivity extends AppCompatActivity {

    private static final int RESULT_CODE_CHANGE = 1;
    private static final int RESULT_CODE_NO_CHANGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        init();
    }

    private void init() {
        initNavigation();
        ((TextView) findViewById(R.id.type_bill_detail)).setText("收入");
        ((TextView) findViewById(R.id.money_bill_detail)).setText("123.4");
        ((TextView) findViewById(R.id.time_bill_detail)).setText("2023-6-6");
        ((TextView) findViewById(R.id.account_bill_detail)).setText("账户1");
        ((TextView) findViewById(R.id.description_bill_detail)).setText("测试描述CRT佛语故不v除非体育馆iv的心态吃饭v给规划发出关于更换");
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