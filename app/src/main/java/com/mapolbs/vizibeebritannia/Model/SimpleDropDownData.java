package com.mapolbs.vizibeebritannia.Model;

/**
 * Created by VENKATESAN on 3/30/2017.
 */
public class SimpleDropDownData {
    private String id;
    private String name;
    private String questionid;
    private String formidid;
    private String childid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getFormidid() {
        return formidid;
    }

    public void setFormidid(String formidid) {
        this.formidid = formidid;
    }

    public String getChildid() {
        return childid;
    }

    public void setChildid(String childid) {
        this.childid = childid;
    }

    public SimpleDropDownData(String id, String name, String questionid, String formidid,String childid){
        super();
        this.id =id;
        this.name =name;
        this.questionid = questionid;
        this.formidid = formidid;
        this.childid = childid;
    }
}
