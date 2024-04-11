package com.example.singin_kylosov;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
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
    ArrayList<DataUser> dataUser = new ArrayList<>();
    class SetDataUser extends AsyncTask<Void, Void, Void> {
        String body;
        @Override
        protected Void doInBackground(Void... params) {
            Document doc_b = null;
            try {
                doc_b = Jsoup.connect("http://192.168.0.108/regin.php?login=" + login + "&password=" + password).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc_b != null) {
                body = doc_b.text();
            } else body = "Ошибка!";
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (body.length() != 0) {
                if (body.contains("0")) AlertDialog("Авторизация", "Пользователь с таким логином существует.");
                else if (body.contains("1")) AlertDialog("Авторизация", "Пользователь успешно зарегистрирован.");
            } else AlertDialog("Авторизация", "Ошибка данных.");
        }
    }
    public void AlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    class GetDataUser extends AsyncTask<Void, Void, Void> {
        String body;
        @Override
        protected Void doInBackground(Void... params) {
            Document doc_b = null;
            try {doc_b = Jsoup.connect("http://192.168.0.108/index.php?login=" + login + "&password=" + password).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc_b != null) {
                body = doc_b.text();
                System.out.println(doc_b.text());
            } else
            {
                body = "Ошибка!";
            }

            return null;
        }
        //                System.out.println("Ошибка!");

        public void saveFile() {
            String fileName = "testFile.txt";
            String fileContents = "login="+login+"&password="+password;
            FileOutputStream outputStream;

            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), fileName);
                outputStream = new FileOutputStream(file);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                System.out.println("!--------------------");
                e.printStackTrace();
                System.out.println("--------------------!");
            }
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if (body.length() != 0) {
                    JSONArray jsonArray = new JSONArray(body);

                    dataUser.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonRead = jsonArray.getJSONObject(i);

                        DataUser duUser = new DataUser();
                        duUser.setId(jsonRead.getString("id"));
                        duUser.setLogin(jsonRead.getString("login"));
                        duUser.setPassword(jsonRead.getString("password"));

                        dataUser.add(duUser);
                    }
                    if (dataUser.size() != 0) {
                        AlertDialog("Авторизация", "Пользователь авторизован.");
                        saveFile();
                    }
                } else AlertDialog("Авторизация", "Пользователя с таким логином и паролем не существует.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
    public void onRegistration(View view) {
        TextView tv_login = findViewById(R.id.ReginLogin);
        TextView tv_password = findViewById(R.id.ReginPassword);
        TextView tv_password2 = findViewById(R.id.repeatPassword);

        String a = tv_password.getText().toString();
        String b = tv_password2.getText().toString();

        if (a.contains(b)) {
            login = tv_login.getText().toString();
            password = tv_password.getText().toString();

            SetDataUser sdu = new SetDataUser();
            sdu.execute();
        } else AlertDialog("Авторизация", "Пароли не совпадают");
    }
}