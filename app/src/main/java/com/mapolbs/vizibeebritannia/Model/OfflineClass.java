package com.mapolbs.vizibeebritannia.Model;

import org.json.JSONObject;

/**
 * Created by RAMMURALI on 13/11/2017.
 */
public class OfflineClass {
    JSONObject jobj;
    String surveyid;

    public JSONObject getJobj() {
        return jobj;
    }

    public void setJobj(JSONObject jobj) {
        this.jobj = jobj;
    }

    public String getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public OfflineClass(JSONObject jobj,String surveyid)
    {
        this.jobj = jobj;
        this.surveyid = surveyid;
    }
}
