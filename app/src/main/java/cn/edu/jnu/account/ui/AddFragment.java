package cn.edu.jnu.account.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.edu.jnu.account.AccountAddActivity;
import cn.edu.jnu.account.R;
import cn.edu.jnu.account.data.Account;
import cn.edu.jnu.account.data.Bill;
import cn.edu.jnu.account.data.DataManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private View view;
    private EditText add_et_money;
    private Spinner add_spinner_type;
    private Spinner add_spinner_account;
    private Spinner add_spinner_billClass;
    private EditText add_et_time;
    private Button add_bt_commit;
    private Bill bill;
    private EditText add_et_description;
    private List<String> accountNames;
    private ArrayList<String> typeNames = new ArrayList<>();

    private DataManager dataManager = DataManager.getDataManager();


    private final ActivityResultLauncher<Intent> accountAddLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (null != result) {
                    if (result.getResultCode() == AccountAddActivity.RESULT_CODE_ADD) {
                        Intent intent = result.getData();
                        assert intent != null;
                        Bundle bundle = intent.getExtras();
                        Account account = bundle.getParcelable("账户");
                        List<Account> accounts = dataManager.loadAccounts(getActivity());
                        if (null != accounts && null != account && !DataManager.duplicate(accounts,account)) {
                            accountNames.add(accountNames.size()-1, account.getName());
                            add_spinner_account.setSelection(accountNames.size()-2);
                            accounts.add(account);
                        }

                        dataManager.saveAccounts(getActivity(),accounts);

                    }
                }
            }
    );
    private ArrayAdapter<String> arrayAdapterLabel;

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

        this.view = inflater.inflate(R.layout.fragment_add, container, false);

        init();

        onClick();

        return this.view;
    }

    private void onClick() {
        //点击时钟框
        onClickTime();

        //点击确认页面跳转到明细
        onClickCommit();

        //点击添加账户
        onClickAddAccount();
    }

    private void onClickAddAccount() {
        add_spinner_account.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("postion: " + position + "\tid: " + id);
                if (position == accountNames.size() - 1){
                    parent.setSelection(0);
                    Intent intent = new Intent(getActivity(), AccountAddActivity.class);
                    intent.putExtra("Add","AddFragment");

                    accountAddLaunch.launch(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void init() {
        //给五个个用户输入框绑定控件
        bandComponent();

        List<Account> accounts = DataManager.getDataManager().loadAccounts(getActivity());

        //初始化账户
        accountNames = DataManager.getDataManager().getAccountNames(accounts);

        initAccount(accountNames);

        //初始化账户消费类型
        initType();
    }

    private void onClickCommit() {
        add_bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //从控件中获取数据
                Bill newbill = new Bill();
                getBillFromComponent(newbill);

                //传递数据
                Bundle result = new Bundle();
                result.putParcelable("newbill", newbill);
                getParentFragmentManager().setFragmentResult("newbill", result);


                //页面跳转
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                assert navHostFragment != null;
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_item1);
            }

            private void getBillFromComponent(Bill newbill) {
                newbill.setDescription(add_et_description.getText().toString());
                newbill.setType(add_spinner_type.getSelectedItem().toString());
                System.out.println("add_et_time.getDrawingTime() --> "+add_et_time.getDrawingTime());
                System.out.println("add_et_time.getText().toString() --> " + add_et_time.getText().toString());
                newbill.setMoney(Double.parseDouble(add_et_money.getText().toString()));
                newbill.setBillClass(add_spinner_billClass.getSelectedItem().toString());
                newbill.setAccountName(add_spinner_account.getSelectedItem().toString());

                try {
                    newbill.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(add_et_time.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onClickTime() {
        add_et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    private void initType() {
        typeNames.add("吃");
        typeNames.add("喝");
        typeNames.add("玩");
        typeNames.add("乐");

        add_spinner_type = view.findViewById(R.id.add_spinner_type);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, typeNames);
        arrayAdapterType.setDropDownViewResource(R.layout.item_spinner_drow_down);
        add_spinner_type.setAdapter(arrayAdapterType);
    }

    private void initAccount(List<String> accountNames) {
        add_spinner_account = view.findViewById(R.id.add_spinner_account);
        arrayAdapterLabel = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, accountNames);
        arrayAdapterLabel.setDropDownViewResource(R.layout.item_spinner_drow_down);
        add_spinner_account.setAdapter(arrayAdapterLabel);
    }

    private void bandComponent() {
        this.add_et_money = view.findViewById(R.id.add_et_money);
        this.add_et_time = view.findViewById(R.id.add_et_time);
        this.add_spinner_account = view.findViewById(R.id.add_spinner_account);
        this.add_spinner_type = view.findViewById(R.id.add_spinner_type);
        this.add_bt_commit = view.findViewById(R.id.add_bt_commit);
        this.add_spinner_billClass = view.findViewById(R.id.add_spinner);
        this.add_et_description = view.findViewById(R.id.add_et_description);
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


}