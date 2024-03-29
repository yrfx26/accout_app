package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
public class AddAccountTest {
    private Account accountAdd;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        accountAdd = new Account();
        accountAdd.setName("12");
        accountAdd.setRemarks("34");
        accountAdd.setMoney(56.0);
    }

    @After
    public void teardown() {
        List<Bill> bills = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        DataManager dataManager = DataManager.getDataManager();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager.saveBills(context, bills);
        dataManager.saveAccounts(context, accounts);
    }


    @Test
    public void addAccountTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_item4), withContentDescription("账户"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_add_account), withText("添加账户"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_account),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editView_account_name), withText("Name"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("12"));

//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.editView_account_name), withText("Name"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
//                                        0),
//                                5),
//                        isDisplayed()));
//        appCompatEditText2.perform(replaceText("12"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editView_account_name), withText("12"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editView_account_remarks),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("34"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editView_account_money), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("56.0"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editView_account_money), withText("56.0"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button_confirm_add_account), withText("保存"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_account_item_name), withText(accountAdd.getName()),
                        withParent(allOf(withId(R.id.constraintLayout_account_item),
                                withParent(withId(R.id.recyclerView_fg_account)))),
                        isDisplayed()));
        textView.check(matches(withText(accountAdd.getName())));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_account_item_money), withText(String.valueOf(accountAdd.getMoney())),
                        withParent(allOf(withId(R.id.constraintLayout_account_item),
                                withParent(withId(R.id.recyclerView_fg_account)))),
                        isDisplayed()));
        textView2.check(matches(withText(String.valueOf(accountAdd.getMoney()))));


        List<Bill> bills = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        DataManager dataManager = DataManager.getDataManager();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager.saveBills(context, bills);
        dataManager.saveAccounts(context, accounts);
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
