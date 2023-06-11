package cn.edu.jnu.account.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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
    private View view;
    private EditText add_et_money;
    private Spinner add_spinner_type;
    private Spinner add_spinner_account;
    private Spinner add_spinner_billClass;
    private EditText add_et_time;
    private Button add_bt_commit;
    private Bill bill;
    private EditText add_et_description;
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

        this.view = inflater.inflate(R.layout.fragment_add, container, false);

        init();

        onClick();

        return this.view;
    }

    private void onClick() {
        //点击时钟框
        onClickTime();

        //点击确认页面跳转到明细
        onCLickCommit();
    }

    private void init() {
        //给五个个用户输入框绑定控件
        bandComponent();

        //初始化账户
        initAccount(accountNames, "账户1", "添加账户");

        //初始化账户消费类型
        initType();
    }

    private void onCLickCommit() {
        DetailsFragment detailsFragment = new DetailsFragment();
        add_bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click commit");
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                assert navHostFragment != null;
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_item1);
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
        typeNames.add("添加类型");

        add_spinner_type = view.findViewById(R.id.add_spinner_type);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(
                getContext(), R.layout.item_spinner, typeNames);
        arrayAdapterType.setDropDownViewResource(R.layout.item_spinner_drow_down);
        add_spinner_type.setAdapter(arrayAdapterType);
    }

    private void initAccount(ArrayList<String> accountNames, String account, String addAccount) {
        accountNames.add(account);
        accountNames.add(addAccount);
        add_spinner_account = view.findViewById(R.id.add_spinner_account);
        ArrayAdapter<String> arrayAdapterLabel = new ArrayAdapter<String>(
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