package com.example.aaronhu.maptest;

import java.util.ArrayList;

/**
 * Created by aaronhu on 7/18/16.
 */
public class DFS {

    Node nodes[];
    int car, you;
    ArrayList<Node> path = new ArrayList<Node>();
    Boolean root = true;
    Boolean finish = false;

    public DFS(int car, int you){
        this.car = car;
        this.you = you;

        InitialMap map = new InitialMap();
        nodes = map.getNodes();
    }

    public void search(int node){
        //change the color of the current node
        nodes[node].color = "grey";
        if(root){
            path.add(nodes[node]);
            root = false;
        }

        if(nodes[node].stops.contains(car)){
            finish = true;
            return;
        }

        //recursive to its edges.
        for(int nextNode = 0;nextNode<nodes[node].edges.size();nextNode++){
            if(nodes[nodes[node].edges.get(nextNode)].color.equals("white")){

                path.add(nodes[nodes[node].edges.get(nextNode)]);
                search(nodes[node].edges.get(nextNode));


                if(finish){
                    nodes[node].color = "black";
                    return;
                }
                path.remove(path.size() - 1);


            }
        }

        nodes[node].color = "black";
    }

    public ArrayList<Node> getPath() {
        return path;
    }

}
