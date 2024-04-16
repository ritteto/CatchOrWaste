package catchorwaste.model.components;

import catchorwaste.model.enums.ItemStatus;
import com.almasb.fxgl.entity.component.Component;

public class ItemStatusComponent extends Component {
    private ItemStatus status;

    public ItemStatusComponent(ItemStatus status) {
        this.status = status;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
