package com.rubik.spyapp_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // stop listening to touches
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
