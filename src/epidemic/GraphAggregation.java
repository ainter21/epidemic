/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Alberto
 */
public class GraphAggregation extends Graph{
    
    public GraphAggregation(PApplet parent, int size){
        super(parent, size);
        
        for(int i=0; i < size; i++){
            
            NodeAggregation n = new NodeAggregation(parent, i, parent.width / 2 + 300 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 300 * PApplet.sin(PApplet.TWO_PI / size * i));
            n.locationInfo = new PVector(parent.width / 2 + 360 * PApplet.cos(PApplet.TWO_PI / size * i), parent.height / 2 + 360 * PApplet.sin(PApplet.TWO_PI / size * i));
            
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
        
    }
    
    
    @Override
    public NodeAggregation getNode(int index) {
        return (NodeAggregation) graph.get(index);
    }
    
}
