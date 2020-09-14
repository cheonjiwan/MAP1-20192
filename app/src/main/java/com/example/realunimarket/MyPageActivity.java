package com.example.realunimarket;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyPageActivity extends AppCompatActivity {
    ImageButton cancel;
    Button people;
    Button find;

    Button findButton,logoutButton;
    Button scrapList;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private String email;
    private String username;
    String name;
    //view objects
    private TextView textViewUserEmail;
   TextView letsgowriteText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        firebaseAuth = FirebaseAuth.getInstance();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        textViewUserEmail = (TextView) findViewById(R.id.useremailText);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //유저가 있다면, null이 아니면 계속 진행
        final FirebaseUser user = firebaseAuth.getCurrentUser();

//        databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren())
//                {
//                    if(snapshot.equals(user.getUid()))
//                    {
//                        UserInfo userinfo = snapshot..getValue(UserInfo.class);
//                        name = userinfo.getName();
//                        System.out.println(name);
//                        break;
//                    }
//                    else
//                        continue;
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        if(user.getUid().equals("DDFo6KWeYfNswxGkPieW53a58hy1"))
//            textViewUserEmail.setText("홍성기");
//        else if(user.getUid().equals("u8zFTkxNmfPiQsFY3ilMCWAevSu1"))
//            textViewUserEmail.setText("천지완");
//        else if(user.getUid().equals("lRTyFc8Yt9NIv2pvWx1Kxji5krl1"))
//            textViewUserEmail.setText("조현우");

        cancel = (ImageButton)findViewById(R.id.cancel);
//        findButton = (Button)findViewById(R.id.findButton);
//        findButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new  Intent(MyPageActivity.this, WriteFindActivity.class);
//                startActivity(intent);
//            }
//        });
        letsgowriteText = (TextView)findViewById(R.id.letsgowrite);
        letsgowriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                finish();
            }
        });
//        people = (Button)findViewById(R.id.people);
//        people.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
//                startActivity(intent);
//            }
//        });
//        find = (Button)findViewById(R.id.findButton);
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), WriteFindActivity.class);
//                startActivity(intent);
//            }
//        });

        Button myList = (Button)findViewById(R.id.myListButton);
        myList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyListActivity.class);
                startActivity(intent);
            }
        });
        logoutButton = (Button)findViewById(R.id.SignOutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(MyPageActivity.this, R.string.success_logout, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        mReference = mDatabase.getInstance().getReference().child("user");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    Log.d("MyPageActivity", "ValueEventListener : " + messageData.getValue());
                    if(user.getUid().equals(messageData.getKey()))
                    {
                        String currentUser_name = String.valueOf(messageData.child("name").getValue());
                        String currentUser_major = String.valueOf(messageData.child("major").getValue());
                        textViewUserEmail.setText(currentUser_major+" "+currentUser_name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
