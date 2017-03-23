import java.util.*;
import java.io.*;
class Input  {
	
	public int inputNumber = 8;
    ArrayList<Double[]> csv = new ArrayList<Double[]>();
    ArrayList<Double[]> trainingData = new ArrayList<Double[]>();
    ArrayList<Double[]> validationData = new ArrayList<Double[]>();
    ArrayList<Double[]> testData = new ArrayList<Double[]>();
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

        while ((line = br.readLine()) != null) {
            boolean valid = true;
            String[] input = line.split(",");
            Double[] checkedInput = new Double[inputNumber+1];
            for(int i=0; i<inputNumber+1;i++) {
                if(valid == true) {
                    if(input[i] != null && checkInput(input[i])) {
                        if(Double.parseDouble(input[i]) >= 0) {
                            checkedInput[i] = Double.parseDouble(input[i]);
                        } else {
                            valid = false;
                        } 
                    } else {
                        valid = false;
                    }
                }
            }
            if(valid == true) {
                csv.add(checkedInput);
            }
        }

        standardiseData();
        splitDataSets();
    }

    void standardiseData() {
    	for(int numberOfInputs = 0; numberOfInputs<csv.get(0).length; numberOfInputs++) {
	    	double[] minMax = findMinMax(numberOfInputs);
	    	if(numberOfInputs == csv.get(0).length-1) {
	    		min = minMax[0];
	    		max = minMax[1];
	    	}
	    	for(int i=0; i<csv.size(); i++) {
	    		csv.get(i)[numberOfInputs] = standardiseFunction(csv.get(i)[numberOfInputs], minMax[0], minMax[1]);
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

    	return minMax;
    }

    double standardiseFunction(double rawValue, double min, double max) {
    	return 0.8 * ((rawValue - min)/(max-min)) + 0.1;
    }

    Double[] currentRound(int round) {
    	return csv.get(round);
    }

    double destandardiseFunction(double standValue) {
    	return (((standValue - 0.1)/0.8)*(max-min))+min;
    }

    void splitDataSets() {
    	int counter = 0;
    	while(counter < csv.size()) {
    		if(counter < Math.round(csv.size()*0.6)) {
    			trainingData.add(csv.get(counter));
    		} else if (counter < Math.round(csv.size()*0.8)) {
    			validationData.add(csv.get(counter));
    		} else {
    			testData.add(csv.get(counter));	
    		}
    		counter++;
    	}
    }

}