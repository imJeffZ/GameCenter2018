package fall18_207project.GameCenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.Serializable;

class Profile implements Serializable {
    /***
     * A profile class for account.
     */

    // Avatar image location.
    transient private Bitmap avatarImage;
    // Self description.
    private String intro;
    // Does not count unfinished game.
    private long totalPlayTime;

    Profile() {
        this.avatarImage = BitmapFactory.decodeStream(getClass().getResourceAsStream(
                "/res/drawable/paulorange1.jpg"));
        this.intro = "A New user who hasn't set his intro.";
        this.totalPlayTime = 0;
    }


    Bitmap getAvatarImage() {
        return this.avatarImage;
    }

    void setAvatarImage(Bitmap avatarImage) {
        this.avatarImage = avatarImage;
    }

    String getIntro() {
        return intro;
    }

    void setIntro(String intro) {
        this.intro = intro;
    }

    long getTotalPlayTime() {
        return totalPlayTime;
    }

    void updateTotalPlayTime(long newPlayTime) {
        this.totalPlayTime += totalPlayTime;
    }


}
