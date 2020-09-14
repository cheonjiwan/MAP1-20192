package com.example.realunimarket;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ChattingRoomActivity extends AppCompatActivity {


    static String userid;
    String name,titletitle;
    String text;
    String currentUser_name,currentUser_major;

    TextView chattitle;
    ImageButton deletebutton;

    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private DatabaseReference mReference2;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;

    ArrayList<ChatModel> items = new ArrayList<ChatModel>();
    ChattingAdapter adapter = new ChattingAdapter();
    // MarketAdapter adapter = new MarketAdapter();
    TextView nameText;

    public class ChattingAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChatModelView view = null;
            if (convertView == null) {
                view = new ChatModelView(getApplicationContext());
            } else {
                view = (ChatModelView) convertView;
            }
            ChatModel item = items.get(position);
            view.setPicture(item.getPicture());
            view.setName(item.getName());
            view.setContent(item.getText());
            String time = item.getTimelog();
            view.setTimetext(time.substring(0,4)+"."+time.substring(4,6)+"."+time.substring(6,8)+" "+
                    time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14));

            return view;
        }
        public void addItem(ChatModel item){
            items.add(item);
        }
        protected void changeTheme(ChatModelView view) {

            LinearLayout linearLayout = findViewById(R.id.layoutForAlign);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
            ImageView profileImg = (ImageView)findViewById(R.id.proImage);
            profileImg.setVisibility(View.GONE);

            TextView timeTextview = (TextView)findViewById(R.id.timeText);
            timeTextview.setVisibility(View.GONE);


            //nameText.setVisibility(View.GONE);

            TextView contentText = (TextView)findViewById(R.id.content);
            contentText.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.chatting_background_white));
            contentText.setTextColor(Color.parseColor("#525252"));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        initDatabase();
        chattitle = (TextView)findViewById(R.id.chattitle);
        nameText = (TextView)findViewById(R.id.name);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        final ListView listView = (ListView)findViewById(R.id.listView);

        Intent intent = getIntent();
        // 0: move , 1: pack, 2: car, 3:animal, 4: etc
        userid = intent.getStringExtra("userid");
        titletitle=intent.getStringExtra("title");
        chattitle.setText(titletitle);
//        if(user.getUid().equals("u8zFTkxNmfPiQsFY3ilMCWAevSu1"))
//            name="천지완";
//        else if(user.getUid().equals("DDFo6KWeYfNswxGkPieW53a58hy1"))
//            name="홍성기";
//        else if(user.getUid().equals("lRTyFc8Yt9NIv2pvWx1Kxji5krl1"))
//            name="조현우";



        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

//        loadChatting();


        listView.setAdapter(adapter);
        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mReference = mDatabase.getInstance().getReference().child("message");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    String key = messageData.getKey();
                    if(key.equals(titletitle))
                    {
                        for(DataSnapshot deepermessageData : messageData.getChildren())
                        {
                            System.out.println(deepermessageData.getKey());
                            Log.d("ChattingRoomActivity", "ValueEventListener : " + deepermessageData.getValue());
                            String str_picture=String.valueOf(deepermessageData.child("picture").getValue());
                            int picture = Integer.parseInt(str_picture);
                            String namename = (String) deepermessageData.child("name").getValue();
                            String contentcontent = (String) deepermessageData.child("text").getValue();
                            String timelog = (String) deepermessageData.child("timelog").getValue();
                            System.out.println(namename+contentcontent);
                            items.add(new ChatModel(picture,namename,contentcontent,timelog));
                            Collections.sort(items);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mReference2 = mDatabase.getInstance().getReference().child("user");
        mReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    Log.d("ChattingRoomActivity", "ValueEventListener : " + messageData.getValue());
                    if(user.getUid().equals(messageData.getKey()))
                    {
                        currentUser_name = String.valueOf(messageData.child("name").getValue());
                        currentUser_major = String.valueOf(messageData.child("major").getValue());
                        name=currentUser_major+" "+currentUser_name;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SendTextMessage(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //edittext
        EditText editText = findViewById(R.id.chatEditText);
        String chatString = editText.getText().toString();
        editText.setText("");

        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

        if(userid.equals(user.getUid()))
        {
            ChatModel chat = new ChatModel(1,name,chatString,today);
            SendMessage(chat);
        }
        else
        {
            ChatModel chat = new ChatModel(2,name,chatString,today);
            SendMessage(chat);
        }


      //  SendMessage(chat);
    }

//    메세지 샌딩 부분 ChatModel 생성자 부분 참고
    public void SendMessage(ChatModel packet){

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mReference.child(titletitle).push().setValue(packet);
    }

    private void initDatabase(){
        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("message");
        //mReference.child("MainBoard").setValue("check");

        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);

    }
}
