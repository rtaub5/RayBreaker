package brickbreaker.model;

public enum Intersection {
    NONE, // No intersection
    PADDLE, // Intersects with the paddle
    WALL, // Intersects with a wall
    FLOOR, // Intersects with bottom wall
    BRICK // Intersects with a brick
}
