/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import epidemic.utils.Visual;
import java.awt.Color;
import java.util.ArrayList;
import processing.core.PApplet;

/**
 *
 * @author ainte
 */
abstract class Graph {

    int size;
    int neighborNumber;
    PApplet parent;
    ArrayList<Node> graph;

    boolean newRound = true;

    boolean showEdges = false;

    public Graph(PApplet parent, int size) {

        this.parent = parent;

        this.size = size;
        neighborNumber = size;
        graph = new ArrayList<>();

        if (Visual.speed > 20) {
            Visual.speed = 20;
        }
        if (Visual.speed < 1) {
            Visual.speed = 1;
        }

    }

    public void switchEdges() {

        showEdges = !showEdges;
    }

    public void drawGraph() {

        updateTimer();
        if (showEdges) {
            for (Node n : graph) {
                n.drawEdges();
            }
        }
        graph.forEach((n) -> {
            n.drawNode();
        });

        float mostRecentValue = Color.WHITE.getRGB();
        int mostRecentTimestamp = 0;
        for (Node n : graph) {
            if (n.timestamp > mostRecentTimestamp) {
                mostRecentValue = n.value;
                mostRecentTimestamp = n.timestamp;
            }
        }
        parent.fill(Color.BLACK.getRGB());
        parent.textAlign(parent.LEFT, parent.CENTER);
        parent.text("most recent value: ", parent.width - 250, 100);
        parent.text("most recent timestamp: " + mostRecentTimestamp, parent.width - 250, 130);
        parent.stroke(Color.BLACK.getRGB());
        parent.fill((int) mostRecentValue);
        parent.rectMode(parent.CENTER);
        parent.rect(parent.width - 100, 100, 25, 25);

        drawInfo();
    }

    public void drawInfo() {

        for (Node n : graph) {
            for (Info i : n.info) {
                if (i != null) {

                    i.update();
                    i.display();
                }
            }
        }
    }

    public abstract void overGraph();

    public void updateTimer() {
        for (Node n : graph) {
            n.timer -= 1;
        }
    }

    public Node getNode(int index) {
        return graph.get(index);
    }

}
