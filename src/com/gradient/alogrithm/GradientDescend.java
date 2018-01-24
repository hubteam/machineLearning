package com.gradient.alogrithm;

public class GradientDescend {

	double[][] trainData = { 
 
			{1,1}, {2,2}, {3,3}, {4,4},{5,5},{6,6},{7,7},{8,8},{9,9},{10,10},
			{11,11},{12,12},
			{13,13},
			{14,14},
			{15,15},
			{16,16},
			{17,17},
			{18,18},
			{19,19},
			{20,20},
			{21,21},
			{22,22},
			{23,23},
			{24,24},
			{25,25},
			{26,26},
			{27,27},
			{28,28},
			{29,29},
			{30,30},
//	  {158,14}, 	  
//	  {128, 5},
//             {61,17},
//             {37,16},
//             {113,16},
//             {59,12},
//             {82,14},
//             {148,16},
//             {18, 2},
//             {168,18},
//             {78,15},
//             {175,13},
//             {80,16},
//             {27, 9},
//             {22,16},
//             {105, 5},
//             {96,12},
//             {131, 3},
//             {15, 2},
//             {9,13},
//             {8, 6},
//             {100,14},
//             {4,16},
//             {151,16},
//             {31,16},
//             {125,11},
//             {130,13},
//             {112,16},
//             {140,11},
//             {93,16},
//             {1, 9},
//             {52, 6},
//             {20, 9},
//             {91,12},
//             {73, 1},
//             {35,13},
//             {143, 3},
//             {61, 1},
//             {97,16},
//             {139,10},
//             {136,15},
//             {131,13},
//             {121, 3},
//             {177,14},
//             {68,10},
//             {9,17},
//             {139, 6},
//             {2,17},
//             {140,15},
//             {72,15},
//             {2,13},
//             {120, 8},
//             {51, 9},
//             {102,13},
//             {130, 1},
//             {114, 8},
//             {81, 1},
//             {118,16},
//             {118,16},
//             {17,10},
//             {195,17},
//             {159,13},
//             {18,11},
//             {15,16},
             };
	private double learningRate = 0.0001;
	private double tolerance = 0.001;
	private int sampleNum = trainData.length;
	

	public  void train(){
		double theta0 = 0;
		double theta1 = 0;
		double newtheta0 = getNewTheta0(theta0,theta1);
		double newtheta1 = getNewTheta1(theta0,theta1);
		double j = getJ(theta0,theta1);
		double newJ = getJ(newtheta0,newtheta1);
		int i = 0;
		while((Math.abs(j-newJ)>tolerance)){
			System.out.println(i);
			theta0 = newtheta0;
			theta1 = newtheta1;
			newtheta0 = getNewTheta0(theta0,theta1);
			newtheta1 = getNewTheta1(theta0,theta1);
			j = getJ(theta0,theta1);
			newJ = getJ(newtheta0,newtheta1);
			i++;
		}
		System.out.println("theta0="+theta0);
		System.out.println("theta1="+theta1);
	}

	public double getJ(double theta0,double theta1){
		
		double sum = 0 ;
		for (int i = 0; i < sampleNum; i++) {
			sum += Math.pow((theta0+theta1*trainData[i][0]-trainData[i][1]), 2);
		}
		return sum/(2*sampleNum);
	}
	
	public double getNewTheta0(double oldTheta0,double oldTheta1){
		double sum = 0 ;
		for (int i = 0; i < sampleNum; i++) {
			sum += oldTheta0+oldTheta1*trainData[i][0]-trainData[i][1];
		}
		double newTheta0 = oldTheta0 - learningRate*sum/sampleNum;
		return newTheta0;
	}

	public double getNewTheta1(double oldTheta0,double oldTheta1){
		double sum = 0 ;
		for (int i = 0; i < sampleNum; i++) {
			sum += (oldTheta0+oldTheta1*trainData[i][0]-trainData[i][1])*trainData[i][0];
		}
		double newTheta1 = oldTheta1 - learningRate*sum/sampleNum;
		return newTheta1;
	}
	
	public static void main(String[] args) {
		System.out.println("test");
		GradientDescend g = new GradientDescend();
		g.train();
	}
}