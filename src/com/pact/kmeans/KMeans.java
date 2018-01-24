package com.pact.kmeans;

import java.util.ArrayList;
import java.util.Random;

public class KMeans {

	private int k = 4;//������������
	private int iteration = 0;//��������
	private int dataLength;
	private ArrayList<float[]> dataSet;// ���ݼ�����  
    private ArrayList<float[]> center;// ��������  
    private ArrayList<ArrayList<float[]>> cluster; // ��  
    private ArrayList<Float> jc;// ���ƽ���ͣ�kԽ�ӽ�dataSetLength�����ԽС  
    private Random random;
    
    /** 
     * ����������ԭʼ���ݼ� 
     *  
     * @param dataSet 
     */  
    public void setDataSet(ArrayList<float[]> dataSet) {  
        this.dataSet = dataSet;  
    }  
  
    /** 
     * ��ȡ������� 
     *  
     * @return ����� 
     */  
  
    public ArrayList<ArrayList<float[]>> getCluster() {  
        return cluster;  
    }  
  
    /** 
     * ��ʼ�� 
     */  
    private void init() {    
        random = new Random(); 
        //���ݼ�Ϊ�յ�ʱ�򣬳�ʼ���ݼ�
        if (dataSet == null || dataSet.size() == 0) {  
            initDataSet();  
        }  
        dataLength = dataSet.size();  
        //��������������ѡȡ��ҪС�����ݸ���
        if (k > dataLength) {  
            k = dataLength;  
        }  
        center = initCenters();  
        cluster = initCluster();  
        jc = new ArrayList<Float>();  
    }  
  
    /** 
     * ���������δ��ʼ�����ݼ���������ڲ��������ݼ� 
     */  
    private void initDataSet() {  
        dataSet = new ArrayList<float[]>();  
        // ����{6,3}��һ���ģ����Գ���Ϊ15�����ݼ��ֳ�14�غ�15�ص���Ϊ0  
        float[][] dataSetArray = new float[][] { { 8, 2 }, { 3, 4 }, { 2, 5 },  
                { 4, 2 }, { 7, 3 }, { 6, 2 }, { 4, 7 }, { 6, 3 }, { 5, 3 },  
                { 6, 3 }, { 6, 9 }, { 1, 6 }, { 3, 9 }, { 4, 1 }, { 8, 6 } };  
  
        for (int i = 0; i < dataSetArray.length; i++) {  
            dataSet.add(dataSetArray[i]);  
        }  
    }  
  
    /** 
     * ��ʼ���������������ֳɶ��ٴؾ��ж��ٸ����ĵ� 
     *  
     * @return ���ĵ㼯 
     */  
    private ArrayList<float[]> initCenters() {  
        ArrayList<float[]> center = new ArrayList<float[]>();  
        int[] randoms = new int[k];  
        boolean flag;  
        int temp = random.nextInt(dataLength);  
        randoms[0] = temp;
        //�����ĸ������������֤���ĸ����������ͬ
        for (int i = 1; i < k; i++) {  
            flag = true;  
            while (flag) {  
                temp = random.nextInt(dataLength);  
                int j = 0; 
                //�൱��һ���Ƚ��㷨����0��ʼ�͵�ǰ��i�Ƚ�
                while (j < i) {  
                    if (temp == randoms[j]) {  
                        break;  //break�˳�����while (j < i)���
                    }  
                    j++;  
                }  
                if (j == i) {  
                    flag = false;  
                }  
            }  
            randoms[i] = temp;  
        }  
  
         //����������������  
         for(int i=0;i<k;i++)  
         {  
        	 System.out.println("test1:randoms["+i+"]="+randoms[i]);  
         }  
        for (int i = 0; i < k; i++) {  
            center.add(dataSet.get(randoms[i]));// ���ɳ�ʼ����������  
        }  
        return center;  
    }  
  
    /** 
     * ��ʼ���ؼ��� 
     *  
     * @return һ����Ϊk�صĿ����ݵĴؼ��� 
     */  
    private ArrayList<ArrayList<float[]>> initCluster() {  
        ArrayList<ArrayList<float[]>> cluster = new ArrayList<ArrayList<float[]>>();  
        for (int i = 0; i < k; i++) {  
            cluster.add(new ArrayList<float[]>());  
        }  
  
        return cluster;  
    }  
  
    /** 
     * ����������֮��ľ��� 
     *  
     * @param element 
     *            ��1 
     * @param center 
     *            ��2 
     * @return ���� 
     */  
    private float distance(float[] element, float[] center) {  
        float distance = 0.0f;  
        float x = element[0] - center[0];  
        float y = element[1] - center[1];  
        float z = x * x + y * y;  
        distance = (float) Math.sqrt(z);  
  
        return distance;  
    }  
  
