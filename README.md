# CSC-375-Project-1

## Project 1 for Doug Lea's Fall 2020 Parallel Computing Class

### Project Background:

Write a parallel genetic algorithm program for maximizing seat placement in reduced-capacity classrooms. The goal is to place as many seats as possible given all the constraints. At least six feet apart, at least 6 feet from poidium/whiteboard, not blocking doors, not moving unmovable fixtures (you may add others). You might approximate a seat as a 2-foot square (or something more accurate), sometimes with movable tables. Use actual measurements from at least two classrooms. The initial campus [plans for Shineman seating](http://gee.cs.oswego.edu/dl/csc375/ShinemanSeatingPlan.pdf) can be used.


- The main metric is number of seats placed.
- Each of K parallel tasks initially creates a random (but legal) configuration.
- Each task tries to improve configuration by moving one or more seats, and/or trying to place a new seat in available space.
- Each task occasionally swaps a subarea (of some size) with another.
- The program occasionally (for example twice per second) graphically displays solutions until converged or performs a given number of iterations. Details are up to you.
- Run the program on a computer with at least 32 cores (and K at least 32). (You can develop with smaller K.)
- The main grading criteria are lack of concurrent programming errors, not necessarily optimality of results. 
