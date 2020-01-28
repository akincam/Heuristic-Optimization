package com.opt1;


/**
 * @author Akin Cam
 */
public class BruteForce {
    public  double [][] array;
    public  int aa = 0;
    /**
     * 1)Sets all permutations
     * 2)Sets a temp value
     * 3)Compare with other if solution is feasible set is currentBest
     * 4)until permutations list has done
     * @param input circles
     * @return best solution
     */
    public double [] bruteForceAlgorithm(double [] input){
        aa = 0;
        double temp = 0;
        double result = 10000000;
        int index=0;
        array= new double[factorial(input.length)][input.length];
        permutation(0, input);
        for(int k=0;k<array.length;k++) {
            temp += array[k][0];
            temp += calculateDi(array[k],0);
            temp += array[k][array[k].length - 1];
            if(temp<result){
                result = temp;
                index = k;
            }
            temp =0;
        }
        System.out.println("\nbestSoFar Brute Force: "+result);
        double [] returnValue = new double[input.length];
        returnValue = array[index];
        return returnValue;
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
    private double calculateDi(double [] r1,int size){
        double sqrt=0;
        if(size>r1.length-2){
            return sqrt;
        } else{
            sqrt+= Math.sqrt(Math.pow(r1[size]+r1[size+1],2)-Math.pow(r1[size]-r1[size+1],2));
            size = size+1;
            return sqrt+calculateDi(r1,size);
        }
    }


    /**
     * 1) init condition if count return and assign this possibility to destination array
     * 2) else swap array
     * 3) increase count and call recursive function
     * 4) swap array
     * @param count is 0 for keep size of input
     * @param input array
     */
    public  void permutation(int count, double[] input ) {
        //1) init condition if count return and assign this possibility to destination array
        if (count == input.length) {
            array[aa] = input.clone();
            aa++;
            return;
        }
        for (int i = count; i < input.length; i++) {
            // 2) else swap array
            double temp = input[i];
            input[i] = input[count];
            input[count] = temp;
            //3) increase count and call recursive function
            permutation(count + 1, input);
            //4) swap array
            double temp2 = input[i];
            input[i] = input[count];
            input[count] = temp2;
        }
    }

    /**
     *
     * @param size input size
     * @return factorial
     */
    public int factorial(int size){
        if (size == 0)
            return 1;
        else
            return(size * factorial(size-1));
    }
}
