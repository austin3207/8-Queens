# 8-Queens

This project is my first attempt at the Eight Queens
problem. The goal is to reach a board-state where no
Queen is in conflict with another according to the
movement rules of chess. This attempt uses the 
Hill-climbing algorithm to reach a satisfactory
board state.

The initial state is randomly generated. Each Queen
is represented by a 1 while empty spaces are filled
with Zeroes.

A Heuristic value is generated for the board state
based on the number of conflicts. Possible moves are
compared by their new Heuristic value. If a lower 
value is found, the board advances to that new 
state and the process is repeated until a value of 
0 is reached.

To avoid local, dead-end maxima I included a method
to manually move a queen to a new position. This 
prevents the board-state from becoming unable to 
progress.
