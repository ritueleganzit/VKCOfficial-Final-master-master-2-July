package com.eleganzit.vkcofficial.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.vkcofficial.HomeActivity;
import com.eleganzit.vkcofficial.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationandMessage extends Fragment {

TextView notification,message;
    public NotificationandMessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_notificationand_message, container, false);
        notification=v.findViewById(R.id.notifications);
        message=v.findViewById(R.id.messages);



        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification.setBackgroundResource(R.drawable.transparentdark_bg);
                message.setBackgroundResource(R.drawable.transparent_bg);

                NotificationandMessage myPhotosFragment = new NotificationandMessage();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containers, myPhotosFragment, "TAG")
                        .commit();
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setBackgroundResource(R.drawable.transparentdark_bg);
                notification.setBackgroundResource(R.drawable.transparent_bg);
                MessageFragment myPhotosFragment = new MessageFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containers, myPhotosFragment, "TAG")
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeActivity.textTitle.setText("NOTIFICATION");

        notification.setBackgroundResource(R.drawable.transparentdark_bg);
        message.setBackgroundResource(R.drawable.transparent_bg);
        NotificationFragment myPhotosFragment = new NotificationFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containers, myPhotosFragment, "TAG")
                .commit();
    }


}
