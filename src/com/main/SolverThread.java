package com.main;

import com.game.Maze;
import com.statemachine.State;

public class SolverThread extends Thread{


    private Display display;
    private  Maze maze;
    private int delay=80;

    public SolverThread(Display display, Maze maze){
        this.display=display;
        this.maze=maze;
    }

    @Override
    public void run(){
        while(maze.getCurrState()!=com.statemachine.State.IDEAL_STATE){
            try {


            if(maze.getCurrState()==com.statemachine.State.BFS_COMPLETED){
                delay=1000;
            }
                display.init();
                maze.stepForward();
                Thread.sleep(delay);
                display.render();




            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        display.init();

        try {
            maze.stepForward();
            display.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }




    }

    public void solve(int source, int target){
        maze.flush();
        maze.setSrcDest(source,target);
        maze.setCurrState(com.statemachine.State.BFS_INIT);
        this.start();
    }
}
