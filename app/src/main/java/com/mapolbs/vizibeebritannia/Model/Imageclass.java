package com.mapolbs.vizibeebritannia.Model;

/**
 * Created by RAMMURALI on 09/21/2017.
 */
public class Imageclass {
    String imagequeid;
    String httpurl;
    String imagepath;

    public String getImagequeid() {
        return imagequeid;
    }

    public void setImagequeid(String imagequeid) {
        this.imagequeid = imagequeid;
    }

    public String getHttpurl() {
        return httpurl;
    }

    public void setHttpurl(String httpurl) {
        this.httpurl = httpurl;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Imageclass(String imagequeid,String httpurl,String imagepath)
    {
        this.imagequeid = imagequeid;
        this.httpurl = httpurl;
        this.imagepath = imagepath;
    }
}
