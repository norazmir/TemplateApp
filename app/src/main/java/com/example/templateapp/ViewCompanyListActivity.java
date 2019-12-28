package com.example.templateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.templateapp.Adapter.CompanyAdapter;
import com.example.templateapp.data.model.Company;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ViewCompanyListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ViewCompanyListActivity";
    private DatabaseReference companyListRef;
    private List<Company> companyList = new ArrayList<>();
    private RecyclerView.Adapter companyAdapter;
    FirebaseAuth auth;
    private RecyclerView rvCompany;
    @BindView(R.id.buttonAdd)
    Button btnAddCompany;
    private String activity ="value";
    private String service;



    @Override
    public void onStart(){
        super.onStart();
        if (auth.getCurrentUser() == null)
            try {
                btnAddCompany.setVisibility(View.INVISIBLE);
                btnAddCompany.setEnabled(false);
            }catch (Exception e){
                Log.e(TAG, e.getMessage());
            }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Kontraktor Sedia Ada");
        auth = FirebaseAuth.getInstance();

        rvCompany = findViewById(R.id.rvCompany);

        serviceView();
        companyListRef = FirebaseDatabase.getInstance().getReference(service).child("Company");
        viewCompanyList();
        btnAddCompany.setOnClickListener(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRef();
        Log.i(TAG, "Test : " + databaseReference);
    }

    private void serviceView() {
        Intent serviceIntent = getIntent();
        if (serviceIntent != null)
            activity = serviceIntent.getStringExtra("uid");

        switch (activity){
            case "serviceA":
                service = "ServiceA";
                break;
            case "serviceB":
                service = "ServiceB";
                break;
            case "serviceC":
                service = "ServiceC";
                break;
            case "serviceD":
                service = "ServiceD";
                break;
            case "serviceE":
                service = "ServiceE";
                break;
            case "serviceF":
                service = "ServiceF";
                break;
        }
    }

    private void viewCompanyList() {
        companyListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Company company = postSnapshot.getValue(Company.class);
                    companyList.add(company);
                }
                companyAdapter = new CompanyAdapter(getApplicationContext(), companyList);
                companyAdapter.notifyDataSetChanged();
                rvCompany.setAdapter(companyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Something wrong : " + databaseError.getMessage());
            }
        });
        rvCompany.setHasFixedSize(true); //set fixed size for element inn recycler view
        rvCompany.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddCompany){
            Intent addIntent = new Intent(getApplicationContext(), AddActivity.class);
            addIntent.putExtra("uid", service);
            startActivity(addIntent);
        }
    }
}
