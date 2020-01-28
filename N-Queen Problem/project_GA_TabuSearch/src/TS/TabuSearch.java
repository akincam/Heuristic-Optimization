package TS;


import Optimization.HelperFunctions;
import java.util.*;

public class TabuSearch {
    private int                         BOARDSIZE;
    private int                         MAX_CYCLE;
    private int                         TABUSIZE;
    private int                         POPSIZE;
    private ArrayList<Neighborhood>     tabuList;
    private ArrayList<Neighborhood>     population;
    private Random                      rand;
    public TabuSearch(int n,int tabuListSize,int populationSize,int maxCycle){
        this.BOARDSIZE          = n;
        this.population         = new ArrayList<>();
        POPSIZE                 = populationSize;
        rand                    = new Random();
        MAX_CYCLE               = maxCycle;
        setTabuList(tabuListSize);

    }
    public TabuSearch(){

    }

    public void initParameters(int n,int tabuListSize,int populationSize,int maxCycle){
        this.BOARDSIZE          = n;
        this.population         = new ArrayList<>();
        POPSIZE                 = populationSize;
        rand                    = new Random();
        MAX_CYCLE               = maxCycle;
        setTabuList(tabuListSize);
    }

    public void TS(){
        int             shuffleNumber;
        int             loop;
        Neighborhood    currentBestN;
        Neighborhood    bestSolution;
        boolean         boo = false;
        bestSolution    = new Neighborhood(BOARDSIZE);
        shuffleNumber   = HelperFunctions.getRandomNumber(BOARDSIZE-2,BOARDSIZE,rand);
        shuffle(bestSolution,shuffleNumber);
        bestSolution.computeFitness();
        loop            = 0;

        while(loop<MAX_CYCLE){
            population.clear();
            if(tabuList.size() >= TABUSIZE) {
                while(tabuList.size()>TABUSIZE/2){
                    tabuList.remove(0);
                }
            }
            initializePopulation();
            calculateFitness();
            currentBestN = new Neighborhood(BOARDSIZE);

            Neighborhood nb = population.get(0);
            for(int k=0;k<BOARDSIZE;k++){
                currentBestN.setGene(k,nb.getGene(k));
            }

            currentBestN.computeFitness();

            for(int i=0;i<POPSIZE;i++) {
                Neighborhood neighborhood1 = population.get(i);
                if (neighborhood1.getFitness() < currentBestN.getFitness()) {
                    for (int ii = 0; ii < BOARDSIZE; ii++)
                        currentBestN.setGene(ii, neighborhood1.getGene(ii));
                    currentBestN.computeFitness();
                }
            }
            if(currentBestN.getFitness()<bestSolution.getFitness()){
                bestSolution = currentBestN;
            }
            if(bestSolution.getFitness()==0) {
                System.out.println("Best Solution:");
                HelperFunctions.printSolution(bestSolution.getGenes(),BOARDSIZE);
                boo = true;
                break;
            }
            loop++;
        }
        if(!boo){
            System.out.println("No Solution");
        }
    }
    private void initializePopulation(){
        int         shuffleNumber;
        Neighborhood  chromosome;
        boolean       boo;
        int           count = 0;
        while(count<POPSIZE){
            chromosome = new Neighborhood(BOARDSIZE);
            shuffleNumber = HelperFunctions.getRandomNumber(BOARDSIZE-2,BOARDSIZE-1,rand);
            shuffle(chromosome,shuffleNumber);
            chromosome.computeFitness();
            boo=false;
            for (Neighborhood neighborhood : tabuList) {
                if (Arrays.equals(neighborhood.getGenes(), chromosome.getGenes())) {
                    boo = true;
                    break;
                }
            }
            if(!boo || tabuList.size() == 0) {
                population.add(chromosome);
                tabuList.add(chromosome);
                count++;
            }
        }
    }

    public void shuffle(Neighborhood  chromosome, int shuffleNumber){
        int index1 = 0,index2 = 0,tempData;
        for(int i= 0;i< shuffleNumber; i++){
            index1      = HelperFunctions.getRandomNumber(0,BOARDSIZE-1,rand);
            index2      = HelperFunctions.getDifferentRandomNumber(BOARDSIZE-1,index1,rand);
            tempData    = chromosome.getGene(index1);
            chromosome.setGene(index1,chromosome.getGene(index2));
            chromosome.setGene(index2,tempData);
        }
    }

    public void calculateFitness() {
        int populationSize = population.size();
        Neighborhood thisChromo = null;
        for(int i = 0; i < populationSize; i++) {
            thisChromo = population.get(i);
            thisChromo.computeFitness();
        }
    }

    public void setTabuList(int tabuListSize){
        if(tabuListSize>HelperFunctions.factorial(BOARDSIZE)){
            System.out.println("Tabu list size must be smaller than BOARDSIZE!\n" +
                    "So tabulist size set as BOARDSIZE*2");
            TABUSIZE        = BOARDSIZE*2;
            tabuList        = new ArrayList<>(tabuListSize);
        }
        else{
            TABUSIZE        = tabuListSize;
            tabuList        = new ArrayList<>(tabuListSize);
        }
    }
}
