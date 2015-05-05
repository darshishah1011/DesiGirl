# DesiGirl
Side Scroller Game Development

Name: DesiGirl
This Girl is nothing less than our Mario. Let us explore her world!

Game:
•	Levels: Has two levels
-	Level 1
-	Level 2 : also the boss
•	Clear Start and End: Each Level starts at a fixed position unless user selects to resume previous game. Each level ends in the last cell of Tile marked with a Brown rod.

User Interface:
•	The Game starts with its Name Screen and I have tried to use Flash animation to display DesiGirl
•	Next Screen displays buttons to Resume, Play and Exit the Game. I have used Animation on Font and Button rendering and display
Play: By Clicking on Play Button, Level 1 begins. There is no way to go to Level 2 without clearing Level 1. 
Pause: User can save the state by clicking the Pause Button on either of Level screens and the data gets saved in a JSON file. 
Resume: While resuming the game, state of previous game is read from JSON file. 
Exit: Application ends as user clicks on Exit button.
•	Either of two maps created using TiledMap, gets rendered on the screen based on button selected by user.
•	Each Level has a status bar which shows Coins collected by user as Score and Pause button to pause game any anytime

Game Engine
•	Left, Right, Up(Jump) buttons on keyboard are used to navigate the DesiGirl through the game
•	Coins/Crystals in the game act as PickUps and contribute towards the score of player
•	Enemies:
-	Level1: has no enemies, but user can die by falling in sea
-	Level2: has enemies in forms: 
	Trees: static enemies and user dies on colliding with it
	Enemies: Rendered at different point in the game based on the ghost sensor. User die if he collides with the enemy. He can survive by jumping over it (Tried AI here)
	Boss: Is present towards end of Level1 and user can reach to gate by defeating the boss which is by means of selecting appropriate path to the door. (Could not implement this. Would make the boss as dynamic body and handle Collison of player fixture and Boss fixtures)
On clearing both level, user gets to see final score and congratulations message on screen
Used Box2D to create physics logic for the game.

Set Up:
Would need following Jar files: 
libs/tween-engine-api.jar
libs/tween-engine-api-sources.jar

Run Code:
Run the DesktopLauncher classin : IntellijProg\desktop\src\com\mygdx\game\desktop

Game Development: 
I used LibGDX development framework to build this game: https://github.com/libgdx/libgdx/blob/master/README.md
IDE: IntelliJ IDEA
Tiled Map Editor: To create Maps for Level 1 and Level 2

Reference: 
Book: LibGDX Game Development Essentials by Juwal Bose
YouTube Channels: ForeignGuyMike, dermetfan
Sprite: http://untamed.wild-refuge.net/rmxpresources.php?characters
TileSet: http://opengameart.org/content/grassland-tileset

Thank You:
Special Thank You to Professor Joseph Fowler for giving me the opportunity to build this game. I hope this makes you win the bet with your Friend =]

