package com.opt1;

import java.util.ArrayList;
import java.util.Collections;

public class GreedyAlgorithm {


    public double [] greedyAlgorithm(ArrayList<Double> input){
        double d = 0;
        ArrayList<Double> solutionArr = new ArrayList<Double>();
        Collections.sort(input);
        Double firstIndex   =  input.get(input.size()/2);
        Double secondIndex    =  input.get(input.size()/2+1);
        input.remove(input.size()/2);
        input.remove(input.size()/2);
        solutionArr.add(firstIndex);
        solutionArr.add(secondIndex);
        double temp1=-1000;
        double temp2=1000;
        int nextIndex=0;
        boolean max = true;
        for(int i=0;i<solutionArr.size();i++){
            temp1=-1000;
            temp2=-1000;
            for(int j=0;j<input.size();j++){
                if(max){
                    if(temp1 < (solutionArr.get(i) - input.get(j))){
                        temp1=  solutionArr.get(i) - input.get(j);
                        nextIndex = j;
                    }
                }else {
                    if(temp2<input.get(j) - solutionArr.get(i)){
                        temp2 =   input.get(j) - solutionArr.get(i);
                        nextIndex = j;
                    }
                }
            }
            if(max)
                max = false;
            else
                max=true;
            double [] arr1 = new double[25];
            int ii=0;
            if(input.size()==0) {
                double [] arr = new double[solutionArr.size()];
                int iii = 0;
                for(double doubles : solutionArr){
                    arr[iii++] =doubles;
                }
                return arr;

            }
            solutionArr.add(solutionArr.size()-1,input.get(nextIndex));
            input.remove(nextIndex);

        }
        double [] arr = new double[solutionArr.size()];
        int iii = 0;
        for(double doubles : solutionArr){
            arr[iii++] =doubles;
        }
        return arr;
    }
    private  double calculateDistance(double [] r1,int size){
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
