package base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 08-12-2014.
 */


public class User {

    public User() {
    }

    @SerializedName("objectId")
    public String objectId;

    @SerializedName("cheatMode")
    public boolean cheatMode;

    @SerializedName("playerName")
    public String playerName;

    @SerializedName("score")
    public int score;



}
