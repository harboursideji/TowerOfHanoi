package com.jixin.towerofhanoi;



public class Record {

    private String id;
    private String grade;

    public Record(){
        this.id=Constants.Default_Id;
        this.grade=Constants.Default_Grade;
    }
    public Record(String grade){
        this.id =Constants.Default_Id;
        this.grade=grade;
    }
    public Record(String id,String grade){
        this.id=id;
        this.grade=grade;
    }

    public  String getId(){
        return id;
    }

    public String getGrade(){
        return grade;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setGrade(String grade){
        this.grade=grade;
    }

}
