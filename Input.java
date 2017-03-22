import java.util.*;
import java.io.*;
class Input  {
	
	public int inputNumber = 8;
    ArrayList<Double[]> csv = new ArrayList<Double[]>();
    double min = 0;
    double max = 0;
        
    boolean checkInput(String input) {
        try {  
            double d = Double.parseDouble(input);  
        }  
        catch(NumberFormatException nfe)  
        {  
            return false;  
        }

        return true;
    }

    void getData() throws FileNotFoundException, IOException {
    	String csvFile = "CWDataStudent.csv";
        BufferedReader br = null;
        String line = "";
        br = new BufferedReader(new FileReader(csvFile));
        // int counter = 0;

        while ((line = br.readLine()) != null) {
            boolean valid = true;
            String[] input = line.split(",");
            Double[] checkedInput = new Double[inputNumber+1];
            // counter++;
            for(int i=0; i<inputNumber+1;i++) { //input change
                if(valid == true) {
                    if(input[i] != null && checkInput(input[i])) {
                        if(Double.parseDouble(input[i]) >= 0) {
                            checkedInput[i] = Double.parseDouble(input[i]);
                        } else {
                            valid = false;
                            // System.out.println(input[i]);
                            // System.out.println(counter + " i:"+ i + " Line: " + line);
                        } 
                    } else {
                        valid = false;
                        // System.out.println(input[i]);
                        // System.out.println(counter + " Line: ");
                    }
                }
            }

            if(valid == true) {
                csv.add(checkedInput);
            }
        }

        standardiseData();

        // System.out.println(csv.get(1)[8]);
    }

    void standardiseData() {
    	for(int numberOfInputs = 0; numberOfInputs<csv.get(0).length; numberOfInputs++) {
	    	double[] minMax = findMinMax(numberOfInputs);
	    	if(numberOfInputs == csv.get(0).length-1) {
	    		min = minMax[0];
	    		max = minMax[1];
	    	}
	    	for(int i=0; i<csv.size(); i++) {
	    		// System.out.println(csv.get(i)[column]);
	    		csv.get(i)[numberOfInputs] = standardiseFunction(csv.get(i)[numberOfInputs], minMax[0], minMax[1]);
	    		// System.out.println(csv.get(i)[column]);
	    		// System.out.println("");
	    	}
    	}
    }

    double[] findMinMax(int column) {
    	double[] minMax = new double[2];
    	double min, max;
    	min = -1;
    	max = -1;

    	for(int i=0; i<csv.size(); i++) {
			if(min == -1) {
				max = csv.get(i)[column];
				min = csv.get(i)[column];
			}

			if(csv.get(i)[column] < min) {
				min = csv.get(i)[column];
			}

			if(csv.get(i)[column] > max) {
				max = csv.get(i)[column];
			}
    	}

    	minMax[0] = min;
    	minMax[1] = max;

    	System.out.println("min: " + minMax[0] + " Max: " + minMax[1]);
    	return minMax;
    }

    double standardiseFunction(double rawValue, double min, double max) {
    	return 0.8 * ((rawValue - min)/(max-min)) + 0.1;
    }

    Double[] currentRound(int round) {
    	return csv.get(round);
    }

    // void outputToCSV(double output, double predictand) throws FileNotFoundException, IOException {
    //     String csvFile = "testoutput.csv";
    //     FileWriter writer = new FileWriter(csvFile);
    //     writer.append(String.valueOf(output));
    //     writer.append(",");
    //     writer.append(String.valueOf(predictand));
    //     writer.append("\n");
    //     writer.flush();
    //     writer.close();

    // }

    double destandardiseFunction(double standValue) {
    	return (((standValue - 0.1)/0.8)*(max-min))+min;
    }

}