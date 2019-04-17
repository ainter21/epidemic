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
    int diameter = 25;
    ArrayList<Node> neighbors;
    int name;
    public int value = 0;
    int colorFill;
    int colorEdge;
    ArrayList<Info> info;
    public int status;
    public int counter;

    public int timer = ROUND_LENGTH;

    public Node(PApplet parent, int name, float x, float y) {

        this.name = name;
        this.parent = parent;
        location = new PVector(x, y);
        neighbors = new ArrayList<>();
        colorFill = Color.WHITE.getRGB();
        colorEdge = Color.BLACK.getRGB();
        info = new ArrayList<>();
        status = Status.SUSCEPTIBLE;
        

    }
    public Node(PApplet parent, int name, float x, float y, int counter) {

        this.name = name;
        this.parent = parent;
        location = new PVector(x, y);
        neighbors = new ArrayList<>();
        colorFill = Color.WHITE.getRGB();
        colorEdge = Color.BLACK.getRGB();
        info = new ArrayList<>();
        status = Status.SUSCEPTIBLE;
        this.counter = counter;
        

    }

    public void drawNode() {

        parent.fill(colorFill);
        parent.stroke(Color.BLACK.getRGB());

        parent.ellipse(location.x, location.y, diameter, diameter);

        parent.fill(Color.BLACK.getRGB());
        parent.textAlign(PApplet.CENTER, PApplet.CENTER);

        parent.text(value, location.x, location.y);

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
        colorFill = Color.WHITE.getRGB();
        colorEdge = Color.BLACK.getRGB();
        value = 0;
    }

    public void updateValue() {

        value += 1;
        colorFill = parent.color((value * 5 + 147) % 256, (value * 13 + 171) % 256, (value * 17 + 121) % 256);
        status = Status.INFECTED;
    }

    /**
     * 
     * @param target nodo destinazione
     * @param type tipo di messaggio che voglio inviare: PUSH, PULL, PUSH_PULL o REPLY
     * @param status stato del nodo che invia il messaggio: SUSCEPTIBLE o INFECTED
     * usato per modello SIR
     */
    public void sendInfo(Node target, int type, int status) {

        if (target != this) {
            Info i = new Info(parent, this, target, type, status);
            info.add(i);
            if(type != Type.REPLY){
                Values.infoSent+=1;
            }
        }
    }
    /**
     * 
     * @param target nodo destinazione
     * @param type tipo di messaggio che voglio inviare: PUSH, PULL, PUSH_PULL o REPLY
     * usato per modello SI
     */
    public void sendInfo(Node target, int type) {

        if (target != this) {
            Info i = new Info(parent, this, target, type);
            info.add(i);
            if(type != Type.REPLY){
                Values.infoSent+=1;
            }
        }
    }
    
    
    public void resetTimer(){
        timer = ROUND_LENGTH;
    }
    
    public void setValue(Info i){
        colorFill = i.colorFill;
        value = i.value;
    }
    
    public void setInfected(){
        
        colorFill = Color.RED.getRGB();
        status = Status.INFECTED;
    }
    
    public void setRemoved(){
        colorFill = Color.GREEN.getRGB();
        status = Status.REMOVED;
    }
}
