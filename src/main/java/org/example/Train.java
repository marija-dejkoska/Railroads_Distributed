package org.example;

public class Train {
    public final int startingRow;
    public final int startingColumn;
    public final int destinationRow;
    public final int destinationColumn;

    public Train(int startingRow, int startingColumn, int destinationRow, int destinationColumn){
        this.startingRow = startingRow;
        this.startingColumn = startingColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
    }

    public int getStartingRow(){return startingRow;}
    public int getStartingColumn(){return startingColumn;}
    public int getDestinationRow(){return destinationRow;}
    public int getDestinationColumn(){return destinationColumn;}
}