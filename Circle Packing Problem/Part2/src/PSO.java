import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class PSO {
    private int         STOP_COND;
    private int         POPULATION_SIZE;
    private int         CIRCLE_SIZE;
    private double      C1;
    private double      C2;
    private Particle    GBEST;
    private double      GBESTCOST;
    private double []   input;
    private static ArrayList<Particle> particles;
    public PSO(double [] arr,int stopCond,int populationSize,double c1,double c2){
        this.STOP_COND          = stopCond;
        this.POPULATION_SIZE    = populationSize;
        this.CIRCLE_SIZE        = arr.length;
        this.C1                 = c1;
        this.C2                 = c2;
        input                   = new double[this.CIRCLE_SIZE];

        System.arraycopy(arr,0,input,0,arr.length);
        particles               = new ArrayList<>(POPULATION_SIZE);
        this.GBESTCOST          = Double.POSITIVE_INFINITY;
    }


    /**
     * Initialize a population until population size has satisfy
     * While Stopping condition
     *  for every particle(Circle Combination)
     *      takes gbest,pBest and current solution
     *      if(currentSolution(1,2,3,4,5) index i not equal to pBestSolution(2,3,1,5,4)
     *          creates a swap operator with a(it maintains probability) p best
     *          creates a swap operator with a(it maintains probability) g best
     *      if rand value satisfy alpha,beta --> makes swap
     *      Update gBest
     */
    public void runPSO(){
        initializePopulation();
        findGBest();
        for(int ii=0;ii<STOP_COND;ii++) {
            for (Particle particle : particles) {
                findGBest();
                particle.clearVelocity();
                double[] solutionGbest      = new double[this.CIRCLE_SIZE];
                double[] solutionPbest      = new double[this.CIRCLE_SIZE];
                double[] currentSolution    = new double[this.CIRCLE_SIZE];

                System.arraycopy(GBEST.getpBest(),0,solutionGbest,0,GBEST.getpBest().length);

                System.arraycopy(particle.getpBest(),0,solutionPbest,0,particle.getpBest().length);

                System.arraycopy(particle.getSolution(),0,currentSolution,0,particle.getSolution().length);

                ArrayList<double[]> tempVelocity = new ArrayList<>();

                for (int i = 0; i < this.CIRCLE_SIZE; i++) {

                    if (currentSolution[i] != solutionPbest[i]) {
                        double[] swapOp = {i, findIndex(solutionPbest, currentSolution[i]), this.C1};
                        tempVelocity.add(swapOp);

                        double tempVal = solutionPbest[(int) swapOp[0]];
                        solutionPbest[(int) swapOp[0]] = solutionPbest[(int) swapOp[1]];
                        solutionPbest[(int) swapOp[1]] = tempVal;
                    }

                    if (currentSolution[i] != solutionGbest[i]) {

                        double[] swapOp = {i, findIndex(solutionGbest, currentSolution[i]), this.C2};
                        tempVelocity.add(swapOp);

                        double tempVal = solutionGbest[(int) swapOp[0]];
                        solutionGbest[(int) swapOp[0]] = solutionGbest[(int) swapOp[1]];
                        solutionGbest[(int) swapOp[1]] = tempVal;
                    }
                }

                for (double[] d : tempVelocity) {
                    double dd = ThreadLocalRandom.current().nextDouble(0, 2);
                    if (dd <= d[2]) {
                        double tempVal = currentSolution[(int) d[0]];
                        currentSolution[(int) d[0]] = currentSolution[(int) d[1]];
                        currentSolution[(int) d[1]] = tempVal;
                    }

                }

                particle.setSolution(currentSolution);
                double cost = calculateDistance(currentSolution, 0) + currentSolution[0] + currentSolution[currentSolution.length - 1];
                particle.setCurrentCost(cost);
                if (cost < particle.getpBestCost()) {
                    particle.setpBest(currentSolution);
                    particle.setpBestCost(cost);
                }
            }
        }
        System.out.println("GBEST ="+GBEST.getpBestCost());
        System.out.println(Arrays.toString(GBEST.getpBest()));
        System.out.println();
    }

    /**
     * Creates a new population with given input
     * Add it into particle because every circle order is a particle
     */
    public void initializePopulation(){
        double [] solution = new double[this.CIRCLE_SIZE];
        for(int i = 0; i<this.POPULATION_SIZE; i++){
            System.arraycopy(shuffleArray(input),0,solution,0,this.CIRCLE_SIZE);
            particles.add(new Particle(solution,calculateDistance(input,0)));
        }
    }

    /**
     * Finds gbest of the population
     */
    public void findGBest(){
        for(Particle particle : particles)
            if(particle.getpBestCost()<this.GBESTCOST){
                this.GBESTCOST  = particle.getpBestCost();
                this.GBEST      = particle;
            }
    }
    /**
     * This method shuffle the given array to increase diversity
     * Takes a random values and swap the given indexes
     * @param array input circle combination
     * @return shuffled array
     */
    public   double [] shuffleArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            int swap = ThreadLocalRandom.current().nextInt(0,array.length);
            double temp = array[swap];
            array[swap] = array[i];
            array[i] = temp;
        }
        return array;
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
     * Particle class keeps every circles combination
     */
    private static class Particle{
        private double []               solution;
        private double []               pBest;
        private double                  currentCost;
        private double                  pBestCost;
        public  ArrayList<double []>    velocity;

        public Particle(double [] solution,double cost){

            this.velocity       = new ArrayList<>();
            this.solution       = new double[solution.length];
            this.pBest          = new double[solution.length];
            this.currentCost    = cost+solution[0]+solution[solution.length-1];
            this.pBestCost      = this.currentCost;
            System.arraycopy(solution,0,this.solution,0,solution.length);
            System.arraycopy(solution,0,this.pBest,0,solution.length);


        }
        public double[] getSolution() {
            return solution;
        }

        public void setSolution(double[] solution) {
            System.arraycopy(solution,0,this.solution,0,solution.length);
        }

        public double[] getpBest() {
            return pBest;
        }

        public void setpBest(double[] pBest) {
            System.arraycopy(pBest,0,this.pBest,0,pBest.length);
        }

        public double getCurrentCost() {
            return currentCost;
        }

        public void setCurrentCost(double currentCost) {
            this.currentCost = currentCost;
        }

        public double getpBestCost() {
            return pBestCost;
        }

        public void setpBestCost(double pBestCost) {
            this.pBestCost = pBestCost;
        }

        public void clearVelocity(){
            this.velocity = null;
        }
    }

    /**
     * Find value index
     * @param array input
     * @param value will finded value
     * @return index of value
     */
    public int findIndex(double array[], double value) {
        int index = 0;

        if (array == null || array.length == 0)
            return -1;
        else{
            while (index < array.length) {

                if (array[index] != value)
                    index+=1;
                else
                    return index;
            }
        }
        return -1;
    }
}
