package com.example.realunimarket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ScrapListActivity extends AppCompatActivity {

    ImageButton searchButton;
    static ArrayList<MarketItem> items = new ArrayList<MarketItem>();
    private ArrayList<BoardControll> boardlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_list);

        class MarketAdapter extends BaseAdapter {
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

            public void addItem(MarketItem item){
                items.add(item);
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
                MarketItem item = items.get(position);
                view.setIcon(item.getIcon());
//                view.setIcon2(item.getIcon2());
                view.setAddress(item.getAddress());
                view.setImg(item.getImg1(),item.getImg2(),item.getImg3(),item.getImg4());
                view.setMoney(item.getMoney());
                view.setTitle(item.getTitle());
                return view;
            }
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setBackgroundColor(Color.WHITE);
        MarketAdapter adapter = new MarketAdapter();
        /*adapter.addItem(new MarketItem(R.drawable.move,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.pack_y,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.car,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,0,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.pet_y,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.car_y,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.pack,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.pet,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,0,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.etc,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.move_y,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));
        adapter.addItem(new MarketItem(R.drawable.etc_y,R.drawable.find,"대구광역시 달서구 진천동",R.drawable.hum_icon,R.drawable.hum_icon,R.drawable.hum_icon,0,"4000"));*/
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailFindActivity.class);
                startActivity(intent);
            }
        });
    }
}
