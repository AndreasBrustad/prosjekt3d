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
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Andreas
 */
public class StadiumAppState extends AbstractAppState {

    private SimpleApplication app;
    private FlyByCamera flyCam;
    private ChaseCamera chaseCam;
    private Camera cam;
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
    private float[] test = new float[100];
    private Spatial football;
    private Spatial footballField;
    private int counter = 0;
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.flyCam = this.app.getFlyByCamera();
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        flyCam.setEnabled(true);
        
        startScreenAppState = stateManager.getState(StartScreenAppState.class);
        startScreenAppState.socketClient.connect();
        startScreenAppState.socketClient.getCoordinates(startScreenAppState.socketClient.getString(), test);
        buildStadium();
        
    }
    
    @Override
    public void update(float tpf){
//        System.out.println(client.getString());
       
        startScreenAppState.socketClient.getCoordinates(startScreenAppState.socketClient.getString(), test);
       
        if (test[0] != 0.0f) {
//            football.setLocalTranslation(test[counter+0], 0.5f , test[counter+1]);
           
//            football.setLocalTranslation(test[0]/(414/60), 0.5f, test[1]/(670/100));
            football.setLocalTranslation(test[0+3]/(414/60), test[2+3]/(670/100), test[1+3]/(670/100));
            Vector3f lookat = new Vector3f(test[0+3]/(414/60), test[2+3]/(670/100), test[1+3]/(670/100));
            Vector3f up = new Vector3f(0f, 1f, 0f);
            cam.setLocation(new Vector3f(test[0+3]/(414/60)+40f, test[2+3]/(670/100)+25f, test[1+3]/(670/100)));
            cam.lookAt(lookat, up);
           
            System.out.println("X: " + test[0+3] + " " + "Y: " + test[2]+0.5f + " " + "Z: " + test[1+3]);
//            System.out.println("X: " + test[counter+0] + " " + "Y: " + test[counter+2]+0.5f + " " + "Z: " + test[counter+1]);
//        }
//        counter += 3;
//        if (counter >= recording2.getNumberOfTimestamps()*3) {
//            counter = 0;
        }
    }
    public void buildStadium() {
        if(rootNode != null) {
            rootNode.detachAllChildren();
        }
        
        //Legger inn fotballbanen
        footballField = assetManager.loadModel("Models/Soccer Arena/Soccer Arena.j3o");
        footballField.setLocalTranslation(test[0]/(414/60), test[5]/(670/100), test[1]/(670/100)+7.75f);
        rootNode.attachChild(footballField);
        
        Vector3f lookat = new Vector3f(test[0+3]/(414/60), test[2+3]/(670/100), test[1+3]/(670/100));
            Vector3f up = new Vector3f(0f, 1f, 0f);
            cam.setLocation(new Vector3f(test[0+3]/(414/60)+40f, test[2+3]/(670/100)+25f, test[1+3]/(670/100)));
            cam.lookAt(lookat, up);
            
        //Legger til fotballen
        football = assetManager.loadModel("Models/Soccer Arena/Football.j3o");
        football.setLocalScale(0.5f, 0.5f, 0.5f);
        rootNode.attachChild(football);
        
        //Setter lys
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -0.3f));
        rootNode.addLight(sun);
        
        //Setter kamera info
        Vector3f vector = new Vector3f(0f, 15f, 0f);
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
