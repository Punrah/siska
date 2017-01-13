package com.androidhive.siska.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidhive.siska.R;
import com.androidhive.siska.app.Config;
import com.androidhive.siska.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;


public class DashboardFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View myInflate=inflater.inflate(R.layout.fragment_dashboard, container, false);



        return myInflate;
    }






}