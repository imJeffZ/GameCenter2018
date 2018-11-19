package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {

    private  int id;
    private  int backId;
    private boolean up;

    public int getId() {
        return id;
    }

   public int getBackId(){
        return backId;
   }

   public boolean isUp(){
        return up;
   }

    Card(int position){
        id = position + 1;
        up = false;
        switch(position + 1){
            case 1:
                backId = R.drawable.tile_1;
                break;
            case 2:
                backId= R.drawable.tile_2;
                break;
            case 3:
                backId = R.drawable.tile_3;
                break;
            case 4:
                backId = R.drawable.tile_4;
                break;
            case 5:
                backId = R.drawable.tile_5;
                break;
            case 6:
                backId = R.drawable.tile_6;
                break;
            case 7:
                backId = R.drawable.tile_7;
                break;
            case 8:
                backId = R.drawable.tile_8;
                break;
            case 9:
                backId = R.drawable.tile_9;
                break;
            case 10:
                backId = R.drawable.tile_10;
                break;
            case 11:
                backId = R.drawable.tile_11;
                break;
            case 12:
                backId = R.drawable.tile_12;
                break;
            case 13:
                backId = R.drawable.tile_13;
                break;
            case 14:
                backId = R.drawable.tile_14;
                break;
            case 15:
                backId = R.drawable.tile_15;
                break;
            case 16:
                backId = R.drawable.tile_16;
                break;
            case 17:
                backId = R.drawable.tile_17;
                break;
            case 18:
                backId = R.drawable.tile_18;
                break;
            case 19:
                backId = R.drawable.tile_19;
                break;
            case 20:
                backId = R.drawable.tile_20;
                break;
            case 21:
                backId = R.drawable.tile_21;
                break;
            case 22:
                backId = R.drawable.tile_22;
                break;
            case 23:
                backId = R.drawable.tile_23;
                break;
            case 24:
                backId = R.drawable.tile_24;
                break;
            default:
                backId = R.drawable.tile_blank;
        }



    }

    public void turn(boolean face){
        up = face;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}
