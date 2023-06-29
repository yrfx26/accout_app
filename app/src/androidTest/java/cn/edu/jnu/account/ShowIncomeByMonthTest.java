package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
//        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        dataManager = DataManager.getDataManager();
//        billsBackUp = dataManager.loadBills(context);
//
//        List<Bill> bills = new ArrayList<>();
//        Bill bill = new Bill();
//        bill.setMoney(10000.00);
//        bill.setBillClass(Bill.INCOME_CLASS);
//        bills.add(bill);
//        bill = new Bill();
//        bill.setMoney(20000.00);
//        bill.setBillClass(Bill.EXPEND_CLASS);
//        bills.add(bill);
//        bills.add(bill);
//
//        income = dataManager.getIncome(bills);
//        dataManager.saveBills(context, bills);
    }

    @After
    public void tearDown() {
        List<Bill> bills = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        DataManager dataManager = DataManager.getDataManager();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager.saveBills(context, bills);
        dataManager.saveAccounts(context, accounts);
    }

    @Test
    public void showIncomeByMonthTest() {
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_item3), withContentDescription("记账"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.add_et_money),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("600"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.add_et_time),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                10),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withClassName(is("com.google.android.material.button.MaterialButton")), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.add_et_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("gg"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.add_bt_commit), withText("确认"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                12),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_details_income), withText("600.0"),
                        withParent(allOf(withId(R.id.constraintLayout1),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("600.0")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
