package cn.edu.jnu.account.ui;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.jnu.account.AccountAddActivity;
import cn.edu.jnu.account.BillDetailsActivity;
import cn.edu.jnu.account.R;
import cn.edu.jnu.account.data.Bill;
import cn.edu.jnu.account.data.DataManager;


interface OnItemClickListener {
    void onItemClick(View view, int position);
    boolean onItemLongClick(View view,int position);
}


public class DetailsFragment extends Fragment implements OnItemClickListener{
    private View view;
    private CustomAdapter recyclerViewAdapter;
    private List<Bill> billsShow;
    private DataManager dataManager;


    private final ActivityResultLauncher<Intent> billDetailsLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (null != result) {

                }
            }
    );

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public CustomAdapter getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }

    public List<Bill> getBillsShow() {
        return billsShow;
    }

    public void setBillsShow(List<Bill> billsShow) {
        this.billsShow = billsShow;
    }

    public void setRecyclerViewAdapter(CustomAdapter recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiveDataFromAddFragment();
    }

    private void receiveDataFromAddFragment() {
        getParentFragmentManager().setFragmentResultListener("newbill", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                Bill newbill = bundle.getParcelable("newbill");
                // Do something with the result...
                if (null != newbill){
                    billsShow.add(newbill);
                    Log.i("data", "onFragmentResult: " + newbill.getTime());
                    DataManager.getDataManager().saveBills(getActivity(),billsShow);
                    updateTextView();
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                else {
                    Log.i("newbill","Oncreate: newbill is null");
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        dataManager = DataManager.getDataManager();
        billsShow = dataManager.loadBills(getActivity());

        init();
        return view;
    }

    public void init() {
        initRecyclerView();

        updateTextView();
    }

    // 更新界面数据 ----------------------------------------------------------------------------------
    private void updateTextView() {
        TextView textViewIncome = view.findViewById(R.id.textView_details_income);
        TextView textViewExpend = view.findViewById(R.id.textView_details_expend);

        String income = dataManager.getIncome(billsShow);
        String expend = dataManager.getExpend(billsShow);
        textViewIncome.setText(income);
        textViewExpend.setText(expend);
        Log.i("视图更新", "收入="+income);
        Log.i("视图更新", "支出="+expend);
    }

    // recycleView的item点击事件----------------------------------------------------------------------
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), BillDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bill", billsShow.get(position));
        System.out.println("bill --> " + billsShow.get(position));
        bundle.putString("time",billsShow.get(position).getTime());
        intent.putExtras(bundle);
        billDetailsLaunch.launch(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.account, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_account_item_delete:
                        dataManager.deleteBill(getActivity(), billsShow.get(position), billsShow);
                        recyclerViewAdapter.notifyDataSetChanged();
                        updateTextView();
                        Toast.makeText(getContext(), "删除账单成功", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popupMenu.show();

        return true;
    }

    // 初始化recycleView ----------------------------------------------------------------------------
    @SuppressLint("NotifyDataSetChanged")
    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.fg_details_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new CustomAdapter(billsShow);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<Bill> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewType;
            private final TextView textViewAccount;
            private final TextView textViewMoney;
            private final TextView textViewDate;
            private final ConstraintLayout constraintLayout;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textViewType = view.findViewById(R.id.textView_type);
                textViewAccount = view.findViewById(R.id.textView_account);
                textViewMoney = view.findViewById(R.id.textView_money);
                textViewDate = view.findViewById(R.id.textView_date);
                constraintLayout = view.findViewById(R.id.recyclerView_fg_account_);
            }

            public TextView getTextViewType() {
                return textViewType;
            }

            public TextView getTextViewAccount() {
                return textViewAccount;
            }

            public TextView getTextViewMoney() {
                return textViewMoney;
            }

            public TextView getTextViewDate() {
                return textViewDate;
            }

            public ConstraintLayout getConstraintLayout() {
                return constraintLayout;
            }
        }


        public CustomAdapter(List<Bill> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_details, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
            viewHolder.getTextViewType().setText(String.valueOf(localDataSet.get(position).getType()));
            viewHolder.getTextViewAccount().setText(localDataSet.get(position).getAccountName());
            viewHolder.getTextViewMoney().setText(String.valueOf(localDataSet.get(position).getMoney()));
            viewHolder.getTextViewDate().setText(localDataSet.get(position).getTime());

            ConstraintLayout constraintLayout = viewHolder.getConstraintLayout();
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment.this.onItemClick(v, position);
                }
            });
            constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return DetailsFragment.this.onItemLongClick(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}