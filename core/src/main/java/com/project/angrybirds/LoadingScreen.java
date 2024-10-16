package com.project.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.assets.AssetManager;

public class LoadingScreen extends Game {

    private Stage stage;
    private Skin skin;
    private ProgressBar progressBar;
    private AssetManager assetManager;
    private Texture loadingImage;

    @Override
    public void create() {
        // Initialize the stage and assets
        stage = new Stage(new ScreenViewport());
        assetManager = new AssetManager();

        // Load the loading screen image
        loadingImage = new Texture(Gdx.files.internal("Loading.png")); // Change to your image path

        // Skin for ProgressBar
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Standard LibGDX skin (you can create your own)

        // Create a progress bar (you can design your own)
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBar = new ProgressBar(0f, 1f, 0.01f, false, skin);

        // Add progress bar to a table for centering
        Table table = new Table();
        table.setFillParent(true);
        table.add(progressBar).fillX().pad(10);

        // Add table to stage
        stage.addActor(table);

        // Load assets to simulate loading
        loadAssets();
    }

    private void loadAssets() {
        // Simulate loading assets (in actual project, load real assets here)
        assetManager.load("Background.png", Texture.class); // Add your game assets here
        assetManager.load("Birds.png", Texture.class);
        // Continue loading other assets like sounds, fonts, etc.
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw loading image
        stage.getBatch().begin();
        stage.getBatch().draw(loadingImage, Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2, 300, 300);
        stage.getBatch().end();

        // Update progress
        progressBar.setValue(assetManager.getProgress());

        // If loading is done, move to the next screen
        if (assetManager.update()) {
            // Move to the main game screen or next screen
            // setScreen(new GameScreen(this));  // Assuming you have a GameScreen class
        }

        // Draw progress bar
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        assetManager.dispose();
        loadingImage.dispose();
    }
}
