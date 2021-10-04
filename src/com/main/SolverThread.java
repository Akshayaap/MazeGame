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

        display.init();

        try {
            maze.stepForward();
            Thread.sleep(5000);
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
