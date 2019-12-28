package com.example.templateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.templateapp.Dialog.UpdateProfileDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements UpdateProfileDialog.UpdateProfileDialogListener {

    private static final String TAG = "ProfileActivity";
    DatabaseReference profileDBRef;
    FirebaseAuth auth;

    @BindView(R.id.textViewName)
    TextView nameView;
    @BindView(R.id.textViewEmail)
    TextView emailView;
    @BindView(R.id.textViewContactNumber)
    TextView contactNumberView;
    @BindView(R.id.buttonUpdateProfile)
    Button btnUpdateProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileDBRef = FirebaseDatabase.getInstance().getReference("Contractor").child(auth.getCurrentUser().getUid());

        viewProfile();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }

    private void viewProfile() {
        profileDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.child("name").getValue().toString();
                String displayEmail = dataSnapshot.child("email").getValue().toString();
                String displayContactNumber = dataSnapshot.child("contact_number").getValue().toString();

                nameView.setText(displayName);
                emailView.setText(displayEmail);
                contactNumberView.setText(displayContactNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error in the code : " + databaseError.getMessage());
            }
        });
    }

    private void openDialog() {
        String displayName = nameView.getText().toString().trim();
        String displayContactNumber = contactNumberView.getText().toString().trim();

        UpdateProfileDialog updateProfileDialog = new UpdateProfileDialog();
        updateProfileDialog.show(getSupportFragmentManager(), "Update Dialog");

        Bundle profileData = new Bundle();
        profileData.putString("name", displayName);
        profileData.putString("contact_number", displayContactNumber);
        updateProfileDialog.setArguments(profileData);
    }

    @Override
    public void applyTexts(String name, String contact_number) {
        nameView.setText(name);
        contactNumberView.setText(contact_number);
    }
}
