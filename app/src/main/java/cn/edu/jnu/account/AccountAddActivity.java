package cn.edu.jnu.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.jnu.account.data.Account;

public class AccountAddActivity extends AppCompatActivity {

    public static final int RESULT_CODE_NO_ADD = -1;
    public static final int RESULT_CODE_ADD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);

        initToolBar();
        initConfirmButton();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_account);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(false);
            }
        });
    }

    private void initConfirmButton () {
        Button button = findViewById(R.id.button_confirm_add_account);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(true);
            }
        });
    }

    private void goBack(boolean isAdd) {
        Intent intent = new Intent();
        if (isAdd) {
            Bundle bundle = new Bundle();
            Account account = getAccount();
            bundle.putParcelable("账户", account);
            intent.putExtras(bundle);
            setResult(RESULT_CODE_ADD, intent);
        }
        else
            setResult(RESULT_CODE_NO_ADD);
        AccountAddActivity.this.finish();
    }

    private Account getAccount() {
        String name = ((EditText)findViewById(R.id.editView_account_name)).getText().toString();
        String remarks = ((EditText)findViewById(R.id.editView_account_remarks)).getText().toString();
        String  money = ((EditText)findViewById(R.id.editView_account_money)).getText().toString();
        Account account = new Account();
        account.setName(name);
        account.setRemarks(remarks);
        account.setMoney(Double.valueOf(money));

        return account;
    }
}