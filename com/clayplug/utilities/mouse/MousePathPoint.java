package com.clayplug.utilities.mouse;

import java.awt.*;
/**
 * MousePathPoint heavily inspired by Shaft, DarkMagician, Swizzbeat, Enfilade, and fixthissite
 */
public class MousePathPoint extends Point {
    private long finishTime;
    public MousePathPoint(int x, int y, int duration){
        super(x,y);
        finishTime = System.currentTimeMillis() + duration;
    }
    public boolean isCurrentTimeAhead(){
        return System.currentTimeMillis() > finishTime;
    }
}
