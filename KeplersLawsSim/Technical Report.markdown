# Kepler's Laws Simulator
Samuel Barr & Joshua Shields
May 2nd, 2018

## Abstract
*Kepler's Laws* define how planets in our solar system orbit our sun.  The Appalachian State University Astronomy Lab Department currently uses a simulator developed in the 1990's that aims to teach students about planetary motions.  Due to its age, the simulator is difficult to use, and forces students to take much more time to complete their lab due to how slow it is.  Our project aims to display accurate planetary positions with respect to time in a straight-forward, easy-to-use simulator.  To achieve this, our simulator must implement Kepler's equation, which defines planetary movement depending on planetary position relative to the sun.  Controls must include the ability to step back and forth by one day at a time, including the ability to run the simulation automatically forward and backward in time.  Controls must also include a way to set a date for the simulation, and a way to scale the simulation without having to go through a tree of menus.  To implement these goals, we generalized the back-end to take on any planetary object and instantiated our own solar system's planets with real world data.  On the front-end, we created an all-in-one user interface that doesn't involve leaving the simulation to change settings, creating an intuitive simulator without complicated menu trees.


<h2 id="contents">Table of Contents</h2>
<ol>
    <li><b><a href="#intro">Introduction and Project Overview</a></b></li>
     <ul>
         <li><a href="#users">Users</a></li>
         <li><a href="#current">The Current Simulator</a></li>
         <li><a href="#objectives">Objectives</a></li>
         <li><a href="#features">Features</a></li>
   </ul>
     <li><b><a href="#dd&t">Design, Development and Testing</a></b></li>
     <ul>
         <li><a href="#design">Design</a></li>
         <li><a href="#d&b">Development and Breakdown</a></li>   
         <li><a href="#testing">Testing</a></li>
   </ul>
<li><b><a href="#results">Results</a></b></li>   
<li><b><a href="#conclusions">Conclusions</a></b></li>
<li><b><a href="#references">References</a></b></li>
</ol>
<h2 id="intro"> Introduction and Project Overview </h2>

-   Introduces the **problem**, **objectives**, and the **users** (or customers) of project results, context of problem and issues that affect solution choices
-   Describes **relevant background information**. May include any alternative solutions (are there existing solutions or similar systems? give references) in terms of strengths and weaknesses.
-   Describe **value or benefits of your solution** and results
-   **Problem scope**--problems addressed and not addressed
-   **Figures** and system diagrams where possible to illustrate problems and solutions.
-   Summary of **features**

<h3 id="users">Users</h3>
The users of this simulator will be Appalachian State Astronomy I Lab students learning about planetary motion and the use of Kepler's Laws.  The Lab involves running the simulation and lining planets to up to study conjunction, opposition, and elongation of different planetary objects at various times.  Below is an image taken from the astronomy lab manual showing these astronomical positions:

