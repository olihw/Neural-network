public class TestPerception {

    public static void main(String[] args) {
        double equation;
    	double[] x1array = {1, 2, 5, 4, 6, 1};
    	double[] x2array = {4, 9, 6, 5, 0.7, 1.5};
    	int[] classarray = {-1, 1, 1, 1, -1, -1};
    	double w0, w1, w2;
    	w0 = 0;
    	w1 = 0;
    	w2 = 0;
    	double bias = 1;
    	boolean check = false;
    	int counter = 0;
    	while(check == false) {
    		counter++;
    		check = true;
	    	for(int i=0; i<classarray.length; i++) {
				equation = w0 + (x1array[i]*w1) + (x2array[i]*w2);

				if( (equation < 0 && classarray[i] < 0) || (equation > 0 && classarray[i] > 0)) {
				} else {
					check = false;
					w0 = w0+(classarray[i]*bias);
					w1 = w1+(classarray[i]*x1array[i]);
					w2 = w2+(classarray[i]*x2array[i]);
					System.out.println(w0+" "+w1+" "+w2);
				}

			}
		}
		System.out.println(counter);	
    }

}