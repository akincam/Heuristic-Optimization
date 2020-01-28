package GA;

import Optimization.HelperFunctions;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class GeneticAlgorithm {
    private ArrayList<Chromosome>   population;
    private ArrayList<Chromosome>   solutions;
    private int                     nextMutation;
    private int                     cycle;
    private int                     populationSize;
    private Random                  rand;
    private int                     BOARDSIZE;
    private int                     INIT_POPULATION_SIZE;
    private int                     MAX_CYCLE;
    private int                     MIN_PARENT_SELECT;
    private int                     MAX_PARENT_SELECT;
    private int                     OFFSPRING;
    private int                     MAX_SHUFFLE;
    private double                  MATING_PROBABILITY;
    private double                  MUTATION_RATE;
    private int                     MIN_SHUFFLE;
    private int                     MUT_COUNT;


    public GeneticAlgorithm(int n) {
        BOARDSIZE               = n;
        INIT_POPULATION_SIZE    = 300;
        MAX_CYCLE               = 100;
        MATING_PROBABILITY      = 0.7;
        MUTATION_RATE           = 0.001;
        MIN_PARENT_SELECT       = 10;
        MAX_PARENT_SELECT       = 50;
        OFFSPRING               = 20;
        MIN_SHUFFLE             = 8;
        MAX_SHUFFLE             = 20;
        cycle                   = 0;
        populationSize          = 0;
        MUT_COUNT               = 4;
    }
    public GeneticAlgorithm(){

    }
    public GeneticAlgorithm(int chessBoard, int popSize,int algLoop,double matingProb, double mutationRate,
                            int minParentS, int maxParentS,int offSpring,int minShuffle,int maxShuffle,int mutCount) {
        MAX_CYCLE                       = algLoop;
        OFFSPRING                       = offSpring;
        MIN_PARENT_SELECT               = minParentS;
        MAX_PARENT_SELECT               = maxParentS;
        BOARDSIZE                       = chessBoard;
        MUTATION_RATE                   = mutationRate;
        MIN_SHUFFLE                     = minShuffle;
        MAX_SHUFFLE                     = maxShuffle;
        MATING_PROBABILITY              = matingProb;
        INIT_POPULATION_SIZE            = popSize;
        MUT_COUNT                       = mutCount;
    }

    public void initParameters(int chessBoard, int popSize,int algLoop,double matingProb, double mutationRate,
                               int minParentS, int maxParentS,int offSpring,int minShuffle,int maxShuffle,int mutCount){
        MAX_CYCLE                       = algLoop;
        OFFSPRING                       = offSpring;
        MIN_PARENT_SELECT               = minParentS;
        MAX_PARENT_SELECT               = maxParentS;
        BOARDSIZE                       = chessBoard;
        MUTATION_RATE                   = mutationRate;
        MIN_SHUFFLE                     = minShuffle;
        MAX_SHUFFLE                     = maxShuffle;
        MATING_PROBABILITY              = matingProb;
        INIT_POPULATION_SIZE            = popSize;
        MUT_COUNT                       = mutCount;
    }

    private void initializeVariables(){
        cycle           = 0;
        populationSize  = 0;
        population      = new ArrayList<>();
        solutions       = new ArrayList<>();
        rand            = new Random();
        nextMutation    = HelperFunctions.getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE),rand);
    }

    public boolean GA() {
        initializeVariables();
        boolean     isFinished = false;
        initializePopulation();
        while(!isFinished) {
            populationSize = population.size();
            for(int i = 0; i < populationSize; i++) {
                if ((population.get(i).getConflicts() == 0)) {
                    isFinished = true;
                    break;
                }
            }
            if(cycle == MAX_CYCLE) {
                isFinished = true;
            }
            calculateFitness();
            selection();
            crossAndMutation();
            nextLoop();

            cycle++;
        }

        if(cycle >= MAX_CYCLE) {
            System.out.println("No solution");
            isFinished = false;
        } else {
            populationSize = population.size();
            for(int i = 0; i < populationSize; i++) {
                if(population.get(i).getConflicts() == 0) {
                    solutions.add(population.get(i));
                    HelperFunctions.printSolution(population.get(i).getGenes(),BOARDSIZE);
                    break;
                }
            }
        }
        return isFinished;
    }

    public void crossAndMutation() {
        int crossoverRand;
        int p1;
        int p2;
        int newIndex1;
        int newIndex2;
        Chromosome n1;
        Chromosome n2;
        for(int i = 0; i < OFFSPRING; i++) {
            p1 = getAChromosomeIndex(-1);
            crossoverRand = HelperFunctions.getRandomNumber(0, 100,rand);
            if(crossoverRand <= MATING_PROBABILITY * 100) {
                p2 = getAChromosomeIndex(p1);
                n1 = new Chromosome(BOARDSIZE);
                n2 = new Chromosome(BOARDSIZE);
                population.add(n1);
                newIndex1 = population.indexOf(n1);
                population.add(n2);
                newIndex2 = population.indexOf(n2);
                crossover(p1, p2, newIndex1, newIndex2);
                if(HelperFunctions.getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE),rand)<= nextMutation) {
                    mutation(newIndex1, (int)Math.round((MUT_COUNT - 1) * rand.nextDouble() + 1));
                }
                population.get(newIndex1).computeConflicts();
                population.get(newIndex2).computeConflicts();

                nextMutation = HelperFunctions.getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE),rand);
            }
        }
    }


    public void crossover(int chromA, int chromB, int child1, int child2) {
        int j;
        int item1;
        int item2;
        int pos1 = 0;
        int pos2 = 0;
        Chromosome thisChromo = population.get(chromA);
        Chromosome thatChromo = population.get(chromB);
        Chromosome newChromo1 = population.get(child1);
        Chromosome newChromo2 = population.get(child2);
        int crossPoint1 = HelperFunctions.getRandomNumber(0,BOARDSIZE-1,rand);
        int crossPoint2 = HelperFunctions.getDifferentRandomNumber(BOARDSIZE-1,crossPoint1,rand);
        if(crossPoint2 < crossPoint1) {
            j = crossPoint1;
            crossPoint1 = crossPoint2;
            crossPoint2 = j;
        }
        for(int i = 0; i < BOARDSIZE; i++) {
            newChromo1.setGene(i, thisChromo.getGene(i));
            newChromo2.setGene(i, thatChromo.getGene(i));
        }
        for(int i = crossPoint1; i <= crossPoint2; i++) {
            item1 = thisChromo.getGene(i);
            item2 = thatChromo.getGene(i);
            for(j = 0; j < BOARDSIZE; j++) {
                if(newChromo1.getGene(j) == item1) {
                    pos1 = j;
                } else if (newChromo1.getGene(j) == item2) {
                    pos2 = j;
                }
            }
            if(item1 != item2) {
                newChromo1.setGene(pos1, item2);
                newChromo1.setGene(pos2, item1);
            }
            for(j = 0; j < BOARDSIZE; j++) {
                if(newChromo2.getGene(j) == item2) {
                    pos1 = j;
                } else if(newChromo2.getGene(j) == item1) {
                    pos2 = j;
                }
            }
            if(item1 != item2) {
                newChromo2.setGene(pos1, item1);
                newChromo2.setGene(pos2, item2);
            }

        }
    }

    public int getAChromosomeIndex(int parentA) {
        int parent = 0;
        Chromosome thisChromo = null;
        boolean done = false;
        while(!done) {
            parent = HelperFunctions.getRandomNumber(0,population.size() - 1,rand);
            if(parent != parentA && parentA!=-1){
                thisChromo = population.get(parent);
                if(thisChromo.isSelected()){
                    done = true;
                }
            }
            else{
                thisChromo = population.get(parent);
                if(thisChromo.isSelected()) {
                    done = true;
                }
            }

        }
        return parent;
    }
    private void selection() {
        int index;
        int count;
        double totalFitness = 0.0;
        double traverseTotal;
        double spinNum;
        Chromosome c1;
        Chromosome c2;
        boolean boo;
        int populationSize  = population.size();
        int selectSize      = HelperFunctions.getRandomNumber(MIN_PARENT_SELECT,MAX_PARENT_SELECT,rand);

        for (Chromosome chromosome : population) {
            c1 = chromosome;
            totalFitness += c1.getFitness();
        }
        totalFitness *= 0.01;
        for(int i = 0; i < populationSize; i++) {
            c1 = population.get(i);
            c1.setSelectionProbability(c1.getFitness() / totalFitness);
        }
        count = 0;
        while(count<selectSize){
            spinNum         = HelperFunctions.getRandomNumber(0,50,rand);
            index           = 0;
            traverseTotal   = 0;
            boo = false;
            while(!boo) {
                c1 = population.get(index);
                traverseTotal += c1.getSelectionProbability();
                if(traverseTotal >= spinNum) {
                    if(index == 0) {
                        c2 = population.get(index);
                    } else if(index >= populationSize - 1) {
                        c2 = population.get(populationSize - 1);
                    } else {
                        c2 = population.get(index-1);
                    }
                    c2.setIsSelected(true);
                    boo = true;
                } else {
                    index++;
                }
            }
            count++;
        }
    }


    public void calculateFitness() {
        int     populationSize = population.size();
        double  percent;
        double  b,w;
        Chromosome chromosome;
        w = Collections.max(population).getConflicts();
        b = w - Collections.min(population).getConflicts();
        b = b/2;
        for(int i = 0; i < populationSize; i++) {
            chromosome = population.get(i);
            percent = (((w - chromosome.getConflicts())/2)* 100.0)/ b;
            chromosome.setFitness(percent/2);
        }
    }


    public void nextLoop() {
        int populationSize;
        Chromosome thisChromo;
        for(int i=0;i<10;i++){
            population.remove(HelperFunctions.getRandomNumber(0,population.size()-1,rand));
        }
        populationSize = population.size();
        for(int i = 0; i < populationSize; i++) {
            thisChromo = population.get(i);
            thisChromo.setIsSelected(false);
        }
    }

    private void initializePopulation(){
        int         shuffleNumber;
        Chromosome  chromosome;
        for(int i= 0;i< INIT_POPULATION_SIZE;i++){
            chromosome = new Chromosome(BOARDSIZE);
            shuffleNumber = HelperFunctions.getRandomNumber(MIN_SHUFFLE,MAX_SHUFFLE,rand);
            shuffleChromosome(chromosome,shuffleNumber);
            chromosome.computeConflicts();
            population.add(chromosome);
        }
    }
    public void shuffleChromosome(Chromosome  chromosome, int shuffleNumber){
        int index1 = 0,index2 = 0,tempData;
        for(int i= 0;i< shuffleNumber; i++){
            index1      = HelperFunctions.getRandomNumber(0,BOARDSIZE-1,rand);
            index2      = HelperFunctions.getDifferentRandomNumber(BOARDSIZE-1,index1,rand);
            tempData    = chromosome.getGene(index1);
            chromosome.setGene(index1,chromosome.getGene(index2));
            chromosome.setGene(index2,tempData);
        }
    }

    public void mutation(int index, int mutCount) {
        int tempData;
        int gene1;
        int gene2;
        Chromosome c1;
        c1 = population.get(index);

        for(int i = 0; i < mutCount; i++) {
            gene1 = HelperFunctions.getRandomNumber(0,BOARDSIZE-1,rand);
            gene2 = HelperFunctions.getDifferentRandomNumber(BOARDSIZE-1,gene1,rand);
            tempData = c1.getGene(gene1);
            c1.setGene(gene1, c1.getGene(gene2));
            c1.setGene(gene2, tempData);
        }
    }
}