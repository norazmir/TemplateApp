package com.example.templateapp.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.ComposePathEffect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.templateapp.R;
import com.example.templateapp.data.model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCompanyProfileDialog extends AppCompatDialogFragment {
    private static final String TAG = "UpdateCompanyDialog";
    private UpdateCompanyProfileDialogListener listener;
    private EditText edtUName;
    private EditText edtUAddress;
    private EditText edtUEmail;
    private EditText edtUContactNumber;
    private Activity activity;
    private String checkService;

    ArrayList<String> serviceList = new ArrayList<>(Arrays.asList("ServiceA", "ServiceB", "ServiceC", "ServiceD", "ServiceE", "ServiceF"));

    DatabaseReference companyRef;
    DatabaseReference updateCompanyRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_company_profile_dialog, null);
        edtUName = view.findViewById(R.id.updateCompanyName);
        edtUAddress = view.findViewById(R.id.updateCompanyAddress);
        edtUEmail = view.findViewById(R.id.updateCompanyEmail);
        edtUContactNumber = view.findViewById(R.id.updateCompanyContactNumber);

        Bundle data = getArguments();
        String displayName = data.getString("company_name");
        String displayAddress = data.getString("company_address");
        String displayEmail = data.getString("company_email");
        String displayContactNumber = data.getString("company_contact_number");

        companyRef = FirebaseDatabase.getInstance().getReference();


        edtUName.setText(displayName);
        edtUAddress.setText(displayAddress);
        edtUEmail.setText(displayEmail);
        edtUContactNumber.setText(displayContactNumber);



        builder.setView(view)
                .setTitle("Update Company")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateCompany();
                    }
                });
        return builder.create();
    }

    private void updateCompany() {
        Bundle data = getArguments();
        String uid = data.getString("companyUid");
        String name = edtUName.getText().toString();
        String address = edtUAddress.getText().toString();
        String email = edtUEmail.getText().toString();
        String contact_num = edtUContactNumber.getText().toString();
        listener.applyTexts(name, address, email, contact_num);
        Map updateCompanyMap = new HashMap();
        updateCompanyMap.put("company_name", name);
        updateCompanyMap.put("company_address", address);
        updateCompanyMap.put("company_email", email);
        updateCompanyMap.put("company_contact_number", contact_num);

        readData(new MyCallback() {
            @Override
            public void onCallback(DatabaseReference databaseReference) {
                Log.i(TAG, databaseReference + " value.");
                databaseReference.child(uid).updateChildren(updateCompanyMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        try {
                            if (databaseError == null)
                                Toast.makeText(activity, "Update Success.", Toast.LENGTH_LONG).show();
                                Log.i(TAG, "Test Success.");
                        }catch (Exception e){
                            Log.e(TAG, "Something wrong : " + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void readData(MyCallback myCallback){
        Bundle data = getArguments();
        String uid = data.getString("companyUid");
        companyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "Test.");
                for (String service : serviceList){
                    if (dataSnapshot.child(service).child("Company").hasChild(uid)){
                        checkService = service;
                    }
                }
                updateCompanyRef = FirebaseDatabase.getInstance().getReference(checkService).child("Company");
                myCallback.onCallback(updateCompanyRef);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface MyCallback {
        void onCallback(DatabaseReference databaseReference);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateCompanyProfileDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement UpdateCompanyProfileDialogListener");
        }
    }

    public interface UpdateCompanyProfileDialogListener{
        void applyTexts(String name, String address, String email, String contact_number);
    }


}
