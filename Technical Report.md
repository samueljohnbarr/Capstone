# Kepler's Laws Simulator
Samuel Barr & Joshua Shields
May 2nd, 2018

## Abstract
*Kepler's Laws* define how planets in our solar system orbit our sun.  The Appalachian State University Astronomy Lab Department currently uses a simulator developed in the 1990's that aims to teach students about planetary motions.  Due to its age, the simulator is difficult to use, and forces students to take much more time to complete their lab due to how slow it is.  Our project aims to display accurate planetary positions with respect to time in a straight-forward, easy-to-use simulator.  To achieve this, our simulator must implement Kepler's equation, which defines planetary movement depending on planetary position relative to the sun.  Controls must include the ability to step back and forth by one day at a time, including the ability to run the simulation automatically forward and backward in time.  Controls must also include a way to set a date for the simulation, and a way to scale the simulation without having to go through a tree of menus.  To implement these goals, we generalized the back-end to take on any planetary object and instantiated our own solar system's data.  On the front-end, we created an all-in-one user interface that doesn't involve leaving the simulation to change settings, creating an intuitive simulator without complicated menu trees.
<hr>
<h3>Keywords</h3><br>

 - Kepler
 - Planetary Orbit
 - Anomalies
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
         <ul>
<li><a href="#body"> Body</a></li>
<li><a href="#model">Model </a></li>
<li><a href="#controller">Controller </a></li>
<li><a href="#view">View </a></li>
</ul>
         <li><a href="#d&b">Development and Breakdown</a></li>   
         <li><a href="#testing">Testing</a></li>
   </ul>
<li><b><a href="#results">Results</a></b></li>   
   <ul>
       <li><a href="#goalsmet">Goals Met</a></li>
       <li><a href="#problems">Problems</a></li>
       <li><a href="#performance">Performance</a></li>
   </ul>
<li><b><a href="#conclusions">Conclusions</a></b></li>
<ul>
      <li><a href="#summary">Summary</a></li>
      <li><a href="#lessonslearned">Lessons Learned</a></li>
      <li><a href="#utility">Utility of Results</a></li>
      <li><a href="#futuredevelopment">Future Development</a></li>
</ul>
<li><b><a href="#references">References</a></b></li>
</ol>
<h2 id="intro"> Introduction and Project Overview </h2>

<h3 id="users">Users</h3><br>
The users of this simulator will be Appalachian State Astronomy I Lab students learning about planetary motion and the use of Kepler's Laws.  The Lab involves running the simulation and lining planets to up to study conjunction, opposition, and elongation of different planetary objects at various times.  The lab worksheet involves taking note of these various positions to derive astronomical data about the planets in our solar system.  Below is an image taken from the astronomy lab manual showing these astronomical positions. 

