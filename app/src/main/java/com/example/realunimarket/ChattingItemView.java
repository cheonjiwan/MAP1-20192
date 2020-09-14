package com.example.realunimarket;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ChattingItemView extends CardView {
    TextView name,content;

    public ChattingItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ChattingItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_chattingroom,this,true);
        name = (TextView)findViewById(R.id.name);
        content = (TextView)findViewById(R.id.content);
    }

    void setName(String name1){
        name.setText(name1);
    }

    void setContent(String content1){
        content.setText(content1);
    }
}
