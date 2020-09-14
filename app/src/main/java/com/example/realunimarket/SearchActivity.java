package com.example.realunimarket;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    ImageButton backButton;
    ImageButton move,car,animal,deliever,gita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        move = (ImageButton)findViewById(R.id.move);
        car = (ImageButton)findViewById(R.id.car);
        animal = (ImageButton)findViewById(R.id.animal);
        deliever = (ImageButton)findViewById(R.id.deliever);
        gita = (ImageButton)findViewById(R.id.gita);
        backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
                finish();
            }
        });
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.move:
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("id","move");
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                break;
            case R.id.car:
                Intent intent2 = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent2.putExtra("id","car");
                startActivity(intent2);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                break;
            case R.id.animal:
                Intent intent3 = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent3.putExtra("id","animal");
                startActivity(intent3);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                break;
            case R.id.deliever:
                Intent intent4 = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent4.putExtra("id","deliever");
                startActivity(intent4);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                break;
            case R.id.gita:
                Intent intent5 = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent5.putExtra("id","gita");
                startActivity(intent5);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
        }
    }
}
