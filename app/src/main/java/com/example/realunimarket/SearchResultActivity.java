package com.example.realunimarket;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResultActivity extends AppCompatActivity {
    String data;
    ImageView ganpan;
    ImageView backButton;
    int flag;


    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;

    int size = MainActivity.items.size();
    ImageButton guhaeBtn;
    ImageButton findBtn;


    ArrayList<MarketItem> whatlist = new ArrayList<MarketItem>();
    MarketAdapter adapter = new MarketAdapter();
    public class MarketAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return whatlist.size();
        }

        @Override
        public Object getItem(int position) {
            return whatlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(MarketItem item){
            whatlist.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MarketItemView view = null;
            if(convertView == null){
                view =  new MarketItemView(getApplicationContext());
            }
            else{
                view = (MarketItemView) convertView;
            }

            MarketItem item = whatlist.get(position);
            view.setIcon(item.getIcon());
//                view.setIcon2(item.getIcon2());
            view.setAddress(item.getAddress());
            view.setImg(item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4());
            view.setMoney(item.getMoney());
            view.setTitle(item.getTitle());
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        backButton = (ImageView)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
            }
        });

        ganpan = (ImageView)findViewById(R.id.ganpan);
        Intent intent = getIntent();
        // 0: move , 1: pack, 2: car, 3:animal, 4: etc
        data = intent.getStringExtra("id");

        if(data.equals("car"))
        {
            ganpan.setImageResource(R.drawable.tagbutton_car);
            flag=2;
        }
        else if(data.equals("move"))
        {
            ganpan.setImageResource(R.drawable.tagbutton_move);
            flag=0;
        }
        else if(data.equals("animal"))
        {
            ganpan.setImageResource(R.drawable.tagbutton_pet);
            flag=3;
        }
        else if(data.equals("deliever"))
        {
            ganpan.setImageResource(R.drawable.tagbutton_pack);
            flag=1;
        }
        else if(data.equals("gita"))
        {
            ganpan.setImageResource(R.drawable.tagbutton_etc);
            flag=4;
        }

        System.out.println(flag);

        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setBackgroundColor(Color.WHITE);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                MarketItem item = whatlist.get(i);
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
//                if(data.equals("car"))
//                {
//                    MarketItem item = carlist.get(i);
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("userid",item.getUserid());
//                    intent.putExtra("maintext",item.getMaintext());
//                    intent.putExtra("starttime",item.getStarttime());
//                    intent.putExtra("endtime",item.getEndtime());
//                    intent.putExtra("price", item.getMoney());
//                    intent.putExtra("people1",item.getImg1());
//                    intent.putExtra("people2",item.getImg2());
//                    intent.putExtra("people3",item.getImg3());
//                    intent.putExtra("people4",item.getImg4());
//                    intent.putExtra("icon",item.getIcon());
//                    intent.putExtra("startday",item.getStartday());
//                    intent.putExtra("endday",item.getEndday());
//                }
//                else if(data.equals("move"))
//                {
//                    MarketItem item = movelist.get(i);
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("userid",item.getUserid());
//                    intent.putExtra("maintext",item.getMaintext());
//                    intent.putExtra("starttime",item.getStarttime());
//                    intent.putExtra("endtime",item.getEndtime());
//                    intent.putExtra("price", item.getMoney());
//                    intent.putExtra("people1",item.getImg1());
//                    intent.putExtra("people2",item.getImg2());
//                    intent.putExtra("people3",item.getImg3());
//                    intent.putExtra("people4",item.getImg4());
//                    intent.putExtra("icon",item.getIcon());
//                    intent.putExtra("startday",item.getStartday());
//                    intent.putExtra("endday",item.getEndday());
//                }
//                else if(data.equals("animal"))
//                {
//                    MarketItem item = petlist.get(i);
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("userid",item.getUserid());
//                    intent.putExtra("maintext",item.getMaintext());
//                    intent.putExtra("starttime",item.getStarttime());
//                    intent.putExtra("endtime",item.getEndtime());
//                    intent.putExtra("price", item.getMoney());
//                    intent.putExtra("people1",item.getImg1());
//                    intent.putExtra("people2",item.getImg2());
//                    intent.putExtra("people3",item.getImg3());
//                    intent.putExtra("people4",item.getImg4());
//                    intent.putExtra("icon",item.getIcon());
//                    intent.putExtra("startday",item.getStartday());
//                    intent.putExtra("endday",item.getEndday());
//                }
//                else if(data.equals("gita"))
//                {
//                    MarketItem item = etclist.get(i);
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("userid",item.getUserid());
//                    intent.putExtra("maintext",item.getMaintext());
//                    intent.putExtra("starttime",item.getStarttime());
//                    intent.putExtra("endtime",item.getEndtime());
//                    intent.putExtra("price", item.getMoney());
//                    intent.putExtra("people1",item.getImg1());
//                    intent.putExtra("people2",item.getImg2());
//                    intent.putExtra("people3",item.getImg3());
//                    intent.putExtra("people4",item.getImg4());
//                    intent.putExtra("icon",item.getIcon());
//                    intent.putExtra("startday",item.getStartday());
//                    intent.putExtra("endday",item.getEndday());
//                }
//                else if(data.equals("deliever"))
//                {
//                    MarketItem item = packlist.get(i);
//                    intent.putExtra("title",item.getTitle());
//                    intent.putExtra("userid",item.getUserid());
//                    intent.putExtra("maintext",item.getMaintext());
//                    intent.putExtra("starttime",item.getStarttime());
//                    intent.putExtra("endtime",item.getEndtime());
//                    intent.putExtra("price", item.getMoney());
//                    intent.putExtra("people1",item.getImg1());
//                    intent.putExtra("people2",item.getImg2());
//                    intent.putExtra("people3",item.getImg3());
//                    intent.putExtra("people4",item.getImg4());
//                    intent.putExtra("icon",item.getIcon());
//                    intent.putExtra("startday",item.getStartday());
//                    intent.putExtra("endday",item.getEndday());
//                }

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });


