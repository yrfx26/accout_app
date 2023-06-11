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

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import cn.edu.jnu.account.data.Bill;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddBillTest {

    Bill bill;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        bill = new Bill();
        bill.setBillClass("吃");
        bill.setAccountName("账户1");
        bill.setMoney(200.0);
        bill.setTime(new Date(System.currentTimeMillis()));
        bill.setType(Bill.INCOME_CLASS);
        bill.setDescription("这是一个测试");
    }

    @After
    public void tearDown(){

    }

    @Test
    public void addBillTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_item3), withContentDescription("记账"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.add_et_money),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(String.valueOf(bill.getMoney())), closeSoftKeyboard());

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
        appCompatEditText3.perform(replaceText(bill.getDescription()), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.add_bt_commit), withText("确认"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                12),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_type), withText(bill.getBillClass()),
                        withParent(allOf(withId(R.id.recyclerView_fg_account_),
                                withParent(withId(R.id.fg_details_recycleView)))),
                        isDisplayed()));
        textView.check(matches(withText(bill.getBillClass())));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_account), withText(bill.getAccountName()),
                        withParent(allOf(withId(R.id.recyclerView_fg_account_),
                                withParent(withId(R.id.fg_details_recycleView)))),
                        isDisplayed()));
        textView2.check(matches(withText(bill.getAccountName())));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView_money), withText(String.valueOf(bill.getMoney())),
                        withParent(allOf(withId(R.id.recyclerView_fg_account_),
                                withParent(withId(R.id.fg_details_recycleView)))),
                        isDisplayed()));
        textView3.check(matches(withText(String.valueOf(bill.getMoney()))));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView_date), withText(bill.getTime()),
                        withParent(allOf(withId(R.id.recyclerView_fg_account_),
                                withParent(withId(R.id.fg_details_recycleView)))),
                        isDisplayed()));
        textView4.check(matches(withText(bill.getTime())));
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
