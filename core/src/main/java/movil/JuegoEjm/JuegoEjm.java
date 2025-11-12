package movil.JuegoEjm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class JuegoEjm extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture player;
    private Texture star;
    private float playerX, playerY;
    private float speed = 200;
    private Rectangle playerRect, starRect;
    private int score = 0;
    private BitmapFont font;
    private Sound pickupSound;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Texture("BlackHole.png");
        star = new Texture("Estrella.png");
        font = new BitmapFont();
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("pickup.wav"));

        // Posicion inicial del jugador
        playerX = 100;
        playerY = 100;

        playerRect = new Rectangle(playerX, playerY, 64, 64);

        // Calcular posiciones para la estrella
        float maxX = Math.max(0, 640 - 48);
        float maxY = Math.max(0, 480 - 48);

        starRect = new Rectangle(
            MathUtils.random(0, 640 - 48),
            MathUtils.random(0, 480 - 48),
            48,
            48
        );

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        // --- Controles de movimiento ---
        // Teclado
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerY -= speed * delta;

        // Pantalla tactil (Celulares)
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX > playerX + player.getWidth() / 2) playerX += speed * delta;
            if (touchX < playerX + player.getWidth() / 2) playerX -= speed * delta;
            if (touchY > playerY + player.getHeight() / 2) playerY += speed * delta;
            if (touchY < playerY + player.getHeight() / 2) playerY -= speed * delta;
        }

        // Actualizar rectángulo del jugador
        playerRect.setPosition(playerX, playerY);

        // --- Lógica de colisión ---
        if (playerRect.overlaps(starRect)) {
            score++;
            pickupSound.play();

            float maxX = Math.max(0, 800 - 48);
            float maxY = Math.max(0, 480 - 48);

            // Buscar una nueva posición que NO colisione con el jugador
            do {
                starRect.setPosition(
                    MathUtils.random(0, maxX),
                    MathUtils.random(0, maxY)
                );
            } while (playerRect.overlaps(starRect));
        }

        // --- Dibujo ---
        batch.begin();
        batch.draw(player, playerX, playerY, 64, 64);
        batch.draw(star, starRect.x, starRect.y, 48, 48);
        font.draw(batch, "Puntos: " + score, 20, 460);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        star.dispose();
        font.dispose();
        pickupSound.dispose();
    }
}
