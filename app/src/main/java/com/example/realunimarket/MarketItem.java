package com.example.realunimarket;
import java.io.Serializable;

public class MarketItem implements Comparable<MarketItem>{
    int icon;
    int icon2;
    String title;

    String address;
    int img1,img2,img3,img4;
    String money;
    String maintext;
    String startday;
    String endday;
    String starttime;
    String endtime;
    String userid;
    String today;
    String lattitude,longitude;

    public MarketItem(String today,String userid,int icon,String title, String address , int img1, int img2, int img3, int img4, String money,
                        String maintext,String startday, String endday, String starttime, String endtime,String lattitude,String longitude){
        this.lattitude=lattitude;
        this.longitude=longitude;
        this.today=today;
        this.userid=userid;
        this.icon = icon; // category
       // this.icon2 = icon2; // 구해요, 찾아요
        this.title=title;
        this.address = address; // 주소
        this.img1 = img1; // 사람모양1
        this.img2 = img2; // 사람모양2
        this.img3 = img3; // 사람모양3
        this.img4 = img4; // 사람모양4
        this.money = money; // 시급
        this.maintext=maintext; // 상세내용
        this.startday=startday; // 시작날
        this.endday=endday; // 마치는날
        this.starttime=starttime; // 시작 시간
        this.endtime=endtime; // 마치는 시간


    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon2() {
        return icon2;
    }
    public void setIcon2(int icon2) {
        this.icon2 = icon2;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getImg1() {
        return img1;
    }
    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }
    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getImg3() {
        return img3;
    }
    public void setImg3(int img3) {
        this.img3 = img3;
    }

    public int getImg4() {
        return img4;
    }
    public void setImg4(int img4) {
        this.img4 = img4;
    }

    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }

    public String getMaintext() {return maintext ;}
    public void setMaintext(String maintext) {this.maintext = maintext; }

    public String getStartday() {return startday; }
    public void setStartday() {this.startday=startday; }

    public String getEndday() {return endday;}
    public void setEndday() {this.endday=endday;}

    public String getStarttime() {return starttime;}
    public void setStarttime() {this.starttime=starttime;}

    public String getEndtime() {return endtime;}
    public void setEndtime() {this.endtime = endtime; }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @Override
    public int compareTo(MarketItem item)
    {
        return today.compareTo(item.getToday());
    }

}
