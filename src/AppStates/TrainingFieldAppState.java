/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppStates;

import classes.SocketClient;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Andreas
 */
public class TrainingFieldAppState extends AbstractAppState{
  private SimpleApplication app;
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
    
    private String IP = "127.0.0.1";
    private int PORT = 1250;
    
    private SocketClient client = new SocketClient();
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        
        startScreenAppState = stateManager.getState(StartScreenAppState.class);
        buildStadium();
        
        client.connect();
    }
    
    @Override
    public void update(float tpf){
        System.out.println(client.getString());
    
    }
    public void buildStadium() {
        if(rootNode != null) {
            rootNode.detachAllChildren();
        }
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
        System.out.println("onStartScreenStadium");

    }

    public void onEndScreen() {
    }
}
