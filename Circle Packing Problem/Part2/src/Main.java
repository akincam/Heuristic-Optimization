public class Main {

    public static void main(String[] args) {

        double[] arr = new double[]{5.0, 6.0, 5.0, 9.0, 3.0, 9.0, 5.0, 7.0};
        double [] arr2 = {2.0,16.0,4.0,6.0,7.0,33.0,1.0,8.0,22.0,44.0};

        System.out.println("***********************************************************************************************************************************");
        System.out.println("<<<<<<<<<<<<<<<<<<<<Ant Colony Optimization>>>>>>>>>>>>>>>>>>>>");

        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(1,1,5,0.9,500,
                0.8,0.45,100,arr);
        long startTime = System.nanoTime();
        antColonyOptimization.solve();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\nExecution Time: "+duration/100000+" milisecond");

        System.out.println("\n<<<<<<<<<<<<<<<<<<<<PSO Optimization>>>>>>>>>>>>>>>>>>>>");
        PSO pso = new PSO(arr,100,100,1,1);
        startTime = System.nanoTime();
        pso.runPSO();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("\nExecution Time: "+duration/100000+" milisecond");


        System.out.println("\n<<<<<<<<<<<<<<<<<<<<Branch And Bound Optimization>>>>>>>>>>>>>>>>>>>>");
        BranchAndBound branchAndBound = new BranchAndBound(arr);
        startTime = System.nanoTime();
        branchAndBound.BBsolver();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("\nExecution Time: "+duration/100000+" milisecond");
        return;



    }
}
