package Project1;

import java.util.concurrent.ThreadLocalRandom;

final class ChartSnapshot {

    // dimensions based on 444, 2 sq.ft. cells
    static final int ROWS = 10;
    static final int COLS = 17;
    // 2-d array of seats of type Project1.Colors
    private final Colors[][] seats;

    /**
     * Creates a random Seating Chart for startup
     */
    public ChartSnapshot(){
        // temporary seat
        Colors tmp;
        // temporary array of seats
        Colors[][] temp = new Colors [ROWS][COLS];
        // for each row
        for(int i = 0; i < ROWS; i++){
            // for each column
            for(int j = 0; j < COLS; j++){
                tmp = getRandomSeat();
                if(tmp == Colors.White){
                    temp[i][j] = tmp;
                }
                else if(tmp == Colors.Green && checkBounds(temp,i,j) == Colors.Green){
                    temp[i][j] = tmp;
                }

                else{
                    temp[i][j] = checkBounds(temp, i, j);
                }
            }
        }

        seats = temp;
    }

    private ChartSnapshot(Colors [][] c){
        seats = c;
    }

    public static Colors getRandomSeat(){

        int rand = ThreadLocalRandom.current().nextInt(2);

        switch(rand){
            case 0:
                return Colors.Green;
            default:
                return Colors.White;
        }
    }

    /**
     * Returns what color the grid can be
     * @param temp: array of Project1.Colors
     * @param rows: specific row
     * @param cols: specific column
     * @return: returns a Color as a result
     */
    private Colors checkBounds(Colors [][] temp, int rows, int cols){
        Colors result = Colors.Green;

        for(int i =-3; i < 3; i++){
            for(int j = -3; j < 3; j++){
                if((rows+i >=0 && cols+j >=0) && (rows+i < ROWS && cols+j < COLS))
                    if(temp[rows+i][cols+j] == Colors.Green || temp[rows+i][cols+j] == Colors.Red)
                        return Colors.White;
            }
        }
        return result;
    }

    /**
     * Produces a new array of seats with a particular seat placed in
     * @param newSeat: seat to add
     * @param row: specific row
     * @param col: specific column
     * @return: returns a new Project1.ChartSnapshot with one seat changed
     */
    public ChartSnapshot replace(Colors newSeat, int row, int col){

        Colors [][] temp = new Colors[ROWS][COLS];

        for(int i = 0; i < seats.length && i < ROWS; i++){
            for(int j =0; j < seats.length && j < COLS; j++){
                temp[i][j] = seats[i][j];
            }
        }
        if(checkBounds(temp,row,col) == newSeat) {
            temp[row % ROWS][col % COLS] = newSeat;
            return new ChartSnapshot(temp);
        }
        else if (checkBounds(temp,row,col) == Colors.Green){
            temp[row % ROWS][col % COLS] = Colors.Green;
            return new ChartSnapshot(temp);
        }

        else{
            temp[row % ROWS][col % COLS] = Colors.White;
            return new ChartSnapshot(temp);
        }
    }

    public Colors type(int row, int col) {
        return seats[row%ROWS][col%COLS];
    }

    /**
     * Calculates and returns number of chairs in a given solution
     * @param cs: A Project1.ChartSnapshot(2-d array of chairs)
     * @return: number of chairs as an int
     */
    public static int numChairs(ChartSnapshot cs){
        int count = 0;

        for(int i = 0; i < ROWS; i++ ){
            for(int j =0; j < COLS; j++){
                if(cs.seats[i][j] == Colors.Green)
                    count ++;
            }
        }
        return count;
    }



}
