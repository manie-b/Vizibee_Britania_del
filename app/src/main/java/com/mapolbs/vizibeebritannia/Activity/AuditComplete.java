package com.mapolbs.vizibeebritannia.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.R;

public class AuditComplete extends Activity {
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.audit_completion);
        imageView= (ImageView) findViewById(R.id.imageView4);
        textView = (TextView) findViewById(R.id.textView7);
        textView.setVisibility(View.GONE);
        // set animation
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        imageView.startAnimation(animZoomIn);



        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                textView.setVisibility(View.VISIBLE);

            }
        };
        handler.postDelayed(r, 1000);
        handler.postDelayed(new Runnable() {
            public void run() {

                AuditComplete.this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 2000);

    }
}