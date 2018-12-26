package com.mendoza.diana.mobiletest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mendoza.diana.mobiletest.home.HomeFragment;
import com.mendoza.diana.mobiletest.login.LoginActivity;
import com.mendoza.diana.mobiletest.login.SessionManager;
import com.mendoza.diana.mobiletest.social.SocialFragment;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        sessionManager = new SessionManager(context);
        checkLogin();
    }

    private void checkLogin() {
        if (!sessionManager.isLogin()){
            redirectToLogin();
        }
        else {
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            redirectToHome();
        }
    }

    private void redirectToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void redirectToHome(){
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            Fragment fragment = null;
            if (itemId == R.id.navigation_home) {
                fragment = new HomeFragment();
            } else if (itemId == R.id.navigation_social) {
                fragment = new SocialFragment();
            } else if (itemId == R.id.navigation_logout) {
                sessionManager.logoutSession();
                redirectToLogin();
            }
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
            return true;
        }
    };

    @Override
    protected void onRestart(){
        super.onRestart();
        if (!sessionManager.isLogin()){
            this.finish();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!sessionManager.isLogin()){
            this.finish();
        }
    }

}
