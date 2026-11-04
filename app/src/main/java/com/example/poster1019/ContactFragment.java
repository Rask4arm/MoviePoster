package com.example.poster1019;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    public ContactFragment(){
        super(R.layout.fragment_contact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailApp(getString(R.string.contact_email));
            }
        });

        // Listens for button and sets text
        Button button = view.findViewById(R.id.switchContactText);
        TextView textView = view.findViewById(R.id.app_info);

        button.setOnClickListener(view1 -> {
            textView.setText(getString(R.string.switched_text));
        });
    }

    private void openEmailApp(String emailAddress){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+emailAddress));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(),R.string.no_email_app,Toast.LENGTH_SHORT).show();
        }
    }
}
