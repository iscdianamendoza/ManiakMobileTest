package com.mendoza.diana.mobiletest.login;

import com.mendoza.diana.mobiletest.MainActivity;
import com.mendoza.diana.mobiletest.models.UserModel;
import com.mendoza.diana.mobiletest.interfaces.OnTaskCompleted;
import com.mendoza.diana.mobiletest.R;
import com.mendoza.diana.mobiletest.utils.NetworkUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        holder = new Holder();

        holder.etUsername = findViewById(R.id.et_username);
        holder.etPassword = findViewById(R.id.et_password);
        holder.btnLogin = findViewById(R.id.btn_login);
        holder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        holder.etUsername.setError(null);
        holder.etPassword.setError(null);
        final String username = holder.etUsername.getText().toString().trim();
        final String password = holder.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            holder.etUsername.setError(getString(R.string.required_field));
            return;
        }
        else if (TextUtils.isEmpty(password)){
            holder.etPassword.setError(getString(R.string.required_field));
            return;
        }

        if (NetworkUtils.isNetworkAvailable(context)) {
            final UserModel user = new UserModel();
            user.setUsername(username);
            user.setPassword(password);
            LoginTask loginTask = new LoginTask(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object result) {
                    SessionManager sessionManager = new SessionManager(context);
                    user.setToken(result.toString());
                    Log.i("token", result.toString());
                    sessionManager.createSession(user);
                    redirectToMain();
                }

                @Override
                public void onTaskError(Object result) {
                    Toast.makeText(context, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                }

            }, user,LoginActivity.this);
            loginTask.execute();
        } else {
            Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
    }

    private void redirectToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private static class Holder{
        Button btnLogin;
        EditText etUsername;
        EditText etPassword;
    }
}