    /** 
     * ��ȡ���뼯������С�����λ�� 
     *  
     * @param distance 
     *            �������� 
     * @return ��С�����ھ��������е�λ�� 
     */  
    private int minDistance(float[] distance) {  
        float minDistance = distance[0];  
        int minLocation = 0;  
        for (int i = 1; i < distance.length; i++) {  
            if (distance[i] < minDistance) {  
                minDistance = distance[i];  
                minLocation = i;  
            } 
//            else if (distance[i] == minDistance){ // �����ȣ��������һ��λ��    
//                if (random.nextInt(10) < 5) {  
//                    minLocation = i;  
//                }  
//            }  
        }  
  
        return minLocation;  
    }  
  
    /** 
     * ���ģ�����ǰԪ�طŵ���С����������صĴ��� 
     * �������ֱ��ÿ��������������ıȽϣ�ѡ���������ľ������ģ��ֵ�������
     */  
    private void clusterSet() {  
        float[] distance = new float[k];  
        for (int i = 0; i < dataLength; i++) {  
            for (int j = 0; j < k; j++) {  
                distance[j] = distance(dataSet.get(i), center.get(j));  
            }  
            //�õ���������ľ������ĵı��0-(k-1)
            int minLocation = minDistance(distance);   
            cluster.get(minLocation).add(dataSet.get(i));// ���ģ�����ǰԪ�طŵ���С����������صĴ���  
        }  
    }  
  
    /** 
     * ���������ƽ���ķ��� 
     *  
     * @param element 
     *            ��1 
     * @param center 
     *            ��2 
     * @return ���ƽ�� 
     */  
    private float errorSquare(float[] element, float[] center) {  
        float x = element[0] - center[0];  
        float y = element[1] - center[1];  
  
        float errSquare = x * x + y * y;  
  
        return errSquare;  
    }  
  
    /** 
     * �������ƽ����׼�������� 
     */  
    private void countRule() {  
        float jcF = 0;  
        for (int i = 0; i < cluster.size(); i++) {  
            for (int j = 0; j < cluster.get(i).size(); j++) {  
                jcF += errorSquare(cluster.get(i).get(j), center.get(i));  
  
            }  
        }  
        jc.add(jcF);  
    }  
  
    /** 
     * �����µĴ����ķ��� 
     * �����������ڸ�������е�ȡƽ��ֵ������Ϊ�µľ�������
     */  
    private void setNewCenter() {  
        for (int i = 0; i < k; i++) {  
            int n = cluster.get(i).size();  
            if (n != 0) {  
                float[] newCenter = { 0, 0 };  
                for (int j = 0; j < n; j++) {  
                    newCenter[0] += cluster.get(i).get(j)[0];  
                    newCenter[1] += cluster.get(i).get(j)[1];  
                }  
                // ����һ��ƽ��ֵ  
                newCenter[0] = newCenter[0] / n;  
                newCenter[1] = newCenter[1] / n;  
                center.set(i, newCenter);  
            }  
        }  
    }  
  
    /** 
     * ��ӡ���ݣ������� 
     *  
     * @param dataArray 
     *            ���ݼ� 
     */  
    public void printDataArray(ArrayList<float[]> dataArray) {  
        for (int i = 0; i < dataArray.size(); i++) {  
            System.out.println("print:" + "[" + i + "]={"  
                    + dataArray.get(i)[0] + "," + dataArray.get(i)[1] + "}");  
        }  
        System.out.println("===================================");  
    } 
    
    /** 
     * ��ӡ���ݣ������� 
     *  
     * @param dataArray 
     *            ���ݼ� 
     */  
    public void printCluster(ArrayList<ArrayList<float[]>> dataArray) { 
    	int j = 0;
        for (ArrayList<float[]> arrayList : dataArray) {
        	System.out.println(j++);
        	for (int i = 0; i < arrayList.size(); i++) {  
                System.out.println("print:" + "[" + i + "]={"  
                        + arrayList.get(i)[0] + "," + arrayList.get(i)[1] + "}");  
            }  
		}
        System.out.println("===================================");  
    }
    
  
    /** 
     * Kmeans�㷨���Ĺ��̷��� 
     */  
    private void kmeans() {  
        //��ʼ��
    	init();   
        // ѭ�����飬ֱ������Ϊֹ  
        while (true) {  
            clusterSet();  
  
            countRule();  
 
            // �����ˣ��������  
            if (iteration != 0) {  
                if (jc.get(iteration) - jc.get(iteration - 1) < 0.001) {  
                    break;  
                }  
            }  
  
            setNewCenter();    
            iteration++;  
            cluster.clear();  
            cluster = initCluster();  
        }  
        printDataArray(center);
        printCluster(cluster);
    }  
    
    public static void main(String[] args) {
		KMeans km = new KMeans();
		km.kmeans();
	}
} 
