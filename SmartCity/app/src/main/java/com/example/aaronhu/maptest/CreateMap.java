package com.example.aaronhu.maptest;

import java.util.ArrayList;

/**
 * Created by aaronhu on 7/18/16.
 */


public class CreateMap {

    public Node nodes[];

    public CreateMap(int size){
        nodes = new Node[size];
        for(int newNode = 0;newNode < size; newNode++){
            nodes[newNode] = new Node();
            nodes[newNode].label = newNode;
        }
    }

    public void addEdges(int node1, int node2){
        nodes[node1].addEdge(node2);
        nodes[node2].addEdge(node1);
    }

    public void addPosition(int node, int positionX, int positionY){
        nodes[node].positionX = positionX;
        nodes[node].positionY = positionY;
    }

    public void addStops(int node, int stop){
        nodes[node].addStop(stop);
    }




}
