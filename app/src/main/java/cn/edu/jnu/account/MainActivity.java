package cn.edu.jnu.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private BottomNavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bottomNav, navController);



//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        BottomNavigationView navigationView = findViewById(R.id.nav_view);
//        NavigationUI.setupWithNavController(navigationView, navController);

//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
//        assert navHostFragment != null;
//        NavController navController = navHostFragment.getNavController();
//        assert navController != null;
//        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
//        assert bottomNav !=null;
//        NavigationUI.setupWithNavController(bottomNav, navController);
//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController,
//                                             @NonNull NavDestination navDestination,
//                                             @Nullable Bundle bundle) {
//                switch (navDestination.getId()){
//                    case R.id.nav_view:
//                        System.out.println();
//                        break;
//                    default:
//                        System.out.println("ddd");
//                        break;
//                }
//            }
//        });
    }
}