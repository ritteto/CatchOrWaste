package catchorwaste.model.components;

import catchorwaste.model.enums.ItemType;
import com.almasb.fxgl.entity.component.Component;

public class ItemTypeComponent extends Component {
    private ItemType type;

    public ItemTypeComponent(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
