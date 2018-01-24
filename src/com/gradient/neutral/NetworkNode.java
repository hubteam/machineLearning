package com.gradient.neutral;

public class NetworkNode {

	public static final int TYPE_INPUT = 0;
	public static final int TYPE_HIDDEN = 1;
	public static final int TYPE_OUTPUT = 2;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	// 节点前向输入输出值
    private double mForwardInputValue;
    private double mForwardOutputValue;

    // 节点反向输入输出值
    private double mBackwardInputValue;
    private double mBackwardOutputValue;
	
    public NetworkNode()
    {
    }

    public NetworkNode(int type)
    {
        this.type = type;
    }
    
    /**
     * sigmoid函数
     * 1.输入层不需要计算S函数
     * 2.隐藏层和输出层要计算S函数
     * @param in
     * @return
     */
    private double forwardSigmoid(double in)
    {
        switch (type)
        {   
        case TYPE_INPUT:
            return in;
        case TYPE_HIDDEN:
        case TYPE_OUTPUT:
            return logS(in);
        }
        return 0;
    }
    
    /**
     * log-sigmoid函数
     * 
     * @param in
     * @return
     */
    private double logS(double in)
    {
        return (double) (1 / (1 + Math.exp(-in)));
    }
    
    /**
     * log-sigmoid函数的导数
     * 
     * @param in
     * @return
     */
    private double logSDerivative(double in)
    {
        return mForwardOutputValue * (1 - mForwardOutputValue) * in;
    }
    
    /**
     * 误差反向传播时，激活函数的导数
     * 计算的是残差
     * @param in
     * @return
     */
    private double backwardPropagate(double in)
    {
        switch (type)
        {
        case TYPE_INPUT:
            return in;
        case TYPE_HIDDEN:
        case TYPE_OUTPUT:
            return logSDerivative(in);
        }
        return 0;
    }
    
    public double getForwardInputValue()
    {
        return mForwardInputValue;
    }

    //函数中传递的参数是加权和
    public void setForwardInputValue(double mInputValue)
    {
        this.mForwardInputValue = mInputValue;
        //得到S函数的值，即为前向的输出值
        setForwardOutputValue(mInputValue);
    }

    public double getForwardOutputValue()
    {
        return mForwardOutputValue;
    }

    private void setForwardOutputValue(double mInputValue)
    {
        this.mForwardOutputValue = forwardSigmoid(mInputValue);
    }

    public double getBackwardInputValue()
    {
        return mBackwardInputValue;
    }

    //后向传播的参数是加权和，同时设置后向的输出值
    public void setBackwardInputValue(double mBackwardInputValue)
    {
        this.mBackwardInputValue = mBackwardInputValue;
        //后向的输出值为S导数
        setBackwardOutputValue(mBackwardInputValue);
    }

    public double getBackwardOutputValue()
    {
        return mBackwardOutputValue;
    }

    private void setBackwardOutputValue(double input)
    {
        this.mBackwardOutputValue = backwardPropagate(input);
    }

}
