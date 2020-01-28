package com.opt1;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Akin Cam
 */
public abstract class OptimizationAlgorithm {
    public double [][]  neighboursArr;
    public int          neighbourSize;
    public int          currentIndex;
    public double []    bestSoFarArr;
    public double       bestSoFar;
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
     * This method takes kth index of neighbour taken from shaking makes local search and find the best and return it
     * @param indexK is taken from shaking method(kth alue of neighbour in shaking).
     * @return best distance of given neigbours
     */
    public double localSearch(double [] indexK){
        int         index   = 0;
        double      cal;
        //the combination which is taken from shaking
        double      temp    = calculateDistance(indexK,0);
        //for x1 x2 x3 calcute distance not add x1 first side radius and x3 last side radius
        temp+= indexK[0]+indexK[indexK.length-1];
        //traverse neighbours and find the best combination
        for(int i=0;i<neighbourSize;i++){
            cal = calculateDistance(neighboursArr[i],0);
            cal+= neighboursArr[i][0]+neighboursArr[i][neighboursArr[0].length-1];
            if(cal<temp){
                temp = cal;
                index = i;
            }
        }
        currentIndex = index;
        return temp;
    }

    /**
     * Best combination Array
     * @return array of best combination
     */
    public double[] getBestSoFarArr() {
        return bestSoFarArr.clone();
    }

    /**
     * minimum width
     * @return minimum width
     */
    public double getBestSoFar() {
        return bestSoFar;
    }


    public abstract double [] opAlgorithmSolver(double [] input);
}
