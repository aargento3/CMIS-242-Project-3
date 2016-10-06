package cmis.pkg242.project.pkg3;

/**
 * 
 * @author AArgento
 * @date 25 September 2016
 * @class CMIS 242
 * @purpose Utility class for CMIS 242 Project 3. Includes algorithms for recursive
 *          and iterative methods of determining nth result in the sequence where
 *          result = 2(n-1) + (n-2). Also contains methods to return efficiency
 *          values for both the GUI and the log file.
 * 
 */

public final class Sequence {

    //efficency variables
    private static int iterativeEfficiency = 0;
    private static int recursiveEfficiency = 0;

    //algorithm for iterative method
    public static int iterativeMethod(int n) {

        iterativeEfficiency = 0;
        int nValue = 0;
        
        switch (n) {
            case 0:
                iterativeEfficiency++;
                return 0;
            case 1:
                iterativeEfficiency++;
                return 1;
            default:
                int previous = 1;
                int nextPrevious = 0;
                for (int i = 2; i <= n; i++) {
                    iterativeEfficiency++;
                    nValue = (2 * previous) + nextPrevious;
                    nextPrevious = previous;
                    previous = nValue;
                }   
                break;
        }
        return nValue;   
    }
    
    //recursive method main calls helper method
    public static int recursiveMethod(int n) {
        recursiveEfficiency = 0;
        return recursiveMethodHelper(n);
    }
    
    //algorithm for recursive method
    private static int recursiveMethodHelper(int n) {
        switch (n) {
            case 0:
                recursiveEfficiency++;
                return 0;
            case 1:
                recursiveEfficiency++;
                return 1;
            default:
                recursiveEfficiency++;
                return (2 * recursiveMethodHelper(n - 1)) + (recursiveMethodHelper(n - 2));
        }
    }
    
    //returns iterative efficiency for use in GUI
    public static int getIterativeEfficiency() {
        return iterativeEfficiency;
    }
    
    //returns recursive efficiency for use in GUI
    public static int getRecursiveEfficiency() {
        return recursiveEfficiency;
    }
    
    //returns iterative efficiency for use in File    
    public static int getIterativeEfficiencyForFile(int n) {
        iterativeMethod(n);
        return iterativeEfficiency;
    }
    
    //returns recursive efficiency for use in File
    public static int getRecursiveEfficiencyForFile(int n) {
        recursiveMethod(n);
        return recursiveEfficiency;
    }
    
}//end Sequence class
