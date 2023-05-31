package cn.edu.jnu.account.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.jnu.account.R;
import cn.edu.jnu.account.data.Bill;


public class DetailsFragment extends Fragment {
    private View view;
    private CustomAdapter recyclerViewAdapter;
    private List<Bill> billsShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("bill", this, new FragmentResultListener() {
            public void onFragmentResult(String key,Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                Bill newbill = bundle.getParcelable("newbill");
                // Do something with the result...
                billsShow.add(newbill);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);

        billsShow = new ArrayList<>();
        Bill bill = new Bill();
        bill.setAccountName("工商银行");
        bill.setMoney(2000);
        bill.setTime(new Date());
        bill.setType("工资");
        billsShow.add(bill);
        billsShow.add(bill);
        billsShow.add(bill);

        initRecyclerView();
        recyclerViewAdapter.notifyDataSetChanged();

        return view;
    }


    // 初始化recycleView ----------------------------------------------------------------------------
    @SuppressLint("NotifyDataSetChanged")
    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.fg_details_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new CustomAdapter(billsShow);
        recyclerViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private View.OnClickListener onClickListener;
        private View.OnLongClickListener onLongClickListener;
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
                constraintLayout = view.findViewById(R.id.constraintLayout);
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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewType().setText(String.valueOf(localDataSet.get(position).getType()));
            viewHolder.getTextViewAccount().setText(localDataSet.get(position).getAccountName());
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