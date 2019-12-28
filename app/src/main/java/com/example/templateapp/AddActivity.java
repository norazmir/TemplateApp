package com.example.templateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.templateapp.data.model.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AddActivity";

    DatabaseReference addCompanyRef;
    FirebaseAuth auth;

    @BindView(R.id.buttonAddCompany)
    Button btnAddCompany;
    @BindView(R.id.textViewName)
    EditText edtNameView;
    @BindView(R.id.textViewAddress)
    EditText edtAddressView;
    @BindView(R.id.textViewEmail)
    EditText edtEmailView;
    @BindView(R.id.textViewContactNumber)
    EditText edtContactNumberView;
    private String activity = "value";
    private String service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Add Company");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        serviceView();
        addCompanyRef = FirebaseDatabase.getInstance().getReference(service);
        btnAddCompany.setOnClickListener(this);
    }

    private void serviceView() {
        Intent serviceIntent = getIntent();
        if (serviceIntent != null)
            activity = serviceIntent.getStringExtra("uid");

        switch (activity){
            case "ServiceA":
                service = "ServiceA";
                break;
            case "ServiceB":
                service = "ServiceB";
                break;
            case "ServiceC":
                service = "ServiceC";
                break;
            case "ServiceD":
                service = "ServiceD";
                break;
            case "ServiceE":
                service = "ServiceE";
                break;
            case "ServiceF":
                service = "ServiceF";
                break;
        }
    }

    @Override
    public void onClick(View view) {
        String current_uid = auth.getCurrentUser().getUid();
        String company_uid = addCompanyRef.child("posts").push().getKey();
        String company_name = edtNameView.getText().toString().trim();
        String company_address = edtAddressView.getText().toString().trim();
        String company_email = edtEmailView.getText().toString().trim();
        String company_contact_number = edtContactNumberView.getText().toString().trim();

        Company company = new Company(company_uid, company_name, company_address, company_email, company_contact_number, current_uid);
        addCompanyRef.child("Company").child(company_uid).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Add Successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("company_uid", company_uid);
                    Log.i(TAG, "UIDDDD : " + company_uid);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "Something Wrong : " + task.getResult().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
