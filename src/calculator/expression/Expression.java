package calculator.expression;
/***
 * ���ʽ�ӿ�
 * 
 * @author zhyu
 * @createdtime 2018��2��12������12:27:51
 */

import calculator.data.MathList;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.Sign;

public interface Expression {
	/****
	 * ��ӡ�����ŵ�Ԫ��Ϣ
	 */
	void show();
	/***
	 * ��ʾ������
	 * @return
	 * @throws InterpreterSyatnxException 
	 */
	double result() throws InterpreterSyatnxException;
	
	MathList<Sign> getList();

	int getPrority();
	void setPrority(int prority) throws InterpreterSyatnxException;
}
