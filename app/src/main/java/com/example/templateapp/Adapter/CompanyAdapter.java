package com.example.templateapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.templateapp.CompanyProfileActivity;
import com.example.templateapp.R;
import com.example.templateapp.data.model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private List<Company> companyList;
    private DatabaseReference databaseReference;
    DatabaseReference databaseReferenceA = FirebaseDatabase.getInstance().getReference("ServiceA").child("Company");
    DatabaseReference databaseReferenceB = FirebaseDatabase.getInstance().getReference("ServiceB").child("Company");
    DatabaseReference databaseReferenceC = FirebaseDatabase.getInstance().getReference("ServiceC").child("Company");
    DatabaseReference databaseReferenceD = FirebaseDatabase.getInstance().getReference("ServiceD").child("Company");
    DatabaseReference databaseReferenceE = FirebaseDatabase.getInstance().getReference("ServiceE").child("Company");
    DatabaseReference databaseReferenceF = FirebaseDatabase.getInstance().getReference("ServiceF").child("Company");
    private String activity = "value";

    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.service_layout, parent, false);

        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        final Company company = companyList.get(position);

        holder.companyNameView.setText(company.getCompany_name());
        holder.companyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyList.clear();
                Intent dataIntent = new Intent(context, CompanyProfileActivity.class);
                dataIntent.putExtra("companyUid", company.getCompanyUid());
                dataIntent.putExtra("company_name", company.getCompany_name());
                dataIntent.putExtra("company_address", company.getCompany_address());
                dataIntent.putExtra("company_email", company.getCompany_email());
                dataIntent.putExtra("company_contact_number", company.getCompany_contact_number());
                dataIntent.putExtra("current_uid", company.getCurrent_uid());
                dataIntent.putExtra("uid", "service");
                context.startActivity(dataIntent);
            }
        });
    }

    @Override
    public int getItemCount() { return companyList.size(); }

    class CompanyViewHolder extends RecyclerView.ViewHolder{
        TextView companyNameView;
        CardView companyLayout;

        public CompanyViewHolder(View view){
            super(view);

            companyNameView = view.findViewById(R.id.textViewName);
            companyLayout = view.findViewById(R.id.cvCompany);
        }
    }
}
