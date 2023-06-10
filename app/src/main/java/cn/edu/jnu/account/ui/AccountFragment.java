package cn.edu.jnu.account.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.account.AccountAddActivity;
import cn.edu.jnu.account.AccountDetailsActivity;
import cn.edu.jnu.account.MainActivity;
import cn.edu.jnu.account.R;
import cn.edu.jnu.account.data.Account;
import cn.edu.jnu.account.data.DataManager;


public class AccountFragment extends Fragment {
    private CustomAdapter recyclerViewAdapter;
    private View view;
    private List<Account> accountsShow;
    private DataManager dataManager;

    private final ActivityResultLauncher<Intent> accountAddLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (null != result) {

                }
            }
    );

    private final ActivityResultLauncher<Intent> accountDetailsLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (null != result) {

                }
            }
    );

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_account, container, false);
        accountsShow = DataManager.getDataManager().loadAccounts(getActivity());

        Account account = new Account();
        account.setName("工商银行卡");
        account.setMoney(10000.00);
        account.setRemarks("备注");
        accountsShow.add(account);
        accountsShow.add(account);
        accountsShow.add(account);

        initAddButton();
        initRecyclerView();
        recyclerViewAdapter.notifyDataSetChanged();
        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_fg_account);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new CustomAdapter(accountsShow);
        recyclerViewAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AccountDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("accountItem", accountsShow.get(position));
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                accountDetailsLaunch.launch(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initAddButton() {
        Button addButton = view.findViewById(R.id.button_add_account);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountAddActivity.class);
                accountAddLaunch.launch(intent);
            }
        });
    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>  {
    private List<Account> localDataSet;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewMoney;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            constraintLayout = view.findViewById(R.id.constraintLayout_account_item);
            textViewName = view.findViewById(R.id.textView_account_item_name);
            textViewMoney = view.findViewById(R.id.textView_account_item_money);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewMoney() {
            return textViewMoney;
        }

        public ConstraintLayout getConstraintLayout() {
            return constraintLayout;
        }
    }


    public CustomAdapter(List<Account> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_account, viewGroup, false);
        return new CustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.getTextViewName().setText(localDataSet.get(position).getName());
        viewHolder.getTextViewMoney().setText(String.valueOf(localDataSet.get(position).getMoney()));


        ConstraintLayout constraintLayout = viewHolder.getConstraintLayout();
            if (onItemClickListener!=null){
                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
                constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener.onItemLongClick(v, position);
                        return true;
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}