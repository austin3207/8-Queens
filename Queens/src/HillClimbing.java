
/**Author: Austin Rogers
 * Class: ITCS-3153
 * Project: Programming Project #1: Eight Queens
 * 9/25/2020
**/
public class HillClimbing {

    public static void main(String[] args) {
        int[][] arr = new int[8][8];
        int heuristicValue;
        int restart = 0;
        int restCount = 0;
        int maxAvoid = 0;
        int[] coordinates = new int[2];

        System.out.println("Initial Board");
        randQueen(arr);
        randRestart(arr, coordinates);
        printQueen(arr);
        heuristicValue = checkVal(arr);


        while(heuristicValue > 0) {
            System.out.println("Heuristic Value: " + heuristicValue);
            if(restart == 1) {
                randRestart(arr, coordinates);
                restCount++;
                maxAvoid++;
                if(maxAvoid > 10){
                    maxAvoid = 0;
                    moveQueen(arr);
                }
            }
            restart = genNextState(arr, heuristicValue, coordinates[0], coordinates[1]);
            heuristicValue = checkVal(arr);
            printQueen(arr);
        }
        System.out.println("FIN");
        System.out.println("Number of Restarts: " + restCount);
    }
    public static void randQueen(int[][] arrQueen){
    //Generate random starting state
        int x;
        for(int i = 0; i < 8; i++){
            x = (int)(Math.random() * 8);
            arrQueen[x][i] = 1;
        }
    }
    public static void printQueen(int[][]arrQueen){
    //output current state of the board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                System.out.print(arrQueen[i][j] + " ");
            }
            System.out.println();
        }

    }
    public static int checkVal(int[][] arrQueen){ //Finds the total number of conflicts on the board
        //Generate Heuristic values
        int hVal = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++) {
                if( arrQueen[i][j] == 1){
                    hVal += checkConflict(arrQueen,i,j);
                }
            }
        }
        hVal = hVal / 2;        // Removes double counts from the value
        return hVal;
    }
    public static int checkConflict(int[][]arrQueen, int posX, int posY){ //counts all conflicts for a specific queen.
        int numConflict = 0;
        int x = posX;
        int y = posY;
        for(int i = 0; i < 8; i++){
            if(arrQueen[posX][i] == 1 && i != posY){             //checks for vertical conflicts
                numConflict++;
            }
            if(arrQueen[i][posY] == 1 && i != posX){             //checks for horizontal conflicts
                numConflict++;
            }
        }
        while(x < 8 && y < 8){                                  //check bottom right diagonal
            if(arrQueen[x][y] == 1 && (x != posX && y != posY)){
                numConflict++;
            }
            x++;
            y++;
        }
        x = posX;
        y = posY;
        while(x >= 0 && y < 8){                                  //check top right diagonal
            if(arrQueen[x][y] == 1 && (x != posX && y != posY)){
                numConflict++;
            }
            x--;
            y++;
        }
        x = posX;
        y = posY;
        while(x < 8 && y >= 0){                                  //check bottom left diagonal
            if(arrQueen[x][y] == 1 && (x != posX && y != posY)){
                numConflict++;
            }
            x++;
            y--;
        }
        x = posX;
        y = posY;
        while(x >= 0 && y >= 0){                                  //check top left diagonal
            if(arrQueen[x][y] == 1 && (x != posX && y != posY)){
                numConflict++;
            }
            x--;
            y--;
        }

        return numConflict;
    }
    public static int genNextState(int[][] arrQueen, int hVal, int x, int y){ //checks all other possible states and
        int[][] arrNew = arrQueen.clone();                                     //then moves onto the next
        int[][] hValue = new int[8][8];//array of heuristic values of all possible moves for a queen
        int[] nextBoard = new int[2]; //array to store coordinates the queen will move to
        int count = 0; //tracks number of states with lower h values
        int restart;
        boolean next = true; //tracks if a new state has been chosen or not
        int temp  = arrNew[x][y];//tracks initial state
        arrNew[x][y] = -1; //identifier for current queen

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(arrNew[i][j] == 1){              //checks if position is taken by another queen already
                    hValue[i][j] = -1;
                }
                else if(arrNew[i][j] == 0){         //Gen heuristic value for board and store it in the array
                    arrNew[i][j] = 1;
                    hValue[i][j] = checkVal(arrNew);
                    arrNew[i][j] = 0;
                    if(hValue[i][j] < hVal && next){//chooses next state
                        nextBoard[0] = i;
                        nextBoard[1] = j;
                        next = false;
                    }
                }
                else if(arrNew[i][j] == -1){    //-2 to indicate this is where the currently evaluated piece is at
                    hValue[i][j] = -2;
                }

            }
        }
        for(int i = 0; i < 8; i++) {                //counts states with lower heuristic values
            for (int j = 0; j < 8; j++) {
                if(hValue[i][j] >= 0 && hValue[i][j] < hVal){
                    count++;
                }
            }
        }
        System.out.println("Neighbors found with lower h: " + count);
        if(count > 0) {
            System.out.println("Setting new current state.");
            temp = 0;
            arrQueen[nextBoard[0]][nextBoard[1]] = 1;
            restart = 0;

        }
        else{
            System.out.println("RESTART");

            restart = 1;
        }
        arrNew[x][y] = temp; //Queen is reset back to 1 if no other solution is found
                            // Changed to 0 if the queen has moved to a new state
        return restart;
    }
    public static void randRestart(int[][] arr, int[] coordinates){ //chooses a random queen to check for potential states
        int x = -1;
        int[] cd = new int[2];  //stores x and y coordinates to be compared
        while(x == -1){
            cd[0] = (int) (Math.random() * 8);
            cd[1] = (int) (Math.random() * 8);
            if(arr[cd[0]][cd[1]] == 1){
                break;
            }
        }
        coordinates[0] = cd[0];
        coordinates[1] = cd[1];
        return;
    }
    public static void moveQueen(int[][] arrQueen){ //Prevent dead end local maxima
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (arrQueen[i][j] == 1 && arrQueen[i + 1][j] != 1) {
                    arrQueen[i][j] = 0;
                    arrQueen[i + 1][j] = 1;
                    return;
                }
            }
        }
    }
}
