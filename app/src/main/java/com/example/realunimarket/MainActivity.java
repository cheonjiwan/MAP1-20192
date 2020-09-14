package com.example.realunimarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;


//    final FirebaseUser user = firebaseAuth.getCurrentUser();
    static ArrayList<MarketItem> items = new ArrayList<MarketItem>();
    MarketAdapter adapter = new MarketAdapter();
    public class MarketAdapter extends BaseAdapter
    {
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

        public void addItem(com.example.realunimarket. MarketItem item) {
            items.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MarketItemView view = null;
            if (convertView == null) {
                view = new MarketItemView(getApplicationContext());
            } else {
                view = (MarketItemView) convertView;
            }

            MarketItem item = items.get(position);
            view.setIcon(item.getIcon());
            view.setAddress(item.getAddress());
            view.setImg(item.getImg1(), item.getImg2(), item.getImg3(), item.getImg4());
            view.setMoney(item.getMoney());
            view.setTitle(item.getTitle());
            return view;
        }
    }


    ImageButton searchButton;


    Intent data = null;

    private ArrayList<BoardControll> boardlist = new ArrayList<>();
    //private ArrayAdapter<MarketItem> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.activity.finish();
        initDatabase();

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        data = getIntent();
        try {
            if (data != null) {
//                int icon = data.getIntExtra("icon",1);
//                int icon2 = data.getIntExtra("icon2",1);
//                String location = data.getStringExtra("locating");
//                int people1 = data.getIntExtra("people1",1);
//                int people2 = data.getIntExtra("people2",1);
//                int people3 = data.getIntExtra("people3",1);
//                int people4 = data.getIntExtra("people4",1);
//                String price = data.getStringExtra("price");
//                int category = data.getIntExtra("category",1);
//                int purpose = data.getIntExtra("purpose",1);

//                adapter.addItem(new MarketItem(category,purpose,location,people1,people2,people3,people4,price,contents,start_day,
//                        end_day,start_time,end_time));
//                String maintext = data.getStringExtra("maintext");
//                String startday = data.getStringExtra("start");
//                String endday = data.getStringExtra("end");






                //UploadBoard(item);
              //  adapter.addItem
               // System.out.println(location + " " + member + " " + icon + " " + maintext + " " + start[0] + " " + end[0]);
            }
        }
        catch(Exception e)
        {


        }


        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mReference = mDatabase.getInstance().getReference().child("MainBoard");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                items.clear();
                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    for(DataSnapshot deeperSnapshot : messageData.getChildren())
                    {
                        System.out.println(deeperSnapshot.getKey());
                        Log.d("MainActivity", "ValueEventListener : " + deeperSnapshot.getValue());
                        String location = (String) deeperSnapshot.child("address").getValue();
                        String price = (String) deeperSnapshot.child("money").getValue();
                        String contents = (String) deeperSnapshot.child("content").getValue();
                        String icon_i = String.valueOf(deeperSnapshot.child("icon").getValue());
                        int icon = Integer.parseInt(icon_i);
                        String icon2_i = String.valueOf(deeperSnapshot.child("icon2").getValue());
                        int icon2 = Integer.parseInt(icon2_i);
                        String people1_i = String.valueOf(deeperSnapshot.child("img1").getValue());
                        String people2_i = String.valueOf(deeperSnapshot.child("img2").getValue());
                        String people3_i = String.valueOf(deeperSnapshot.child("img3").getValue());
                        String people4_i = String.valueOf(deeperSnapshot.child("img4").getValue());
                        int people1 = Integer.parseInt(people1_i);
                        int people2 = Integer.parseInt(people2_i);
                        int people3 = Integer.parseInt(people3_i);
                        int people4 = Integer.parseInt(people4_i);
                        String maintext = (String)deeperSnapshot.child("maintext").getValue();
                        String start_day = (String)deeperSnapshot.child("startday").getValue();
                        String end_day = (String)deeperSnapshot.child("endday").getValue();
                        String start_time = (String)deeperSnapshot.child("starttime").getValue();
                        String end_time = (String)deeperSnapshot.child("endtime").getValue();
                        String user_id = (String)deeperSnapshot.child("userid").getValue();
                        String today = (String)deeperSnapshot.child("today").getValue();
                        String title = (String)deeperSnapshot.child("title").getValue();
                        String input_lattitude = (String)deeperSnapshot.child("lattitude").getValue();
                        String input_longitude = (String)deeperSnapshot.child("longitude").getValue();
                        items.add(new MarketItem(today,user_id,icon,title,location,people1,people2,people3,people4,price,maintext,
                                start_day,end_day,start_time,end_time,input_lattitude,input_longitude));
                        Collections.sort(items);
                    }
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

        ImageButton menuButton = (ImageButton) findViewById(R.id.menuButton);
        searchButton = (ImageButton) findViewById(R.id.searchButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();
            }
        });




       /* adapter.addItem(new MarketItem(R.drawable.move, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8350"));
        adapter.addItem(new MarketItem(R.drawable.pack_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "9000"));
        adapter.addItem(new MarketItem(R.drawable.pet, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.car, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "8500"));
        adapter.addItem(new MarketItem(R.drawable.pet_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "10000"));
        adapter.addItem(new MarketItem(R.drawable.car_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8350"));
        adapter.addItem(new MarketItem(R.drawable.pet_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.pack, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8700"));
        adapter.addItem(new MarketItem(R.drawable.pet, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.pet_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.etc, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8350"));
        adapter.addItem(new MarketItem(R.drawable.move_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8350"));
        adapter.addItem(new MarketItem(R.drawable.etc_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, R.drawable.hum_icon, 0, "8800"));
        adapter.addItem(new MarketItem(R.drawable.pet, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.pet_y, R.drawable.find, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));
        adapter.addItem(new MarketItem(R.drawable.pet, R.drawable.guhae, "대구광역시 달서구 진천동", R.drawable.hum_icon, R.drawable.hum_icon, 0, 0, "9500"));*/




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                MarketItem item = items.get(i);
                intent.putExtra("title",item.getTitle());
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
                intent.putExtra("lattitude",item.getLattitude());
                intent.putExtra("longitude",item.getLongitude());
                intent.putExtra("address",item.getAddress());

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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


    public String MakeDateString(String _am, String _year, String _hour, String _min) {
        int hour = Integer.parseInt(_hour);
        if (_am.equals("오후") == true)
            hour += 12;
        return _year + " " + hour + ":" + _min + ":00";
    }

    public Date MakeDate(String time) {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date to;
        try {
            to = transFormat.parse(time);
            return to;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }
}


