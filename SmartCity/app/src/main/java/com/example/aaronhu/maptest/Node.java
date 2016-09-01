package com.example.aaronhu.maptest;

import java.util.ArrayList;
/***
 * Node.java
 * @author aaronhu
 * November 28,2015
 *
 * Create a object, record all information needed for a node.
 * Node is for create a new node.
 * addEdge is for add edge to ArrayList edges.
 *
 */


public class Node {
    int label;
    int positionX;
    int positionY;
    String color;
    ArrayList<Integer> edges;
    ArrayList<Integer> stops;


    public Node(){

        edges = new ArrayList();
        stops = new ArrayList();
        color = "white";
    }
    public void addEdge(int edge){
        edges.add(edge);
    }
    public void addStop(int stop){
        stops.add(stop);
    }





}