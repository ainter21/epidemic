/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import epidemic.utils.Type;
import epidemic.utils.Values;
import epidemic.utils.Visual;
import java.awt.Color;
import processing.core.PApplet;

/**
 *
 * @author Alberto
 */
public class NodeAggregation extends Node {

    public NodeAggregation(PApplet parent, int name, float x, float y) {
        super(parent, name, x, y);

        value = name;
    }

    @Override
    public void drawNode() {

        parent.fill(Color.WHITE.getRGB());
        parent.stroke(Color.BLACK.getRGB());

        parent.ellipse(location.x, location.y, diameter, diameter);

        if (Visual.nodeInfo) {

            parent.fill(0);
            parent.textAlign(parent.CENTER, parent.CENTER);
            parent.textSize(11);
            parent.text("timer: " + timer, locationInfo.x, locationInfo.y + 7);
            parent.fill(Color.RED.getRGB());
            parent.text("value: " + value, locationInfo.x, locationInfo.y - 7);

        }

    }

    public void sendInfo(Node target, int type, float value) {

        if (target != this) {
            if (neighbors.contains(target)) {
                Info i = new Info(parent, this, target, type, status);
                i.value = value;
                i.isAggregation = true;
                info.add(i);
                if (type != Type.REPLY) {
                    Values.infoSent += 1;
                }
            }
        }
    }

    public void setValue(float value) {

        this.value = value;
    }

    public NodeAggregation getRandomNeighbor() {

        int i = (int) parent.random(neighbors.size());
        return (NodeAggregation) neighbors.get(i);
    }

}
