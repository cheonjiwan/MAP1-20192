package com.example.realunimarket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {

    ImageButton searchButton;
    static ArrayList<MarketItem> items = new ArrayList<MarketItem>();
    ArrayList<MarketItem> mylist = new ArrayList<MarketItem>();

    private ArrayList<BoardControll> boardlist = new ArrayList<>();
    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;

    MarketAdapter adapter = new MarketAdapter();
    public class MarketAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public Object getItem(int position) {
            return mylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(com.example.realunimarket. MarketItem item) {
            mylist.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MarketItemView view = null;
            if (convertView == null) {
                view = new MarketItemView(getApplicationContext());
            } else {
                view = (MarketItemView) convertView;
            }

            MarketItem item = mylist.get(position);
            view.setIcon(item.getIcon());
            view.setAddress(item.getAddress());
            view.setImg(item.getImg1(), item.getImg2(), item.getImg3(), item.getImg4());
            view.setMoney(item.getMoney());
            view.setTitle(item.getTitle());
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        initDatabase();
        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setBackgroundColor(Color.WHITE);

        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                MarketItem item = mylist.get(i);
                intent.putExtra("userid",item.getUserid());
                intent.putExtra("maintext",item.getMaintext());
                intent.putExtra("starttime",item.getStarttime());
                intent.putExtra("endtime",item.getEndtime());
                intent.putExtra("price", item.getMoney());
                intent.putExtra("people1",item.getImg1());
                intent.putExtra("people2",item.getImg2());
                intent.putExtra("people3",item.getImg3());
                intent.putExtra("people4",item.getImg4());
                intent.putExtra("icon",item.getIcon());
                intent.putExtra("startday",item.getStartday());
                intent.putExtra("endday",item.getEndday());
                intent.putExtra("title",item.getTitle());
                intent.putExtra("lattitude",item.getLattitude());
                intent.putExtra("longitude",item.getLongitude());
                intent.putExtra("address",item.getAddress());

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mReference = mDatabase.getInstance().getReference().child("MainBoard").child(user.getUid());
        System.out.println("ready");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Go");
                mylist.clear();
                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    Log.d("MylistActivity", "ValueEventListener : " + messageData.getValue());
                    String location = (String) messageData.child("address").getValue();
                    String price = (String) messageData.child("money").getValue();
                    String contents = (String) messageData.child("content").getValue();
                    String icon_i = String.valueOf(messageData.child("icon").getValue());
                    int icon = Integer.parseInt(icon_i);
                    //String icon2_i = String.valueOf(messageData.child("icon2").getValue());
                    //int icon2 = Integer.parseInt(icon2_i);
                    String people1_i = String.valueOf(messageData.child("img1").getValue());
                    String people2_i = String.valueOf(messageData.child("img2").getValue());
                    String people3_i = String.valueOf(messageData.child("img3").getValue());
                    String people4_i = String.valueOf(messageData.child("img4").getValue());
                    int people1 = Integer.parseInt(people1_i);
                    int people2 = Integer.parseInt(people2_i);
                    int people3 = Integer.parseInt(people3_i);
                    int people4 = Integer.parseInt(people4_i);
                    String title = (String)messageData.child("title").getValue();
                    String maintext = (String)messageData.child("maintext").getValue();
                    String start_day = (String)messageData.child("startday").getValue();
                    String end_day = (String)messageData.child("endday").getValue();
                    String start_time = (String)messageData.child("starttime").getValue();
                    String end_time = (String)messageData.child("endtime").getValue();
                    String userid = (String)messageData.child("userid").getValue();
                    String today=(String)messageData.child("today").getValue();
                    String input_lattitude = (String)messageData.child("lattitude").getValue();
                    String input_longitude = (String)messageData.child("longitude").getValue();
                    mylist.add(new MarketItem(today,userid,icon,title,location,people1,people2,people3,people4,price,maintext,
                            start_day,end_day,start_time,end_time,input_lattitude,input_longitude));

//                    adapter.notifyDataSetChanged();
//                    adapter.addItem(new MarketItem(icon,icon2,location,people1,people2,people3,people4,price,maintext,
//                            start_day,end_day,start_time,end_time));
                }

                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initDatabase(){
        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("MainBoard");
        //mReference.child("MainBoard").setValue("check");

        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
