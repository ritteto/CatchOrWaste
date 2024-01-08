package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.component.Component;

public class ImageNameComponent extends Component {
    private final String imageName;

    public ImageNameComponent(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}

