package com.gradient.neutral;

import java.util.ArrayList;
import java.util.List;

public class AnnClassifier {

	//����һ�����飬ģ������
	private double trainData[][] = 
		{
			{0.4,-0.7,0.1},
			{0.3,-0.5,0.05},
			{0.6,0.1,0.3},
			{0.2,0.4,0.25},
			{0.1,-0.2,0.12}
		};
	
	//����ÿ��������Ԫ����
	private int mInputCount;
    private int mHiddenCount;
    private int mOutputCount;
    //ÿ�����ϵ���Ԫ
    private List<NetworkNode> mInputNodes;
    private List<NetworkNode> mHiddenNodes;
    private List<NetworkNode> mOutputNodes;
    //����㵽���ز��Ȩ��
    private double[][] mInputHiddenWeight;
    //���ز㵽������Ȩ��
    private double[][] mHiddenOutputWeight;
    //���췽������ʼ������
    public AnnClassifier(int inputCount, int hiddenCount, int outputCount)
	{
		mInputCount = inputCount;
		mHiddenCount = hiddenCount;
		mOutputCount = outputCount;
		mInputNodes = new ArrayList<NetworkNode>();
		mHiddenNodes = new ArrayList<NetworkNode>();
		mOutputNodes = new ArrayList<NetworkNode>();
		mInputHiddenWeight = new double[mInputCount][mHiddenCount];
		mHiddenOutputWeight = new double[mHiddenCount][mOutputCount];
	}
    
    /**
	 * ��ʼ��
	 */
	private void reset()
	{
		mInputNodes.clear();
		mHiddenNodes.clear();
		mOutputNodes.clear();
		for (int i = 0; i < mInputCount; i++)
			mInputNodes.add(new NetworkNode(NetworkNode.TYPE_INPUT));
		for (int i = 0; i < mHiddenCount; i++)
			mHiddenNodes.add(new NetworkNode(NetworkNode.TYPE_HIDDEN));
		for (int i = 0; i < mOutputCount; i++)
			mOutputNodes.add(new NetworkNode(NetworkNode.TYPE_OUTPUT));
		for (int i = 0; i < mInputCount; i++)
			for (int j = 0; j < mHiddenCount; j++)
				mInputHiddenWeight[i][j] = (double) (Math.random() * 0.1);
		for (int i = 0; i < mHiddenCount; i++)
			for (int j = 0; j < mOutputCount; j++)
				mHiddenOutputWeight[i][j] = (double) (Math.random() * 0.1);
	}
    
    /*count ��ʾ��ǰ���������еĵڼ�������
     * 
     */    
	private void forward(int count){
    	//�����
    	for (int i = 0; i < mInputCount; i++) {
			mInputNodes.get(i).setForwardInputValue(trainData[count][i]);
		}
    	//���ز�
    	for (int i = 0; i < mHiddenCount; i++) {
    		double temp = 0;
			for (int j = 0; j < mInputCount; j++) {
				temp += mInputHiddenWeight[j][i]
						* mInputNodes.get(j).getForwardOutputValue();
			mHiddenNodes.get(i).setForwardInputValue(temp);
			}
		}
    	//�����
    	for (int i = 0; i < mOutputCount; i++) {
    		double temp = 0;
			for (int j = 0; j < mHiddenCount; j++) {
				temp += mHiddenOutputWeight[j][i]
						* mHiddenNodes.get(j).getForwardOutputValue();
			mOutputNodes.get(i).setForwardInputValue(temp);
			}
		}
    }
    
    //���򴫲�������㲻�ò��뷴����㣩
    public void backward(int count){
    	//�����
    	for (int j = 0; j < mOutputCount; j++)
		{
			mOutputNodes.get(j).setBackwardInputValue(
					mOutputNodes.get(j).getForwardOutputValue()-trainData[count][2]);
		}
    	//���ز�
    	for (int j = 0; j < mHiddenCount; j++)
		{
			float temp = 0;
			for (int k = 0; k < mOutputCount; k++)
				temp += mHiddenOutputWeight[j][k]
						* mOutputNodes.get(k).getBackwardOutputValue();
			mHiddenNodes.get(j).setBackwardInputValue(temp);
		}
    }
    
    /**
	 * ����Ȩ�أ�ÿ��Ȩ�ص��ݶȶ���������������ǰһ��ڵ������������������ĺ�һ��ķ��򴫲������
	 * eta Ϊѧϰ��
	 * 
	 */
	private void updateWeights(double eta)
	{
		// ��������㵽�����Ȩ�ؾ���
		for (int i = 0; i < mInputCount; i++)
			for (int j = 0; j < mHiddenCount; j++)
				mInputHiddenWeight[i][j] += eta
						* mInputNodes.get(i).getForwardOutputValue()
						* mHiddenNodes.get(j).getBackwardOutputValue();
		// �������㵽������Ȩ�ؾ���
		for (int i = 0; i < mHiddenCount; i++)
			for (int j = 0; j < mOutputCount; j++)
				mHiddenOutputWeight[i][j] += eta
						* mHiddenNodes.get(i).getForwardOutputValue()
						* mOutputNodes.get(j).getBackwardOutputValue();
	}
	
	//ѵ������
	public void train(){
		reset();
		int iteration = 0;
		//����100��
		while(iteration < 100){
			System.out.println(iteration);
			for (int i = 0; i < trainData.length; i++) {
				forward(i);
				backward(i);
				updateWeights(0.6);				
			}	
			iteration++;
		}
		//����㵽���ز��Ȩ�����
		System.out.println("------------"+"����㵽���ز��Ȩ��"+"------------");
		for (int i = 0; i < mInputCount; i++)
			for (int j = 0; j < mHiddenCount; j++)
				System.out.println(mInputHiddenWeight[i][j]);
		//���ز㵽������Ȩ�����
		System.out.println("------------"+"���ز㵽������Ȩ��"+"------------");
		for (int i = 0; i < mHiddenCount; i++)
			for (int j = 0; j < mOutputCount; j++)
				System.out.println(mHiddenOutputWeight[i][j]);
	}

	public static void main(String[] args) {
		AnnClassifier ac = new AnnClassifier(2, 2, 1);
		ac.train();
	}
}
