import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class NearestNeighbor {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Programming Fundamentals"); //header
        System.out.println("NAME: Dan Hannigan");
        System.out.println("PROGRAMMING ASSIGNMENT 3");
        System.out.println(" ");

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the training file: "); //asking, then locating & importing file
        File trainFile = new File(input.next());
        Scanner userTrainValue = new Scanner(trainFile);
        Scanner userTrainLabel = new Scanner(trainFile);

        System.out.print("Enter the name of the testing file: ");  //asking, then locating & importing file
        File testFile = new File(input.next());
        Scanner userTestValue = new Scanner(testFile);
        Scanner userTestLabel = new Scanner(testFile);

        Double[][] trainData = new Double [75][4];
        Double[][] testData = new Double[75][4];
        String[] trainLabel = new String [75];
        String[] testLabel = new String [75];

        trainData = parseData(userTrainValue, trainData); //writing our imported files into arrays
        testData = parseData(userTestValue, testData);
        trainLabel = parseLabels(userTrainLabel, trainLabel);
        testLabel = parseLabels(userTestLabel, testLabel);

        System.out.println("\nEX#: TRUE LABEL, PREDICTED LABEL"); //output results
         int correct = 0;
         for (int i = 1; i < 75; i ++) {
             String predictLabel = trainLabel[neighborCalc(trainData, testData[i])];
             String trueLabel = testLabel[i];
             if (predictLabel.equals(trueLabel)) {
                 correct++;
             }
             System.out.println((i+1) + ": " + trueLabel + " " + predictLabel);
         }
         System.out.println("ACCURACY: " + (double)correct/75); //scores accuracy

         input.close();
    }

    static String[] parseLabels(Scanner input, String[] labels){ //puts string values in array
        int index = 0;
        String[] newLabels;

        while (input.hasNextLine()) {
            newLabels = input.nextLine().split(",");
            labels[index] = newLabels[4];
            index++;
        }
        return labels;
    }

    static Double[][] parseData(Scanner input, Double[][] values) { //turning string values into double and copying them into array
        int line = 0;
        String[][] newData = new String[75][4];

        while (input.hasNextLine()) {
            String[] newLine = input.nextLine().split(",");

            for (int i = 0; i < 4; i++) {
                newData[line][i] = newLine[i];
                values[line][i] = Double.parseDouble(newData[line][i]);
            }
            line++;
        }
        return values;
    }

    private static Double calcDist(Double[] testing, Double[] train) { //calculates distance between two values from testing/training arrays
        Double distance = 0.0;

        for (int i = 0; i < testing.length; i++) {
            distance += (testing[i] - train[i]) * (testing[i] - train[i]);
        }
        return Math.sqrt(distance);
    }

    private static int neighborCalc(Double[][] training, Double[] testing) { //calls calcDist to test between training/testing values

        Double closest = calcDist(training[0], testing);
        int neighbor = 0;

        for (int i = 0; i < 75; i++) {
            if(calcDist(training[i], testing) < closest) {
                closest = calcDist(training[i], testing);
                neighbor = i;
            }
        }
        return neighbor;
    }
}