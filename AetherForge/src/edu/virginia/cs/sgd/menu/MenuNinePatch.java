package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class MenuNinePatch extends NinePatch {
        private static MenuNinePatch instance;
        
        private MenuNinePatch(){
                super((Texture) SingletonAssetManager.getInstance().get("Menu Skin"), 8, 8, 8, 8);
        }
        
        public static MenuNinePatch getInstance(){
                if(instance == null){
                        instance = new MenuNinePatch();
                }
                return instance;
        }
}