package com.pact.knn;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TestKNN {

	private double[][] train = {{1,1.1},{1,1},{0,0},{0,0.1}};
	private int[] labels = {1,1,2,2};
	
	public void getdistance(double[][] test,int k){
		
		//jian距离，zhi类别(按照距离从小到达排序)
		TreeMap<Double, Integer> tm = new TreeMap<>();
		//键类别，值个数，统计键的个数
		HashMap<Integer, Integer> hm = new HashMap<>();
		//统计键个数的计数变量
		int count = 1;
		//控制K值
		int sum = 0;
		//得到距离，按照从小到达存入
		for (int i = 0; i < train.length; i++) {
			double temp = Math.sqrt(Math.pow(test[0][0]-train[i][0], 2) + Math.pow(test[0][1]-train[i][1], 2));
		    tm.put(temp, labels[i]);
		}
		//按照距离从小到大，取前K个，对前K个的类别计数
		for (Entry<Double, Integer> i : tm.entrySet()) {
			if(sum<k){
				if(hm.containsKey(i.getValue())){
					hm.put(i.getValue(), ++count);
				}else{
					hm.put(i.getValue(), 1);
				}
			}else{
				break;
			}	
			sum++;
		}
		//System.out.println(hm.get(1));
		//System.out.println(hm.get(2));
		if(hm.get(1) > hm.get(2)){
			System.out.println("类别为1");
		}else{
			System.out.println("类别为2");
		}
	}
	
	public static void main(String[] args) {
		TestKNN tk = new TestKNN();
		double[][] test = {{0,0}};
		tk.getdistance(test, 3);
	}
	
}
