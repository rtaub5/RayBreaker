package brickbreakertest;
import brickbreaker.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import brickbreaker.view.GameComponent;
import brickbreaker.view.GameFrame;

import java.util.Random;

import static org.mockito.Mockito.*;


public class GameTest {

    //test for ball.moveBall() straight
    @Test
    public void moveBall() {
        Ball ball = new Ball(10, 10, 15);
        ball.moveBall();
      //  assertEquals()

    }

    //test for ball.reflectOffWall()
    @Test
    public void reflectOffWall() {
        Game game = mock();
        Ball ball = mock();
        doReturn(ball).when(game).getBall();

        //when
        game.getBall().reflectOffWall(anyInt(), anyInt());

        //then
        verify(ball).reflectOffWall(anyInt(), anyInt());
    }

    //test for game.setAngleFromPaddle()
    @Test
    public void setAngleFromPaddle() {
        Game game = mock();
        Ball ball = mock();
        doReturn(ball).when(game).getBall();
        //when
        game.setAngleFromPaddle(anyInt());

        //then
        verify(game).setAngleFromPaddle(anyInt());
    }

    @Test
    public void intersects() {
        Game game = new Game(new Random());
        game.setBall(new Ball(10, 10, 15));
        game.addBrick(new Brick(10, 10, 15, 5));
        game.setPaddle(new Paddle(20, 5, 15, 5));

        Intersection actual = game.intersects(10, 10);
        assertEquals(Intersection.BRICK, actual);

        Intersection actual2 = game.intersects(25, 25);
        assertEquals(Intersection.NONE, actual2);

        game.setBall(new Ball(20, 5, 15));
        Intersection actual3 = game.intersects(20, 5);
        assertEquals(Intersection.PADDLE, actual3);

        assertEquals(Intersection.WALL, game.intersects(0, 0));
    }
}
