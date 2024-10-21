package com.project.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pig {
    Texture texture;
    Rectangle bounds;

    public Pig(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    // Dispose method to release resources
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
