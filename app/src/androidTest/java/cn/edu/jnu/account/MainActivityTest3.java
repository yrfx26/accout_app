package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;


import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Date;
import java.util.List;

import cn.edu.jnu.account.data.Bill;
import cn.edu.jnu.account.ui.DetailsFragment;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {
    List<Bill> billsShow;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        FragmentScenario<DetailsFragment> scenario = FragmentScenario.launchInContainer(DetailsFragment.class);
        scenario.onFragment(fragment -> {
            // 获取Fragment实例
//            DetailsFragment fragment = (DetailsFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_details);
//            if (fragment != null) {
                // 设置Fragment的数据
//                billsShow = ;
            fragment.init();

            billsShow = fragment.getBillsShow();
            if (billsShow == null) {
                System.out.println("123456");
            }
            Bill bill = new Bill();
            bill.setAccountName("工商银行");
            bill.setMoney(2000);
            bill.setTime(new Date());
            bill.setType("工资");
            bill.setBillClass(Bill.INCOME_CLASS);
            billsShow.add(bill);
            billsShow.add(bill);
            billsShow.add(bill);
            billsShow.add(bill);
            fragment.setBillsShow(billsShow);
            fragment.getRecyclerViewAdapter().notifyDataSetChanged();
//            }
//            else {
//                System.out.println("1231235213");
//            }
        });
    }


    @Test
    public void mainActivityTest3() {


        String  income = getIncome();
        String  outcome = getExpend();

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView7), withText("10000.00"),
                        withParent(allOf(withId(R.id.constraintLayout1),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText(income)));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView8), withText("2003.00"),
                        withParent(allOf(withId(R.id.constraintLayout1),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));

        textView2.check(matches(withText(outcome)));
    }


    private String getExpend() {
        double sum = 0;
        for (Bill bill: billsShow) {
            if (bill.getBillClass().equals(Bill.EXPEND_CLASS))
                sum += bill.getMoney();
        }
        System.out.println(sum);
        return String.valueOf(sum);
    }

    private String getIncome() {
        double sum = 0;
        for (Bill bill: billsShow) {
            if (bill.getBillClass().equals(Bill.INCOME_CLASS))
                sum += bill.getMoney();
        }
        System.out.println(sum);
        return String.valueOf(sum);
    }
}
