package calculator.expression;
/***
 * 表达式接口
 * 
 * @author zhyu
 * @createdtime 2018年2月12日上午12:27:51
 */

import calculator.data.MathList;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.Sign;

public interface Expression {
	/****
	 * 打印出符号单元信息
	 */
	void show();
	/***
	 * 显示运算结果
	 * @return
	 * @throws InterpreterSyatnxException 
	 */
	double result() throws InterpreterSyatnxException;
	
	MathList<Sign> getList();

	int getPrority();
	void setPrority(int prority) throws InterpreterSyatnxException;
}
