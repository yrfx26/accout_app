package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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
public class ViewBillDetailsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        List<Bill> bills = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        DataManager dataManager = DataManager.getDataManager();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager.saveBills(context, bills);
        dataManager.saveAccounts(context, accounts);
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
    public void viewBillDetailsTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_item3),
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
        appCompatEditText.perform(replaceText("1567"), closeSoftKeyboard());

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
                allOf(withId(R.id.add_bt_commit),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                12),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_item2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_item1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_money), withText("1567.0"),
                        withParent(allOf(withId(R.id.recyclerView_fg_account_),
                                withParent(withId(R.id.fg_details_recycleView)))),
                        isDisplayed()));
        textView.perform(click());

//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.fg_details_recycleView),
//                        childAtPosition(
//                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
//                                0)));
//        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.type_bill_detail),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
//        editText.check(matches(withText("��")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.money_bill_detail),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        editText2.check(matches(withText("1567.0")));

//        ViewInteraction editText3 = onView(
//                allOf(withId(R.id.time_bill_detail),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
//                        isDisplayed()));
//        editText3.check(matches(withText("2023-6-29")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.description_bill_detail),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        editText4.check(matches(withText("gg")));
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
