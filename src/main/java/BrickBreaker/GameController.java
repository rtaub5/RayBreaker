package BrickBreaker;

public class GameController {

    /*
    Game logic:
        - Ball begins somewhere pointed in some direction and keeps moving
        - when ball hits either: a wall (edge of screen), a brick, or the paddle, it changes its angle
        - if the ball hits a brick, the brick vanishes
        - if the ball hits the bottom wall, game is over

    Process (not necessarily in order):
        - void startGame(): perhaps a button in GameFrame that starts the ball moving (starts timer)
        - mouseEvent to drag paddle along the bottom of the screen
        - void moveBall(int x, int y): moves the ball along the screen one pixel (or more) in the ball's current angle
        - timer event calls moveBall() and checks after or before each move whether it has/will bump into something
            - this can use Ellipse2D.intersects(x, y, width, height)
                - If true: checkObject(): switch case if object is wall, brick, or paddle
            - intersects probably won't check for walls though, so perhaps first check that the ball is still in bounds.
              If it's below bounds, game over;
              otherwise, calculateAngle() and call moveBall()
        - double calculateAngle(double angle): decides which angle to reflect based on the angle the ball is as it hits. reset ball.angle.
            - perhaps based on the surface type it hits (ie if it hits a brick, reflect by 180; if a wall, by 90 etc)


     */
}
