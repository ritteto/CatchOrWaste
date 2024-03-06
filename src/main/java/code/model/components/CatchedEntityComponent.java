package code.model.components;

import code.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;


public class CatchedEntityComponent extends Component {

    private Entity catchedEntity;

    public CatchedEntityComponent(Entity entity){
        this.catchedEntity = entity;
    }

    public boolean isFull() {return this.catchedEntity != null;}

    public Entity getCatchedEntity() {return catchedEntity;}

    public void setCatchedEntity(Entity entity) {this.catchedEntity = entity;}

}
