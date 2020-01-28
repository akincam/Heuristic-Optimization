import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Akın Cam
 */
public class AntColonyOptimization {
    //path_pheromone init value
    private double                      c;
    //alpha value to calculate pij(node i to node j with probability)
    private double                      alpha;
    //beta is a parameter to control value
    private double                      beta;
    //pheromone evaporation velocity
    private double                      evaporationRate;
    //pheromone
    private double                      pheromone;
    //this determines how many ant will occur
    private double                      antFactor;
    //random factor
    private double                      randomFactor;
    //max iteration of algorithm
    private int                         maxIterations;
    //number of circles
    private int                         numberOfCircle;
    //number of ants
    private int                         numberOfAnts;
    //keeps all visited paths
    private ArrayList<int []>           allPaths;
    //keeps pheromone of the cities according to path list
    private ArrayList<double []>        paths;
    //keeps ants
    private List<Ant>                   ants    = new ArrayList<>();
    //rand value
    private Random                      random  = new Random();
    //next circle visited probability
    private double                      probabilities[];
    //input
    private double[]                    input;
    //ants' currrent index to choose step by step
    private int                         currentIndex;
    //bestSoFar circle path
    private int[]                       bestCirclesOrder;
    //bestSoFar circle length
    private double                      bestCirclesLength;

    public AntColonyOptimization(double c, double alpha, double beta, double evaporationRate,
                                 double pheromone, double antFactor, double randomFactor,
                                 int maxIterations,double input[]) {
        this.c                  = c;
        this.alpha              = alpha;
        this.beta               = beta;
        this.evaporationRate    = evaporationRate;
        this.pheromone          = pheromone;
        this.antFactor          = antFactor;
        this.randomFactor       = randomFactor;
        this.maxIterations      = maxIterations;
        this.allPaths           = new ArrayList<>();
        this.input              = new double[input.length];
        this.numberOfCircle     = input.length;
        this.numberOfAnts       = (int)(numberOfCircle*antFactor);
        int indexArray [] = new int[numberOfCircle];
        for(int i =0;i<numberOfCircle;i++){
            indexArray[i] = i;
        }
        int d [][] = generateRandomCirclePaths(indexArray);
        for(int [] dd : d)
            allPaths.add(dd);
        paths                   = new ArrayList<>();
        probabilities           = new double[this.numberOfCircle];

        for(int i=0;i<this.numberOfAnts;i++){
            ants.add(new Ant(numberOfCircle));
        }
        System.arraycopy(input,0,this.input,0,input.length);
    }

    /**
     * Generates random paths to choose the ants
     * @param circles input list
     * @return path of the circleCombinations
     */
    public int[][] generateRandomCirclePaths(int[] circles){
        int[][] randomPaths = new int[numberOfCircle][circles.length];
        for(int i = 0; i<numberOfCircle;i++){
            int [] arr = new int[circles.length];
            System.arraycopy(shuffleArray(circles),0,arr,0,circles.length);
            boolean b = false;
            for(int [] d : randomPaths)
                if(Arrays.equals(d, arr)){
                    b = true;
                    break;
                }
            if(!b)
                System.arraycopy(arr,0,randomPaths[i],0,circles.length);
        }
        return randomPaths;
    }

    /**
     * First every ant select a circle
     * The paths are cleaned
     * up to maximum number
     *  first move ants another circle with to condition
     *      firstly random
     *      secondly calculates probability from pheromone
     *  updatePheromone and makes evoporation of the pheromone
     *  ant clear ants traverse path and starts from first circle
     *
     */
    public void solve(){
        initializeAnts();
        initPaths();
        for (int i = 0; i < maxIterations; i++) {
            moveAnts();
            updatePathPheromone();
            updateBest();
            for (Ant a : ants) {
                a.clear();
                currentIndex = -1;
            }
        }
        System.out.println("min width:    " + (bestCirclesLength));
        System.out.println("cirles: ");
        for(int i =0;i<numberOfCircle;i++)
            System.out.print(input[bestCirclesOrder[i]]+" ");
    }

    /**
     * This method sets the ants with random circles.
     */
    public void initializeAnts(){
        for(Ant ant : ants){
            ant.clear();
            ant.setNextCircle(-1,random.nextInt(numberOfCircle));
        }
        currentIndex = 0;
    }

    /**
     * inits Path with parameter c
     */
    public void initPaths(){
        for(int i =0;i<allPaths.size();i++) {
            double [] arr = new double[numberOfCircle];
            Arrays.fill(arr, c);
            paths.add(arr);
        }
    }

