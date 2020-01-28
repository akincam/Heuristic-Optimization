package Optimization;
import java.util.Random;

public  class HelperFunctions {
    public static int getRandomNumber(int low, int high, Random random) {
        return (int)Math.round((high - low) * random.nextDouble() + low);
    }

    public static int getDifferentRandomNumber(int high,int except,Random random){
        int number = 0;
        do {
            number = random.nextInt(high);
        } while (number == except);
        return number;
    }

    public static void printSolution(int [] solution,int size) {
        String[][] board = new String[size][size];

        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                board[x][y] = "";
            }
            board[x][solution[x]] = "Q";
        }
        System.out.println("Board:");
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(board[x][y].equals("Q")) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.print("\n");
        }
    }

    public static int factorial(int num){
        int fact = 1;
        for(int i=2;i<=num;i++){
            fact = fact*i;
        }
        return fact;
    }
}
