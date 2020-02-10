package com.mapolbs.vizibeebritannia.Model;

/**
 * Created by RAMMURALI on 2/10/2017.
 */
public class GridFilterClass {
    String code;
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GridFilterClass(String code, String name)
    {
     this.code = code;
     this.name = name;
    }
}
