package com.project.angrybirds;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    private MainMenu mainMenu;
    private LevelScreen levelScreen;
    private String playerId;

    @Override
    public void create() {

        playerId = "SJ_2005";
        mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }
    public void switchToLevelScreen() {
        if (levelScreen == null) {

            levelScreen = new LevelScreen(playerId);
        }
        setScreen(levelScreen);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
