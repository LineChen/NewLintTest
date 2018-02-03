package com.line.newlinttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "sdsdsd", Toast.LENGTH_SHORT).show();
        Log.e("MainActivity", "MainActivity");

    }



    private void test(){
        Log.e("MainActivity", "test");
    }
}
