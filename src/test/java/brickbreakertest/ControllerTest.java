package brickbreakertest;

import brickbreaker.GameController;
import brickbreaker.model.Ball;
import brickbreaker.model.Direction;
import brickbreaker.model.Game;
import brickbreaker.model.Paddle;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

public class ControllerTest {

    @Test

    public void movePaddleRight()
    {
        // given
    }

    public void movePaddle() {
        Game game = mock();
        Paddle paddle = mock();
        GameController gameController = mock();

        doReturn(game).when(gameController).getModel();
        doReturn(paddle).when(game).getPaddle();

        // when
        gameController.movePaddle(KeyEvent.VK_RIGHT);

        // then
        verify(paddle).setDirection(Direction.RIGHT);
    }

   /* public void movePaddleLeft() {
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


        KeyEvent rightKeyEvent = new KeyEvent(new JButton(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'R');
      //  gameController.movePaddle(rightKeyEvent);

        verify(paddle).setDirection(true);

        KeyEvent leftKeyEvent = new KeyEvent(new JButton(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'L');
    //    gameController.movePaddle(leftKeyEvent);

        verify(paddle).setDirection(false);

    } */

//    @Test
//    public void moveBall()
//    {
//        Game game = mock();
//        Ball ball = mock();
//        GameController gameController = mock();
//
//        doReturn(game).when(gameController).getModel();
//        doReturn(ball).when(game).getBall();
//
//        // when
//        gameController.moveBall(anyInt(), anyInt());
//
//        // then
//        verify(game).nextMove();
//    }


}
