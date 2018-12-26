package com.mendoza.diana.mobiletest.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mendoza.diana.mobiletest.interfaces.OnTaskCompleted;
import com.mendoza.diana.mobiletest.models.UserModel;
import com.mendoza.diana.mobiletest.sync.Synchronization;

public class LoginTask extends AsyncTask<Void, Void, Object> {

    private ProgressDialog dialog;
    private OnTaskCompleted listener;
    private Synchronization sync;
    private UserModel user;

    LoginTask(OnTaskCompleted listener, UserModel user, Context context){
        this.listener = listener;
        this.user = user;
        sync = new Synchronization();
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Login in...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return sync.loginSync(user);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        if (!LoginTask.this.isCancelled() && dialog != null) {
            dialog.dismiss();
        }
        if (result instanceof Exception){
            listener.onTaskError(result);
        } else {
            listener.onTaskCompleted(result);
        }

    }

}
