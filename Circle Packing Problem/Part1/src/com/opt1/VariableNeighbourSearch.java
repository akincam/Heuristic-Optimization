package com.opt1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Akin Cam
 */
public class VariableNeighbourSearch extends OptimizationAlgorithm {
    private int             stoppingCondition;
    private double          kMax;

    /**
     *
     * @param kMax max neighbourSize
     * @param stoppingCondition the finish condition
     */
    public VariableNeighbourSearch(int kMax,int stoppingCondition){
        this.neighbourSize      = kMax;
        neighboursArr           = new double[neighbourSize][];
        this.kMax               = kMax;
        this.stoppingCondition  = stoppingCondition;
    }

    /**
     * takes init input value shuffle it and calculate the initial distance value.
     * @param input is the initial input combinations.
     */
    private void generateInitialSolution(double [] input){
        bestSoFarArr = shuffleArray(input).clone();
        bestSoFar    = calculateDistance(bestSoFarArr,0);
        bestSoFar    += bestSoFarArr[0]+bestSoFarArr[bestSoFarArr.length-1];
    }

    /**
     *
     * @param initialSolution takes the initial solution and creates a neigbours according to given initial solution
     *  returns index value negibours.
     * @param index kth value of neighbours
     * @return kth value of neighbours
     */
    private double [] shake(double [] initialSolution, int index){
        for(int i = 0;i<neighbourSize;i++){
            neighboursArr[i] = shuffleArray(initialSolution);
        }
        return neighboursArr[index];
    }


    /**
     * Main VNS algorithm
     * 1) Select neighboring structures set
     * 2) Create a initial solution for this problem
     * 3) Determine the stopping condition
     * 4) until stopping condition(count<stoppingCondition)
     * 5)   set k = 0;
     * 6)   until traverse all neighbour of this problem
     * 7)       take current best solution. Create a neigbours array. and return kth index(Shaking)
     * 8)       local search and find the best combinations of neighbours
     * 9)       if best combinations of local search method is minimum from bestSoFar
     * 10)          initialize k=0
     * 11)          update bestSoFar and bestSoFarArray
     * 12)      else increase k
     * 13)  count+=1
     * @param input is the cirles raidus
     */
    @Override
    public double[] opAlgorithmSolver(double[] input) {
        int k       =0;
        int count   =0;
        double temp =0;
        double [] indexK;
        double [] returnValue=new double[input.length];
        //2) Create a initial solution for this problem
        generateInitialSolution(input);
        //3) Determine the stopping condition
        //4) until stopping condition(count<stoppingCondition)
        while(count<stoppingCondition){
            //5)   set k = 0;
            k=0;
            //6)   until traverse all neighbour of this problem
            while(k<kMax){
                //7) take current best solution. Create a neigbours array. and return kth index(Shaking)
                indexK=shake(bestSoFarArr,k);
                //8) local search and find the best combinations of neighbours
                temp=localSearch(indexK);
                //9)       if best combinations of local search method is minimum from bestSoFar
                //10)          initialize k=0
                //11)          update bestSoFar and bestSoFarArray
                if(temp<bestSoFar){
                    k=0;
                    bestSoFarArr = neighboursArr[currentIndex].clone();
                    bestSoFar = temp;

                    for(int i=0;i<bestSoFarArr.length;i++){
                        returnValue[i]=bestSoFarArr[i];
                    }
                    //12) else increase k
                }else{
                    k=k+1;
                }
            }
            //13)  count+=1
            count++;
        }

        return returnValue;
    }
}