package com.sheygam.java_18_26_03_18;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyFragment.MyFragmentListener {
    public static float SCALE = 1.0F;
    private FragmentManager fragmentManager;
    private Button addBtn, replaceBtn, removeBtn, attachBtn, detachBtn;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        addBtn = findViewById(R.id.add_btn);
        replaceBtn = findViewById(R.id.replace_btn);
        removeBtn = findViewById(R.id.remove_btn);
        attachBtn = findViewById(R.id.attach_btn);
        detachBtn = findViewById(R.id.detach_btn);

        addBtn.setOnClickListener(this);
        replaceBtn.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
        attachBtn.setOnClickListener(this);
        detachBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Random rnd = new Random();
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        int color = Color.rgb(red, green, blue);

        if (v.getId() == R.id.add_btn) {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.add(R.id.fragment_container,new MyFragment());
//            transaction.commit();
            if (count == 2) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, MyFragment.newInstance(color,SCALE), "MY_FRAG")
                        .addToBackStack(null)
                        .commit();
                SCALE -= 0.1;
            } else {
                MyFragment fragment = new MyFragment();
                Bundle args = new Bundle();
                args.putInt("COLOR",color);
                args.putFloat("SCALE",SCALE);
                fragment.setArguments(args);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
            SCALE -= 0.1;
            count++;
        } else if (v.getId() == R.id.replace_btn) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyFragment())
                    .commit();

        } else if (v.getId() == R.id.remove_btn) {
            MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("MY_FRAG");
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        } else if (v.getId() == R.id.attach_btn) {
            MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("MY_FRAG");
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .attach(fragment)
                        .commit();
            }

        } else if (v.getId() == R.id.detach_btn) {
            MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("MY_FRAG");
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .detach(fragment)
                        .commit();
            }
        }
    }

    @Override
    public void onBtnClicked() {
        Toast.makeText(this, "On btn clicked", Toast.LENGTH_SHORT).show();
    }
}
