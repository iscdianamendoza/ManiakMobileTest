package com.mendoza.diana.mobiletest.social;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mendoza.diana.mobiletest.interfaces.OnTaskCompleted;
import com.mendoza.diana.mobiletest.sync.Synchronization;

public class SocialTask extends AsyncTask<Void, Void, Object> {

    private ProgressDialog dialog;
    private OnTaskCompleted listener;
    private Synchronization sync;
    private String token;

    SocialTask(OnTaskCompleted listener, String token, Context context) {
        this.listener = listener;
        this.token = token;
        sync = new Synchronization();
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Synchronizing...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return sync.socialSync(token);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        if (!SocialTask.this.isCancelled() && dialog != null) {
            dialog.dismiss();
        }
        if (result instanceof Exception){
            listener.onTaskError(result);
        }
        else {
            listener.onTaskCompleted(result);
        }
    }
}