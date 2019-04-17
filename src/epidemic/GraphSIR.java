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

/**
 *
 * @author ainte
 */
public class GraphSIR extends Graph {

    public GraphSIR(PApplet parent, int size, int counter) {
        super(parent, size);

        for (int i = 0; i < size; i++) {

            Node n = new Node(parent, i, parent.width / 2 + 500 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 500 * PApplet.sin(PApplet.TWO_PI / size * i), counter);

//              Node n = new Node(parent, i, parent.random(parent.width), parent.random(parent.height));
            graph.add(n);
        }

        for (Node n : graph) {

            for (int i = 0; i < neighborNumber; i++) {

                n.addNeighbor(graph.get(i));
            }
        }
        System.out.println("creato grafo");
    }

    @Override
    public void overGraph() {

        for (Node n : graph) {
            if (PApplet.dist(parent.mouseX, parent.mouseY, n.location.x, n.location.y) < n.diameter / 2) {
                if (n.status == Status.SUSCEPTIBLE) {
                    n.setInfected();
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

        for (int i = 0; i < size; i++) {

            Node n = graph.get(i);
            if (n.status == Status.SUSCEPTIBLE) {
                susceptible += 1;
            }
        }
        
        
        
        parent.fill(Color.BLACK.getRGB());
        parent.text("S: " + susceptible, parent.width / 2, parent.height / 2);
        parent.text("Average traffic: " + Values.infoSent/(float) size, parent.width/2, parent.height/2 + 20);
    }

}
