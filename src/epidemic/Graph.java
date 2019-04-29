/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

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

        drawInfo();
    }

    public void drawInfo() {

        for (Node n : graph) {
            for (Info i : n.info) {
                if (i != null) {

                    if (i.arrived()) {
                        if (i.value > i.target.value) {
                            i.target.colorFill = i.colorFill;
                            i.target.value = i.value;
//                            i = null;
                        }
                    } else {
                        i.update();
                        i.display();
                    }
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
