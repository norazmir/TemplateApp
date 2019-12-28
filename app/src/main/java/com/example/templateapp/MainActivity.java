package com.example.templateapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.categoryA)
    CardView cardViewA;
    @BindView(R.id.categoryB)
    CardView cardViewB;
    @BindView(R.id.categoryC)
    CardView cardViewC;
    @BindView(R.id.categoryD)
    CardView cardViewD;
    @BindView(R.id.categoryE)
    CardView cardViewE;
    @BindView(R.id.categoryF)
    CardView cardViewF;
    @BindView(R.id.buttonViewProfile)
    Button btnViewProfile;
    @BindView(R.id.buttonSignOut)
    Button btnSignOut;
    FirebaseAuth auth;
    private Boolean isGuest = false;


    @Override
    public void onStart(){
        super.onStart();
        checkAuth();
    }

    @Override
    public void onBackPressed() { }


    private void checkAuth() {
        if (auth.getCurrentUser() == null) {
            try {
                Intent guestIntent = getIntent();
                isGuest = guestIntent.getExtras().getBoolean("guest");
                if (isGuest) {
                    btnViewProfile.setVisibility(View.INVISIBLE);
                    btnViewProfile.setEnabled(false);
                    btnSignOut.setText("Keluar");
                    btnSignOut.setGravity(Gravity.CENTER);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error" + e.getMessage());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        cardViewA.setOnClickListener(this);
        cardViewB.setOnClickListener(this);
        cardViewC.setOnClickListener(this);
        cardViewD.setOnClickListener(this);
        cardViewE.setOnClickListener(this);
        cardViewF.setOnClickListener(this);
        btnViewProfile.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == cardViewA){
            Intent intentA = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentA.putExtra("uid", "serviceA");
            startActivity(intentA);
        }
        else if (view == cardViewB){
            Intent intentB = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentB.putExtra("uid", "serviceB");
            startActivity(intentB);        }
        else if (view == cardViewC){
            Intent intentC = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentC.putExtra("uid", "serviceC");
            startActivity(intentC);        }
        else if (view == cardViewD){
            Intent intentD = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentD.putExtra("uid", "serviceD");
            startActivity(intentD);        }
        else if (view == cardViewE){
            Intent intentE = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentE.putExtra("uid", "serviceE");
            startActivity(intentE);        }
        else if (view == cardViewF){
            Intent intentF = new Intent(getApplicationContext(), ViewCompanyListActivity.class);
            intentF.putExtra("uid", "serviceF");
            startActivity(intentF);        }
        else if (view == btnViewProfile){
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        else if(view == btnSignOut){
            try {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            }catch (Exception e){
                Log.i(TAG, "Error is : " + e.getMessage());
            }
        }
    }
}
