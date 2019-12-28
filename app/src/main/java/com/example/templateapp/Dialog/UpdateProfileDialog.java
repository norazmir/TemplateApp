package com.example.templateapp.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.templateapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileDialog extends AppCompatDialogFragment {
    private static final String TAG = "UpdateProfileDialog";
    private EditText edtName;
    private EditText edtContactNumber;
    private UpdateProfileDialogListener listener;
    private Activity activity;

    DatabaseReference updateProfRef;
    FirebaseAuth auth;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_dialog, null);
        updateProfRef = FirebaseDatabase.getInstance().getReference("Contractor");
        edtName = view.findViewById(R.id.editName);
        edtContactNumber = view.findViewById(R.id.editContactNumber);

        auth = FirebaseAuth.getInstance();
        String displayName = getArguments().getString("name");
        String displayContactNumber = getArguments().getString("contact_number");

        edtName.setText(displayName);
        edtContactNumber.setText(displayContactNumber);

        builder.setView(view)
                .setTitle("Update Profile")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateContractor();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateProfileDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement UpdateProfileDialogListener");
        }
    }

    public interface UpdateProfileDialogListener{
        void applyTexts(String name, String contact_number);
    }

    public void updateContractor(){
        String uid = auth.getCurrentUser().getUid();
        String name = edtName.getText().toString();
        String contact_num = edtContactNumber.getText().toString();
        listener.applyTexts(name, contact_num);
        Map updateMap = new HashMap();
        updateMap.put("name", name);
        updateMap.put("contact_number", contact_num);
        updateProfRef.child(uid).updateChildren(updateMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                try {
                    if (databaseError == null)
                        Toast.makeText(activity, "Update Success.", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Log.e(TAG, "Something wrong : " + e.getMessage());
                }
            }
        });
    }
}
