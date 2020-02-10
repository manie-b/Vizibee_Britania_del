package com.mapolbs.vizibeebritannia.Model;

import org.json.JSONArray;

/**
 * Created by RAMMURALI on 4/12/2017.
 */
public class GridFromLayoutdata {
    String questionid;
    String questionname;
    JSONArray values;

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public JSONArray getValues() {
        return values;
    }

    public void setValues(JSONArray values) {
        this.values = values;
    }


    public GridFromLayoutdata(String questionid, String questionname, JSONArray values)
    {
        this.questionid = questionid;
        this.questionname = questionname;
        this.values = values;
    }
}
