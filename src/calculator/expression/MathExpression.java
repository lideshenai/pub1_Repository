package calculator.expression;

import java.util.Iterator;

import calculator.data.MathList;
import calculator.interpreter.ExpressionInterpreter;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.Doubled;
import calculator.sign.Sign;
import calculator.sign.impl.Num;

public class MathExpression extends Num implements Expression{
	private ExpressionInterpreter interpreter;
	private MathList<Sign> list;
	private int prority;
	private Sign left;
	private Sign right;
	public MathExpression(ExpressionInterpreter interpreter, MathList<Sign> list) {
		this.interpreter = interpreter;
		this.list = list;
	}
	@Override
	public boolean isCombined() {
		return true;
	}
	public int getPrority() {
		return prority;
	}
	public void setPrority(int prority) throws InterpreterSyatnxException {
		this.prority = prority;
		if(prority > 0 ){
			Sign sign1 = list.getFirst();
			Sign sign2 = list.getLast();
			System.out.println(list.getPos() + " " + list.getSize());
			if((sign1 instanceof Doubled) && (sign2 instanceof Doubled)) {
				left = sign1;
				right = sign2;
				System.out.println("¿ªÊ¼É¾³ýÀ¨ºÅ" + sign1 + "" + sign2);
				boolean f1 = list.remove(sign1);
				System.out.println(list.getPos() + " " + list.getSize());
				System.out.println(sign2);
				boolean f2 = list.remove(sign2);
				if(f1 && f2){
					System.out.println("À¨ºÅÒÑÉ¾³ý list= " + list);
				}else {
					throw new RuntimeException("À¨ºÅÉ¾³ýÊ§°Ü");
				}
			}else {
				throw new InterpreterSyatnxException("±í´ïÊ½À¨ºÅ²¿·Ö´íÎó");
			}
		}
	}
	@Override
	public void show() {
	}

	public ExpressionInterpreter getInterpreter() {
		return interpreter;
	}
	public MathList<Sign> getList() {
		return list;
	}
	@Override
	public double result() throws InterpreterSyatnxException {
		return interpreter.interpreter(this);
	}
	
	@Override
	public double getValue() throws InterpreterSyatnxException {
		return result();
	}
	@Override
	public boolean isMe(char c) {
		return false;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("E{");
		if(left != null) 
			sb.append(left);
		System.out.println(list.getPos() + " " + list.getSize());
		Iterator<Sign> it = list.iterator();
		while (it.hasNext()) {
			Sign sign = it.next();
			sb.append(sign);
		}
		if(right != null) 
			sb.append(right);
		sb.append("}");
		return sb.toString();
	}
}
