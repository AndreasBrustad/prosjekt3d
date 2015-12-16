package mygame;

import AppStates.StartScreenAppState;
import com.jme3.app.SimpleApplication;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Andreas
 */
public class Main extends SimpleApplication {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.ALL);
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
       setDisplayFps(false);
       setDisplayStatView(false);
       StartScreenAppState startScreenAppState = new StartScreenAppState();
       stateManager.attach(startScreenAppState);
    }
}
