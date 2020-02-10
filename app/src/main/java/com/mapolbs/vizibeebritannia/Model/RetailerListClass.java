package com.mapolbs.vizibeebritannia.Model;

/**
 * Created by RAMMURALI on 2/10/2017.
 */
public class RetailerListClass {
    String retailerid;
    String retailername;

    public String getRetailerid() {
        return retailerid;
    }

    public void setRetailerid(String retailerid) {
        this.retailerid = retailerid;
    }

    public String getRetailername() {
        return retailername;
    }

    public void setRetailername(String retailername) {
        this.retailername = retailername;
    }

    public RetailerListClass(String retailerid, String retailername)
    {
        this.retailerid = retailerid;
        this.retailername = retailername;
    }
}
