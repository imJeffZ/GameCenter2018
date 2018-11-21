package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {

    private  int id;
    private  int backId;
    private boolean up;
    private boolean used;

    public int getId() {
        return id;
    }

   public int getBackId(){
        return backId;
   }

   public boolean isUp(){
        return up;
   }

   public boolean isUsed(){ return used; }

    Card(int position){
        id = position + 1;
        up = false;
        used = false;
        switch(position + 1){
            case 1:
                backId = R.drawable.p1;
                break;
            case 2:
                backId= R.drawable.p2;
                break;
            case 3:
                backId = R.drawable.p3;
                break;
            case 4:
                backId = R.drawable.p4;
                break;
            case 5:
                backId = R.drawable.p5;
                break;
            case 6:
                backId = R.drawable.p6;
                break;
            case 7:
                backId = R.drawable.p7;
                break;
            case 8:
                backId = R.drawable.p8;
                break;
            case 9:
                backId = R.drawable.p9;
                break;
            case 10:
                backId = R.drawable.p10;
                break;
            case 11:
                backId = R.drawable.p11;
                break;
            case 12:
                backId = R.drawable.p12;
                break;
            case 13:
                backId = R.drawable.p13;
                break;
            case 14:
                backId = R.drawable.p14;
                break;
            case 15:
                backId = R.drawable.p15;
                break;
            case 16:
                backId = R.drawable.p16;
                break;
            case 17:
                backId = R.drawable.p17;
                break;
            case 18:
                backId = R.drawable.p18;
                break;
            case 19:
                backId = R.drawable.p19;
                break;
            case 20:
                backId = R.drawable.p20;
                break;
            case 21:
                backId = R.drawable.p21;
                break;
            case 22:
                backId = R.drawable.p22;
                break;
            case 23:
                backId = R.drawable.p23;
                break;
            case 24:
                backId = R.drawable.p24;
                break;
            default:
                backId = R.drawable.matching_front;
        }



    }

    public void turn(boolean face){
        up = face;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}
