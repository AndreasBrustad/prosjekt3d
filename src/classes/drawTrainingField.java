/*
 * Tegner opp fotballbane.
 */
package classes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.awt.Color;



public class drawTrainingField extends Node {
    private String filename;
    private AssetManager assetManager;
    protected float coordsX, coordsY, coordsZ;
    private Recording recording;
    private float transx, transy;
    private Circle2d circle;
    private Material defaultUnshaded;
    
    public drawTrainingField(AssetManager assetManager, String filename) {
        this.assetManager = assetManager;
        this.filename = filename;
        createCorner();
    }
    
    //Metoden henter ut koordinatene til en node fra et opptak. 
    public void getCornerCoords(int corner){
        coordsX = 0;
        coordsY = 0;
        coordsZ = 0;
        recording = new Recording(filename, 0);
        for(int i = 0; i < recording.getNumberOfTimestamps(); i++){
            coordsX += recording.getCoordinate(i, corner);
            coordsY += recording.getCoordinate(i, corner+1);
            coordsZ += recording.getCoordinate(i, corner+2);
        }
        coordsX = coordsX/recording.getNumberOfTimestamps();
        coordsY = coordsY/recording.getNumberOfTimestamps();
        coordsZ = coordsZ/recording.getNumberOfTimestamps();
        System.out.println(coordsX + "  " + coordsY + "   " + coordsZ);
    }
    
    //Metoden oppretter selve fotballbanen utifra gitte koordinater.
    public void createCorner(){      
        
        //Nodene plassert på banen
        //Henter ut koordinatene på hver node og lagrer det som en vektor.
        getCornerCoords(0);//venstre ned
        Vector3f node1 = new Vector3f(coordsX, coordsY, 0);
        
        getCornerCoords(3);
        Vector3f node2 = new Vector3f(coordsX, coordsY, 0);
        
        getCornerCoords(6);
        Vector3f node3 = new Vector3f(coordsX, coordsY, 0);
        
        getCornerCoords(9);
        Vector3f node4 = new Vector3f(coordsX, coordsY, 0);
        
        getCornerCoords(12);
        Vector3f node5 = new Vector3f(coordsX, coordsY, 0);

        getCornerCoords(15);
        Vector3f node6 = new Vector3f(coordsX, coordsY, 0);

        getCornerCoords(18);
        Vector3f node7 = new Vector3f(coordsX, coordsY, 0);

        getCornerCoords(21);
        Vector3f node8 = new Vector3f(coordsX, coordsY, 0);
        
        //Hvor langt fra x- og y-aksen fotballbanen står
        transx = node1.getX();
        transy = node2.getY()-(node2.getY()-node1.getY())*2;
        
        //Tegner opp de forskjellige aspektene med fotballbanen ved hjelp av gitte korrdinater
        //Den er laget ved å tegne rektangler og legge de oppå hverandre. 
        //main field
        CreateQuad((node6.getX()-node1.getX())*2, (node2.getY()-node1.getY())*2, "white", 0, 0, 0);
        CreateQuad((node6.getX()-node1.getX())*2 - 10, (node2.getY()-node1.getY())*2 - 10, "green", 5, 5, 0.1f);
        
        //16-meter white
        CreateQuad(((node6.getX()-node1.getX())*2)-((node3.getX() - node2.getX())*2), node3.getY()-node4.getY(), "white", (node3.getX()-node2.getX()),0, 0.2f);        
        CreateQuad(((node6.getX()-node1.getX())*2)-((node3.getX() - node2.getX())*2), node3.getY()-node4.getY(), "white", (node3.getX()-node2.getX()), (node2.getY()-node1.getY())*2-(node3.getY()-node4.getY()), 0.2f);
        
        //16-meter green
        CreateQuad(((node6.getX()-node1.getX())*2)-((node3.getX() - node2.getX())*2) - 10, node3.getY()-node4.getY() - 10, "green", (node3.getX()-node2.getX()) + 5,0 + 5, 0.3f);        
        CreateQuad(((node6.getX()-node1.getX())*2)-((node3.getX() - node2.getX())*2) - 10, node3.getY()-node4.getY() - 10, "green", (node3.getX()-node2.getX()) + 5, (node2.getY()-node1.getY())*2-(node3.getY()-node4.getY()) + 5, 0.3f);
        
        //5-meter white
        CreateQuad((node6.getX()-node1.getX())*2-(node5.getX()-node2.getX())*2, (node5.getY()-node8.getY()), "white", (node5.getX()-node2.getX()), 0, 0.4f);
        CreateQuad((node6.getX()-node1.getX())*2-(node5.getX()-node2.getX())*2, (node5.getY()-node8.getY()), "white", (node5.getX()-node2.getX()), (node2.getY()-node1.getY())*2-(node5.getY()-node8.getY()), 0.4f);

        //5-meter green
        CreateQuad((node6.getX()-node1.getX())*2-(node5.getX()-node2.getX())*2 - 10, (node5.getY()-node8.getY()) - 10, "green", (node5.getX()-node2.getX()) + 5, 0 + 5, 0.5f);
        CreateQuad((node6.getX()-node1.getX())*2-(node5.getX()-node2.getX())*2 - 10, (node5.getY()-node8.getY()) - 10, "green", (node5.getX()-node2.getX()) + 5, (node2.getY()-node1.getY())*2-(node5.getY()-node8.getY()) + 5, 0.5f);
        
        //mid line
        CreateQuad((node6.getX()-node1.getX())*2, 5, "white", 0, node2.getY()-node1.getY() - 2.5f, 0.3f);
        
        
        //Oppretter midtsirkelen og midtpunktet.
        //mid circle
        circle = new Circle2d(assetManager, (node7.getX()-node6.getX())*2, 2.5f, Color.WHITE, 360, Color.red, 0);
        circle.rotate(FastMath.PI/2, 0, 0);
        circle.setLocalTranslation(node6.getX()-node1.getX() - (node7.getX()-node6.getX()) + transx, node2.getY()-node1.getY() - (node7.getX()-node6.getX()) + transy, 0.6f);
        this.attachChild(circle);
        
        //mid dot
        circle = new Circle2d(assetManager, (10)*2, 2.5f, Color.WHITE, 360, Color.WHITE, 360);
        circle.rotate(FastMath.PI/2, 0, 0);
        circle.setLocalTranslation(node6.getX()-node1.getX() - 10 + transx, node2.getY()-node1.getY() - 10 + transy, 0.8f);
        this.attachChild(circle);
    }
    
    //Metoden som tegner opp rektanglene
    public void CreateQuad(float tall1, float tall2, String color, float trans1, float trans2, float trans3) {
        Quad quad = new Quad(tall1, tall2);
        Geometry field = new Geometry("Field", quad);
        field.setLocalTranslation(new Vector3f(trans1+transx, trans2+transy, trans3));
        defaultUnshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture;
        
        if(color.equals("white")) {
            texture = assetManager.loadTexture("texture/white.png");
        } else {
            texture = assetManager.loadTexture("texture/green.png");
        }
        Material material = defaultUnshaded.clone();
        material.setTexture("ColorMap", texture);
        field.setMaterial(material);

        this.attachChild(field);
    }
}