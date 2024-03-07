package code.model.components;

import com.almasb.fxgl.entity.component.Component;


public class CartDirectionComponent extends Component {

    private boolean direction;

    public CartDirectionComponent(Boolean direction){
        this.direction = direction;
    }

    public boolean getDirection() {
        return this.direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }
}
