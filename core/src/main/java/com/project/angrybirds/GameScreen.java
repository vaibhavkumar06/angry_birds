package com.project.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;
import java.util.ArrayList;


public class GameScreen implements Screen {

    final Drop game;
    Texture backgroundImage;
    Texture catapultImage;
    Texture bird1Image, bird2Image, bird3Image;
    OrthographicCamera camera;
    Texture woodVerticalTexture;
    Texture woodHorizontalTexture;
    Texture tntTexture;
    Texture pigTexture;
    Stage stage;

    TextButton resumeButton;
    TextButton restartButton;
    TextButton exitButton;
    Table pauseMenuTable;

    BitmapFont font;
    int score;


    Texture pauseButtonTexture;
    ImageButton pauseButton;
    boolean isPaused;

    // Declare blocks and pigs
    List<Block> blocks = new ArrayList<>();
    List<Pig> pigs = new ArrayList<>();

    public GameScreen(final Drop gam) {
        this.game = gam;

        // Load the background and catapult images
        backgroundImage = new Texture(Gdx.files.internal("game_background.jpg"));
        catapultImage = new Texture(Gdx.files.internal("catapult.png"));
        bird1Image = new Texture(Gdx.files.internal("bird1.png"));
        bird2Image = new Texture(Gdx.files.internal("bird2.png"));
        bird3Image = new Texture(Gdx.files.internal("bird3.png"));

        // Initialize camera and stage
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage();

        // Pause button setup
        pauseButtonTexture = new Texture(Gdx.files.internal("pause_gamescreen.png"));
        pauseButton = new ImageButton(new TextureRegionDrawable(pauseButtonTexture));
        pauseButton.setSize(32,32);
        pauseButton.setPosition(10, 480 - pauseButton.getHeight() - 10);  // Top-left position

        // Load textures
        woodVerticalTexture = new Texture(Gdx.files.internal("veritical_thin.jpg"));
        woodHorizontalTexture = new Texture(Gdx.files.internal("horizontal_thin.jpg"));
        tntTexture = new Texture(Gdx.files.internal("tnt_bomb.jpg"));
        pigTexture = new Texture(Gdx.files.internal("pig.png"));

        blocks = new ArrayList<>();
        pigs = new ArrayList<>();

        // Add blocks to the structure (coordinates and sizes are examples)
        // First layer (bottom)
        blocks.add(new Block(woodVerticalTexture, 600, 65, 10, 100)); // Left vertical
        blocks.add(new Block(woodVerticalTexture, 680, 65, 10, 100)); // Right vertical
        blocks.add(new Block(woodHorizontalTexture, 600, 160, 90, 10)); // Horizontal beam

        // Second layer
        blocks.add(new Block(woodVerticalTexture, 600, 170, 10, 30)); // Left vertical
        blocks.add(new Block(woodVerticalTexture, 680, 170, 10, 30)); // Right vertical
        blocks.add(new Block(woodHorizontalTexture, 600, 200, 90, 10)); // Horizontal beam

        // Add TNT block
        blocks.add(new Block(tntTexture, 633, 170, 25, 25)); // TNT on the side

        // Third layer
        blocks.add(new Block(woodVerticalTexture, 600, 210, 10, 60)); // Left vertical
        blocks.add(new Block(woodVerticalTexture, 680, 210, 10, 60)); // Right vertical
        blocks.add(new Block(woodHorizontalTexture, 600, 270, 90, 10)); // Horizontal beam

        // Add pigs
        pigs.add(new Pig(pigTexture, 625, 60, 40, 40)); // Pig on first layer
        pigs.add(new Pig(pigTexture, 625, 203, 40, 40)); // Pig on second layer



        // Add listener for pause button click
        pauseButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;  // Toggle pause state
                pauseMenuTable.setVisible(isPaused);  // Show or hide pause menu based on the state
            }
        });

        // Add the pause button to the stage
        stage.addActor(pauseButton);

        // Make sure input events are processed
        Gdx.input.setInputProcessor(stage);

        // Initialize as not paused
        isPaused = false;

        font = new BitmapFont(); // Default font, but you can load your own font if needed
        score = 0;  // Initialize score to 0

        // Create buttons for pause menu
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // LibGDX's default skin, or you can use your own

        resumeButton = new TextButton("Resume", skin);
        restartButton = new TextButton("Restart", skin);
        exitButton = new TextButton("Exit", skin);

        // Create a table to organize the pause menu buttons
        pauseMenuTable = new Table();
        pauseMenuTable.setFillParent(true);  // Center the table on the stage

        // Add buttons to the table with padding
        pauseMenuTable.add(resumeButton).pad(10).row();
        pauseMenuTable.add(restartButton).pad(10).row();
        pauseMenuTable.add(exitButton).pad(10).row();

        // Initially, hide the pause menu (it only appears when paused)
        pauseMenuTable.setVisible(false);

        // Add the table to the stage
        stage.addActor(pauseMenuTable);
    }

    @Override
    public void render(float delta) {
        // Clear the screen and set the background color
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera matrices
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (!isPaused) {
            // Draw the background, catapult, and birds
            game.batch.begin();
            game.batch.draw(backgroundImage, 0, 0, 800, 480);
            game.batch.draw(catapultImage, 160, 65, 64, 70);  // Example catapult positioning
            game.batch.draw(bird1Image, 130, 65, 20, 20);
            game.batch.draw(bird2Image, 100, 65, 20, 20);
            game.batch.draw(bird3Image, 70, 65, 20, 20);

            // Render blocks
            for (Block block : blocks) {
                block.render(game.batch);
            }

            // Render pigs
            for (Pig pig : pigs) {
                pig.render(game.batch);
            }

            // Render the score on the top-right corner of the screen
            String scoreText = "Score: " + score;
            font.draw(game.batch, scoreText, 800 - 100, 480 - 10); // Positioned at the top-right with some padding

            game.batch.end();

            // Render the stage (which includes the pause button)
            stage.act(delta);
            stage.draw();
        }

        // If paused, skip updating game logic
        if (isPaused) {
            pauseMenuTable.setVisible(true);

            // Handle resume button click
            if (resumeButton.isPressed()) {
                isPaused = false; // Resume the game
                pauseMenuTable.setVisible(false); // Hide the pause menu
            }

            // Handle restart button click
            if (restartButton.isPressed()) {
                // Add logic here to reset the game state
            }

            // Handle exit button click
            if (exitButton.isPressed()) {
                Gdx.app.exit(); // Exit the game
            }

            // Skip game updates when paused
            return;
        }

        // If not paused, handle game logic here
        if (!isPaused) {
            // Game logic updates
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        catapultImage.dispose();
        bird1Image.dispose();
        bird2Image.dispose();
        bird3Image.dispose();
        pauseButtonTexture.dispose();
        woodVerticalTexture.dispose();
        woodHorizontalTexture.dispose();
        tntTexture.dispose();
        pigTexture.dispose();
        stage.dispose();
        font.dispose(); // Dispose the font when no longer needed

        // Dispose blocks and pigs
        for (Block block : blocks) {
            block.dispose();
        }
        for (Pig pig : pigs) {
            pig.dispose();
        }
    }
}