    /**
     * This method select a circle step by step
     * And this circle combination not in the list add it
     */
    public void moveAnts(){
        for(int i=currentIndex;i<numberOfCircle-1;i++){
            for(Ant a : ants){
                a.setNextCircle(currentIndex,setNewCircle(a));
            }
            currentIndex++;
        }
        boolean boo = false;
        for(int i =0;i<numberOfAnts;i++){
            for(int[] compArr : allPaths){
                if(Arrays.equals(ants.get(i).path,compArr)==true){
                    boo= true;

                    break;
                }
            }
            if(boo == false){
                int [] d = new int[numberOfCircle];
                System.arraycopy(ants.get(i).path,0,d,0,numberOfCircle);
                allPaths.add(d);
                double [] myarray = new double[numberOfCircle];
                Arrays.fill(myarray, c);
                paths.add(myarray);
            }
            boo = false;
        }

    }


    /**
     * This method shuffle the given array to increase diversity
     * Takes a random values and swap the given indexes
     * @param array input circle combination
     * @return shuffled array indexes
     */
    private int [] shuffleArray(int[] array) {
        int [] arr=null;
        for (int i = 0; i < array.length; i++) {
            arr = new int[array.length];
            System.arraycopy(array,0,arr,0,array.length);
            int swap = ThreadLocalRandom.current().nextInt(0,array.length);
            int temp = arr[swap];
            arr[swap] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    /**
     * First next circle can be selected randomly
     * calculate Selectable probability
     * Second next circle can be selected according to probability.
     * last select not selected circle
     * @param ant
     * @return
     */
    private int setNewCircle(Ant ant){
        //First next circle can be selected randomly
        int randV = random.nextInt(numberOfCircle-1);
        double rand = random.nextDouble();
        if(randomFactor > rand){
            for(int i=0;i<numberOfCircle;i++){
                if(i == randV && !ant.visited(i)){
                    return randV;
                }
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCircle; i++) {
            total += probabilities[i];
            if (total >= r && !ant.visited(i)) {
                return i;
            }
        }
        for(int i = 0; i<numberOfCircle;i++){
            if(!ant.visited(i))
                return i;
        }

        return 0;
    }

    /**
     * Finds the not setted circles and calculates pheromone of them
     * Use formula of node i to node j with probability
     *
     * @param ant
     */
    public void calculateProbabilities(Ant ant){
        int i = -1;
        for(int d []: allPaths){
            i++;
            if(Arrays.equals(ant.path,d)==true){
                break;
            }
        }
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCircle; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(paths.get(i)[l], alpha) * Math.pow(1.0 / input[allPaths.get(i)[l]], beta);
            }
        }
        for (int j = 0; j < numberOfCircle; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(paths.get(i)[j], alpha) * Math.pow(1.0 / input[allPaths.get(i)[j]], beta);
                probabilities[j] = numerator / (pheromone+0.000008);

            }
        }
    }

    /**
     * evoporates pheromone with given evaporationRate
     */
    private void updatePathPheromone() {
        for(double [] doubles : paths){
            for(int i=0;i<doubles.length;i++){
                doubles[i]*= evaporationRate;
            }
        }
        //τi,j = (1 − ρ)τi,j + ∆τi,j pheromone updated this formula
        for (Ant a : ants) {
            //if ant k travels on edge i, j --> 1/Lk Lk is the cost of the k
            double newP = this.pheromone / a.pathLength(input);
            for (int i = 0; i < numberOfCircle - 1; i++) {
                paths.get(a.path[i])[a.path[i + 1]]+= newP;
            }
            paths.get(a.path[numberOfCircle - 1])[a.path[0]]+= newP;
        }
    }

    /**
     * Update best solution array and length
     */
    private void updateBest() {
        if (bestCirclesOrder == null) {
            bestCirclesOrder = new int[ants.get(0).path.length];
            System.arraycopy(ants.get(0).path,0,bestCirclesOrder,0,numberOfCircle);
            bestCirclesLength = ants.get(0).pathLength(input);
        }
        for (Ant a : ants) {
            if (a.pathLength(input) < bestCirclesLength) {
                bestCirclesLength = a.pathLength(input);
                System.arraycopy(a.path,0,bestCirclesOrder,0,numberOfCircle);
            }
        }
    }

    public static class Ant{

        protected int       pathSize;
        //keeps the ants circle order index
        protected int       path[];
        public    boolean   selected[];

        public Ant(int tourSize) {
            this.pathSize   = tourSize;
            this.path       = new int[tourSize];
            this.selected   = new boolean[tourSize];
        }

        public boolean visited(int i) {
            return selected[i];
        }

        public void setNextCircle(int currentIndex, int circleIndex) {
            path[currentIndex + 1] = circleIndex;
            selected[circleIndex] = true;
        }

        /**
         * calculates with of the circle order array
         * @param input  circle order array index
         * @return width of the solution
         */
        public double pathLength(double input[]){
            double arr[] = new double[pathSize];
            for(int i = 0;i<pathSize;i++){
                arr[i] = input[path[i]];
            }
            return  calculateDistance(arr,0)+arr[0]+arr[pathSize-1];
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
         * Clear ant to set new circle arrays.
         */
        public void clear(){
            for(int i = 0; i< selected.length; i++) {
                selected[i] = false;
                path[i] = 0;
            }
        }
    }
}
