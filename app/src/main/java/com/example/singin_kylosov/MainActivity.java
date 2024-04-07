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
    public int start_y = 0;

    public boolean onTouchEvent(MotionEvent event)
    {


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                start_x = (int)event.getX();
                start_y = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                int end_x = (int)event.getX();
                int end_y = (int)event.getY();

                // свайп влево или вправо
                if(Math.abs(end_x - start_x) > 50){
                    if(start_x < end_x){
                        setContentView(R.layout.signin);
                    } else {
                        setContentView(R.layout.regin);
                    }
                }

                // свайп вверх
                if(Math.abs(end_y - start_y) > 50 && start_y > end_y){
                    setContentView(R.layout.reset);
                }
                break;
        }
        return false;
    }
}