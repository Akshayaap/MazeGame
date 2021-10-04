/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;


import com.game.Maze;
import com.io.Input;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;


/**
 * @author Akshay
 */
public class MazeMaker extends Canvas implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener,FocusListener {

    // Game Windows
    private static long serialVersionID = 1;
    private final JFrame frame;
    private final String title = "MazeMaker";
    private int WIDTH = 800;
    private int HEIGHT = 600;

    //Game

    // Graphics
    private BufferStrategy bs;
    private Graphics g;

    //Maze
    private Maze maze;


    public MazeMaker(int w,int h) throws Throwable {

        serialVersionID++;

        this.frame = new JFrame(title);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        this.addKeyListener(this);
        this.addFocusListener(this);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);

        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            Display display;
            public void windowClosing(WindowEvent e) {
                try {
                    display=new Display();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }

                g.dispose();
                ((Graphics2D) display.getGraphics()).setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND));
                maze.setGraphics(display.getGraphics());
                SolverThread solver=new SolverThread(display,maze);
                solver.solve(2,388);
                frame.setVisible(false);
                try {
                    solver.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Canvas and Graphics initialization
        bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            bs = this.getBufferStrategy();
        }
        g = bs.getDrawGraphics();
        System.out.println(g);
        ((Graphics2D) g).setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        this.maze=new Maze(g,w,h);

    }

    public void init(){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH + 10, HEIGHT + 10);
    }

    public void render() throws Throwable {


        bs.show();
        System.out.println("Display | render");
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {


    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        try {
            init();
            maze.onDrag(mouseEvent.getX(),mouseEvent.getY());
            render();
            render();
        } catch (Throwable e) {
            e.printStackTrace();
        }



    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    public Maze getMaze(){
        return maze;
    }

    @Override
    public void setVisible(boolean b){
        this.frame.setVisible(b);

    }
}