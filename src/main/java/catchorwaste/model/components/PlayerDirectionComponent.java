package catchorwaste.model.components;

import com.almasb.fxgl.entity.component.Component;


public class PlayerDirectionComponent extends Component {

    private boolean direction;

    public PlayerDirectionComponent(Boolean direction){
        this.direction = direction;
    }

    public boolean getDirection() {
        return this.direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }
}
