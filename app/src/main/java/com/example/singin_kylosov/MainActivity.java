package com.example.singin_kylosov;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    public class DataUser {
        public String id;
        public String login;
        public String password;

        public void setId(String _id) { this.id = _id; }
        public String getId() { return this.id; }
        public void setLogin(String _login) { this.login = _login; }
        public String getLogin() { return this.login; }
        public void setPassword(String _password) { this.password = _password; }
        public String getPassword() { return this.password; }
    }

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

    public  String login, password;
    public void onAutorization(View view)
    {
        TextView tv_login = findViewById(R.id.SigninLogin);
        login = tv_login.getText().toString();

        TextView tv_password = findViewById(R.id.SigninPassword);
        password = tv_password.getText().toString();

        GetDataUser gdu = new GetDataUser();
        gdu.execute();
    }
}