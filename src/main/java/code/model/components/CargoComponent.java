package code.model.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;


public class CargoComponent extends Component {

    private Entity cargo;

    public CargoComponent(Entity entity){
        this.cargo = entity;
    }

    public Entity getCargo() {
        return cargo;
    }

    public void setCargo(Entity cargo) {
        this.cargo = cargo;
    }
}