![enter image description here](https://lh3.googleusercontent.com/nwJxIkU5wHxPuQ_WPihQbc8fZIwGq6hFqQE9Oy-mgmnklf-kqW_vAsBPFwS4OuThFBrXq2V_c7qD "Planetary Positions")
<hr>

<h3 id="current">The Current Simulator</h3>

The current simulation that the Astronomy Lab Department is using was last updated in the mid '90s, and only runs in an emulator.  Starting the program lands the user into a simple menu interface, as shown below:  
![enter image description here](https://lh3.googleusercontent.com/lFKNpT7X8A9ZWytvgyaNagTCXI-PtXVQzEPwR6m1vN_1OSubDo9SG-pR3tXAHf4gfOBRpmYBxM9y "Kepler Menu")
The user would pick from one of the choices listed by using the keyboard, as mouse input does not exist with this simulator.  Entering the first option displays the inner four planets and their locations with respect to the date on the left:
![enter image description here](https://lh3.googleusercontent.com/dJ_slR8SZx1AO3orKXzL-KzDgmUuPl9NftjbF-3bZqi00kW9v1lFPAFHHomwcQRBpYgLRlGY4Z3d "Option 1")
The controls on the left manipulate the forward and backward time direction of the simulation, the speed (ranging from low speed to high speed), and an interval step, which allows the user to step the simulation forward or backward in time by one day.  The 'Arc' and 'Retrograde' controls have no use in the lab setting, and therefore, is not a required feature for the new simulator.  
The third option from the menu works exactly the same, but for the outer four planets.  The fifth option allows the user to enter a date for the simulation to start on.  Options two and four are not used in teaching the labs, and therefore, is not a required feature in the development of the new simulator.
<br>
It's obvious from looking at this simulator that it's in desperate need for an update.  The controls and user-interface are clunky, and are not user-friendly.  Another challenge that students have with the simulator is the time it takes to use it.  For example, one of the lab questions involves moving Pluto to certain spot on the simulator.  Pluto's orbit takes 248 Earth years to complete a full rotation.  In the simulator, setting the controls to high speed and letting it run still takes a considerable amount of time for Pluto to move to where the user needs it to go.  Another issue involves finding exact planet positions relative to each other.  In the lab, there are various points where the user must find conjuncture, elongation, and opposition for various planets, but due to the simulator's small size and dated display, it's difficult for users to find the exact dates for which these planets line up.
<hr>

<h3 id="objectives">Objectives</h3>
To create a new simulator for the Astronomy Lab Department, the simulator must be able to accomplish all of what the old simulator did;  this includes implementing Kepler's equation, and making the planetary objects accurate to real-world positions. The focus, however, rests on ease-of-use.  The objective is to eliminate having to go through a menu tree to change settings or views.  This means eliminating the menu system entirely, and having a single interface that does what it took four to do in the old simulator.  Another objective is to add controls that allow for users to have greater control over the simulation including the simulation speed to eliminate the wait for outer planets reach points in their orbit, the ability to jump directly to dates, and control over how much data is displayed at a given time.  All this allows for an intuitive user experience that aims for simplicity and ease-of-use.
<hr>

<h3 id="features">Features</h3>
Our simulator comes with all the same features of the old simulator, which includes:
<ul>
     <li> The ability to track accurate planetary positions of eleven planetary objects in our solar system, including all eight planets, Pluto, Halley's comet, and Toutatis. </li> 
    <li> A display of planetary positions relative to each other relative to a certain date along with the orbital rings they travel on. </li>
    <li> The ability to run the simulation automatically, along with the ability to pause and reverse the simulation. </li>
    <li> The ability to step the simulation forward and backward in one day intervals. </li>
    <li> A control that allows users to set the date of the simulation. </li>

</ul>


Along with features contained in the old simulator, our simulator contains other features focused on ease-of-use: 
<ul>
    <li> A control that allows for the manipulation of how many days to interval step.  This allows the user to manipulate simulation speeds without having to deal with presets. </li>
        <li> The ability to toggle on and off the background and textures to simplify the view. </li>
        <li>A function which creates a highlight around the bodies in the simulation in order to help visibility.</li>
        <li>A preset library of zoom levels to best focus on bodies making it easier to navigate.</li>
        <li> A help file that displays control information , and contains simple instructions to the user if needed. </li>
    <li> A control that allows users to have the simulation display lines between planets, and lines that connect planets to the sun.  This makes it easier to answer various lab questions that deal with exact planetary positioning.   </li>
    <li> A slider control that allows the user to scale the simulation up and down free hand, eliminating the need to manually change views from the inner and outer planets. </li>
    <li> An all-in-one user interface that has all user controls on one screen.  This eliminates the need to leave the simulation in order to change settings or views like the old simulator. </li> 
    <li>A built in reset function that allows the simulation to be placed back into its initial start up state with out the need to exit out and start it up again. </li>
</ul>



  
<h2 id="dd&t">Design, Development and Testing</h2>
*-   **Design** - describe system **components (e.g. software modules/components, libraries, etc.)**, **interfaces**, and **operations**. **Use figures** to illustrate your description, for example: photos, block diagrams, class diagrams, state diagrams, flow charts, tables, etc.
-   **Development** - describe how system was developed (for example, order of subsystem development and how risks were addressed early)
-   **Test** - describe your test approach (what was tested, how tested, what was not tested). You should organize this by feature (as you did for the System Features assignment).*

<h3 id="design">Design</h3>
The software design pattern this simulator implements is a simple Model-View-Controller pattern.  The classes used in this simulator are also named as such, along with a Body class.  The benefits of this are two fold, the design is tried and true, and by keeping with convention, any future changes made by later individuals should be more straightforward to impliment. 
<hr>
<h3 id="d&b">Development and Breakdown</h3>
To accomplish all the goals we had for this project, we decided to divide and conquer.  Josh started with the front-end design, and Sam started at the back, both working on separate ends to eventually tie together with a Controller.  Below is a break down of what each class does starting from back-end to front.
<br>
<b><u>Body</u></b><br>
This class defines a general planetary body that orbits.  The bare minimum for a Body to work in the simulation is data for the orbital period (in Earth years), the eccentricity (how elliptic the orbit is), and the semi-major axis (the elliptic "radius") in terms of 10^10 meters multiplied by the scale.  Other fields within this class are calculated with those initial data points, such as the semi-minor axis, the offsets for which the center of the orbit is positioned in the simulator, and the angle at which the orbit is rotated with respect to the Sun.
Some fields, such as the 'visible' and 'showLine' fields, are used by the View class to manipulate display features on specific bodies. The body class also contains information on the color and/or texture which the body should display in the view.
<br>
<b><u>Model</u></b><br>
This class contains an ArrayList that holds all of the Bodies in the simulator, and contains all the equations that makes this simulator run.  Planets are instantiated at end of the class, and are instantiated by using null Body constructors and individual mutator setting for readability.  The heart of this class is the 'step' method, which steps all planets forward or backward by the 'days' parameter.  To accomplish this, it utilizes Kepler's equation, which is broken up into three helper methods.  
Referring to the figure below, the first step is calculating the *Mean Anomaly (M)*, which is the average distance a planet moves in one day.  It then adds that value to the 'lastMeanAnom' accumulator in the respective Body to track overall position.  The next step is calculating the *Eccentric Anomaly (E)*, which determines the angle from the center if the planet had a completely circular orbit.  The equation for the eccentric anomaly cannot be solved algebraically, and thus is solved by an iterative process.  The last step is the calculating the *True Anomaly (theta)*, which calculates the angle from the Sun (S) to the Body (or planet, P).

<a title="By Brews ohare [CC BY-SA 3.0 (https://creativecommons.org/licenses/by-sa/3.0) or GFDL (http://www.gnu.org/copyleft/fdl.html)], from Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:Anomalies.PNG"><img width="512" alt="Anomalies" src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Anomalies.PNG/512px-Anomalies.PNG"></a>

Once the True Anomaly is found, the 'getAngle' method derives the relation between the true anomaly and the angle from the center of the elliptic orbit.  The math here is complicated and mystical, thus none of us knows exactly how it works, but here is a link to the only forum on the internet with the math for this problem: <a> https://math.stackexchange.com/questions/2539604/relation-between-ellipse-true-anomaly-and-center-angle </a>

Once the angle from the center is found, that angle is then used to find the x and y positions of that particular Body on it's orbit using the 'getXPosition' and 'getYPosition' methods.
<br>

<b><u>Controller</u></b><br>
This class acts as a traffic controller for both Model and View much the same way any Controller would function, and it also contains the simulations main method.  It error-checks any request made to the Model from the View class, and has a series of accessors from the Model that View uses to update the display.  It also handles the calendar functions needed for calculating the Julian and Gregorian dates. 
<br>

<b><u>View</u></b><br>
This class manages all the display functionality of the simulation using *JavaFX*.  JavaFx utilizes its own thread separate from the Controller to run, therefore, the first two methods in View are in place to allow the Controller thread to wait for this thread to fully initialize before starting main functions. 
It contains all of the functions and methods that set up and display the window the simulator is displayed in. It initiates and displays the menus, and controls, as well as all of the actual objects, such as the bodies, their associated orbital rings, and the lines which can be toggled on or off that connect bodies together. It also holds methods to update the display coordinates of the bodies, and refresh the display as the simulator runs. It lastly contains a kill method that can be used to close the program safely. 
<hr>

<h3 id="testing">Testing</h3>
To test this simulator, we referenced the old simulator by comparing planetary position by the same date and judging positions by the two simulators.  We also used the old one to compare speeds of planets as they traveled their orbit.  

<h2 id="results">Results</h2>

- Actual results of project. Describe how well you met your objectives, feature by feature. A table of results will help to summarize this.
-   This section describes final system in terms of features completed and actual performance of the system under test.
-   Include discussion of problems encountered, accuracy of estimates
-   Use figures and diagrams whenever possible
...
![The new simulator](https://lh3.googleusercontent.com/IT99bmPbUjyAC8vHyTc9es3puCk--7G0HvTyG8_FfpdN7IMm5eMOnU2bi51LB-3HBEkKqEivOdQ")The new simulator.

![The old simulator](https://lh3.googleusercontent.com/hP6vf6aYd-_ecpK4JSBoZ9DJqRxssQ3VdJqCSef6Qx9MvonkJWynthOy6e3Q2ImU4i-BCUqgOfg)
The old simulator.

The final product meet all of the main goals that were initially set out. The new simulator retained all of the functionality of the previous, while modernizing the interface, and improving the graphics, although there were several snags along the way.
The team started out with no experience using the JavaFX package, but felt strongly that the most up to data package should be used. This resulted in many fits and starts as the new methods and styles were learned. This was overcome through reading the API, watching instructional videos, and trial and error. While it was difficult to start, the final products look and feel speak to the fact that it was the right decision to make. 
The problem of properly implementing the physics into the simulation came up many times, and in many ways. 

<h2 id="conclusions">Conclusions and Future Work</h2>

-   Briefly summarize problem, approach and results
-   Describe your conclusions and "lessons learned" regarding the results
-   Describe utility of results
-   Suggest areas for further study and/or development*
...
<h2 id="references">References</h2>
-   Provide references in standard ACM format (numbers in text correspond to numbered references here).  
-   List all references to code used as part of your system (libraries, etc.)*
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTU4MTg1OTkxLC0xMzU2MDQ3NzEzLDU5Mj
k2Mjg3OCwtNTI5NzM2OTM3LDE2MzIzMzczOTAsLTc0NjM1MDM5
MCwtMTM0MTY1NjUyMSwtMTI2MjcyNzAzMSwyMTM4Njk0NDcyLD
MxMDc5ODc4Nyw3NDY1NDE3MDQsLTczMjc1NTQ2OSwtOTg4NzAw
NDg3LC0xMDM2NjIzMywtMTUyNTU4NzUsLTEwODAxMTcyMzYsND
U0NTY4Njc3LC0xNzk2NDAyODk4LC03ODE1NDM2MCwxNTA2MzE2
MjQyXX0=
-->