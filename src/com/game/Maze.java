package com.game;

import com.dsa.Queue;
import com.statemachine.State;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.sql.Array;
import java.sql.Struct;
import java.util.Arrays;

public class Maze {

    private final int width;
    private final int height;
    private Graphics g;
    Graph graph = Graph.EX1;
    private final int[] prev;
    private final int n;
    private Queue q;
    private int currNode;
    boolean[] visited;
    boolean[] expored;
    private int dist[];
    private int currDist = -1;
    private int source, target;
    private int currState = State.BFS_INIT;
    int currSrc=-1;

    private boolean pathtrace[];
    private int offX=50;
    private int offY=50;

    private boolean discarded[];

    public Maze(Graphics g, int width, int height) {
        this.g = g;
        this.width = width;
        this.height = height;
        this.n = height * width;
        this.prev = new int[n];
        this.q = new Queue(n);
        visited = new boolean[n];
        this.expored = new boolean[n];
        this.dist = new int[n];
        Arrays.fill(visited, false);
        Arrays.fill(expored, false);
        Arrays.fill(prev, -1);
        Arrays.fill(dist, -1);
        discarded=new boolean[n];
        pathtrace=new boolean[n];

        Arrays.fill(discarded, false);
        Arrays.fill(pathtrace, false);


        initGraph();

    }

    public Maze(Graphics g) {
        this.g = g;
        this.width = 8;
        this.height = 10;
        this.n = height * width;
        this.prev = new int[n];
        this.q = new Queue(n);
        visited = new boolean[n];
        this.expored = new boolean[n];
        this.dist = new int[n];

        Arrays.fill(visited, false);
        Arrays.fill(expored, false);
        Arrays.fill(prev, -1);
        Arrays.fill(dist, -1);
        discarded=new boolean[n];
        Arrays.fill(discarded, false);
        pathtrace=new boolean[n];

        Arrays.fill(pathtrace, false);



    }

    public void BFS(int source) throws Throwable {
        int curr = source;
        int i = 0;
        q.enqueue(curr);
        while (!q.isEmpty()) {
            curr = q.dequeue();
            if (expored[curr]) {
                continue;
            }
            for (int node : graph.graph[curr]) {
                if (this.visited[node]) {
                    continue;
                }

                this.visited[node] = true;
                this.prev[node] = curr;
                q.enqueue(node);
            }
            this.expored[curr] = true;
        }
        System.out.println(Arrays.toString(prev));
    }

    public void render() {
        drawGrid();

    }

    public void drawGrid(){
        int i=0,j=0;
        g.setColor(Color.WHITE);
        for(int node=0;node<n;node++){
            if(discarded[node]){
                i=node/width;
                j=node-i*width;
                g.fillRect(offX-2+j*25,offY-2+i*25,24,24);
            }
        }
    }

    public int getN() {
        return n;
    }

    public void drawPath(int source, int target) {
        int counter = 0;
        int pV = 0;
        int iP, jP;
        int iT, jT;
        while (target != source) {
            if(pathtrace[target]){
                return;
            }
            pV = prev[target];
            iT = target / this.width;
            jT = target - iT * width;
            iP = pV / this.width;
            jP = pV - iP * this.width;

            g.drawLine(offX+10 + jP * 25, offY+10 + iP * 25, offX+10 + jT * 25, offY+10 + iT * 25);
            pathtrace[target]=true;
            target = pV;
            System.out.println(counter++);

        }
    }

