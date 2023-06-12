package cn.edu.jnu.account;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
import cn.edu.jnu.account.data.DataManager;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShowTotalMoneyByAccountTest {
    DataManager dataManager;
    List<Account> accountsBackUp;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataManager = DataManager.getDataManager();
        accountsBackUp = dataManager.loadAccounts(context);
        List<Account> accounts = new ArrayList<>();

        Account account = new Account();
        account.setMoney(10000.00);
        accounts.add(account);
        account = new Account();
        account.setMoney(20000.00);
        accounts.add(account);
        accounts.add(account);
        dataManager.saveAccounts(context, accounts);
    }

    @After
    public void tearDown() {
        dataManager.saveAccounts(context, accountsBackUp);
    }

    @Test
    public void showTotalMoneyByAccountTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_item4), withContentDescription("账户"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_account_total_money),
                        withParent(allOf(withId(R.id.constraintLayout2),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText(dataManager.getTotalAccountMoney())));
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