![enter image description here](https://lh3.googleusercontent.com/nwJxIkU5wHxPuQ_WPihQbc8fZIwGq6hFqQE9Oy-mgmnklf-kqW_vAsBPFwS4OuThFBrXq2V_c7qD "Planetary Positions")
<hr>

<h3 id="current">The Current Simulator</h3>

The current simulation that the Astronomy Lab Department is using was last updated in the mid '90s, and only runs in an emulator.  Starting the program lands the user into a simple menu interface, as shown below:  
![enter image description here](https://lh3.googleusercontent.com/lFKNpT7X8A9ZWytvgyaNagTCXI-PtXVQzEPwR6m1vN_1OSubDo9SG-pR3tXAHf4gfOBRpmYBxM9y "Kepler Menu")
<br>
The user would pick from one of the choices listed by using the keyboard, as mouse input does not exist with this simulator.  Entering the first option displays the inner four planets and their locations with respect to the date on the left:
![enter image description here](https://lh3.googleusercontent.com/dJ_slR8SZx1AO3orKXzL-KzDgmUuPl9NftjbF-3bZqi00kW9v1lFPAFHHomwcQRBpYgLRlGY4Z3d "Option 1")
<br>
The controls on the left manipulate the forward and backward time direction of the simulation, the speed (ranging from low speed to high speed), and an interval step, which allows the user to step the simulation forward or backward in time by one day.  The 'Arc' and 'Retrograde' controls have no use in the lab setting, and therefore, is not a required feature for the new simulator.  
The third option from the menu works exactly the same, but for the outer four planets.  The fifth option allows the user to enter a date for the simulation to start on.  Options two and four are not used in teaching the labs, and therefore, is not a required feature in the development of the new simulator.
<br>
It's obvious from looking at this simulator that it's in desperate need for an update.  The controls and user-interface are clunky, and are not user-friendly.  Another challenge students have with the simulator is the time it takes to use it.  For example, one of the lab questions involves moving Pluto to certain spot on the simulator.  Pluto's orbit takes 248 Earth years to complete a full rotation [3].  In the simulator, setting the controls to high speed and letting it run still takes a considerable amount of time for Pluto to move to where the user needs it to go.  Another issue involves finding exact planet positions relative to each other.  In the lab, there are various points where the user must find conjuncture, elongation, and opposition for various planets, but due to the simulator's small size and dated display, it's difficult for users to find the exact dates for which these planets line up.
<hr>

<h3 id="objectives">Objectives</h3>
To create a new simulator for the Astronomy Lab Department, the simulator must be able to accomplish all of what the old simulator did;  this includes implementing Kepler's equation, and making the planetary objects accurate to real-world positions. The focus, however, rests on ease-of-use.  The objective is to eliminate having to go through a menu tree to change settings or views.  This means eliminating the menu system entirely, and having a single interface that does what it took four to do in the old simulator.  Another objective is to add a wider range of control compared to the last simulator.  These added controls will allow the user to fine-tune the simulation speed to eliminate the wait for outer planets reach points in their orbit.  We also plan to make date-setting controls simple and easily adjustable.  All this allows for an intuitive user experience that aims for simplicity and ease-of-use.
<hr>

<h3 id="features">Features</h3>
Our simulator comes with all the same features of the old simulator, which includes:
<ul>
     <li> The ability to track accurate planetary positions of eleven planetary objects in our solar system, including all eight planets, Pluto, Halley's comet, and Toutatis </li> 
    <li> A display of planetary positions relative to each other dependent on the date </li>
    <li> The ability to run the simulation automatically, along with the ability to pause and reverse the simulation </li>
    <li> The ability to step the simulation forward and backward in one day intervals </li>
    <li> A control that allows users to set the date of the simulation</li>

</ul>


Along with features contained in the old simulator, our simulator contains other features focused on ease-of-use: 
<ul>
    <li> A control that allows for the manipulation of how many days to interval step.  This allows the user to manipulate simulation speeds without having to deal with speed presets</li>
        <li> The ability to toggle on and off the background and textures to simplify the view </li>
        <li>A function which creates a highlight around the bodies in the simulation in order to help visibility</li>
        <li>A preset library of zoom levels to best focus on bodies making it easier to navigate</li>
        <li> A help file that displays control information , and contains simple instructions to the user if needed </li>
    <li> A control that allows users to have the simulation display lines between planets, and lines that connect planets to the sun. 
    <ul><li> This makes it easier to answer various lab questions that deal with exact planetary positioning  </li></ul>
    <li> A slider control that allows the user to scale the simulation up and down free hand, eliminating the need to manually change views from the inner and outer planets</li>
    <li> An all-in-one user interface that has all user controls on one screen.  This eliminates the need to leave the simulation in order to change settings or views like the old simulator </li> 
    <li>A built in reset function that allows the simulation to be placed back into its initial start up state with out the need to exit out and start it up again </li>
</ul>



  
<h2 id="dd&t">Design, Development and Testing</h2>

<h3 id="design">Design</h3><br>
The software design pattern this simulator implements is a simple Model-View-Controller pattern.  The classes used in this simulator are also named as such, along with a Body class.  The benefits of this are two fold: the design is tried and true, and by keeping with convention, any future changes made by later individuals should be more straightforward to approach. 
<hr>
<h3 id="d&b">Development and Breakdown</h3><br>
To accomplish all the goals we had for this project, we decided to divide and conquer.  Josh started with the front-end design, and Sam started at the back, both working on separate ends to eventually tie together with a Controller.  When we came together, we started with a single planet, Earth,  going around a circular orbit within its orbital period (365.25 days).  Calculating how much to step for one day in terms of an angle was derived by dividing the days in a year by the degrees in a circle.  We then started making circular orbits elliptical using math from [3].  Calculating offset orbits and mapping x and y positioning given an angle was found from [1]. 
Once all the groundwork math was in place, all we had to do was instantiate the other planets and the math takes over the rest.  Below is a break down of what each class does starting from back-end to front.
<br>

<h3 id="body"><b><u>Body</u></b></h3><br>
This class defines a general planetary body that orbits.  The bare minimum for a Body to work in the simulation is data for the orbital period (in Earth years), the eccentricity (how elliptic the orbit is), and the semi-major axis (the elliptic "radius") in terms of 10^10 meters multiplied by the scale.  Other fields within this class are calculated with those initial data points, such as the semi-minor axis, the offsets for which the center of the orbit is positioned in the simulator, and the angle at which the orbit is rotated with respect to the Sun.
Some fields, such as the 'visible' and 'showLine' fields, are used by the View class to manipulate display features on specific bodies. The body class also contains information on the color and/or texture which the body should display in the view.
<br>
<h3 id="model"><b><u>Model</u></b></h3><br>
This class contains an ArrayList that holds all of the Bodies in the simulator, and contains all the equations that makes this simulator run.  Planets are instantiated at end of the class with reliable data [3], and are instantiated by using null Body constructors and individual mutator setting for readability.  The heart of this class is the 'step' method, which steps all planets forward or backward by the 'days' parameter.  To accomplish this, it utilizes Kepler's equation, which is broken up into three helper methods below:

    /**
     * Calculates the mean anomaly
     * @param period of the planet in Earth years
     * @param days to step 
     * @return mean anomaly
     */
    private double getMeanAnomaly(double period, int days) {
		double n = (2 * Math.PI) / (period * Model.EARTH_DAYS);
		return n * days;
    }
    
    /**
     * Finds eccentric anomaly using iterative calculation
     * @param meanAnomaly
     * @param eccentricity
     * @return eccentric anomaly
     */
    private double getEccentricAnomaly(double meanAnomaly, double eccentricity) {
    	//Two eccentric anomaly accumulators
    	double eA0 = meanAnomaly;
    	double eA1 = meanAnomaly + eccentricity * Math.sin(eA0);
    	
    	//Loop until the error is negligible
    	while ((Math.abs(eA0-eA1)/eA1 > 0.00001)) {
    		eA0 = eA1;
    		eA1 = meanAnomaly + eccentricity * Math.sin(eA0);
    	} 	
    	return eA1;
    }
    
    /**
     * Calculates the true anomaly
     * @param eccentricity
     * @param eccentricAnomaly
     * @return true anomaly
     */
    private double getTrueAnomaly(double eccentricity, double eccentricAnomaly) {
    	double x = Math.sqrt(1 - eccentricity) * Math.cos(eccentricAnomaly/2);
    	double y = Math.sqrt(1 + eccentricity) * Math.sin(eccentricAnomaly/2);
    	
    	//atan2 is a polar argument vector
    	return Math.abs(2*Math.atan2(y, x)) % (2*Math.PI);
    }

  
Referring to the figure below, the first step is calculating the <i>Mean Anomaly (M)</i>, which is the average distance a planet moves in one day.  
It then adds that value to the 'lastMeanAnom' accumulator in the respective Body to track overall position.  The next step is calculating the <i>Eccentric Anomaly (E)</i>, which determines the angle from the center if the planet had a completely circular orbit.  The equation for the eccentric anomaly cannot be solved algebraically, and thus is solved by an iterative process.  The last step is the calculating the <i>True Anomaly (theta)</i>, which calculates the angle from the Sun (S) to the Body (or planet, P).

<a title="By Brews ohare [CC BY-SA 3.0 (https://creativecommons.org/licenses/by-sa/3.0) or GFDL (http://www.gnu.org/copyleft/fdl.html)], from Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:Anomalies.PNG"><img width="512" alt="Anomalies" src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Anomalies.PNG/512px-Anomalies.PNG"></a><br>


Once the True Anomaly is found, the 'getAngle' method derives the relation between the true anomaly and the angle from the center of the elliptic orbit.  The math for this was taken from [5]. This is an area of uncertainty, as we were unable to find corroborating sources for those operations.

Once the angle from the center is found, that angle is then used to find the x and y positions of that particular Body on it's orbit using the 'getXPosition' and 'getYPosition' methods [3].
<br>

<h3 id="controller"><b><u>Controller</u></b></h3><br>
This class acts as a traffic controller for both Model and View much the same way any Controller would function, and it also contains the simulations main method.  It error-checks any request made to the Model from the View class, and has a series of accessors from the Model that View uses to update the display.  It also handles the calendar functions needed for calculating the Julian and Gregorian dates. 
<br>

<h3 id="view"><b><u>Window</u></b></h3><br>
This class manages all the display functionality of the simulation using <i>JavaFX</i>.  JavaFx utilizes its own thread separate from the Controller to run, therefore, the first two methods in View are in place to allow the Controller thread to wait for this thread to fully initialize before starting main functions. 

It contains all of the functions and methods that set up and display the window the simulator is displayed in. It initiates and displays the menus, controls, and all of the visual objects, such as the bodies, their associated orbital rings, and the lines which can be toggled on or off that connect bodies together. It also holds methods to update the display coordinates of the bodies, and refresh the display as the simulator runs. It lastly contains a kill method that can be used to close the program safely. 
<hr>

<h3 id="testing">Testing</h3>
To test this simulator, we referenced the old simulator by comparing planetary position by the same date and judging positions by the two simulators. This only got us within a ballpark of where the planets should be.  Fine-tuning positioning involves measuring the time it takes for planets to be in 'inferior conjunction' with Earth, this means judging the time it takes from when that planet was closest to Earth to when that planet is closest to Earth again.  To check this, we used the answer key for the astronomy lab where students have to judge this themselves.  We also used the old simulator to compare speeds of planets as they traveled their orbit.   In-program testing involved moving each planet by their orbital period and judging if they resulted in the same position. This ensured every planet would move one full orbit within their respective period. 
<h2 id="results">Results</h2>

This is a screen shot of the replacement simulator below the old simulator to show just how drastically different they are in terms of visuals and the controls.

![The old simulator](https://lh3.googleusercontent.com/hP6vf6aYd-_ecpK4JSBoZ9DJqRxssQ3VdJqCSef6Qx9MvonkJWynthOy6e3Q2ImU4i-BCUqgOfg)<center> The old simulator. </center>



![enter image description here](https://lh3.googleusercontent.com/MhnG6bIvyxnWsq2Xexz-GrUs_bWGKASAo2CQILyBgGMHJ42roLWjsbVtJKxHFhosYEGc0h2OTWhQ "New Simulator")
<center>The new simulator. </center>

This clearly shows the mouse inputs which were not implemented in the original version. The fields where date information can be entered is also visible, along with the slider which allows the user to freely zoom the display in and out instead of going in and out of menus. It also shows the drop down menus along the top of the window which give the user more options.  As well as a functional simulator, we also created a simple installer  using open source software [2] that grants the user the ability to download to a custom file location.  The installer also allows us to create a custom .exe icon and a splash screen that displays while the simulator is booting up.


<h3 id="goalsmet"> Goals Met </h3>
The final product meets nearly all of the main goals that were initially set out. The new simulator retained nearly all of the functionality of the previous, which includes implementing Kepler's Laws into a simulator to display accurate planetary positions relative to each other.  We also implemented our other goals for this project, including modernizing the interface, and improving the graphics to make the user experience simple and straight forward. The user should never need to restart the simulator while doing the lab project, unlike the previous version, unless there is some external system crash. The simulator fully utilizes mouse support, and window interactivity. There are also additional features which allow the user to simplify the display back to about the same level as the original if so desired. 

<h3 id="problems">Problems</h3>
The team started out with no experience using the JavaFX package, but felt strongly that the most up to data package should be used. This resulted in many fits and starts as the new methods and styles were learned. This was overcome through reading the API, watching instructional videos, and trial and error. While it was difficult to start, the final products look and feel speak to the fact that it was the right decision to make. 
The problem of properly implementing the physics into the simulation came up many times, and in many ways.  We realized that, halfway through the project when most of the groundwork was already completed, we had missed a major portion of the simulator, which was Kepler's equation itself.  We had gotten carried away with other aspects of Kepler's Laws and fine-tuning other aspects that this equation wasn't even realized till halfway through the project.  Solving this equation computationally was not easy, and it required lots of research and many meetings with professors to get it right.  When it was finally implemented, the back-end had to be redesigned to work with this equation as well. While our implementation of this equation works with all planets (which aren't that elliptical), the incredibly elliptical comets are not working as well with it.  This is a goal we haven't fully completed yet, though it is the last goal we have.

<h3 id="performance"> Performance </h3>
The simulation takes around five seconds to boot up, and this is due to all the initialization that takes place and the resources for the textures that must be loaded in.  Once the simulation is up, there is a one to two second lag to performance as things are still getting settled.  Once that is done, however, the simulation runs smooth under any conditions.  The exception to this are the comets, as they do not work well with our implementation of Kepler's equation yet.

<h2 id="conclusions">Conclusions and Future Work</h2>

<h3 id="summary">Summary</h3><br>
The team noticed that the simulator used in the Appalachian State Astronomy I Lab was outdated, and was in desperate need for an update.  We set out to create a new simulator that had all the features of the old simulator with the ease-of-use of modern programs.  To achieve this, we started on separate ends of the project, working our way to the middle, then working forward together from there.  In the end, we made a working simulator that will be a viable replacement for the one the Astronomy department is using currently.

<h3 id="lessonslearned">Lessons Learned</h3>
<br>
When we first started on the project, it would've helped tremendously to research the project first, and come up with a detailed list of everything it needed to be a viable simulator.  If we had done so, we wouldn't have overlooked Kepler's Equation, which is a monumental portion of this project.  Laying the groundwork for the back-end with that equation in mind would have made this project much easier figure out.  Realizing this feature later on caused a lot of code revisions, and ended up taking weeks to get it fully implemented.  

<h3 id="utility">Utility of Results</h3><br>
This simulator will go on to be the simulator used in the Astronomy Lab Department instead of the simulator they're using now.  The lab that currently goes with the old simulator will be updated with the new one, and Astronomy students will no longer have to deal with a program from the 1990s anymore.  We hope that this simulator will make the lab easier for students to learn about planetary positioning, and we hope that it'll take less time to complete that particular lab using this simulator. Hundreds of students every year take Astronomy I, and so hundreds of students will be using our simulator to learn about Kepler's Laws of planetary motion.

<h3 id="futuredevelopment">Future Development</h3><br>
The comets in our simulator are acting odd with their motions, and it's believed to be because of how elliptic they are.  More research must be done to determine if more calculation must take place to calculate correct motion.  For ease-of-use from the professor stand-point, it would be helpful to have a user control to save current settings, like date, view settings, etc, and have the ability to reload those settings with a click of a button.  After lab is over, the lab assistants are usually tasked with going to each computer and resetting values and programs to their initial state.  That feature would make it easier on them, as they would save initial state information and reload later.  This idea was not a part of our original feature list, but since we'll be polishing this project over the summer, it's definitely a feature we might pursue in that time.

<h2 id="references">References</h2>

[1] Mark C. Hendricks. 2012. Rotated Ellipses And Their Intersections With &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Lines. (March 2012). Retrieved May 2, 2018 from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://quickcalcbasic.com/ellipse%20line%20intersection.pdf

[2] Grzegorz Kowal. 2005. Launch4j - Cross-platform Java executable 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; wrapper. (2005). Retrieved May 3, 2018 from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://launch4j.sourceforge.net/

[3] R. Nave. Kepler's Laws. Retrieved May 3, 2018 from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://hyperphysics.phy-astr.gsu.edu/hbase/kepler.html

[4] Anon. JavaFX - The Rich Client Platform. Retrieved May 6, 2018 from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://www.oracle.com/technetwork/java/javase/overview/
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[javafx-overview-2158620.html](http://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html)

 [5] Anon. 2017. Relation between ellipse true anomaly and center angle.  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(December 2017). Retrieved May 3, 2018 from &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://math.stackexchange.com/questions/2539604/relation-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[between-ellipse-true-anomaly-and-center-angle](https://math.stackexchange.com/questions/2539604/relation-between-ellipse-true-anomaly-and-center-angle)







<!--stackedit_data:
eyJoaXN0b3J5IjpbLTI1NDExMDc3MywtOTM3NzE1ODY2LDIwMz
g3NjU1ODUsLTkyNTE3NTQyMiwyNDk1NDUzMzMsLTEwNzc0NTcx
NDQsLTEwMTU5ODYzNjYsMjE1ODIyMjM0LDEwMDQzNjM4MDMsLT
c4NDczNzEyNiwyMDMzOTU2MzY4LDk0MTg0NTc3MSw2MDg2Njg0
NjcsLTExMjY1Mzk1MjUsLTM2NzQ4Nzk0LDE1NjM0MjMzOTAsLT
E3OTMxMTU0NzUsMTE0MTc4NTE2MCwxNjU4OTQ4MDU1LDIwOTYw
MzQ5NDFdfQ==
-->