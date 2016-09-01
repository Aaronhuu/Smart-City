package com.example.aaronhu.maptest;

/**
 * Created by aaronhu on 7/15/16.
 */
public class HandleAllBlocks {
    private String[] blockstatus = new String[9];
    private int car, you;
    public HandleAllBlocks(){

        for(int i=0;i<9;i++){
            blockstatus[i] = "default";
        }
    }

    public void setCar(int car){
        this.car = car;
        blockstatus[car] = "car";
    }

    public void setYou(int you){
        this.you = you;
        blockstatus[you] = "you";
    }

    public String[] getBlockStatus(){
        return blockstatus;
    }




}
