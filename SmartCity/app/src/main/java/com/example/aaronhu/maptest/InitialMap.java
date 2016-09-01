package com.example.aaronhu.maptest;

/**
 * Created by aaronhu on 7/18/16.
 */
public class InitialMap {
    int leng = 500, space = 70, rdi = leng -space, height = 500;

    private Node nodes[];
    public InitialMap(){
        CreateMap map = new CreateMap(13);
        map.addEdges(1, 2);
        map.addEdges(2, 3);
        map.addEdges(2, 6);
        map.addEdges(3, 4);
        map.addEdges(4, 5);
        map.addEdges(4, 7);
        //map.addEdges(6, 9);
        map.addEdges(7, 11);
        map.addEdges(8, 9);
        map.addEdges(9, 10);
        map.addEdges(10, 11);
        map.addEdges(11, 12);

        map.addStops(1, 1);
        map.addStops(1, 4);
        map.addStops(3, 2);
        map.addStops(3, 5);
        map.addStops(5, 3);
        map.addStops(5, 6);
        map.addStops(6, 4);
        map.addStops(6, 5);
        map.addStops(7, 5);
        map.addStops(7, 6);
        map.addStops(8, 4);
        map.addStops(8, 7);
        map.addStops(10, 5);
        map.addStops(10, 8);
        map.addStops(12, 6);
        map.addStops(12, 9);



        map.addPosition(1,rdi/2,leng-space/2);
        map.addPosition(2,leng-space/2,leng-space/2);
        map.addPosition(3,leng+rdi/2,leng-space/2);
        map.addPosition(4,2*leng-space/2,leng-space/2);
        map.addPosition(5,2*leng+rdi/2,leng-space/2);
        map.addPosition(6,leng-space/2,leng+rdi/2);
        map.addPosition(7,2*leng-space/2,leng+rdi/2);
        map.addPosition(8,rdi/2,2*leng-space/2);
        map.addPosition(9,leng-space/2,2*leng-space/2);
        map.addPosition(10,leng+rdi/2,2*leng-space/2);
        map.addPosition(11,2*leng-space/2,2*leng-space/2);
        map.addPosition(12,2*leng+rdi/2,2*leng-space/2);



        this.nodes = map.nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
