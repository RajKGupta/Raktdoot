package com.example.rajk.raktdoot;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Snackbar extends AppCompatActivity {

    ViewGroup viewGroup;
    public static ImageButton home,profile,notify;
    ActivityGroup activityGroup;
    Activity activity;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        activityGroup = new ActivityGroup();

         home =(ImageButton)findViewById(R.id.home);
         profile = (ImageButton)findViewById(R.id.profile);
         notify = (ImageButton)findViewById(R.id.notify);
        home.setEnabled(true);
        profile.setEnabled(true);
        notify.setEnabled(true);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                    /*int transition = R.transition.snackbuttonclick;
                    TransitionInflater transitionInflater = TransitionInflater.from(getApplicationContext());
                    Transition trans = transitionInflater.inflateTransition(transition);

                    trans.addListener(new Transition.TransitionListener() {
                        @Override
                        public void onTransitionStart(Transition transition) {

                        }

                        @Override
                        public void onTransitionEnd(Transition transition) {
                            home.setVisibility(v.GONE);
                            int x = (v.getLeft() + v.getRight()) / 2;
                            int y = v.getTop();

                            float r = (float) Math.max(v.getWidth(), v.getHeight());

                            Animator animation = ViewAnimationUtils.createCircularReveal(v, x, y, 0, r);
                            viewGroup.setVisibility(View.VISIBLE);
                            animation.start();
                        }

                        @Override
                        public void onTransitionCancel(Transition transition) {

                        }

                        @Override
                        public void onTransitionPause(Transition transition) {

                        }

                        @Override
                        public void onTransitionResume(Transition transition) {

                        }
                    });

                    //TransitionManager.beginDelayedTransition(viewGroup,trans);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                    home.setLayoutParams(layoutParams);*/

                    Intent i1 = new Intent(getApplicationContext(), Main2Activity.class);

                    //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getApplicationContext(),home,"row");
                    startActivity(i1);
                }

        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i2 = new Intent(getApplicationContext(),MessageRequestShow.class);
                startActivity(i2);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreference = getSharedPreferences("UserRegistered", MODE_PRIVATE);

                String c = sharedPreference.getString("UserRegistered", "");
                if(c.equals(""))
                {
                    Toast.makeText(Snackbar.this, "You are not a registered donor", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i3 = new Intent(getApplicationContext(), profile.class);
                    startActivity(i3);
                }
            }
        });
    }

}
