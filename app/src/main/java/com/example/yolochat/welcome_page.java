package com.example.yolochat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcome_page extends AppCompatActivity {

    Button register,mlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        register=findViewById(R.id.register);
        mlogin=findViewById(R.id.mlogin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(welcome_page.this,registeryolo.class);
                startActivity(intent);

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(welcome_page.this,login.class);
                startActivity(intent);

            }
        });
    }
}
