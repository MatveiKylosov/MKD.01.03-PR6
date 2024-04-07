package com.example.singin_kylosov;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
    }

    public int start_x=0;

    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                start_x=(int)event.getX();
                break;

            case MotionEvent.ACTION_UP:
                if(Math.abs((int)event.getX() - start_x) > 50){
                    if(start_x < (int)event.getX()){
                        setContentView(R.layout.signin);
                    } else {
                      setContentView(R.layout.regin);
                    }
                }
                break;
        }
        return false;
    }
}