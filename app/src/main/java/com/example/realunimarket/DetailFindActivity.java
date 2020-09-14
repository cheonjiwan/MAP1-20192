package com.example.realunimarket;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class DetailFindActivity extends AppCompatActivity {

    private int itemId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_find);

        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
            }
        });

        ImageButton chatBtn = (ImageButton)findViewById(R.id.chatButton);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ChattingRoomActivity.class);
                intent.putExtra("boardid", itemId);
                startActivity(intent);
            }
        });
    }
}