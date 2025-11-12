package movil.JuegoEjm.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import movil.JuegoEjm.JuegoEjm;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Atrapa la Estrella");
        config.setWindowedMode(800, 480);
        config.useVsync(true);
        new Lwjgl3Application(new JuegoEjm(), config);
    }
}
