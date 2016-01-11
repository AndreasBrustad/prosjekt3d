/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppStates;

import classes.Circle2d;
import classes.drawTrainingField;
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
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

public class TrainingFieldAppState extends AbstractAppState {

    private SimpleApplication app;
    private FlyByCamera flyCam;
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
    private float[] test = new float[100];
    private Spatial football;
    private int counter = 0;
    private Circle2d circle;
    private drawTrainingField footballField;
    private float diffx, diffy;

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
        System.out.println("f0r connect");
        startScreenAppState.socketClient.connect();
        System.out.println("etter connect");
        startScreenAppState.socketClient.getCoordinates(startScreenAppState.socketClient.getString(), test);
        System.out.println("første getstring og getcoordinates");
        buildStadium();
    }

    @Override
    public void update(float tpf) {
        startScreenAppState.socketClient.getCoordinates(startScreenAppState.socketClient.getString(), test);
        if (test[0] != 0.0f) {
            football.setLocalTranslation(test[0 + 3], test[1 + 3], test[2 + 3]);
        }
    }

    public void buildStadium() {
        diffx = (float) 0.5 * (test[9] - test[0]);
        diffy = (float) 0.1 * (test[7] - test[1]);
        
        if (rootNode != null) {
            rootNode.detachAllChildren();
        }
        
        //Legger inn fotballbanen
        footballField = new drawTrainingField(assetManager, "src\\socket_data\\socket_20151112_132337.dat");
        footballField.setLocalTranslation(test[0] - diffx+20, test[1] - diffy-20, test[5] - 7f);
        rootNode.attachChild(footballField);

        //Legger til fotballen
        football = assetManager.loadModel("Models/Soccer Arena/Football.j3o");
        football.setLocalScale(7.0f, 7.0f, 7.0f);
        rootNode.attachChild(football);

        //Setter lys
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -0.3f));
        rootNode.addLight(sun);
        rootNode.rotate(-FastMath.PI / 2, 0, 0);

        //Setter kamera info
        Vector3f vector = new Vector3f(400f, 300f, 100f);
        cam.setLocation(vector);
        flyCam.setMoveSpeed(210);
        flyCam.setRotationSpeed(3);
        flyCam.setZoomSpeed(210);
    }

    public void bind(Nifty nifty, Screen screen) {}

    public void onStartScreen() {
        System.out.println("onStartScreenStadium");
    }
    public void onEndScreen() {}
}