/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import epidemic.utils.Status;
import epidemic.utils.Type;
import epidemic.utils.Values;
import java.awt.Color;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author ainte
 */
public class GraphSIR extends Graph {

    int startTime = -1;
    boolean started = false;
    boolean finished = false;
    int endTime;
    int rounds = 0;

    public GraphSIR(PApplet parent, int size, int counter) {
        super(parent, size);

        for (int i = 0; i < size; i++) {

            Node n = new Node(parent, i, parent.width / 2 + 300 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 300 * PApplet.sin(PApplet.TWO_PI / size * i), counter);
            n.locationInfo = new PVector(parent.width / 2 + 440 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 440 * PApplet.sin(PApplet.TWO_PI / size * i));
//              Node n = new Node(parent, i, parent.random(parent.width), parent.random(parent.height));
            graph.add(n);
        }

        for (Node n : graph) {

            for (int i = 0; i < neighborNumber; i++) {

                n.addNeighbor(graph.get(i));
            }
        }
    }

    @Override
    public void overGraph() {

        for (Node n : graph) {
            if (PApplet.dist(parent.mouseX, parent.mouseY, n.location.x, n.location.y) < n.diameter / 2) {
                if (n.status == Status.SUSCEPTIBLE) {
                    n.setInfected();
                    if (!started) {
                        startTime = parent.millis();
                    }
                    break;
                }
            }
        }
    }

    @Override
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

        drawInfo();

        int susceptible = 0;
        int infected = 0;

        for (int i = 0; i < size; i++) {

            Node n = graph.get(i);
            if (n.status == Status.SUSCEPTIBLE) {
                susceptible += 1;
            } else if (n.status == Status.INFECTED) {
                infected++;
            }

        }

        for (Node n : graph) {

            if (startTime != -1) {

                if (n.timer == 0) {
                    rounds++;
                    break;
                }
            }
        }

        parent.textAlign(parent.CENTER, parent.CENTER);
        parent.fill(Color.BLACK.getRGB());
        parent.text("S: " + susceptible, parent.width / 2, parent.height / 2);
        parent.text("Average traffic: " + Values.infoSent / (float) size, parent.width / 2, parent.height / 2 + 20);
        if (Values.infoArrived != 0.0f) {
            parent.text("Average time: " + (Values.tAverage - startTime * Values.infoArrived) / (Values.infoArrived * 1000.0f), parent.width / 2, parent.height / 2 + 40);
        }
        if ((susceptible == 0 || (infected == 0 && startTime != -1)) && !finished) {
            finished = true;
            endTime = parent.millis() - startTime;
        }
        if (finished || (infected == 0 && startTime != -1)) {
            parent.text("Last time: " + endTime / 1000.0f, parent.width / 2, parent.height / 2 + 60);
        }
    }

}
