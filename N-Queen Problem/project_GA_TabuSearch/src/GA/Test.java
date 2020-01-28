package GA;

import TS.TabuSearch;

public class Test {

    public static void main(String args[]) {
        long startTime;
        long endTime;
        /*
        startTime = System.nanoTime();
        TabuSearch tabuSearch = new TabuSearch();
        tabuSearch.initParameters(4,1,4,10);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 4*4: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(4,1,4,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 4*4: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(4,2,4,10);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 4*4: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(4,2,4,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 4*4: "+(endTime - startTime)/1000000+" ms");
        */
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        /*
        startTime = System.nanoTime();
        tabuSearch.initParameters(8,1,8,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(8,1,100,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(8,10,20,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(8,30,60,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");*/
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        //******************************************************************************//
        /*startTime = System.nanoTime();
        tabuSearch.initParameters(16,1,16,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 16*16: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(16,10,20,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 16*16: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(16,200,300,100);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 16*16: "+(endTime - startTime)/1000000+" ms");

        startTime = System.nanoTime();
        tabuSearch.initParameters(16,256,512,10000);
        tabuSearch.TS();
        endTime = System.nanoTime();
        System.out.println("Duration Time 16*16: "+(endTime - startTime)/1000000+" ms");
        */
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
       /* geneticAlgorithm.initParameters(4,10,10,0.7,0.001,10,50,20,8,20,4);
        geneticAlgorithm.GA();*/
        startTime = System.nanoTime();
        geneticAlgorithm.initParameters(12,144,300,0.7,0.001,10,100,80,8,20,4);
        geneticAlgorithm.GA();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");
        startTime = System.nanoTime();
        geneticAlgorithm.initParameters(12,144,300,0.7,0.1,10,100,80,8,20,4);
        geneticAlgorithm.GA();
        endTime = System.nanoTime();
        System.out.println("Duration Time 8*8: "+(endTime - startTime)/1000000+" ms");
        /*geneticAlgorithm.initParameters(4,10,10,0.7,0.001,10,50,20,8,20,4);
        geneticAlgorithm.GA();*/
        /*geneticAlgorithm.initParameters(4,10,10,0.7,0.001,10,50,20,8,20,4);
        geneticAlgorithm.GA();*/
        /*geneticAlgorithm.initParameters(4,10,10,0.7,0.001,10,50,20,8,20,4);
        geneticAlgorithm.GA();*/
        /*geneticAlgorithm.initParameters(4,10,10,0.7,0.001,10,50,20,8,20,4);
        geneticAlgorithm.GA();*/
    }
}
