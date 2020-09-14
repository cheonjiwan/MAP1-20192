package com.example.realunimarket;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MarketItemView extends LinearLayout {

    ImageView icon;

    ImageView icon2;
    TextView address;
    ImageView img1,img2,img3,img4;
    TextView money,title;
    TextView maintext,startday,endday,starttime,endtime;

    public MarketItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MarketItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.market_item,this,true);
        icon = (ImageView)findViewById(R.id.icon);
        title = (TextView)findViewById(R.id.title_location);
       // icon2 = (ImageView)findViewById(R.id.icon2);
        address = (TextView)findViewById(R.id.address);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);
        money = (TextView)findViewById(R.id.money);

    }

    public void setIcon(int a)
    {
        if(a==0)
            icon.setImageResource(R.drawable.move);
        if(a==1)
            icon.setImageResource(R.drawable.pack_y);
        if(a==2)
            icon.setImageResource(R.drawable.car);
        if(a==3)
            icon.setImageResource(R.drawable.pet_y);
        if(a==4)
            icon.setImageResource(R.drawable.etc);
    }




    public void setImg(int a,int b,int c,int d){
        System.out.printf("%d %d %d %d.%n",a,b,c,d);
        if(a==1) {
            img1.setImageResource(R.drawable.hum_icon);
        }
        else
            img1.setImageBitmap(null);

        if(b==1) {
            img2.setImageResource(R.drawable.hum_icon);
        }
        else
            img2.setImageBitmap(null);
        if(c==1) {
            img3.setImageResource(R.drawable.hum_icon);
        }
        else
            img3.setImageBitmap(null);

        if(d==1) {
            img4.setImageResource(R.drawable.hum_icon);
        }
        else
            img4.setImageBitmap(null);
    }


   public void setMoney(String money1){
        money.setText(money1);
    }
    public void setAddress(String name){
        address.setText(name);
    }
   public void setTitle(String title1)
   {
       title.setText(title1);
   }

}
