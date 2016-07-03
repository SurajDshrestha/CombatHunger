package com.aashish.dell.findmearent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dell on 23-Mar-16.
 */
public class Notconnected extends AppCompatActivity implements View.OnClickListener {

    Button bretry, bexit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notconnected);

        bretry = (Button) findViewById(R.id.bretry);
        bexit = (Button) findViewById(R.id.bexit);

        bretry.setOnClickListener(this);
        bexit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bretry:
                startActivity(new Intent(this, profile.class));
                break;
            case R.id.bexit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }

    }

}
