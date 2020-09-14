package com.example.realunimarket;

import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<ChatModel> items;
    LayoutInflater inflater = null;

    class ViewHolder{

        public TextView name;
        public TextView content;
        public LinearLayout linearLayout;
        public ImageView profileImg ;
        public  TextView timeTextview ;
        public TextView nameText ;
        public  TextView contentText ;
        public RelativeLayout.LayoutParams params;


        public void SetInfo(String _name,String _text){
            name.setText(_name);
            content.setText(_text);
        }

        public void GetMyId(View v){
            linearLayout.setLayoutParams(params);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_END, 1);

            profileImg.setVisibility(View.GONE);
            timeTextview.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
            contentText.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.chatting_background_white));
            contentText.setTextColor(Color.parseColor("#525252"));
        }
    }

    public CustomAdapter(ArrayList<ChatModel> items,LayoutInflater _inflater){
        this.items = items;
        inflater =  _inflater;
    }

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

        View v = convertView;
        ChatModel nation = items.get(position);

        if (v  == null) {
            v = inflater.inflate(R.layout.cardview_chattingroom,null);
            ViewHolder viewHolder;
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)v.findViewById(R.id.name);
            viewHolder.content = (TextView)v.findViewById(R.id.content);
            viewHolder.SetInfo(nation.getName(),nation.getText());
            viewHolder.profileImg = (ImageView)v.findViewById(R.id.proImage);
            viewHolder.timeTextview = (TextView)v.findViewById(R.id.timeText);
            viewHolder.nameText = (TextView)v.findViewById(R.id.name);
            viewHolder.contentText = (TextView)v.findViewById(R.id.content);
            viewHolder.linearLayout = v.findViewById(R.id.layoutForAlign);
            viewHolder.params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

//            if(nation.getOtherid() ==2){
//                viewHolder.GetMyId(v);
//            }

            v.setTag(viewHolder);

        }
        else {
            ViewHolder viewHolder =(ViewHolder)v.getTag();
            viewHolder.SetInfo(viewHolder.name.getText().toString(),viewHolder.content.getText().toString());
//            if(nation.getOtherid() == 2){
//                viewHolder.GetMyId(v);
//            }
        }

        return v;
    }
}