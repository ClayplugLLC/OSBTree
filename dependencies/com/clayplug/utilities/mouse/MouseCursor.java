package com.clayplug.utilities.mouse;

import org.osbot.rs07.script.MethodProvider;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Mouse Cursor heavily inspired by Shaft, DarkMagician, Swizzbeat, Enfilade, and fixthissite
 */

public class MouseCursor {
    //Mouse paint
    int mouseX, mouseY, mouseSize;
    long angle;
    BasicStroke cursorStroke;
    Color cursorColor;
    AffineTransform oldTransform;
    MethodProvider api;

    public MouseCursor(int size, int thickness, MethodProvider api) {
        this.mouseSize = size;
        this.cursorStroke = new BasicStroke(thickness);
        this.api = api;
    }

    public void paint(Graphics2D g) {
        mouseX = api.getMouse().getPosition().x;
        mouseY = api.getMouse().getPosition().y;

        if (mouseX != -1) {
            mouseSize = 10;
            cursorStroke = new BasicStroke(2.0f);
            // Instantiate clay-colored cursor
            cursorColor = new Color(213,163,114, 155);
            oldTransform = g.getTransform();
            g.rotate(Math.toRadians(angle += 6), mouseX, mouseY);
            g.setColor(cursorColor);
            g.setStroke(cursorStroke);
            g.drawLine(mouseX - mouseSize, mouseY, mouseX + mouseSize, mouseY);
            g.drawLine(mouseX, mouseY - mouseSize, mouseX, mouseY + mouseSize);
            g.setTransform(oldTransform);
        }
    }
}
