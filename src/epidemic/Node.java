/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemic;

import epidemic.utils.Status;
import epidemic.utils.Type;
import epidemic.utils.Values;
import epidemic.utils.Visual;
import java.awt.Color;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author ainte
 */
public class Node {

    final int ROUND_LENGTH = 900;
    PApplet parent;

    PVector location;
    PVector locationInfo;
    int diameter = 20;
    ArrayList<Node> neighbors;
    public int name;
    public int value;
    public int timestamp = 0;
    int colorEdge;
    ArrayList<Info> info;
    public int status;
    public int counter = -1;

    public int timer = ROUND_LENGTH;

    /**
     * Node used in SI
     * @param parent
     * @param name
     * @param x
     * @param y 
     */
    public Node(PApplet parent, int name, float x, float y) {

        this.name = name;
        this.parent = parent;
        location = new PVector(x, y);
        neighbors = new ArrayList<>();
        colorEdge = Color.BLACK.getRGB();
        info = new ArrayList<>();
        status = Status.SUSCEPTIBLE;
        value = Color.WHITE.getRGB();

    }

    /**
     * Node used in SIR
     * @param parent
     * @param name
     * @param x
     * @param y
     * @param counter 
     */
    public Node(PApplet parent, int name, float x, float y, int counter) {

        this.name = name;
        this.parent = parent;
        location = new PVector(x, y);
        neighbors = new ArrayList<>();
        colorEdge = Color.BLACK.getRGB();
        info = new ArrayList<>();
        status = Status.SUSCEPTIBLE;
        this.counter = counter;
        value = Color.WHITE.getRGB();

    }

    public void drawNode() {

        parent.fill(value);
        parent.stroke(Color.BLACK.getRGB());

        parent.ellipse(location.x, location.y, diameter, diameter);
        
        if(Visual.nodeInfo){
            
            parent.fill(0);
            parent.textAlign(parent.CENTER, parent.CENTER);
            parent.textSize(11);
            parent.text("ts: " + timestamp, locationInfo.x, locationInfo.y - 15);
            parent.text("timer: " + timer, locationInfo.x, locationInfo.y + 15);
            if(counter != -1){
                
                parent.text("counter: " + counter, locationInfo.x, locationInfo.y);
            }
            
        }

//        parent.fill(Color.BLACK.getRGB());
//        parent.textAlign(PApplet.CENTER, PApplet.CENTER);
//
//        parent.text(value, location.x, location.y);

    }

    public void drawEdges() {

        for (Node neighbor : neighbors) {

            parent.stroke(colorEdge);
            parent.line(location.x, location.y, neighbor.location.x, neighbor.location.y);

        }
    }

    public void addNeighbor(Node n) {

        if (n != this) {
            if (!neighbors.contains(n)) {

                neighbors.add(n);
                n.neighbors.add(this);
            }
        }
    }

    public void deleteNeighbors() {

        for (Node n : neighbors) {
            n.deleteNeighbors(this);
        }
    }

    public void deleteNeighbors(Node n) {

        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i) == n) {
                neighbors.remove(i);
                break;
            }
        }
    }

    public int getNeighborsSize() {
        return neighbors.size();
    }

    void resetNode() {
        
        colorEdge = Color.BLACK.getRGB();
        value = Color.WHITE.getRGB();
    }

    public void updateValue() {

        
        value = parent.color(parent.random(256), parent.random(256), parent.random(256));
        timestamp = parent.millis();
        status = Status.INFECTED;
    }

    /**
     *
     * @param target nodo destinazione
     * @param type tipo di messaggio che voglio inviare: PUSH, PULL, PUSH_PULL o
     * REPLY
     */
    public void sendInfo(Node target, int type) {

        if (target != this) {
            if (neighbors.contains(target)) {
                Info i = new Info(parent, this, target, type, status);
                info.add(i);
                if (type != Type.REPLY) {
                    Values.infoSent += 1;
                }
            }
        }
    }


    public void resetTimer() {
        timer = ROUND_LENGTH;
    }

    public void setValue(Info i) {
        value = i.value;
        timestamp = i.timestamp;
        status = Status.INFECTED;
    }

    public void setInfected() {

        value = Color.RED.getRGB();
        status = Status.INFECTED;
    }

    public void setRemoved() {
        value = Color.GREEN.getRGB();
        status = Status.REMOVED;
    }

    public Node getRandomNeighbor() {

        int i = (int) parent.random(neighbors.size());
        return neighbors.get(i);
    }
    
    public Info receivedInfo(){
        
        for(Node n: neighbors){
            
            for(Info i: n.info){
                
                if (i.arrived() && i.target == this) {
                    if(status == Status.SUSCEPTIBLE && i.status == Status.INFECTED){
                        Values.tAverage += parent.millis();
                        Values.infoArrived += 1.0f;
                    }
                    n.info.remove(i);
                    return i;
                }
            }
        }
        
        return null;
    }
    
    public boolean hasReceived(){
        
        for(Node n: neighbors){
            
            for(Info i: n.info){
                
                if (i.arrived() && i.target == this) {
                   return true;
                }
            }
        }
        
        return false;
    }
}
