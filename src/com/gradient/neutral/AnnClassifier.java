package com.gradient.neutral;

import java.util.ArrayList;
import java.util.List;

public class AnnClassifier {

	//定义一个数组，模拟数据
	private double trainData[][] = 
		{
			{0.4,-0.7,0.1},
			{0.3,-0.5,0.05},
			{0.6,0.1,0.3},
			{0.2,0.4,0.25},
			{0.1,-0.2,0.12}
		};
	
	//定义每个层上神经元数量
	private int mInputCount;
    private int mHiddenCount;
    private int mOutputCount;
    //每个层上的神经元
    private List<NetworkNode> mInputNodes;
    private List<NetworkNode> mHiddenNodes;
    private List<NetworkNode> mOutputNodes;
    //输入层到隐藏层的权重
    private double[][] mInputHiddenWeight;
    //隐藏层到输出层的权重
    private double[][] mHiddenOutputWeight;
    //构造方法，初始化参数
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
	 * 初始化
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
    
    /*count 表示当前属于数组中的第几条数据
     * 
     */    
	private void forward(int count){
    	//输入层
    	for (int i = 0; i < mInputCount; i++) {
			mInputNodes.get(i).setForwardInputValue(trainData[count][i]);
		}
    	//隐藏层
    	for (int i = 0; i < mHiddenCount; i++) {
    		double temp = 0;
			for (int j = 0; j < mInputCount; j++) {
				temp += mInputHiddenWeight[j][i]
						* mInputNodes.get(j).getForwardOutputValue();
			mHiddenNodes.get(i).setForwardInputValue(temp);
			}
		}
    	//输出层
    	for (int i = 0; i < mOutputCount; i++) {
    		double temp = 0;
			for (int j = 0; j < mHiddenCount; j++) {
				temp += mHiddenOutputWeight[j][i]
						* mHiddenNodes.get(j).getForwardOutputValue();
			mOutputNodes.get(i).setForwardInputValue(temp);
			}
		}
    }
    
    //方向传播（输入层不用参与反向计算）
    public void backward(int count){
    	//输出层
    	for (int j = 0; j < mOutputCount; j++)
		{
			mOutputNodes.get(j).setBackwardInputValue(
					mOutputNodes.get(j).getForwardOutputValue()-trainData[count][2]);
		}
    	//隐藏层
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
	 * 更新权重，每个权重的梯度都等于与其相连的前一层节点的输出乘以与其相连的后一层的反向传播的输出
	 * eta 为学习率
	 * 
	 */
	private void updateWeights(double eta)
	{
		// 更新输入层到隐层的权重矩阵
		for (int i = 0; i < mInputCount; i++)
			for (int j = 0; j < mHiddenCount; j++)
				mInputHiddenWeight[i][j] += eta
						* mInputNodes.get(i).getForwardOutputValue()
						* mHiddenNodes.get(j).getBackwardOutputValue();
		// 更新隐层到输出层的权重矩阵
		for (int i = 0; i < mHiddenCount; i++)
			for (int j = 0; j < mOutputCount; j++)
				mHiddenOutputWeight[i][j] += eta
						* mHiddenNodes.get(i).getForwardOutputValue()
						* mOutputNodes.get(j).getBackwardOutputValue();
	}
	
	//训练数据
	public void train(){
		reset();
		int iteration = 0;
		//迭代100次
		while(iteration < 100){
			System.out.println(iteration);
			for (int i = 0; i < trainData.length; i++) {
				forward(i);
				backward(i);
				updateWeights(0.6);				
			}	
			iteration++;
		}
		//输入层到隐藏层的权重输出
		System.out.println("------------"+"输入层到隐藏层的权重"+"------------");
		for (int i = 0; i < mInputCount; i++)
			for (int j = 0; j < mHiddenCount; j++)
				System.out.println(mInputHiddenWeight[i][j]);
		//隐藏层到输出层的权重输出
		System.out.println("------------"+"隐藏层到输出层的权重"+"------------");
		for (int i = 0; i < mHiddenCount; i++)
			for (int j = 0; j < mOutputCount; j++)
				System.out.println(mHiddenOutputWeight[i][j]);
	}

	public static void main(String[] args) {
		AnnClassifier ac = new AnnClassifier(2, 2, 1);
		ac.train();
	}
}
