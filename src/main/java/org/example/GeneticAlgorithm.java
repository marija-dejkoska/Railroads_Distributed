package org.example;

import java.util.Random;
public class GeneticAlgorithm {
    private int[][] currentBoard;
    private int currentScore;
    private final GameEvaluator evaluator;
    private final Train[] trains;
    private final int trainPairCount;
    private int[][] initialBoard;
    private final Random rnd;
    private int iterations = 10000;
    private int bestScore;
    private int[][] bestBoard;




    public GeneticAlgorithm (GameEvaluator evaluator, Train[] trains, int trainPairCount){
        this.evaluator = evaluator;
        this.trains = trains;
        this.trainPairCount = trainPairCount;
        this.rnd = new Random(Configurations.RANDOM_SEED);
    }

    public void run(int[][] startBoard, int[][] initialBoard, BoardPanel boardPanel){
        this.initialBoard = initialBoard;
        int rows = startBoard.length;
        int columns = startBoard[0].length;
        currentBoard = new int[rows][columns];
        for(int r=0;r<rows;r++){
            for(int c=0;c<columns;c++){
                currentBoard[r][c] = startBoard[r][c];
            }
        }

        currentScore=evaluator.evaluateTiles(this.initialBoard, currentBoard, trains, trainPairCount);
        System.out.println("GA recieved. Size = "+rows+"X"+columns);
        System.out.println("initial score = "+currentScore);
        bestScore = currentScore;
        bestBoard = new int[rows][columns];
        for(int r=0;r<rows;r++){
            for(int c=0;c<columns;c++){
                bestBoard[r][c] = currentBoard[r][c];
            }
        }

        for(int i=0;i<iterations;i++){
            int[][] candidateBoard = new int[rows][columns];
            for(int r=0;r<rows;r++){
                for(int c=0;c<columns;c++){
                    candidateBoard[r][c] = currentBoard[r][c];
                }
            }

            int mutateRow, mutateColumn;
            while(true){
                mutateRow = rnd.nextInt(rows);
                mutateColumn = rnd.nextInt(columns);
                if(candidateBoard[mutateRow][mutateColumn] >= 0){
                    break;
                }
            }

            int oldType = candidateBoard[mutateRow][mutateColumn];
            int newType;
            do{
                newType = rnd.nextInt(11);
            } while (newType == oldType);

            candidateBoard[mutateRow][mutateColumn] = newType;
            int candidateScore = evaluator.evaluateTiles(this.initialBoard, candidateBoard, trains, trainPairCount);
            if (candidateScore >=bestScore){
                bestScore = candidateScore;
                for(int r=0;r<rows;r++){
                    for (int c=0; c<columns;c++){
                        bestBoard[r][c] = candidateBoard[r][c];
                    }
                }

                currentBoard = candidateBoard;
                currentScore= candidateScore;

                if (i%50 == 0){
                    int[][] s = new int[rows][columns];
                    for(int r=0;r<rows;r++){
                        for(int c=0;c<columns;c++){
                            s[r][c] = bestBoard[r][c];
                        }
                    }
                    javax.swing.SwingUtilities.invokeLater(() -> boardPanel.applyBoard(s));
                }
            }

        }
        System.out.println("BEST score after "+iterations+" iterations = "+bestScore);
    }

}
