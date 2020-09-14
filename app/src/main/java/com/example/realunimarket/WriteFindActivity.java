package com.example.realunimarket;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class WriteFindActivity extends AppCompatActivity {

    ImageButton move,pack,car,animal,etc;
    Button sun,mon,tue,wed,thur,fri,sat;
    ArrayList<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_find);

        list = new ArrayList<Integer>();
        for(int i=0;i<7;i++)
            list.add(0);
        move = (ImageButton)findViewById(R.id.move);
        car = (ImageButton)findViewById(R.id.car);
        pack = (ImageButton)findViewById(R.id.pack);
        animal = (ImageButton)findViewById(R.id.animal);
        etc = (ImageButton)findViewById(R.id.etc);

        sun = (Button)findViewById(R.id.sunday);
        mon = (Button)findViewById(R.id.monday);
        tue = (Button)findViewById(R.id.tuseday);
        wed = (Button)findViewById(R.id.wednesday);
        thur = (Button)findViewById(R.id.thursday);
        fri = (Button)findViewById(R.id.friday);
        sat = (Button)findViewById(R.id.saturday);

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.move:
                move.setImageResource(R.drawable.cat_move_on_y);
                pack.setImageResource(R.drawable.cat_pack_y);
                car.setImageResource(R.drawable.cat_car_y);
                animal.setImageResource(R.drawable.cat_pet_y);
                etc.setImageResource(R.drawable.cat_etc_y);
                break;
            case R.id.pack:
                move.setImageResource(R.drawable.cat_move_y);
                pack.setImageResource(R.drawable.cat_pack_on_y);
                car.setImageResource(R.drawable.cat_car_y);
                animal.setImageResource(R.drawable.cat_pet_y);
                etc.setImageResource(R.drawable.cat_etc_y);
                break;
            case R.id.car:
                move.setImageResource(R.drawable.cat_move_y);
                pack.setImageResource(R.drawable.cat_pack_y);
                car.setImageResource(R.drawable.cat_car_on_y);
                animal.setImageResource(R.drawable.cat_pet_y);
                etc.setImageResource(R.drawable.cat_etc_y);
                break;
            case R.id.animal:
                move.setImageResource(R.drawable.cat_move_y);
                pack.setImageResource(R.drawable.cat_pack_y);
                car.setImageResource(R.drawable.cat_car_y);
                animal.setImageResource(R.drawable.cat_pet_on_y);
                etc.setImageResource(R.drawable.cat_etc_y);
                break;
            case R.id.etc:
                move.setImageResource(R.drawable.cat_move_y);
                pack.setImageResource(R.drawable.cat_pack_y);
                car.setImageResource(R.drawable.cat_car_y);
                animal.setImageResource(R.drawable.cat_pet_y);
                etc.setImageResource(R.drawable.cat_etc_on_y);
        }
    }

    public void onClick2(View view) {
        switch (view.getId()) {
            case R.id.sunday:
                int k = list.get(0);
                if(k==0) {
                    list.set(0,1);
                    sun.setBackgroundColor(Color.RED);
                }
                else {
                    list.set(0,0);
                    sun.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.monday:
                int k1 = list.get(1);
                if(k1==0){
                    list.set(1,1);
                    mon.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(1,0);
                    mon.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.tuseday:
                int k2 = list.get(2);
                if(k2==0){
                    list.set(2,1);
                    tue.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(2,0);
                    tue.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.wednesday:
                int k3 = list.get(3);
                if(k3 == 0){
                    list.set(3,1);
                    wed.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(3,0);
                    wed.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.thursday:
                int k4 = list.get(4);
                if(k4 == 0){
                    list.set(4,1);
                    thur.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(4,0);
                    thur.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.friday:
                int k5 = list.get(5);
                if(k5 == 0){
                    list.set(5,1);
                    fri.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(5,0);
                    fri.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.saturday:
                int k6 = list.get(6);
                if(k6 == 0){
                    list.set(6,1);
                    sat.setBackgroundColor(Color.RED);
                }
                else{
                    list.set(6,0);
                    sat.setBackgroundColor(Color.WHITE);
                }
        }

        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
