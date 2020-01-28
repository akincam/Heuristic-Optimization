package com.opt1;

import java.util.ArrayList;

/**
 * @author Akın ÇAM
 */
public class Main {

    public static void main(String[] args) {
        //input size 10 ve yakın değerler
        double[] arr1 = new double[]{5.0, 6.0, 5.0, 9.0, 3.0, 9.0, 5.0, 7.0, 4.0, 12.0};
        // input size = 10 uzak değerler için
        //double[] arr1 = new double[]{1.3,157.6,177.3,12.7,1000.5,7.4,666.4,1.4,144.7,55.8};
        //input size = 9
        //double[] arr1 = new double[]{7.8, 56.9, 4.6, 7.0, 8.0, 1.0, 77.0, 1.0, 8444.0};
        //input size = 60
      /*  double[] arr1 = new double[]{7.0, 5.0, 4.0, 4.0, 8.0, 1.0, 77.0, 1.0, 8.0, 5.0, 4.0,
                3.0, 3.0, 6.0, 6.0, 2.0, 6.0, 7.0, 2.0, 9.0, 9.0, 3.0, 5.0, 5.0, 4.0,55.6,
                33.2,11.2,5.5,6.7,8.8,54.4,3.2,24.5,6.73,5.3,4.5,5.6,2.1,6.0, 1.0, 99.5, 1.0, 77.4, 1.2, 77.0, 2.0, 67.3, 2.0, 66.3, 3.0, 44.5,
                3.0, 34.5, 3.0, 22.1, 4.0, 9.0, 4.0, 9.0};*/
        System.out.println("***********************************************************************************************************************************");
        System.out.println("<<<<<<<<<<<<<<<<<<<<Variable Neighbour Search Algorithm>>>>>>>>>>>>>>>>>>>>");

        OptimizationAlgorithm variableNeighbourSearch = new VariableNeighbourSearch(100,100);

        long startTime = System.nanoTime();

        double [] result = variableNeighbourSearch.opAlgorithmSolver(arr1);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Best Circles Combination: ");
        for(double d1 : result){
            System.out.print(d1+", ");
        }
        System.out.println("\nbestSoFar VNS: "+variableNeighbourSearch.getBestSoFar());
        System.out.println("Execution Time: "+duration/100000+" milisecond");

        System.out.println("\n<<<<<<<<<<<<<<<<<<<<Simulated Annealing Algorithm>>>>>>>>>>>>>>>>>>>>");
        OptimizationAlgorithm simulatedAnnealing = new SimulatedAnnealing(1,0.1,100,100,.00001);
        startTime = System.nanoTime();
        result=simulatedAnnealing.opAlgorithmSolver(arr1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Best Circles Combination: ");
        for(double d1 : result){
            System.out.print(d1+", ");
        }
        System.out.println("\nbestSoFar Simulated Annealing: "+simulatedAnnealing.getBestSoFar());
        System.out.println("Execution Time: "+duration/100000+" milisecond");

        System.out.println("\n<<<<<<<<<<<<<<<<<<<<Iterated Local Search Algorithm>>>>>>>>>>>>>>>>>>>>");
        startTime = System.nanoTime();
        IteratedLocalSearch it = new IteratedLocalSearch(1000,100);
        result=it.opAlgorithmSolver(arr1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Best Circles Combination: ");
        for(double d1 : result){
            System.out.print(d1+", ");
        }
        System.out.println("\nbestSoFar Simulated Annealing: "+it.getBestSoFar());
        System.out.println("Execution Time: "+duration/100000+" milisecond");
        System.out.println("\n<<<<<<<<<<<<<<<<<<<<Greedy Algorithm>>>>>>>>>>>>>>>>>>>>");

        ArrayList<Double> arr2 = new ArrayList<Double>();
        for(double doub : arr1)
            arr2.add(doub);
        GreedyAlgorithm gr= new GreedyAlgorithm();
        startTime = System.nanoTime();
        result=gr.greedyAlgorithm(arr2);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Best Circles Combination: ");
        for(double d1 : result){
            System.out.print(d1+", ");
        }
        double distance = calculateDistance(result,0)+result[0]+result[result.length-1];
        System.out.println("\nbestSoFar Simulated Annealing: "+distance);
        System.out.println("Execution Time: "+duration/100000+" milisecond");

        System.out.println("\n<<<<<<<<<<<<<<<<<<<<Brute Force Algorithm>>>>>>>>>>>>>>>>>>>>");
        BruteForce bruteForce = new  BruteForce();
      //  double[] arr3 = new double[]{3.0,6.5,4.3,3.3,2.8,66.6,6.4};
      //  double[] arr3 = new double[]{2.0,3.0,49.0};
       // double[] arr3 = new double[]{3.0,6.5,4.3,3.3,2.8,66.6,6.4,88.6,88.5,44.6};
       // double[] arr3 = new double[]{44.5,66.3,22.3};
       // double[] arr3 = new double[]{3.2,4.5,66.7,22.4};
        startTime = System.nanoTime();
        result=bruteForce.bruteForceAlgorithm(arr1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Best Circles Combination: ");
        for(double d1 : result){
            System.out.print(d1+", ");
        }
        System.out.println("\nExecution Time: "+duration/100000+" milisecond");
        System.out.println("\n***********************************************************************************************************************************");

    }

    public static double calculateDi(double r1, double r2){
        double pow1;
        double pow2;
        double sqrt;
        pow1 = Math.pow(r1+r2,2);
        pow2 = Math.pow(r1-r2,2);
        sqrt = Math.sqrt(pow1-pow2);
        return sqrt;
    }

    public static double calculateDistance(double [] r1,int size){
        double sqrt=0;
        if(size>r1.length-2){
            return sqrt;
        } else{
            sqrt+= Math.sqrt(Math.pow(r1[size]+r1[size+1],2)-Math.pow(r1[size]-r1[size+1],2));
            size = size+1;
            return sqrt+calculateDistance(r1,size);
        }
    }

}
