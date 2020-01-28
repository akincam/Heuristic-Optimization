package com.opt1;

/**
 * @author Akin Cam
 */
public class IteratedLocalSearch extends OptimizationAlgorithm {
    private double []       temArr;
    private int             iterationSize;

    /**
     *
     * @param neighbourSize which neighbour can be created
     * @param iterationSize iteration size of algorithm
     */
    public IteratedLocalSearch(int neighbourSize,int iterationSize){
        this.neighbourSize = neighbourSize;
        this.iterationSize = iterationSize;
        neighboursArr  = new double[neighbourSize][];
    }

    /**
     * 1)Created a initial solution
     * 2)Make local search finded currentBestSolution
     * 3)until iterationSize is satisfy
     * 4)   mutate the bestSolutionArray and create different neighbour usign bestSolutionArray
     * 5)   Make local search finded currentBestSolution
     * 6)   if this solution is best
     * 7)       update currentBest solution and array
     * 8)   increase iterationSize;
     * @param input circle array
     * @return the best solution
     */
    @Override
    public double[] opAlgorithmSolver(double[] input) {
        double initialSolution;
        double [] returnValue = new double[input.length];
        //1)Created a initial solution
        for(int ii = 0;ii<neighbourSize;ii++){
            neighboursArr[ii] = shuffleArray(input).clone();
        }
        //2)Make local search finded currentBestSolution
        initialSolution=localSearch(input);
        returnValue = neighboursArr[currentIndex];
        temArr = neighboursArr[currentIndex];

        // 3)until iterationSize is satisfy
        for(int i = 0;i<iterationSize;i++){
            //4)   mutate the bestSolutionArray and create different neighbour usign bestSolutionArray
            for(int ii = 0;ii<neighbourSize;ii++){
                neighboursArr[ii] = shuffleArray(temArr).clone();
            }
            // 5)   Make local search finded currentBestSolution
            double tempVal = localSearch(temArr);
            temArr = neighboursArr[currentIndex];

            // 6)   if this solution is best
            if(tempVal<initialSolution) {
                //7)       update currentBest solution and array
                initialSolution = tempVal;
                for(int l=0;l<temArr.length;l++){
                    returnValue[l]=temArr[l];
                }
            }
            //8)   increase iterationSize;
        }
        bestSoFar = initialSolution;
        return returnValue;
    }
}
