import java.util.Arrays;

public class BranchAndBound {

    //keeps circle size;
    private int      circleSize;
    //keeps best solution sequence
    private int      solution[];
    //sets the circle selected
    private boolean  selected[];
    //keeps the minimum width
    private double   minWidth;
    //keeps distance of circles
    private double   matrix[][];
    //user input.
    private double   input[];

    /**
     *
     * @param input double circle array
     */
    public BranchAndBound(double [] input){
        this.circleSize     = input.length;
        this.solution       = new int[circleSize];
        this.selected = new boolean[circleSize];
        this.minWidth       = Double.POSITIVE_INFINITY;
        this.matrix         = new double[this.circleSize][this.circleSize];
        this.input          = new double[this.circleSize];
        System.arraycopy(input,0,this.input,0, input.length);
    }

    /**
     *Creates a matrix and calculate width two circle
     * Same indexs sets -1 (1,1) (2,2) etc.
     */
    public void initializeAdjMatrix(){
        for(int i=0;i<this.circleSize;i++){
            for(int j=0;j<this.circleSize;j++){
                if(i == j){
                    this.matrix[i][j] = -1;
                }
                else{
                    this.matrix[i][j] = calculateDistance(new double[]{input[i],input[j]},0);
                }
            }
        }
    }

    /**
     * Traverse the matrix and find the min the rows
     * @param matrix distance matrix
     * @param index searched index
     * @return min value
     */
    public double rowMin(double matrix[][], int index) {

        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < this.circleSize; i++) {
            if (matrix[index][i] < min && index != i)
                min = matrix[index][i];

        }
        return min;
    }

    /**
     * 1) Takes the array which is a combinations of circles(for example x1 x2 x3 x4)
     * 2) sequentially computes X1+x2 x2+x3 x3+x4 and return of sum
     * 3**) The formula which is given problem is that it uses 	pythagorous' theorem(sqrt(x1^2+x2^2)=x3
     * 3**) Then adds alls.
     * @param r1 is a circle combination array
     * @param size this is used for recursive function
     * @return width of the given cirlces combinations
     */
    public  double calculateDistance(double [] r1,int size){
        double sqrt=0;
        if(size>r1.length-2){
            return sqrt;
        } else{
            sqrt+= Math.sqrt(Math.pow(r1[size]+r1[size+1],2)-Math.pow(r1[size]-r1[size+1],2));
            size = size+1;
            return sqrt+calculateDistance(r1,size);
        }
    }

    /**
     *Set each circle start of the circle list
     * Initialize Distance matrix 2d
     * Sets the path with 0
     * Finds each row min value
     * Sets first circle as a selected
     *
     */
    public void BBsolver()
    {
        for(int j=0;j<this.circleSize;j++) {
            double tmp      = 0;
            double bound    = 0;
            int size        = this.circleSize;
            int path[]      = new int[size];
            //change the start circle of sequences
            tmp=input[0];
            input[0] = input[j];
            input[j] = tmp;
            initializeAdjMatrix();

            Arrays.fill(path, -1);
            Arrays.fill(selected, false);

            for (int i = 0; i < size; i++)
                bound += (rowMin(this.matrix, i));
            selected[0] = true;
            path[0] = 0;

            BBhelper(bound, 0, 1, path);
        }
        double result [] = new double[circleSize];
        for(int ii=0;ii<circleSize;ii++)
            result[ii] = input[solution[ii]];

        System.out.print("Min Width-->"+(calculateDistance(result,0)+result[0]+result[circleSize-1]));
        System.out.println("\n Best Combination--->"+Arrays.toString(result));
    }

    /**
     * if index not last side of matrix
     * if circle not selected and not the like (1,1   2,2)
     *  takes the distance value of current index
     *  calculate the best distance of index.
     *  subs the value main bound
     *  if this value is best so far this value set
     *  Increase index and call with new level
     *
     *  if last index of the matrix and not the like (1,1   2,2)
     *      takes distance and check this is best
     * @param bound best width combination
     * @param weight matrix weights
     * @param index computable matrix row
     * @param path includes traverse indexes
     */
    public void BBhelper(double bound, double weight, int index, int path[]) {
        if(index!=this.circleSize){
            for (int i = 0; i < this.circleSize; i++){
                int inx = path[index-1];
                double tempBound = 0;
                if(selected[i]!= true && this.matrix[inx][i]!=0){
                    tempBound = bound;
                    weight += this.matrix[inx][i];
                    double tmp = (rowMin(matrix, inx));
                    bound = bound - tmp;

                    double newDistance = bound+weight;

                    if(newDistance<minWidth) {
                        path[index] = i;
                        selected[i] = true;
                        BBhelper(bound, weight, index + 1, path);
                    }

                    weight  = weight - matrix[inx][i];
                    bound   = tempBound;

                    Arrays.fill(selected,false);
                    for (int j = 0; j <= index - 1; j++)
                        selected[path[j]] = true;
                }
            }
        }
        else{
            int inx = path[index-1];
            if (matrix[inx][path[0]] != 0) {
                double result = weight+matrix[inx][path[0]];
                if (result < minWidth) {
                    minWidth = result;
                    System.arraycopy(path,0,solution,0,circleSize);
                }
            }
            return;
        }
    }
}
