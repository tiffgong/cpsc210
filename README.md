# My Personal Project

## Pew pew game


- **What will the application do?**  
It will be an arcade type  game where the player 
can go around and shoot targets and also die. The main goal is to just get as high
of a score as possible. Collecting "reloads" grants 5 points and killing
targets grant 1 each. Bullets are limited but can picked up under the guise of falling stars.
- **Who will use it?**
  <br> Anyone who wants to play a game.
- **Why is this project of interest to you?**
  <br>This project is of interest to me as I play lots of video games
in my free time. In previous classes in high school, I also had opportunities
for independent project, where I also created games, mainly 2d ones. 
Additionally, I hope to maybe go into game development
in the future.

## User stories

As a user, I want to be able to collect "reloads" and use them.
_(add Reloads to Game)_

As a user, I want to be able to move my player in all 4 directions.

As a user, I want to be able to shoot bullets from my player in all directions.

As a user, I want to be able to die if I run into a target or off-screen.

As a user, I want to be able to press enter to shoot, back key to reload, arrow keys to move.

As a user, I want to be able to save my current score, number of bullets/reloads, targets and position to file by pressing F1. 

As a user, I want to be able to load my current score, number of bullets/reloads, targets and position from file by pressing F1.

##Phase 4 : Task 2

Got reload. \
Got reload. \
Used reload. \
Got reload. \
Used reload. \
Used reload. 

##Phase 4 : Task 3

- Player, Bullet and Reload behaved pretty similarly, they mainly differed
in their hit box and way they moved, thus I could've extracted the duplicated code
from each into a new class that the others would extend. 
- In Game, there is some duplicated methods that could be refactored. The 
duplications were due to checking if Player had collided with reload, and the
other with targets (positions in Game). 


## Citation

Code taken from:
- https://github.students.cs.ubc.ca/CPSC210/SnakeConsole-Lanterna.git
- https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git
- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
- http://www2.hawaii.edu/~takebaya/ics111/simple_gui/simple_gui.html
- https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
- https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
