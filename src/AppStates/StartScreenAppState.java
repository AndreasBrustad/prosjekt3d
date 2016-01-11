/*
 * 
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
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenAppState extends AbstractAppState implements ScreenController {
    protected SocketClient socketClient;
    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private AppStateManager stateManager;
    private boolean flagSoundEnabled = true;
    private Listener listener;
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    private Node sceneNode;
    protected String ip_address, port;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        this.app.getFlyByCamera().setEnabled(false);
        this.listener = this.app.getListener();
        
        startNifty();
    }
    
    private void startNifty(){
        niftyDisplay = new NiftyJmeDisplay(assetManager,inputManager,audioRenderer,guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Gui/startScreen.xml","start", this);
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    public void menuShowStadium(){
        ip_address = nifty.getCurrentScreen().findNiftyControl("ip_address_edit", TextField.class).getDisplayedText();
        port = nifty.getCurrentScreen().findNiftyControl("port_edit", TextField.class).getDisplayedText();
        socketClient = new SocketClient(Integer.parseInt(port), ip_address);
        System.out.println("going to stadium\nIp address: " + ip_address + "\nPort: " + port);
        StadiumAppState stadiumAppState = new StadiumAppState();
        nifty.exit();
        stateManager.attach(stadiumAppState);
    }
    
    public void menuShowTrainingField(){
        ip_address = nifty.getCurrentScreen().findNiftyControl("ip_address_edit", TextField.class).getDisplayedText();
        port = nifty.getCurrentScreen().findNiftyControl("port_edit", TextField.class).getDisplayedText();
        socketClient = new SocketClient(Integer.parseInt(port), ip_address);
        System.out.println("going to Training field\nIp address: " + ip_address + "\nPort: " + port);
        TrainingFieldAppState trainingFieldAppState = new TrainingFieldAppState();
        nifty.exit();
        stateManager.attach(trainingFieldAppState);
    }
    
    public void menuQuitGame(){
        app.stop();
    }
    
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind(" + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
        
    }
}