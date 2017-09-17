package com.example.user.firebasedemo;

import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by User on 8/26/2017.
 */

public class Blog {
    //variable nme same hote hbe database name er saathe
    private String title;
    private String desc;
    private String image;
public String postid;

    private String username;
 public Blog()
{

}

    public Blog(String title, String desc, String image, String postid, String username) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.postid = postid;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
