package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private Toolbar tb;
    private ViewPager vp;
    private viewpadapter va;

    private TabLayout tbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = mAuth.getInstance();
        tb=(Toolbar)findViewById(R.id.main_page_toolbar);
        vp=findViewById(R.id.vp);
        tbl=findViewById(R.id.tbl);

        setSupportActionBar(tb);
        getSupportActionBar().setTitle("YOLO Chats");

        va=new viewpadapter(getSupportFragmentManager(),0);
        vp.setAdapter(va);

        tbl.setupWithViewPager(vp);



    }

    public void onStart() {
        super.onStart();

        set_on_start();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    private void set_on_start()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        if(currentUser==null)
        {
            Intent intent=new Intent(MainActivity.this,welcome_page.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.menu_for_app,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                set_on_start();
                break;
            case R.id.profile:
                Intent intent=new Intent(MainActivity.this,profile.class);
                startActivity(intent);
                break;
            case R.id.alluser:
                Intent intent2=new Intent(MainActivity.this,allusers.class);
                startActivity(intent2);
                break;

        }
        return true;
    }
}
