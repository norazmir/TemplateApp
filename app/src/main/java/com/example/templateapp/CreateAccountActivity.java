package com.example.templateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.templateapp.Database.DatabaseHelper;
import com.example.templateapp.data.model.Contractor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth auth;
    DatabaseReference contractorDBRef;
    @BindView(R.id.buttonCreateAccount)
    Button btnCreateAccount;
    @BindView(R.id.editTextContactNumber)
    EditText edtTxtContactNumber;
    @BindView(R.id.editTextUsername)
    EditText edtTxtUsername;
    @BindView(R.id.editTextCreatePassword)
    EditText edtTxtCreatePassword;
    @BindView(R.id.editTextConfirmPassword)
    EditText edtTxtConfirmPassword;
    @BindView(R.id.editTextEmail)
    EditText edtTxtEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        signUpUser();
    }

    public void signUpUser(){
        String name = edtTxtUsername.getText().toString().trim();
        String email = edtTxtEmail.getText().toString().trim();
        String password = edtTxtCreatePassword.getText().toString().trim();
        String confPassword = edtTxtConfirmPassword.getText().toString().trim();
        String contact_num = edtTxtContactNumber.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(confPassword) || !confPassword.equals(password)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(contact_num)){
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            createContractor();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure : ", task.getException());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createContractor() {
        String contractorID = auth.getCurrentUser().getUid();
        String name = edtTxtUsername.getText().toString();
        String email = edtTxtEmail.getText().toString();
        String password = edtTxtCreatePassword.getText().toString();
        String confPassword = edtTxtConfirmPassword.getText().toString();
        String contact_num = edtTxtContactNumber.getText().toString();

        contractorDBRef = FirebaseDatabase.getInstance().getReference("Contractor").child(contractorID);
        HashMap<String, String> contractorMap = new HashMap<>();
        contractorMap.put("name", name);
        contractorMap.put("email", email);
        contractorMap.put("contact_number", contact_num);
        contractorMap.put("password", password);
        contractorMap.put("confirm_password", confPassword);

        contractorDBRef.setValue(contractorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registered Succesful.", Toast.LENGTH_SHORT).show();
                    Intent contractorIntent = new Intent(getApplicationContext(), MainActivity.class);
                    contractorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(contractorIntent);
                }
                else
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
