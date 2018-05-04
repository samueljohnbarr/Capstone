************************************
* Kepler's Laws Simulator Controls *
************************************

***Bottom Control Bar***
(From left to right)
Scale Slider: 
-Moving the slider left will zoom out of the simulation
-Moving the slider right will zoom in the simulation

Step Buttons:
-Shown by arrows with lines on ends
-Steps simulation by 'Days to step' input (below)
-Right arrow steps forward; left arrow steps backward
-Default step is 1

Auto-run Buttons:
-Shown by arrows without lines on ends
-Auto-steps the simulation by 'Days to step' input (below)
-Right arrow runs forward; left arrow steps backward
-Step occors every 250ms

Pause Button:
-Stops the auto-run

Days to Step Input:
-Manipulates step and auto-run controls
-Inputting a number and pressing 'enter' on the keyboard
 while the input box is active sets the days-to-step
-This field can be changed even if auto-run is active
-Entering an invalid input (i.e. a letter) will default
 days-to-step to 1

Set Date:
-Sets the date to the simulation in mm/dd/yyyy format
-Pressing 'enter' on the keyboard in any one of the
 three input boxes will set the desired date
-Each input box is indepedent, meaning if the user wanted to
 set just the year, typing in the desired year in the year
 input box and keeping the others blank will keep the 
 current month and day, and only change the year
-Input boxes are forgiving of possible mistakes;
 for example, if the user tries to enter '02/29/2018,' 
 (February only has 28 days), the simulator will set to 
 '03/01/2018' instead.
-Entering an invalid input in any of the input fields 
 (i.e. a letter) will default to the current
 month/day/year of that respective invalid field
 (the other fields, if valid, will set the other
  parts of the date)

***Top Toolbar***
File:
>Reset-Sets the simulator to the start date (12/21/2000)
>Exit-Exits the simulator (Can also be done by clicking 'X' on the page)

View:
-Background> Toggles background from image to plain black for readability
-Planet Textures> Toggles planet textures 
-Planet Highlights> Toggles a highlighted ring around planets
-Show Planet Connections> Toggles lines between planets - Lines are only 
  displayed if a line is toggled from the 'Line' control on the toolbar (below)

Focus:
-Clicking any planet from the list will auto-zoom to focus on that
 particular planet

Line:
-Clicking any planet from the list will draw a line from checked planet
 to the sun
-Can toggle multiple planetary objects at a time
-If 'Show Planet Connections' is toggled, toggled planets on this control
 will also have lines drawn between them

Help:
-About> Shows a window about the simulator
-Help> Displays information about the toolbar, exactly like this is doing :)


** Please report any issues to github.com/samueljohnbarr/Capstone or email at
   samueljohnbarr@gmail.com. enjoy!
