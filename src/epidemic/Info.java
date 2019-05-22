/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import java.awt.Color;
import processing.core.PApplet;
import processing.core.PVector;
import epidemic.utils.*;

/**
 *
 * @author ainte
 */
public class Info {

    PApplet parent;
    PVector location;
    PVector velocity;

    public float value;
    int SPEED;
    final int DIAMETER = 10;
    public Node origin;
    public Node target;
    public int type;
    public int status;
    public int timestamp;
    boolean isAggregation = false;

    public Info(PApplet parent, Node origin, Node target, int type, int status) {

        this.parent = parent;

        this.origin = origin;
        this.target = target;

        this.type = type;
        this.status = status;
        this.timestamp = origin.timestamp;
        value = origin.value;

        SPEED = (int) (Visual.speed * 7.0 / 9.0);

        location = origin.location.copy();

        velocity = PVector.sub(target.location, location);
        velocity.normalize();
        velocity.mult(SPEED);
    }

    public void update() {
        location.add(velocity);
    }

    public void display() {
        parent.stroke(Color.BLACK.getRGB());
        if (isAggregation) {
            parent.fill(Color.WHITE.getRGB());
        } else {
            parent.fill((int) value);
        }

        parent.ellipse(location.x, location.y, DIAMETER, DIAMETER);
        parent.fill(0);
        parent.textSize(11);
        parent.textAlign(parent.CENTER, parent.CENTER);

        if (Visual.infoType) {
            switch (type) {
                case Type.PUSH:
                    parent.text("PUSH", location.x, location.y - 15);
                    break;
                case Type.PULL:
                    parent.text("PULL", location.x, location.y - 15);
                    break;
                case Type.PUSHPULL:
                    parent.text("PUSHPULL", location.x, location.y - 15);
                    break;
                case Type.REPLY:
                    parent.text("REPLY", location.x, location.y - 15);
                    break;
                default:
                    break;
            }

            if (isAggregation) {

                parent.fill(0);
                parent.textSize(8);
                parent.text(value, location.x, location.y + 15);

            }
        }

    }

    public boolean arrived() {

        return PApplet.dist(location.x, location.y, target.location.x, target.location.y) < 25;
    }

}
