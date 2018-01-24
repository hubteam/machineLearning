package com.pact.exception;

public class TestException {

	private double s = 0.02;
	
	public void test(double[][] data){
		int hang = data.length;
		int lie = data[0].length;
		double[] u = new double[lie];
		double[] theta = new double[lie];
		double[] p = new double[hang];
		//【用训练集的到参数】
		//得到平均
		for (int i = 0; i < lie; i++) {//lie
			double temp = 0;//为了计算u
			for (int j = 0; j < hang; j++) {//hang
				temp += data[j][i];	
			}			
			u[i] = temp/hang;
		}
		//得到theta
		for (int i = 0; i < lie; i++) {//lie
			double temp = 0;//为了计算u
			for (int j = 0; j < hang; j++) {//hang
				temp += Math.pow(data[j][i]-u[i], 2);	
			}
			//得到平均
			theta[i] = temp/hang;
		}
		//得到p(x)【要用测试集，代码里只是为了沥青思路】
		for (int i = 0; i < hang; i++) {
			double temp = 1;
			for (int j = 0; j < lie; j++) {
				temp *= (1/(Math.pow(2*Math.PI, 1/2)*theta[j]))*
						Math.exp(-(Math.pow(data[i][j]-u[j], 2)/(2*Math.pow(theta[j], 2))));
			}
			p[i] = temp;
		}
		//检测
		for (int i = 0; i < p.length; i++) {
			if(p[i] < s){
				System.out.println("异常dian");
			}else if(p[i] >= s){
				System.out.println("非异常点");
			}
		}
		
	}
}
