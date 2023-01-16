package com.clayplug.utilities.mouse;

import org.osbot.rs07.script.MethodProvider;

import java.awt.*;
import java.util.LinkedList;

/**
 * Mouse Trail heavily inspired by Shaft, DarkMagician, Swizzbeat, Enfilade, and fixthissite
 */

public class MouseTrail {

    private int r;
    private int g;
    private int b;
    private final int duration;
    public LinkedList<MousePathPoint> mousePath = new LinkedList<>();


    public MouseTrail(int r, int g, int b, int duration) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.duration = duration;
    }

    public Color nextRGB() {
        boolean maxRed = r == 185;
        boolean minRed = r == 78;
        boolean someRed = r > 78;
        boolean notAllRed = r < 18;

        boolean maxGreen = g == 116;
        boolean minGreen = g == 53;
        boolean someGreen = g > 53;
        boolean notAllGreen = g < 71;

        boolean maxBlue = b == 73;
        boolean minBlue = b == 0;
        boolean someBlue = b > 0;
        boolean notAllBlue = b < 73;

        boolean needsGreen = maxRed && minBlue && notAllGreen;
        boolean reduceGreen = minRed && maxBlue && someGreen;
        boolean needsBlue = maxGreen && minRed && notAllBlue;
        boolean reduceBlue = minGreen && maxRed && someBlue;
        boolean needsRed = maxBlue && minGreen && notAllRed;
        boolean reduceRed = minBlue && maxGreen && someRed;

        r += needsRed ? 1 : reduceRed ? -1 : 0;
        g += needsGreen ? 1 : reduceGreen ? -1 : 0;
        b += needsBlue ? 1 : reduceBlue ? -1 : 0;

        return new Color(r,g,b);
    }


    public void paint(MethodProvider api, Graphics2D g) {

        Point clientCursor = api.getMouse().getPosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, duration);
        while (!mousePath.isEmpty() && mousePath.peek().isCurrentTimeAhead()) {
            mousePath.remove();
        }
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp)) {
            mousePath.add(mpp);
        }
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                g.setColor(nextRGB());
                g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
                //Following lines for "chalky" effect
                g.drawLine(a.x + 1, a.y + 1, lastPoint.x, lastPoint.y);
                g.drawLine(a.x - 1, a.y - 1, lastPoint.x, lastPoint.y);
            }
            lastPoint = a;
        }

    }
}