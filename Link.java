class Link {

	int input = -1;     
	double weight = 0;
	
	void setLink(int newInput, double newWeight) {
		input = newInput;
		weight = newWeight;
	}
	void updateWeight (double newWeight) {
		weight = newWeight;
	}

}