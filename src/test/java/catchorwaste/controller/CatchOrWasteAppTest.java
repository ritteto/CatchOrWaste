package catchorwaste.controller;

import catchorwaste.CatchOrWasteApp;
import catchorwaste.model.TimerModel;
import catchorwaste.view.PunktesystemView;
import catchorwaste.view.TimerView;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatchOrWasteAppTest {

    @InjectMocks
    private CatchOrWasteApp app;

    @Mock
    private TimerModel timerModel;

    @Mock
    private TimerView timerView;

    @Mock
    private PunktesystemView punktesystemView;

    @Mock
    private GameWorld gameWorld;

    @BeforeEach
    public void setUp() {
        app = spy(new CatchOrWasteApp());
    }

    @Test
    public void testInitSettings() {
        GameSettings settings = mock(GameSettings.class);
        app.initSettings(settings);
        verify(settings).setFullScreenAllowed(true);
        verify(settings).setFullScreenFromStart(true);
        verify(settings).setTicksPerSecond(60);
        verify(settings).setTitle("CatchOrWaste");
    }

    @Test
    public void testStartGame() {
        try (MockedStatic<FXGLForKtKt> utilities = mockStatic(FXGLForKtKt.class)) {
            utilities.when(FXGLForKtKt::getGameScene).thenReturn(mock(GameScene.class));

            doNothing().when(getGameScene()).removeUINode(any());
            doNothing().when(getGameScene()).addUINode(any());

            app.startGame();
            assertTrue(app.gameStarted);

            verify(getGameScene(), times(1)).removeUINode(any());
            verify(getGameScene(), times(1)).addUINode(any(TimerView.class));
            verify(getGameScene(), times(1)).addUINode(any(PunktesystemView.class));
        }
    }



    @Test
    public void testTimeIsUp() {
        try (MockedStatic<FXGLForKtKt> utilities = mockStatic(FXGLForKtKt.class)) {
            utilities.when(FXGLForKtKt::getGameScene).thenReturn(mock(GameScene.class));
            utilities.when(FXGLForKtKt::getGameWorld).thenReturn(gameWorld);

            doNothing().when(getGameScene()).removeUINode(any());
            doNothing().when(getGameScene()).addUINode(any());
            when(getGameWorld().getEntitiesCopy()).thenReturn(List.of(mock(Entity.class)));

            app.timeIsUp(timerView, punktesystemView);

            verify(getGameScene(), times(1)).removeUINode(timerView);
            verify(getGameScene(), times(1)).removeUINode(punktesystemView);
            verify(getGameWorld(), times(1)).getEntitiesCopy();
            verify(getGameScene(), times(1)).addUINode(any());
        }
    }
}
