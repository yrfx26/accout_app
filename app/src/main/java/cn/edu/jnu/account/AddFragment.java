package cn.edu.jnu.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import cn.edu.jnu.account.data.Bill;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    private EditText jizhang_et_money;
    private Spinner jizhang_spinner_type;
    private Spinner jizhang_spinner_account;
    private EditText jizhang_et_time;
    private Button jizhang_bt_commit;
    private Bill bill;

    public AddFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        /**
         * 给四个用户输入框绑定控件
         */
        this.jizhang_et_money = view.findViewById(R.id.jizhang_et_money);
        this.jizhang_et_time = view.findViewById(R.id.jizhang_et_time);
        this.jizhang_spinner_account = view.findViewById(R.id.jizhang_spinner_account);
        this.jizhang_spinner_type = view.findViewById(R.id.jizhang_spinner_type);
        this.jizhang_bt_commit = view.findViewById(R.id.jizhang_bt_commit);

        /**
         * 给金额输入框绑定点击事件监听器
         */
        this.jizhang_et_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"click money",Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 给收支类型下拉框绑定点击事件监听器
         */
//        this.jizhang_spinner_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(),"click type position: "+position+" id: "+id,Toast.LENGTH_LONG);
//            }
//        });

        /**
         * 给收支账户下拉框绑定点击事件监听器
         */
//        this.jizhang_spinner_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(),"click account position: "+position+" id: "+id,Toast.LENGTH_LONG);
//            }
//        });

        /**
         * 给收支时间输入框绑定点击事件监听器
         */
        this.jizhang_et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"click time",Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 给确认按钮绑定点击事件监听器
         */
        this.jizhang_bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"click commit",Toast.LENGTH_LONG).show();
                bill = new Bill();
                bill.setMoney(Double.parseDouble(jizhang_et_money.getText().toString()));
                bill.setType(1);
                bill.setAccountName("account1");
                bill.setTime(new Date(System.currentTimeMillis()));
                if (0 != bill.getMoney() && null != bill.getAccountName() && null != bill.getTime()){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("newbill", bill);
                    getParentFragmentManager().setFragmentResult("bill", bundle);
                }else {
                    Toast.makeText(getContext(),"请将账单信息输入完整",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public EditText getJizhang_et_money() {
        return jizhang_et_money;
    }

    public void setJizhang_et_money(EditText jizhang_et_money) {
        this.jizhang_et_money = jizhang_et_money;
    }

    public Spinner getJizhang_spinner_type() {
        return jizhang_spinner_type;
    }

    public void setJizhang_spinner_type(Spinner jizhang_spinner_type) {
        this.jizhang_spinner_type = jizhang_spinner_type;
    }

    public Spinner getJizhang_spinner_account() {
        return jizhang_spinner_account;
    }

    public void setJizhang_spinner_account(Spinner jizhang_spinner_account) {
        this.jizhang_spinner_account = jizhang_spinner_account;
    }

    public EditText getJizhang_et_time() {
        return jizhang_et_time;
    }

    public void setJizhang_et_time(EditText jizhang_et_time) {
        this.jizhang_et_time = jizhang_et_time;
    }

    public Button getJizhang_bt_commit() {
        return jizhang_bt_commit;
    }

    public void setJizhang_bt_commit(Button jizhang_bt_commit) {
        this.jizhang_bt_commit = jizhang_bt_commit;
    }
}