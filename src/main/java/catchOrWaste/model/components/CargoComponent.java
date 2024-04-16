package catchOrWaste.model.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;


public class CargoComponent extends Component {

    private Entity catchedEntity;

    public CargoComponent(Entity entity){
        this.catchedEntity = entity;
    }

    public boolean isFull() {
        return this.catchedEntity != null;
    }

    public Entity getCatchedEntity() {
        return catchedEntity;
    }

    public void setCatchedEntity(Entity entity) {
        this.catchedEntity = entity;
    }

}
