package catchorwaste.view;


import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.ImageView;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class BackgroundView {
    public static void setStreetImage(Boolean left){
        for (Entity en :FXGL.getGameWorld().getEntitiesByType(EntityType.BACKGROUND)) {
            if(en.getComponent(ImageNameComponent.class).getImageName().equals("streets")){
                if(left){
                    setImageFullscreen(en, "streets_left");
                }else{
                    setImageFullscreen(en, "streets_right");
                }
            }
        }
    }

    private static void setImageFullscreen(Entity en, String imageName){
        en.getViewComponent().clearChildren();
        en.getViewComponent().addChild(new ImageView(imageMap.get(imageName)));
        en.getViewComponent().getChild(0, ImageView.class).setFitWidth(getAppWidth());
        en.getViewComponent().getChild(0, ImageView.class).setFitHeight(getAppHeight());
    }
}
