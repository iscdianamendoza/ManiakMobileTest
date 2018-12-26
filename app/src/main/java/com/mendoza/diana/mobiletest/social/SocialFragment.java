package com.mendoza.diana.mobiletest.social;

import com.mendoza.diana.mobiletest.R;
import com.mendoza.diana.mobiletest.interfaces.OnTaskCompleted;
import com.mendoza.diana.mobiletest.login.SessionManager;
import com.mendoza.diana.mobiletest.utils.NetworkUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class SocialFragment extends Fragment {

    private Holder holder;
    private Context context;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_social, container, false);

        context = requireContext();
        holder = new Holder();
        SessionManager sessionManager = new SessionManager(context);

        holder.lvSocial = view.findViewById(R.id.lv_social);
        if (NetworkUtils.isNetworkAvailable(context)){
            SocialTask socialTask = new SocialTask(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object result) {
                        ArrayList<String> socialList = (ArrayList<String>) result;
                        ArrayAdapter<String> aaSocialList = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, socialList);
                        holder.lvSocial.setAdapter(aaSocialList);
                }

                @Override
                public void onTaskError(Object result) {
                    Toast.makeText(context, getString(R.string.sync_failed), Toast.LENGTH_LONG).show();
                }

            }, sessionManager.getToken(), getContext());
            socialTask.execute();
        }
        return view;
    }

    private static class Holder{
        ListView lvSocial;
    }

}