//        if(data.equals("move")){
//            ganpan.setImageResource(R.drawable.tagbutton_move);
//            for(int i=0;i<size;i++){
//                MarketItem item = MainActivity.items.get(i);
//                if(item.getIcon()==0 ){
//                    movelist.add(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                    adapter.addItem(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                }
//            }
//        }
//        else if(data.equals("car")){
//            ganpan.setImageResource(R.drawable.tagbutton_car);
//            for(int i=0;i<size;i++){
//                MarketItem item = MainActivity.items.get(i);
//                if(item.getIcon()==2){
//                    carlist.add(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                    adapter.addItem(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                }
//            }
//        }
//        else if(data.equals("animal")){
//            ganpan.setImageResource(R.drawable.tagbutton_pet);
//            for(int i=0;i<size;i++){
//                MarketItem item = MainActivity.items.get(i);
//                if(item.getIcon()==3){
//                    petlist.add(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                    adapter.addItem(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                }
//            }
//        }
//        else if(data.equals("deliever")){
//            ganpan.setImageResource(R.drawable.tagbutton_pack);
//            for(int i=0;i<size;i++){
//                MarketItem item = MainActivity.items.get(i);
//                if(item.getIcon()==1){
//                    packlist.add(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                    adapter.addItem(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                }
//            }
//        }
//        else if(data.equals("gita")){
//            ganpan.setImageResource(R.drawable.tagbutton_etc);
//            for(int i=0;i<size;i++){
//                MarketItem item = MainActivity.items.get(i);
//                if(item.getIcon()==4){
//                    etclist.add(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                    adapter.addItem(new MarketItem(item.getToday(),item.getUserid(),item.getIcon(),item.getTitle(),item.getAddress(),item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4(),item.getMoney(),
//                            item.getMaintext(),item.getStartday(),item.getEndday(),item.getStarttime(),item.getEndtime()));
//                }
//            }
//        }


//        guhaeBtn = (ImageButton)findViewById(R.id.guhaeButton);
//        findBtn = (ImageButton)findViewById(R.id.findButton);
//
//        guhaeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                guhaeBtn.setImageResource(R.drawable.guhae_on);
//                findBtn.setImageResource(R.drawable.find);
//            }
//        });
//
//        findBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                guhaeBtn.setImageResource(R.drawable.guhae);
//                findBtn.setImageResource(R.drawable.find_on);
//            }
//        });

        mReference = mDatabase.getInstance().getReference().child("MainBoard");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // 0: move , 1: pack, 2: car, 3:animal, 4: etc
                whatlist.clear();
                for(DataSnapshot messageData : dataSnapshot.getChildren())
                {
                    for(DataSnapshot deeperSnapshot : messageData.getChildren())
                    {
                        String checkstring = String.valueOf(deeperSnapshot.child("icon").getValue());
                        int check = Integer.parseInt(checkstring);
                        System.out.println("check: "+check);
                        if(flag==check)
                        {
                            System.out.println("flag==check");
                            Log.d("SearchResultActivity", "ValueEventListener : " + deeperSnapshot.getValue());
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
                            whatlist.add(new MarketItem(today,user_id,icon,title,location,people1,people2,people3,people4,price,maintext,
                                    start_day,end_day,start_time,end_time,input_lattitude,input_longitude));
                            Collections.sort(whatlist);
                        }
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
    }


}
