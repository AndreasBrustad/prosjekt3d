/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppStates;

import classes.Recording;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Andreas
 */
public class StadiumAppState extends AbstractAppState {

    private SimpleApplication app;
    private FlyByCamera flyCam;
    private Node rootNode;
    private Node guiNode;
    private Node sceneNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private ViewPort viewPort;
    private AudioRenderer audioRenderer;
    private Listener listener;
    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");
    private final ColorRGBA backgroundColor = ColorRGBA.Gray;
    private StartScreenAppState startScreenAppState;
    
    private Recording recording2;
    private float[] test;
    private Spatial football;
    private int counter = 0;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.flyCam = this.app.getFlyByCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        flyCam.setEnabled(true);
        
        startScreenAppState = stateManager.getState(StartScreenAppState.class);
        buildStadium();
    }
    
    @Override
    public void update(float tpf){
        if (test[counter] != 0.0f) {
            football.setLocalTranslation(test[counter+0], 0.5f , test[counter+1]); 
            //Vector3f vector = new Vector3f(test[counter+0]-transx/6, 1.5f , test[counter+1]-transy/6 + 2);
            //cam.setLocation(vector);
        }
        counter += 3;
        if (counter >= recording2.getNumberOfTimestamps()*3) {
            counter = 0;
        }
    
    }
    public void buildStadium() {
        if(rootNode != null) {
            rootNode.detachAllChildren();
        }
        
        //Legger inn fotballbanen
        Spatial footballField = assetManager.loadModel("Models/Soccer Arena/Soccer Arena.j3o");
        footballField.setLocalTranslation(counter, counter, counter);
        rootNode.attachChild(footballField);
        
        //Legger til fotballen
        football = assetManager.loadModel("Models/Soccer Arena/Football.j3o");
        football.setLocalScale(0.5f, 0.5f, 0.5f);
        rootNode.attachChild(football);
        
        // Testing Coordinate class.
        recording2 = new Recording("src\\socket_data\\socket_20151112_134007.dat", 1);
        test = recording2.getCoordinatesMarker(1);
        
        
        //Setter lys
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -0.3f));
        rootNode.addLight(sun);
        
        //Setter kamera info
       
        flyCam.setMoveSpeed(30);
        flyCam.setRotationSpeed(3);
        flyCam.setZoomSpeed(30);
    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
        System.out.println("onStartScreenStadium");

    }

    public void onEndScreen() {
    }
}
