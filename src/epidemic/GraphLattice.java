/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import processing.core.PApplet;

/**
 *
 * @author Alberto
 */
public class GraphLattice extends Graph {

    public GraphLattice(PApplet parent, int size) {
        super(parent, size);

        int x = (int) Math.floor(Math.sqrt(size));
        int s = size % x == 0 ? 0 : 1;

        int y = size / x + s;

        int name = 0;
        int k = 75;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {

                if (name < size) {
                    Node n = new Node(parent, name, parent.width / 2 - k * x / 2 + j * k, parent.height / 2 - k * y / 2 + i * k);
                    graph.add(n);
                    name++;
                }
            }
        }

        for (Node n : graph) {

            if (n.name + 1 < size) {
                if ((n.name + 1) % x != 0) {
                    Node neigh = getNode(n.name + 1);
                    n.addNeighbor(neigh);
                    neigh.addNeighbor(n);
                }
            }
            if (n.name + x < size) {
                Node neigh = getNode(n.name + x);
                n.addNeighbor(neigh);
                neigh.addNeighbor(n);

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

    public void addEdge() {
        Node node1 = getNode((int) parent.random(size));
        Node node2 = getNode((int) parent.random(size));

        if (node1 != node2) {
            node1.addNeighbor(node2);
            node2.addNeighbor(node1);
        }
    }

}
