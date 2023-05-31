package cn.edu.jnu.account.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.edu.jnu.account.R;
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
    private Spinner jizhang_spinner_billClass;
    private EditText jizhang_et_time;
    private Button jizhang_bt_commit;
    private Bill bill;
    private ArrayList<String> accountNames = new ArrayList<>();
    private ArrayList<String> typeNames = new ArrayList<>();

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
        this.jizhang_spinner_billClass = view.findViewById(R.id.jizhang_spinner);

        accountNames.add("账户1");
        accountNames.add("添加账户");

        jizhang_spinner_account = view.findViewById(R.id.jizhang_spinner_account);
        ArrayAdapter<String> arrayAdapterLabel = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, accountNames);
        arrayAdapterLabel.setDropDownViewResource(R.layout.item_spinner_drow_down);
        jizhang_spinner_account.setAdapter(arrayAdapterLabel);

        typeNames.add("吃");
        typeNames.add("喝");
        typeNames.add("玩");
        typeNames.add("乐");
        typeNames.add("添加类型");

        jizhang_spinner_type = view.findViewById(R.id.jizhang_spinner_type);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, typeNames);
        arrayAdapterType.setDropDownViewResource(R.layout.item_spinner_drow_down);
        jizhang_spinner_type.setAdapter(arrayAdapterType);


        /**
         * 给金额输入框绑定点击事件监听器
         */
//        this.jizhang_et_money.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"click money",Toast.LENGTH_LONG).show();
//            }
//        });

        /**
         * 给收支类型下拉框绑定点击事件监听器
         */
//        this.jizhang_spinner_type.setOnItemSelectedListener();

        /**
         * 给收支账户下拉框绑定点击事件监听器
         */
//        this.jizhang_spinner_account.setOnItemSelectedListener();


        /**
         * 给收支时间输入框绑定点击事件监听器
         */
        this.jizhang_et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"click time",Toast.LENGTH_LONG).show();
                showDatePickerDialog(v);
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

                //设置bill的金额
                bill.setMoney(Double.parseDouble(jizhang_et_money.getText().toString()));

                //设置bill类型
                bill.setType(jizhang_spinner_type.getSelectedItem().toString());

                //设置bill的账户类型
                bill.setAccountName(jizhang_spinner_account.getSelectedItem().toString());

                //设置billClass类型
                bill.setBillClass(jizhang_spinner_billClass.getSelectedItem().toString());

                //设置bill的时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    bill.setTime(format.parse(jizhang_et_time.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //填好信息后将bill对象序列化之后传输到明细页面
                if (0 != bill.getMoney() && null != bill.getAccountName() && null != bill.getTime() && bill.getBillClass() != null){
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

    /**
     * 弹出对话框选择账单产生时间
     * @param view
     */
    public void showDatePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 处理选择的日期
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                jizhang_et_time.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
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