package com.example.templateapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.templateapp.Dialog.UpdateCompanyProfileDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyProfileActivity extends AppCompatActivity implements UpdateCompanyProfileDialog.UpdateCompanyProfileDialogListener {

    private static final String TAG = "CompanyProfileActivity";
    private FirebaseAuth auth;
    private String contact_num = "";

    @BindView(R.id.textViewName)
    TextView tvName;
    @BindView(R.id.textViewContactNumber)
    TextView tvCNumber;
    @BindView(R.id.textViewAddress)
    TextView tvAddress;
    @BindView(R.id.textViewEmail)
    TextView tvEmail;
    @BindView(R.id.buttonGoWhatsApp)
    Button btnWhatsApp;
    @BindView(R.id.buttonUpdateProfile)
    Button btnUpdateCompanyProf;

    @Override
    public void onStart(){
        Log.i(TAG, "Start.");
        super.onStart();
        authCheck();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resume.");
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Company Profile");

        auth = FirebaseAuth.getInstance();

        Intent dataIntent = getIntent();
        String name = dataIntent.getStringExtra("company_name");
        String address = dataIntent.getStringExtra("company_address");
        String email = dataIntent.getStringExtra("company_email");
        contact_num = dataIntent.getStringExtra("company_contact_number");

        tvName.setText(name);
        tvAddress.setText(address);
        tvEmail.setText(email);
        tvCNumber.setText(contact_num);

        btnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=+6" + contact_num;
                goWhatsApp(url);
            }
        });
        btnUpdateCompanyProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCompanyDialog();
            }
        });
    }

    private void authCheck() {
        Intent dataIntent = getIntent();
        String current_user = dataIntent.getStringExtra("current_uid");
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().getUid().equals(current_user)) {
            try {
                btnUpdateCompanyProf.setVisibility(View.INVISIBLE);
                btnUpdateCompanyProf.setEnabled(false);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void updateCompanyDialog() {
        Intent dataIntent = getIntent();
        String uid = dataIntent.getStringExtra("companyUid");
        String displayName = tvName.getText().toString().trim();
        String displayAddress = tvAddress.getText().toString().trim();
        String displayEmail = tvEmail.getText().toString().trim();
        String displayContactNumber = tvCNumber.getText().toString().trim();

        UpdateCompanyProfileDialog updateCompanyProfileDialog = new UpdateCompanyProfileDialog();
        updateCompanyProfileDialog.show(getSupportFragmentManager(), "Update Dialog");

        Bundle companyData = new Bundle();
        companyData.putString("companyUid", uid);
        companyData.putString("company_name", displayName);
        companyData.putString("company_address", displayAddress);
        companyData.putString("company_email", displayEmail);
        companyData.putString("company_contact_number", displayContactNumber);
        updateCompanyProfileDialog.setArguments(companyData);
    }

    private void goWhatsApp(String url) {
        Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW);
        whatsAppIntent.setData(Uri.parse(url));
        startActivity(whatsAppIntent);
    }

    @Override
    public void applyTexts(String name, String address, String email, String contact_number) {
        tvName.setText(name);
        tvAddress.setText(address);
        tvEmail.setText(email);
        tvCNumber.setText(contact_number);
        this.contact_num = contact_number;
    }
}
