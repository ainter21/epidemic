/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import processing.core.PApplet;

/**
 *
 * @author ainte
 */
public class GraphSI extends Graph{
    
    public GraphSI(PApplet parent, int size) {
        super(parent, size);
        
        for (int i = 0; i < size; i++) {

            Node n = new Node(parent, i, parent.width / 2 + 500 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 500 * PApplet.sin(PApplet.TWO_PI / size * i));

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
                n.updateValue();
                break;
            }
        }
    }
    
    
    
}
