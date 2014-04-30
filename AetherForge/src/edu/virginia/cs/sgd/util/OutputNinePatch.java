package edu.virginia.cs.sgd.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class OutputNinePatch extends NinePatch {
        private static OutputNinePatch instance;
        
        private OutputNinePatch(){
                super(new Texture(Gdx.files.internal("skins/menuSkin.png")), 8, 8, 8, 8);
        }
        
        public static OutputNinePatch getInstance(){
                if(instance == null){
                        instance = new OutputNinePatch();
                }
                return instance;
        }
}
