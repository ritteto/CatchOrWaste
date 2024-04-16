package catchorwaste.model.components;

import com.almasb.fxgl.entity.component.Component;


public class IsCatchedComponent extends Component {

    private boolean isCatched;

    public IsCatchedComponent(boolean state){
        this.isCatched = state;
    }

    public boolean isCatched() {
        return this.isCatched;
    }

    public void setCatched(boolean state) {
        this.isCatched = state;
    }
}
