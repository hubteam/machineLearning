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
	
	// �ڵ�ǰ���������ֵ
    private double mForwardInputValue;
    private double mForwardOutputValue;

    // �ڵ㷴���������ֵ
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
     * sigmoid����
     * 1.����㲻��Ҫ����S����
     * 2.���ز�������Ҫ����S����
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
     * log-sigmoid����
     * 
     * @param in
     * @return
     */
    private double logS(double in)
    {
        return (double) (1 / (1 + Math.exp(-in)));
    }
    
    /**
     * log-sigmoid�����ĵ���
     * 
     * @param in
     * @return
     */
    private double logSDerivative(double in)
    {
        return mForwardOutputValue * (1 - mForwardOutputValue) * in;
    }
    
    /**
     * ���򴫲�ʱ��������ĵ���
     * ������ǲв�
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

    //�����д��ݵĲ����Ǽ�Ȩ��
    public void setForwardInputValue(double mInputValue)
    {
        this.mForwardInputValue = mInputValue;
        //�õ�S������ֵ����Ϊǰ������ֵ
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

    //���򴫲��Ĳ����Ǽ�Ȩ�ͣ�ͬʱ���ú�������ֵ
    public void setBackwardInputValue(double mBackwardInputValue)
    {
        this.mBackwardInputValue = mBackwardInputValue;
        //��������ֵΪS����
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
