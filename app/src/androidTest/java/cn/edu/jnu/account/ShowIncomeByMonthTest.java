package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jnu.account.data.Account;
import cn.edu.jnu.account.data.Bill;
import cn.edu.jnu.account.data.DataManager;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShowIncomeByMonthTest {
    DataManager dataManager;
    private Context context;
    private List<Bill> billsBackUp;
    private String income;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager = DataManager.getDataManager();
        billsBackUp = dataManager.loadBills(context);

        List<Bill> bills = new ArrayList<>();
        Bill bill = new Bill();
        bill.setMoney(10000.00);
        bill.setBillClass(Bill.INCOME_CLASS);
        bills.add(bill);
        bill = new Bill();
        bill.setMoney(20000.00);
        bill.setBillClass(Bill.EXPEND_CLASS);
        bills.add(bill);
        bills.add(bill);

        income = dataManager.getIncome(bills);
        dataManager.saveBills(context, bills);
    }

    @After
    public void tearDown() {
        dataManager.saveBills(context, billsBackUp);
    }

    @Test
    public void showIncomeByMonthTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.textView7), withText(income),
                        withParent(allOf(withId(R.id.constraintLayout1),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText(income)));
    }
}
