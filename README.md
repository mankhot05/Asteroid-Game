Description

The Asteroid Game is a simple 2D game built using Java Swing. The player controls a spaceship that can move up, down, left, or right on the screen while dodging incoming asteroids. The goal is to survive as long as possible without colliding with any asteroids.

Features

Spaceship Movement: The player can move the spaceship in four directions using the arrow keys.

Asteroid Generation: Asteroids spawn at random positions at the top of the screen and move downwards.

Collision-Free Movement: The spaceship and asteroids are rendered using basic shapes for simplicity.


How to Play

Launch the game by running the Game class.

Use the arrow keys to move the spaceship:

Left Arrow: Move left

Right Arrow: Move right

Up Arrow: Move up

Down Arrow: Move down

Avoid colliding with the asteroids as they move towards the bottom of the screen.


Controls

Arrow Keys: Control the movement of the spaceship.


Installation

Clone or download this repository.

Ensure you have the Java Development Kit (JDK) installed.

Compile and run the Game class using the following commands:

javac Game.java
java Game


Development Notes

Graphics: The game uses Graphics2D for rendering the spaceship and asteroids.

Asteroid Logic: New asteroids spawn every second at random horizontal positions, and they move downward at a fixed speed.

Threading: The game logic runs on a separate thread to ensure smooth gameplay.


Known Issues

Asteroid Overlap: Occasionally, asteroids may overlap at the same position due to random generation logic.

Edge Wrapping: The spaceship can move partially off-screen. Boundary constraints can be added for a better player experience.


Future Improvements

Add collision detection between the spaceship and asteroids.

Improve asteroid spawning logic to prevent overlaps.

Include a scoring system based on the survival time.

Enhance graphics by adding custom images for the spaceship and asteroids.

Add sound effects and animations.


Credits

Developed by Mayank Hothur


License

This project is open-source and available under the MIT License.
