package com.pact.kmeans;

import java.util.ArrayList;
import java.util.Random;

public class KMeans {

	private int k = 4;//聚类中心数量
	private int iteration = 0;//迭代次数
	private int dataLength;
	private ArrayList<float[]> dataSet;// 数据集链表  
    private ArrayList<float[]> center;// 中心链表  
    private ArrayList<ArrayList<float[]>> cluster; // 簇  
    private ArrayList<Float> jc;// 误差平方和，k越接近dataSetLength，误差越小  
    private Random random;
    
    /** 
     * 设置需分组的原始数据集 
     *  
     * @param dataSet 
     */  
    public void setDataSet(ArrayList<float[]> dataSet) {  
        this.dataSet = dataSet;  
    }  
  
    /** 
     * 获取结果分组 
     *  
     * @return 结果集 
     */  
  
    public ArrayList<ArrayList<float[]>> getCluster() {  
        return cluster;  
    }  
  
    /** 
     * 初始化 
     */  
    private void init() {    
        random = new Random(); 
        //数据集为空的时候，初始数据集
        if (dataSet == null || dataSet.size() == 0) {  
            initDataSet();  
        }  
        dataLength = dataSet.size();  
        //聚类中心数量的选取，要小于数据个数
        if (k > dataLength) {  
            k = dataLength;  
        }  
        center = initCenters();  
        cluster = initCluster();  
        jc = new ArrayList<Float>();  
    }  
  
    /** 
     * 如果调用者未初始化数据集，则采用内部测试数据集 
     */  
    private void initDataSet() {  
        dataSet = new ArrayList<float[]>();  
        // 其中{6,3}是一样的，所以长度为15的数据集分成14簇和15簇的误差都为0  
        float[][] dataSetArray = new float[][] { { 8, 2 }, { 3, 4 }, { 2, 5 },  
                { 4, 2 }, { 7, 3 }, { 6, 2 }, { 4, 7 }, { 6, 3 }, { 5, 3 },  
                { 6, 3 }, { 6, 9 }, { 1, 6 }, { 3, 9 }, { 4, 1 }, { 8, 6 } };  
  
        for (int i = 0; i < dataSetArray.length; i++) {  
            dataSet.add(dataSetArray[i]);  
        }  
    }  
  
    /** 
     * 初始化中心数据链表，分成多少簇就有多少个中心点 
     *  
     * @return 中心点集 
     */  
    private ArrayList<float[]> initCenters() {  
        ArrayList<float[]> center = new ArrayList<float[]>();  
        int[] randoms = new int[k];  
        boolean flag;  
        int temp = random.nextInt(dataLength);  
        randoms[0] = temp;
        //生成四个随机数，并保证这四个随机数不相同
        for (int i = 1; i < k; i++) {  
            flag = true;  
            while (flag) {  
                temp = random.nextInt(dataLength);  
                int j = 0; 
                //相当于一个比较算法，从0开始和当前的i比较
                while (j < i) {  
                    if (temp == randoms[j]) {  
                        break;  //break退出的是while (j < i)这个
                    }  
                    j++;  
                }  
                if (j == i) {  
                    flag = false;  
                }  
            }  
            randoms[i] = temp;  
        }  
  
         //测试随机数生成情况  
         for(int i=0;i<k;i++)  
         {  
        	 System.out.println("test1:randoms["+i+"]="+randoms[i]);  
         }  
        for (int i = 0; i < k; i++) {  
            center.add(dataSet.get(randoms[i]));// 生成初始化中心链表  
        }  
        return center;  
    }  
  
    /** 
     * 初始化簇集合 
     *  
     * @return 一个分为k簇的空数据的簇集合 
     */  
    private ArrayList<ArrayList<float[]>> initCluster() {  
        ArrayList<ArrayList<float[]>> cluster = new ArrayList<ArrayList<float[]>>();  
        for (int i = 0; i < k; i++) {  
            cluster.add(new ArrayList<float[]>());  
        }  
  
        return cluster;  
    }  
  
    /** 
     * 计算两个点之间的距离 
     *  
     * @param element 
     *            点1 
     * @param center 
     *            点2 
     * @return 距离 
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
     * 获取距离集合中最小距离的位置 
     *  
     * @param distance 
     *            距离数组 
     * @return 最小距离在距离数组中的位置 
     */  
    private int minDistance(float[] distance) {  
        float minDistance = distance[0];  
        int minLocation = 0;  
        for (int i = 1; i < distance.length; i++) {  
            if (distance[i] < minDistance) {  
                minDistance = distance[i];  
                minLocation = i;  
            } 
//            else if (distance[i] == minDistance){ // 如果相等，随机返回一个位置    
//                if (random.nextInt(10) < 5) {  
//                    minLocation = i;  
//                }  
//            }  
        }  
  
        return minLocation;  
    }  
  
    /** 
     * 核心，将当前元素放到最小距离中心相关的簇中 
     * 做法：分别对每个数据与聚类中心比较，选择距离最近的聚类中心，分到该类中
     */  
    private void clusterSet() {  
        float[] distance = new float[k];  
        for (int i = 0; i < dataLength; i++) {  
            for (int j = 0; j < k; j++) {  
                distance[j] = distance(dataSet.get(i), center.get(j));  
            }  
            //得到距离最近的聚类中心的标记0-(k-1)
            int minLocation = minDistance(distance);   
            cluster.get(minLocation).add(dataSet.get(i));// 核心，将当前元素放到最小距离中心相关的簇中  
        }  
    }  
  
    /** 
     * 求两点误差平方的方法 
     *  
     * @param element 
     *            点1 
     * @param center 
     *            点2 
     * @return 误差平方 
     */  
    private float errorSquare(float[] element, float[] center) {  
        float x = element[0] - center[0];  
        float y = element[1] - center[1];  
  
        float errSquare = x * x + y * y;  
  
        return errSquare;  
    }  
  
    /** 
     * 计算误差平方和准则函数方法 
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
     * 设置新的簇中心方法 
     * 做法：将属于该类的所有点取平均值后设置为新的聚类中心
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
                // 设置一个平均值  
                newCenter[0] = newCenter[0] / n;  
                newCenter[1] = newCenter[1] / n;  
                center.set(i, newCenter);  
            }  
        }  
    }  
  
    /** 
     * 打印数据，测试用 
     *  
     * @param dataArray 
     *            数据集 
     */  
    public void printDataArray(ArrayList<float[]> dataArray) {  
        for (int i = 0; i < dataArray.size(); i++) {  
            System.out.println("print:" + "[" + i + "]={"  
                    + dataArray.get(i)[0] + "," + dataArray.get(i)[1] + "}");  
        }  
        System.out.println("===================================");  
    } 
    
    /** 
     * 打印数据，测试用 
     *  
     * @param dataArray 
     *            数据集 
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
     * Kmeans算法核心过程方法 
     */  
    private void kmeans() {  
        //初始化
    	init();   
        // 循环分组，直到误差不变为止  
        while (true) {  
            clusterSet();  
  
            countRule();  
 
            // 误差不变了，分组完成  
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
