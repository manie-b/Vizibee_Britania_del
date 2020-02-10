package com.mapolbs.vizibeebritannia.Model;

public class NotificationClass {
    String id;
    String title;
    String message;
    String time;
    String bannerurl;

    public NotificationClass(String id, String title, String message, String time, String bannerurl) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.time = time;
        this.bannerurl = bannerurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }
}
