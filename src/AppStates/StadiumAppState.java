/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppStates;

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
    }
    
    @Override
    public void update(float tpf){
    
    }
    public void buildStadium() {
        if(rootNode != null) {
            rootNode.detachAllChildren();
        }
        Spatial footballField = assetManager.loadModel("Models/Soccer Arena/Soccer Arena.j3o");
        rootNode.attachChild(footballField);
    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
        System.out.println("onStartScreenStadium");

    }

    public void onEndScreen() {
    }
}
