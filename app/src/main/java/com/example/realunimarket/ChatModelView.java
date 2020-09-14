package com.example.realunimarket;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChatModelView extends CardView {
    TextView name,content,timetext;
    ImageView picture;


    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_chattingroom,this,true);
        this.setCardElevation(0);
        name = (TextView)findViewById(R.id.name);
        picture=(ImageView)findViewById(R.id.proImage);
        content = (TextView)findViewById(R.id.content);
        timetext=(TextView)findViewById(R.id.timeText);
    }

    public ChatModelView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ChatModelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setPicture(int a)
    {
        if(a==1)
            picture.setImageResource(R.drawable.go_pro);
        else if(a==2)
            picture.setImageResource(R.drawable.sample_pro);
    }

    void setName(String name1){ name.setText(name1); }
    void setContent(String content1){ content.setText(content1); }
    void setTimetext(String timetext1)
    {
        timetext.setText(timetext1);
    }

}
