package fall18_207project.GameCenter;

import java.io.Serializable;

class Profile implements Serializable {
    /***
     * A profile class for account.
     */

    // Avatar image location.
    private String avatarImage;
    private String context;
    // Self description.
    private String intro;
    // Does not count unfinished game.
    private long totalPlayTime;

    Profile() {
        // TODO: Should have a default avatar image here.
        this.avatarImage = null;
        this.intro = "A New user who hasn't set his intro.";
        this.totalPlayTime = 0;
    }


    String getAvatarImage() {
        return avatarImage;
    }

    void setAvatarImage(String avatarImage) {
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
