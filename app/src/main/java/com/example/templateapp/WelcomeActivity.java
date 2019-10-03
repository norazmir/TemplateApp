package com.example.templateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class WelcomeActivity extends AppCompatActivity {

    LottieAnimationView thumb_up;
    LottieAnimationView thumb_down;
    LottieAnimationView toggle;
    LottieAnimationView animationView;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        animationView = findViewById(R.id.animation_view);
//        startCheckAnimation();

//        thumb_up = findViewById(R.id.lav_thumbUp);
//        thumb_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                thumb_down.setProgress(0);
//                thumb_down.pauseAnimation();
//                thumb_up.playAnimation();
//                Toast.makeText(WelcomeActivity.this, "Cheers!!", Toast.LENGTH_SHORT).show();
//                //---- Your code here------
//            }
//        });
//
//        thumb_down = findViewById(R.id.lav_thumbDown);
//        thumb_down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                thumb_up.setProgress(0);
//                thumb_up.pauseAnimation();
//                thumb_down.playAnimation();
//                Toast.makeText(WelcomeActivity.this, "Boo!!", Toast.LENGTH_SHORT).show();
//                //---- Your code here------
//            }
//        });
//
//        toggle = findViewById(R.id.lav_toggle);
//        toggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeState();
//            }
//        });
    }

//    private void startCheckAnimation() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(5000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                animationView.setProgress((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//
//        if (animationView.getProgress() == 0f) {
//            animator.start();
//        } else {
//            animationView.setProgress(0f);
//        }
 //   }

    private void changeState() {
        if (flag == 0) {
            toggle.setMinAndMaxProgress(0f, 0.43f); //Here, calculation is done on the basis of start and stop frame divided by the total number of frames
            toggle.playAnimation();
            flag = 1;
            //---- Your code here------
        } else {

        }
    }
}

