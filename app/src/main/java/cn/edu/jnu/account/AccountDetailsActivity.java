package cn.edu.jnu.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.jnu.account.data.Bill;
import cn.edu.jnu.account.ui.DetailsFragment;


public class AccountDetailsActivity extends AppCompatActivity {
    private static final int RESULT_CODE_CHANGE = 1;
    private static final int RESULT_CODE_NO_CHANGE = 0;
    private DetailsFragment.CustomAdapter recyclerViewAdapter;
    private List<Bill> billsShow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        billsShow = new ArrayList<>();
        Bill bill = new Bill();
        bill.setAccountName("工商银行232");
        bill.setMoney(2000);
        bill.setTime(new Date());
        bill.setType("工资");
        billsShow.add(bill);
        billsShow.add(bill);
        billsShow.add(bill);
        billsShow.add(bill);

        initRecyclerView();
        initNavigation();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_at_account_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new DetailsFragment.CustomAdapter(billsShow);
        recyclerViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Acc.this, BookDetailsActivity.class);

            }
        });
        recyclerViewAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar_account_details);
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
        AccountDetailsActivity.this.finish();
    }

    public static class CustomAdapter extends RecyclerView.Adapter<AccountDetailsActivity.CustomAdapter.ViewHolder> {
        private View.OnClickListener onClickListener;
        private View.OnLongClickListener onLongClickListener;
        private final List<Bill> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewType;
            private final TextView textViewMoney;
            private final TextView textViewDate;
            private final ConstraintLayout constraintLayout;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                textViewType = view.findViewById(R.id.textView_type);
                textViewMoney = view.findViewById(R.id.textView_money);
                textViewDate = view.findViewById(R.id.textView_date);
                constraintLayout = view.findViewById(R.id.recyclerView_fg_account_);
            }

            public TextView getTextViewType() {
                return textViewType;
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
        public AccountDetailsActivity.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_account_details, viewGroup, false);
            return new AccountDetailsActivity.CustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountDetailsActivity.CustomAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewType().setText(String.valueOf(localDataSet.get(position).getType()));
            viewHolder.getTextViewMoney().setText(String.valueOf(localDataSet.get(position).getMoney()));
            viewHolder.getTextViewDate().setText(localDataSet.get(position).getTime());

            ConstraintLayout constraintLayout = viewHolder.getConstraintLayout();
            constraintLayout.setOnClickListener(onClickListener);
            constraintLayout.setOnLongClickListener(onLongClickListener);
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
            this.onLongClickListener = onLongClickListener;
        }
    }
}