package com.pact.kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TestKMeans {
	//����
	private float[][] dataSetArray = new float[][] { { 8, 2 }, { 3, 4 }, { 2, 5 },  
        { 4, 2 }, { 7, 3 }, { 6, 2 }, { 4, 7 }, { 6, 3 }, { 5, 3 },  
        { 6, 3 }, { 6, 9 }, { 1, 6 }, { 3, 9 }, { 4, 1 }, { 8, 6 } };  
     //������ݼ����б�
     ArrayList<float[]> dataSet = new ArrayList<>();  
     //������ݼ��ĳ���
     int length = dataSetArray.length;
     //��������
     ArrayList<float[]> clusters = new ArrayList<>();
     
     public void setDataSet(float[][] data){
    	 for (int i = 0; i < data.length; i++) {
			dataSet.add(data[i]);
		}
     }
     
     public ArrayList<float[]> getDataSet(){
    	 return dataSet;
     }
     //�������ĵĲ���
     public void setClusters(int k,int m){
    	 Set<Integer> s = new HashSet<>();
    	 while(s.size() < k){
    		 s.add(new Random().nextInt(m));
    	 }
    	 Iterator<Integer> iterator = s.iterator();
    	 while(iterator.hasNext()){
			clusters.add(dataSet.get(iterator.next()));
		}
     }
     
     public ArrayList<float[]> getClusters(){
    	 
    	 return clusters;
     }
     //�������
     public float distance(float[] element, float[] center) {  
         float distance = 0.0f;  
         float x = element[0] - center[0];  
         float y = element[1] - center[1];  
         float z = x * x + y * y;  
         distance = (float) Math.sqrt(z);  
         return distance;  
     }  
     //��С����
     
     
}