    public int stepForward() throws Throwable {
        this.drawGrid();
        g.setColor(Color.BLUE);
        fillNode(source);
        fillNode(target);
        switch (currState) {
            case State.BFS_INIT:
                System.out.println("Maze|stepForward|BFS_INIT");

                this.currNode = this.source;
                this.q.flush();
                this.q.enqueue(this.currNode);
                Arrays.fill(visited, false);
                Arrays.fill(expored, false);
                Arrays.fill(prev, -1);
                Arrays.fill(dist, -1);
                this.currDist = 0;
                dist[currNode]=0;
                this.currState = State.BFS_RUNNING;
                break;
            case State.BFS_RUNNING:
                System.out.println("Maze|stepForward|BFS_RUNNING");


                if (!q.isEmpty()) {
                    this.currNode = q.dequeue();
                    currDist=dist[currNode];
                    g.setColor(Color.RED);
                    Arrays.fill(pathtrace,false);
                    for (int i = 0; i < n; i++) {
                        if (this.dist[i] >0) {
                            this.drawPath(this.source, i);
                        }
                    }


                    g.setColor(Color.YELLOW);
                    fillNode(currNode);
                    if (expored[currNode]) {
                        break;
                    }
                    g.setColor(Color.CYAN);
                    for (int node : graph.graph[currNode]) {
                        if (node<0||visited[node]) {
                            continue;
                        }
                        this.visited[node] = true;
                        this.prev[node] = currNode;
                        dist[node]=dist[currNode]+1;
                        if(node>0){
                            q.enqueue(node);
                            fillNode(node);

                        }

                    }
                } else {
                    this.currState = State.BFS_COMPLETED;
                    currSrc=target;
                }
                break;
            case State.BFS_COMPLETED:
                System.out.println("Maze|stepForward|BFS_COMPLETE");

                g.setColor(Color.RED);
                this.currSrc=this.prev[currSrc];
                Arrays.fill(pathtrace,false);

                this.drawPath(currSrc, target);
                if(currSrc==source){
                    currState=State.IDEAL_STATE;
                }
                break;
            default:
                break;
        }

        return this.currState;
    }

    public void fillNode(int node){
        int i=node/width;
        int j=node-i*width;
        g.fillRect(offX+j*25,offY+i*25,20,20);
    }

    public void drawMaze(){
        g.setColor(Color.WHITE);
//        g.fillRect(50,50,155,5);
//        g.fillRect(50,205,5,155);
//        g.fillRect(200,50,5,155);
//        g.fillRect(50,200,155,5);

        g.setColor(Color.BLUE);

        g.drawRect(offX-15,offY-15,this.width*25+25,this.height*25+25);
        int node=0;
        g.setColor(Color.WHITE);
        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++){
                node=i*this.width+j;

                if(graph.graph[node][0]<-1){
                    g.fillRect(offX+j*25,offY-5+i*25,20,5);
                }if(graph.graph[node][1]<-1){
                    g.fillRect(offX+20+j*25,offY+i*25,5,20);

                }
                if(graph.graph[node][2]<-1){
                    g.fillRect(offX+j*25,offY+20+i*25,20,5);

                }
                if(graph.graph[node][3]<-1){

                    g.fillRect(offX-5+j*25,offY+i*25,5,20);
                }


            }
        }

    }

    public void flush(){
        Arrays.fill(this.visited,false);
        Arrays.fill(this.expored,false);
        Arrays.fill(this.prev,-1);
        Arrays.fill(this.dist,-1);
        Arrays.fill(pathtrace,false);
        this.q.flush();
        this.currNode=-1;
        this.currState=State.IDEAL_STATE;
        this.currDist=-1;
        this.source=-1;
        this.target=-1;
        this.currSrc=-1;
    }

    public void initGraph(){
        int [][]graph=new int[this.n][4];
        this.graph=new Graph(n,graph);
        for(int i=0;i<this.n;i++){
            graph[i][0]=i-width;
            graph[i][1]=i+1;
            graph[i][2]=i+width;
            graph[i][3]=i-1;
        }


        for(int i=0;i<width;i++){
            graph[i][0]=-1;
        }
        for(int i=n-1;i>=n-width;i--){
            graph[i][2]=-1;
        }

        for(int i=0;i<n;i+=width){
            graph[i][3]=-1;
        }

        for(int i=width-1;i<n;i+=width){
            graph[i][1]=-1;
        }

        printMaze(graph);
    }

    public void printMaze(int [][]graph){
        for(int i=0;i<this.height;i++){
            for(int j=0;j<width;j++){
                for(int k=0;k<4;k++){
                    System.out.print(graph[width*i+j][k]+" ");
                }
                System.out.print("   ");
            }
            System.out.println();
        }
    }


    public void setSrcDest(int src, int dest){
        this.source=src;
        this.target=dest;
    }


    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getCurrState() {
        return currState;
    }
    public void setCurrState(int state){
        this.currState=state;
    }


    public void onDrag(int x,int y){
        x-=offX;
        y-=offY;
        x/=25;
        y/=25;
        if(x>width||y>height){

            this.render();
            return;
        }
        int node=width*y+x;
        discarded[node] = true;

        for(int i=0;i<4;i++){
            graph.graph[node][i]=-1;
        }
        graph.graph[node-1][1]=-1;
        graph.graph[node+1][3]=-1;
        graph.graph[node-width][2]=- 1;
        graph.graph[node+width][0]=-1;

        this.render();

    }


    public void setGraphics(Graphics g){
        this.g=g;
    }
}
