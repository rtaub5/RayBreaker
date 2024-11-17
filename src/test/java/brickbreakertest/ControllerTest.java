package brickbreakertest;

import brickbreaker.controller.GameController;
import brickbreaker.model.Game;
import brickbreaker.model.Paddle;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

public class ControllerTest {

    @Test
    public void movePaddleRight() {
        // given
        GameComponent view = mock();
        GameFrame model = mock();
        Game game = mock();
        Paddle paddle = mock();

        doReturn(game).when(view).getGame();
        doReturn(paddle).when(game).getPaddle();

        GameController gameController = new GameController(model, view);

        // when
        gameController.movePaddle(KeyEvent.VK_RIGHT);

        // then
        verify(paddle).setDirection(true);
    }

    @Test
    public void movePaddleLeft() {
        // given
        GameComponent view = mock();
        GameFrame model = mock();
        Game game = mock();
        Paddle paddle = mock();

        doReturn(game).when(view).getGame();
        doReturn(paddle).when(game).getPaddle();

        GameController gameController = new GameController(model, view);

        // when
        gameController.movePaddle(KeyEvent.VK_LEFT);

        // then
        verify(paddle).setDirection(false);
    }

}
