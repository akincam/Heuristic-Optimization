package com.opt1;

/**
 * @author Akin Cam
 */
public class SimulatedAnnealing extends OptimizationAlgorithm {
    private double temp;
    private double decreaseRate;
    private double range;
    private int    numIterations;

    /**
     *
     * @param temp initial temp
     * @param coolingRate temp*coolingRate
     * @param iteration number of iteration
     * @param size neighboursize
     * @param range finish value
     */
    public SimulatedAnnealing(double temp,double coolingRate,int iteration,int size,double range){
        this.temp               = temp;
        this.decreaseRate       = coolingRate;
        this.numIterations      = iteration;
        this.range              = range;
        this.neighbourSize      = size;
        neighboursArr           = new double[neighbourSize][];
    }

    /**
     * 1) Selects  init solution areas.
     * 2) find current best solution
     * 3) variable selected T,constant,reduction factor
     * 4)while termination crieteria is not satisfied
     * 5)  get a new solution area size = neighbourSize
     * 6)  do local search and find currentBest
     * 7)  if new best < current best update it
     * 8)  else random number < Math.pow((currentBest - secondFirst) / temp,2 currentBestSolution update it
     * 10) if currentBest > the best
     * 11)     update the best.
     * 12)  temp *= coolingRate for finish
     * @param input circles input
     * @return the best solutions
     */
    @Override
    public double[] opAlgorithmSolver(double[] input) {
        // Initialize initial solution
        double currentBest;
        double theBest;
        double [] returnValue=new double[input.length];
        //get random solution area
        for(int ii = 0; ii< neighbourSize; ii++){
            neighboursArr[ii] = shuffleArray(input).clone();
        }
        //1) Selects the best solution init solution areas.
        currentBest = localSearch(input);
        theBest = currentBest;
        returnValue=neighboursArr[currentIndex];

        // * 3) variable selected T,constant,reduction factor
        // 4)while termination crieteria is not satisfied
        while (temp>range) {
            //5)  get a new solution area size = neighbourSize
            for(int iter=0;iter<numIterations;iter++){
                for (int ii = 0; ii < neighbourSize; ii++) {
                    neighboursArr[ii] = shuffleArray(input).clone();
                }
                //do local search and find currentBest
                double secondFirst = localSearch(input);
                //if new best < current best update it
                if (currentBest > secondFirst) {
                    currentBest = secondFirst;
                }
                // 8)  else random number < Math.pow((currentBest - secondFirst) / temp,2 currentBestSolution update it
                else if(Math.random()<Math.pow((currentBest - secondFirst) / temp,2)){
                    currentBest = secondFirst;
                }
                // 10) if currentBest > the best
                //  11)     update the best.
                if(theBest>currentBest){
                    theBest = currentBest;
                    bestSoFar = theBest;
                    bestSoFarArr = neighboursArr[currentIndex];
                    for(int i=0;i<bestSoFarArr.length;i++){
                        returnValue[i]=bestSoFarArr[i];
                    }
                }
            }
            temp *= decreaseRate;; // decrease temperature
        }
        bestSoFar = theBest;
        return returnValue;
    }
}