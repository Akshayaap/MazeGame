/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;


import com.io.Input;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;


/**
 * @author Akshay
 */
public class Display extends Canvas {

    // Game Windows
    private static long serialVersionID = 1;
    private final JFrame frame;
    private final String title = "MazeGame";
    private int WIDTH = 800;
    private int HEIGHT = 600;

    //Game

    // Graphics
    BufferStrategy bs;
    Graphics g;


    // Input
    Input input;

    public Display() throws Throwable {

        serialVersionID++;
        input = new Input(this);

        this.frame = new JFrame(title);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);
        this.addMouseListener(this.input);
        this.addMouseMotionListener(this.input);
        this.addMouseWheelListener(this.input);

        this.addKeyListener(this.input);
        this.addFocusListener(this.input);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);

        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);
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

    }

    public void init(){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH + 10, HEIGHT + 10);
    }

    public void render() throws Throwable {
        bs.show();
        System.out.println("Display | render");
    }


}