
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author kulla6503
 */
public class Game extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 1300;
    static final int HEIGHT = 1000;
    
    // Sets the framerate and delay for our game
    // You just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    
    // Player positiion variable 
    int x = 100;
    int y = 500;
    
    // Mouse variables 
    int mouseX = 0;
    int mouseY = 0;
    
    // Boolean variables 
    boolean inAir = false;
    boolean buttonPressed = false;
    boolean direction = false;
    
    // Creating an array for physical blocks for the Obstacle Course  
    ArrayList<Rectangle> blocks = new ArrayList<>();
    
    // Creating an array for a obstacle (spikes) 
    ArrayList<Rectangle> spikes = new ArrayList<>();
    
    // Creating an array for a obstacle (spikes) 
    ArrayList<Rectangle> spikesInverted = new ArrayList<>();
    
    // Creating an array for a obstacle (spikes) 
    ArrayList<Rectangle> spikesSideways = new ArrayList<>();
    
    // Origin of the player 
    Rectangle player = new Rectangle(0, 500, 50, 50);
    
    // Variables for the movement of player 
    int moveX = 0;
    int moveY = 0;
    
    // Gravity 
    int gravity = 1; 
    
    // variable used as direction animation 
    int frames = 0; 
    
    // Keyboard variables 
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    int camx = 0;

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading" + filename);
        }
        return img;
    }
    // Images used to provide textures for the structure of the game
    BufferedImage BlockImgBackground = loadImage("Background.png");
    BufferedImage BlockImgGround = loadImage("Block02.png");
    BufferedImage BlockImgSpike = loadImage("Spikes.png");
    BufferedImage BlockImgSpikeInverted = loadImage("SpikesInverted.png");
    BufferedImage BlockImgSpikeSideways = loadImage("SpikesSideways.png");
    
    // Pictures used on players for the animation 
    BufferedImage[] ManDirection = new BufferedImage[2];

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {


        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);


        // Generates the Background of the Obstacle Course 
        for (int x = 0; x < WIDTH; x = x + 1200) {
            for (int y = 0; y < HEIGHT; y = y + 1050) {
                g.drawImage(BlockImgBackground, x, y, null);
            }
        }

        // GAME DRAWING GOES HERE 


        // Image and size of the character 
        g.drawImage(ManDirection[frames], player.x - camx, player.y, player.width, player.height, null);

        // Color of Blocks being set 
        g.setColor(Color.darkGray);

        // Goes Through each Block
        for (int i = 0; i < blocks.size(); i++) {
            Rectangle block = blocks.get(i);

            // Draws the image of the block using given format
            if (i == 0) {

                // Constantly loops the image of the main block as the ground
                // As the size of course increases, so can the ground texture
                for (int x = block.x; x < block.x + block.width; x = x + BlockImgGround.getWidth()) {

                    // Uses the variables(x,y,width,height) to get the size of the block
                    // Camera uses x value to track character's constant movement 
                    g.drawImage(BlockImgGround, x - camx, block.y, BlockImgGround.getWidth(), HEIGHT - block.y, null);
                }
            } else {

                // Calculates the measurments of each block
                // Camera uses x value to track character's constant movement 
                g.fillRect(block.x - camx, block.y, block.width, block.height);
            }

        }

        // Coding the logic and propeties of the Spikes aswell as choosing the texture 
        for (int obstaclespikeType1 = 0; obstaclespikeType1 < spikes.size(); obstaclespikeType1++) {
            Rectangle spikeUp = spikes.get(obstaclespikeType1);
            
            // Adding the image for the first type of obstacle 
            for (int spikeObstacle1 = spikeUp.x; spikeObstacle1 < spikeUp.x + spikeUp.width; spikeObstacle1 = spikeObstacle1 + BlockImgSpike.getWidth()) {
                g.drawImage(BlockImgSpike, spikeObstacle1 - camx, spikeUp.y, BlockImgSpike.getWidth(), spikeUp.height, null);
            }
        }


        // Coding the logic and propeties of the Spikes aswell as choosing the texture 
        for (int obstaclespikeType2 = 0; obstaclespikeType2 < spikesInverted.size(); obstaclespikeType2++) {
            Rectangle spikeDown = spikesInverted.get(obstaclespikeType2);
            
            // Adding the image for the Second type of obstacle 
            for (int spikeObstacle2 = spikeDown.x; spikeObstacle2 < spikeDown.x + spikeDown.width; spikeObstacle2 = spikeObstacle2 + BlockImgSpikeInverted.getWidth()) {
                g.drawImage(BlockImgSpikeInverted, spikeObstacle2 - camx, spikeDown.y, BlockImgSpikeInverted.getWidth(), spikeDown.height, null);
            }
        }

        // Coding the logic and propeties of the Spikes aswell as choosing the texture 
        for (int obstaclespikeType3 = 0; obstaclespikeType3 < spikesSideways.size(); obstaclespikeType3++) {
            Rectangle spikeSide = spikesSideways.get(obstaclespikeType3);
            // Adding the image for the Third type of obstacle 
            for (int spikeObstacle3 = spikeSide.x; spikeObstacle3 < spikeSide.x + spikeSide.width; spikeObstacle3 = spikeObstacle3 + BlockImgSpikeSideways.getWidth()) {
                g.drawImage(BlockImgSpikeSideways, spikeObstacle3 - camx, spikeSide.y, BlockImgSpikeSideways.getWidth(), spikeSide.height, null);
            }
        }



        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {

        // Intial things to do before the game starts 
        // The Section below has arrays that involve with blocks

        // Blocks Arrays
        // Making the Ground
        blocks.add(new Rectangle(0, 850, 4000, 50));
        blocks.add(new Rectangle(-50, 100, 50, 800));

        //First Section of obstacles (Four pillars) 
        blocks.add(new Rectangle(350, 750, 30, 100));
        blocks.add(new Rectangle(550, 650, 20, 200));
        blocks.add(new Rectangle(800, 475, 10, 375));
        blocks.add(new Rectangle(1025, 275, 10, 575));

        // Left Wall of the tower 
        blocks.add(new Rectangle(1250, 30, 10, 755));

        // Middle Wall of the tower 
        blocks.add(new Rectangle(2000, 250, 10, 600));

        // Right Wall of the tower 
        blocks.add(new Rectangle(2750, 75, 10, 710));

        // First Level in tower 
        blocks.add(new Rectangle(1250, 775, 690, 10));
        blocks.add(new Rectangle(2075, 775, 675, 10));

        // Second Level in tower  
        blocks.add(new Rectangle(1310, 600, 690, 10));
        blocks.add(new Rectangle(2005, 600, 680, 10));

        // Third Level in tower 
        blocks.add(new Rectangle(1250, 425, 690, 10));
        blocks.add(new Rectangle(2075, 425, 675, 10));

        // Fourth Level in tower  
        blocks.add(new Rectangle(1310, 250, 690, 10));
        blocks.add(new Rectangle(2000, 250, 690, 10));

        // Top Ceiling in tower 
        blocks.add(new Rectangle(1250, 75, 1500, 10));

 
        // Spikes Arrays
        // First Set of Spikes surrounding the pillars 
        spikes.add(new Rectangle(240, 830, 100, 20));
        spikes.add(new Rectangle(375, 830, 170, 20));
        spikes.add(new Rectangle(575, 830, 215, 20));
        spikes.add(new Rectangle(810, 830, 180, 20));
        
        // Sideways spikes on the pillars 
        // First Pillar 
        spikesSideways.add(new Rectangle(330, 760, 15, 20));
        // Second Pillar 
        spikesSideways.add(new Rectangle(530, 660, 15, 20));
        spikesSideways.add(new Rectangle(530, 760, 15, 20));
        // Third Pillar 
        spikesSideways.add(new Rectangle(780, 520, 15, 20));
        spikesSideways.add(new Rectangle(780, 600, 15, 20));
        spikesSideways.add(new Rectangle(780, 680, 15, 20));
        // Fourth Pillar 
        spikesSideways.add(new Rectangle(1005, 520, 15, 20));
        spikesSideways.add(new Rectangle(1005, 420, 15, 20));
        spikesSideways.add(new Rectangle(1005, 320, 15, 20));

        // Spikes in the tower 
        // Spikes on First Layer (section: going up the tower)
        spikes.add(new Rectangle(1800, 755, 25, 20));
        spikes.add(new Rectangle(1600, 755, 25, 20));
        spikes.add(new Rectangle(1400, 755, 25, 20));

        // Spikes on Second Layer (section: going up the tower)    
        spikes.add(new Rectangle(1820, 580, 25, 20));
        spikes.add(new Rectangle(1380, 580, 25, 20));

        // Spikes on Third Layer (section: going up the tower)
        spikes.add(new Rectangle(1800, 405, 25, 20));
        spikes.add(new Rectangle(1600, 405, 25, 20));
        spikes.add(new Rectangle(1400, 405, 25, 20));

        // Spikes on fourth Layer 
        spikes.add(new Rectangle(1820, 230, 25, 20));
        spikes.add(new Rectangle(1380, 230, 25, 20));
        spikes.add(new Rectangle(2520, 230, 75, 20));
        spikes.add(new Rectangle(2130, 230, 75, 20));

        // Spikes on Third Layer (section: going down the tower)
        spikes.add(new Rectangle(2590, 405, 25, 20));
        spikes.add(new Rectangle(2130, 405, 25, 20));

        // Spikes on Second Layer (section: going down the tower)
        spikes.add(new Rectangle(2540, 580, 100, 20));
        spikes.add(new Rectangle(2130, 580, 100, 20));

        // Spikes on First Layer (section: going down the tower)
        spikes.add(new Rectangle(2500, 755, 50, 20));
        spikes.add(new Rectangle(2250, 755, 50, 20));


        // Spikes Inverted Arrays 
        // Inverted Spikes on First Layer (section: going up the tower)    
        spikesInverted.add(new Rectangle(1510, 610, 25, 20));
        spikesInverted.add(new Rectangle(1710, 610, 25, 20));

        // Inverted Spikes on Third Layer (section: going up the tower)  
        spikesInverted.add(new Rectangle(1510, 260, 25, 20));
        spikesInverted.add(new Rectangle(1710, 260, 25, 20));

        // Inverted Spikes on Third Layer (section: going down the tower)  
        spikesInverted.add(new Rectangle(2350, 260, 75, 20));

        // Inverted Spikes on Second Layer (section: going down the tower)  
        spikesInverted.add(new Rectangle(2320, 435, 150, 20));

        // Inverted Spikes on First Layer (section: going down the tower)  
        spikesInverted.add(new Rectangle(2130, 610, 50, 20));
        spikesInverted.add(new Rectangle(2380, 610, 25, 20));

        
        
      

        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // Picture of player turning Right 
        ManDirection[0] = loadImage("man1Right.png");
        // Picture of player turning Left
        ManDirection[1] = loadImage("man1Left.png");

        // Main game loop section  
        boolean done = false;
        while (!done) {

            // Determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();


            // GAME LOGIC STARTS HERE 

            // Mouse Variables 
            x = mouseX;
            y = mouseY;
            
            // Gravity pulling the player down 
            moveY = moveY + gravity;

            // The speed of the player moving in each direction (left, right)
            if (left) {
                moveX = -10;
            } else if (right) {
                moveX = 10;
            } else {
                moveX = 0;
            }
                     
            // Direction of which way the player faces
            if (right) {
                frames = 0;  
                
            } else if (left) {
                frames = 1; 
            }

            
            // Tracks the player 
            if (player.x > WIDTH / 2) {
                camx = player.x - WIDTH / 2;
            }

            // Allows player to Jump 
            // Jump being pressed and not in air 
            if (jump && prevJump == false && !inAir) {
                moveY = -20;
                inAir = true;
            }
            
            // Keeps track of jump key changes
            prevJump = jump;

            //move the player
            player.y = player.y + moveY;
            player.x = player.x + moveX;


            // If feet of player become lower than the screen 
            if (player.y + player.height > HEIGHT) {

                // Stops the falling
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }

            
            //Prevents Player from foing through all blocks 
            for (Rectangle block : blocks) {

                // Checks if player is hitting the block
                if (player.intersects(block)) {

                    // Get collision rectangle 
                    Rectangle intersection = player.intersection(block);

                    // Then fixes the x movement 
                    if (intersection.width < intersection.height) {
                        if (player.x < block.x) {
                            player.x = player.x - intersection.width;
                        } else {
                            player.x = player.x + intersection.width;
                        }
                    } else { 
                        // Fixes the y movement 
                        // Prevents player from jumping through blocks
                        if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                        }
                    }
                }
            }
            
             // If player hits spike it will reset back to its origin 
            if (player.x < 1050) {
                for (Rectangle spikeUp : spikes) {
                    // Asks if the player intersects the spike
                    // Sends back to start of Game if hits spike
                    if (player.intersects(spikeUp)) {
                        player.x = 75;
                        player.y = 800; 
                        camx = 5;
                    }
                }
                
                // If player hits spike it will reset back to its origin 
                for (Rectangle spikeSide : spikesSideways) {
                    // Asks if the player intersects the spike
                    // Sends back to start of Game if hits spike
                    if (player.intersects(spikeSide)) {
                        player.x = 75;
                        player.y = 800; 
                        camx = 5;
                    }
                }

            }
                              
            // If player hits spike it will reset to the checkpoint
            if (player.x > 1050) {
                for (Rectangle spikeUp : spikes) {
                    // Asks if the player intersects the spike
                    // Sends back to the checkpoint (before entering tower) 
                    if (player.intersects(spikeUp)) {
                        player.x = 1050;
                        player.y = 800;
                        camx = 5;
                    }
                    
                    // If player hits spike upside down then it will reset to the checkpoint
                    for (Rectangle spikeDown : spikesInverted) {
                        // Asks if the player intersects the spike
                        // Sends back to the checkpoint (before entering tower) 
                        if (player.intersects(spikeDown)) {
                            player.x = 1050;
                            player.y = 800;
                            camx = 5;
                        }
                    }
                }
            }
            
            // If player succesfully beats course they get sent to start and try to beat the course faster
            if (player.x==2800) {
                player.x = 75; 
            }

            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();


            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        Game game = new Game();
        
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        
        // shows the window to the user
        frame.setVisible(true);

        //add the listeners 
        frame.addKeyListener(game); //keyboard

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        // Controls for player to move left and right using arrow keys (when pressed)
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // Controls for player to move left and right using arrow keys (when released)
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        }
    }
}
