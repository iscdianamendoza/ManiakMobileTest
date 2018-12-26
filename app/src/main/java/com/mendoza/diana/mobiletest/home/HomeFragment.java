package com.mendoza.diana.mobiletest.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mendoza.diana.mobiletest.R;
import com.mendoza.diana.mobiletest.login.SessionManager;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        Holder holder = new Holder();

        holder.tvWelcomeMessage = view.findViewById(R.id.tv_welcome_message);
        holder.tvWelcomeUser = view.findViewById(R.id.tv_welcome_user);

        holder.tvWelcomeMessage.setText(getString(R.string.welcome_message));
        holder.tvWelcomeUser.setText(getString(R.string.welcome_user, sessionManager.getUsername()));

        return view;
    }

    private class Holder{
        TextView tvWelcomeMessage;
        TextView tvWelcomeUser;
    }
}
