package com.jixin.towerofhanoi;

public class RoundDish {
    private int dishSize;

    public RoundDish(){
        new RoundDish(Constants.Default_Size);
    }

    public RoundDish(int size){
        dishSize=size;
    }

    public void setDishSize(int i){dishSize=i;}

    public int getDishSize(){
        return dishSize;
    }

}
