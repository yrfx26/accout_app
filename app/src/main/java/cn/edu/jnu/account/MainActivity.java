package cn.edu.jnu.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_item1:
                        System.out.println("item 1");
                        break;
                    case R.id.navigation_item2:
                        System.out.println("item 2");
                        break;
                    case R.id.navigation_item3:
                        System.out.println("item 3");
                        break;
                    //check id
                }
                return true;
            }
        });
        Log.i("start", "onCreate: ");

    }
}