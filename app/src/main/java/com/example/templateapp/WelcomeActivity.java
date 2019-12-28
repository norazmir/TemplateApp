package com.example.templateapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WelcomeActivity";

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.btn_enter_guest)
    Button btnGuest;
    FirebaseAuth auth;

    private Boolean userGuest = true;
    private DatabaseReference guestReference;

    @Override
    public void onStart(){
        super.onStart();
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        guestReference = FirebaseDatabase.getInstance().getReference("Guest");

        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnGuest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSignUp){
            startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
        }
        else if (view == btnSignIn){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        else if (view == btnGuest){
            String uid = guestReference.push().getKey();
            String name = "guest";
            HashMap<String, String> guestMap = new HashMap<>();
            guestMap.put("Guest_id", uid);
            guestMap.put("Guest_name", name);
            guestReference.setValue(guestMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent guestIntent = new Intent(getApplicationContext(), MainActivity.class);
                    guestIntent.putExtra("guest", userGuest);
                    startActivity(guestIntent);
                    finish();
                }
            });
        }
    }
}

