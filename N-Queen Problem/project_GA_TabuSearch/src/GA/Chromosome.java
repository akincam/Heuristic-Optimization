package GA;

/**
 * @author Akin Cam
 */
public class Chromosome implements Comparable<Chromosome>{
    private int[] 		gene;
    private int         BOARDSIZE;
    private int 		conflicts;
    private double 		fitness;
    private boolean     isSelected;
    private double 		selectionProbability;


    public Chromosome(int boardSize) {
        BOARDSIZE               = boardSize;
        gene 					= new int[BOARDSIZE];
        fitness 				= 0.0;
        selectionProbability	= 0.0;
        conflicts	 			= 0;
        isSelected              = false;

        initChromosome();
    }

    private void initChromosome() {
        for(int i = 0; i < BOARDSIZE; i++) {
            gene[i] = i;
        }
    }
    public int [] getGenes(){
        return gene;
    }

    private void clearBoard(String[][] board) {
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = "";
            }
        }
    }
    private void setQueens(String[][] board) {
        int queenIndex;
        for(int row = 0; row < BOARDSIZE; row++) {
            queenIndex = gene[row];
            board[row][queenIndex] = "Q";
        }
    }

   public int compareTo(Chromosome c) {
        return this.conflicts - c.getConflicts();
    }

    public void computeConflicts() {
        String[][] board = new String[BOARDSIZE][BOARDSIZE];
        int row , column;
        int tempx, tempy;
        boolean boo;
        int[] coordinateX = new int[] {-1, 1, -1, 1};
        int[] coordinateY = new int[] {-1, 1, 1, -1};
        int conflicts       = 0;
        int size            = coordinateX.length;
        clearBoard(board);
        setQueens(board);

        for(int i = 0; i < BOARDSIZE; i++) {
            row = i;
            column = gene[i];
            for(int j = 0; j <size ; j++) {
                tempx = row;
                tempy = column;
                boo = false;
                while(!boo) {
                    tempx += coordinateX[j];
                    tempy += coordinateY[j];
                    if((tempx < 0 || tempy < 0) || (tempx >= BOARDSIZE || tempy >= BOARDSIZE)) {
                        boo = true;
                    }
                    else {
                        if(board[tempx][tempy].equals("Q"))
                            conflicts +=1;
                    }
                }
            }
        }
        this.conflicts = conflicts;
    }


    public int getGene(int index) {
        return gene[index];
    }


    public void setGene(int index, int position) {
        this.gene[index] = position;
    }


    public double getFitness() {
        return fitness;
    }


    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    public int getConflicts() {
        return conflicts;
    }


    public void setConflicts(int conflicts) {
        this.conflicts = conflicts;
    }


    public boolean isSelected() {
        return isSelected;
    }


    public void setIsSelected(boolean selected) {
        this.isSelected = selected;
    }


    public double getSelectionProbability() {
        return selectionProbability;
    }


    public void setSelectionProbability(double selectionProbability) {
        this.selectionProbability = selectionProbability;
    }
}