package com.pact.knn;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TestKNN {

	private double[][] train = {{1,1.1},{1,1},{0,0},{0,0.1}};
	private int[] labels = {1,1,2,2};
	
	public void getdistance(double[][] test,int k){
		
		//jian���룬zhi���(���վ����С��������)
		TreeMap<Double, Integer> tm = new TreeMap<>();
		//�����ֵ������ͳ�Ƽ��ĸ���
		HashMap<Integer, Integer> hm = new HashMap<>();
		//ͳ�Ƽ������ļ�������
		int count = 1;
		//����Kֵ
		int sum = 0;
		//�õ����룬���մ�С�������
		for (int i = 0; i < train.length; i++) {
			double temp = Math.sqrt(Math.pow(test[0][0]-train[i][0], 2) + Math.pow(test[0][1]-train[i][1], 2));
		    tm.put(temp, labels[i]);
		}
		//���վ����С����ȡǰK������ǰK����������
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
			System.out.println("���Ϊ1");
		}else{
			System.out.println("���Ϊ2");
		}
	}
	
	public static void main(String[] args) {
		TestKNN tk = new TestKNN();
		double[][] test = {{0,0}};
		tk.getdistance(test, 3);
	}
	
}
