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

    private EditText add_et_money;
    private Spinner add_spinner_type;
    private Spinner add_spinner_account;
    private Spinner add_spinner_billClass;
    private EditText add_et_time;
    private Button add_bt_commit;
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
        this.add_et_money = view.findViewById(R.id.add_et_money);
        this.add_et_time = view.findViewById(R.id.add_et_time);
        this.add_spinner_account = view.findViewById(R.id.add_spinner_account);
        this.add_spinner_type = view.findViewById(R.id.add_spinner_type);
        this.add_bt_commit = view.findViewById(R.id.add_bt_commit);
        this.add_spinner_billClass = view.findViewById(R.id.add_spinner);

        accountNames.add("账户1");
        accountNames.add("添加账户");

        add_spinner_account = view.findViewById(R.id.add_spinner_account);
        ArrayAdapter<String> arrayAdapterLabel = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, accountNames);
        arrayAdapterLabel.setDropDownViewResource(R.layout.item_spinner_drow_down);
        add_spinner_account.setAdapter(arrayAdapterLabel);

        typeNames.add("吃");
        typeNames.add("喝");
        typeNames.add("玩");
        typeNames.add("乐");
        typeNames.add("添加类型");

        add_spinner_type = view.findViewById(R.id.add_spinner_type);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, typeNames);
        arrayAdapterType.setDropDownViewResource(R.layout.item_spinner_drow_down);
        add_spinner_type.setAdapter(arrayAdapterType);


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
        this.add_et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"click time",Toast.LENGTH_LONG).show();
                showDatePickerDialog(v);
            }
        });

        /**
         * 给确认按钮绑定点击事件监听器
         */
        this.add_bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"click commit",Toast.LENGTH_LONG).show();
                bill = new Bill();

                //设置bill的金额
                bill.setMoney(Double.parseDouble(add_et_money.getText().toString()));

                //设置bill类型
                bill.setType(add_spinner_type.getSelectedItem().toString());

                //设置bill的账户类型
                bill.setAccountName(add_spinner_account.getSelectedItem().toString());

                //设置billClass类型
                bill.setBillClass(add_spinner_billClass.getSelectedItem().toString());

                //设置bill的时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    bill.setTime(format.parse(add_et_time.getText().toString()));
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
                add_et_time.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    public EditText getAdd_et_money() {
        return add_et_money;
    }

    public void setAdd_et_money(EditText add_et_money) {
        this.add_et_money = add_et_money;
    }

    public Spinner getAdd_spinner_type() {
        return add_spinner_type;
    }

    public void setAdd_spinner_type(Spinner add_spinner_type) {
        this.add_spinner_type = add_spinner_type;
    }

    public Spinner getAdd_spinner_account() {
        return add_spinner_account;
    }

    public void setAdd_spinner_account(Spinner add_spinner_account) {
        this.add_spinner_account = add_spinner_account;
    }

    public EditText getAdd_et_time() {
        return add_et_time;
    }

    public void setAdd_et_time(EditText add_et_time) {
        this.add_et_time = add_et_time;
    }

    public Button getAdd_bt_commit() {
        return add_bt_commit;
    }

    public void setAdd_bt_commit(Button add_bt_commit) {
        this.add_bt_commit = add_bt_commit;
    }
}