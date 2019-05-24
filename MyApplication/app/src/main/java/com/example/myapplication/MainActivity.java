package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.async.LoginTask;

import org.json.JSONObject;

// вход в систему
public class MainActivity extends AppCompatActivity {

    private Button button2; // войти

    private EditText editText; // пароль

    private EditText editText3; // логин

    private TextView textView4; // ошибки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = findViewById(R.id.button2);
        editText = findViewById(R.id.editText);
        editText3 = findViewById(R.id.editText3);
        textView4 = findViewById(R.id.textView4);
        textView4.setText("");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LoginTask loginTask = new LoginTask(
                            editText3.getText().toString(),
                            editText.getText().toString()
                    );
                    String response = loginTask.execute().get();
                    if(response == null) {
                        if(textView4.getText().equals("Вход не выполнен"))
                            textView4.setText("Попробуйте ещё раз");
                        else if (textView4.getText().equals("Попробуйте ещё раз"))
                            textView4.setText("Попытка - не пытка");
                        else
                            textView4.setText("Вход не выполнен");
                        textView4.setTextColor(Color.RED);
                    } else {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject user = jsonObj.getJSONObject("user");
                        JSONObject tokens = jsonObj.getJSONObject("tokens");
                        String token = tokens.getString("refresh");
                        String fullName = user.getString("fullName");
                        Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                        intent.putExtra("fullName", fullName);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        });

    }


}
