package com.jixin.towerofhanoi;


import java.util.Stack;

public class HanoiTower {

    private Stack<RoundDish> towerStack;
    public HanoiTower(){

        towerStack=new Stack<>();
    }



    public int getSize(){
        return towerStack.size();
    }

    public void push(RoundDish roundDish){
        towerStack.push(roundDish);
    }

    public RoundDish getTop(){
        return towerStack.pop();
    }

    public RoundDish getPeek(){
        return towerStack.peek();
    }

    public boolean canPlaceDish(RoundDish roundDish){
        return towerStack.peek().getDishSize()>roundDish.getDishSize();
    }

}
