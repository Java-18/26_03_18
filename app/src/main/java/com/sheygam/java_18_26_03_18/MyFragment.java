package com.sheygam.java_18_26_03_18;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by gregorysheygam on 26/03/2018.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private float scale = 0.5F;
    private int color;
    private Button myButton;
    private MyFragmentListener listener;

    public MyFragment() {
    }

    public static MyFragment newInstance(int color, float scale){
        MyFragment fragment = new MyFragment();
        fragment.color = color;
        fragment.scale = scale;
        return fragment;
    }

    public void setListener(MyFragmentListener listener){
        this.listener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MyFragmentListener){
            listener = (MyFragmentListener) activity;
        }else{
            throw new RuntimeException(activity.getClass().getSimpleName() +
                    " must implements MyFragmentListener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            scale = args.getFloat("SCALE");
            color = args.getInt("COLOR");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);
//        myButton = view.findViewById(R.id.frag_btn);
//        myButton.setOnClickListener(this);

        view.setScaleY(scale);
        view.setScaleX(scale);
        view.setBackgroundColor(color);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myButton = view.findViewById(R.id.frag_btn);
        myButton.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.my_item){
            Toast.makeText(getActivity(), "Was selected menu item", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.frag_btn){
            if(listener!=null){
                listener.onBtnClicked();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,new MyFragment())
                        .commit();
            }
        }
    }

    public interface MyFragmentListener{
        void onBtnClicked();
    }
}
