package calculator.interpreter.impl;

import java.util.Iterator;

import calculator.data.MathList;
import calculator.expression.Expression;
import calculator.expression.MathExpression;
import calculator.interpreter.ExpressionInterpreter;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.Doubled;
import calculator.sign.Sign;
import calculator.sign.UnTerminalSign;
import calculator.sign.impl.Num;

public class ExpressionInterpreterImpl implements ExpressionInterpreter{
	private MathList<Sign> list;
	private Sign bef;
	private Sign lat;
	private UnTerminalSign cur;
	private UnTerminalSign nex;
	private Expression expression;
	private Iterator<Sign> it;
	@Override
	public Double interpreter(Expression expression) throws InterpreterSyatnxException {
		list = expression.getList();
		this.expression = expression;
		it = list.iterator();
		System.out.println("list.size = " + list.getSize());
		while(list.getSize() > 1) {
			while(it.hasNext()) {
				//System.out.println( "its pos : " + list.iteratorGetPos(it));
				//System.out.println(bef + " " + cur + " " + lat  + " nex = " + nex);
				Sign sign = it.next();
				System.out.println("��ǰsign " + sign);
				if(sign instanceof UnTerminalSign) {
					if(cur == null) {
						cur = (UnTerminalSign)sign;
					} else {
						nex = (UnTerminalSign) sign;
						System.out.println("������һ�����ս��ַ�,�������ȼ��Ƚ� bef : " + bef + "   "
								+ " lat : " + lat + "  cur:" + cur + "  nex:"  + nex);
						compare();
					}
				}else {
					if(bef == null ) {
						bef = sign;
					}else {
						lat = sign;
					}
				}
			}
			System.out.println("�ѵ�����ĩβ,��ʼ����ĩβ�ַ�:" + list.getLast());
			System.out.println(list);
			Sign l = list.getLast();
			if(!(l instanceof Num) )
				throw new InterpreterSyatnxException("�﷨����,���ʽĩβֻ�����������Ž���");
			/*if(l instanceof Expression){
				System.out.println("����ĩβ�����Ǳ��ʽ,�����´�ѭ��");
				continue;
			}*/
			System.out.println("��ĩβ��������.���Լ��㵱ǰ���� bef : " + bef + "    lat : " + lat + "  cur:" 
					+ cur + "  nex:"  + nex);
			replaceList();
			bef = null;
			lat = null;
			nex = null;
			cur = null;
			list.iteratorRemark(it);
			System.out.println(list);
		//	System.out.println("list.size = " + list.getSize());
		}
		return ((Num)list.getFirst()).getValue();
	}
	private void compare() throws InterpreterSyatnxException {
		System.out.println("cur : " + cur + " nex : " + nex );
		if(nex.getPrority() > cur.getPrority()) {
			System.out.println("��������ȼ�����,��ʼ�鿴�Ƿ��ǳɶԷ���");
			if(nex instanceof Doubled) {
				System.out.println("�ǳɶԷ���,Ѱ�Ҷ�Ӧ����");
				dealDoubled();
				
			} else {
				cur = nex;
				bef = lat;
				nex = null;
				lat =null;
				System.out.println("���ǳɶԷ���,��ǰ���ݸı�  bef : " + bef + "  cur : " + cur + "  lat : " + lat);
			}
		}else {
			System.out.println("cur >= nex  cur:" + cur + " nex:" + nex + " ��ʼ���㵱ǰ���� bef : " + bef + "    lat : " + lat);
			replaceList();
		}
	}
	private void dealDoubled() throws InterpreterSyatnxException {
		Doubled left = (Doubled) nex;
		int prority = left.getDoubleCount();
		if((prority & 1) != 0)
			throw new InterpreterSyatnxException("��������˳�����");
		if(expression.getPrority() > prority)
			throw new InterpreterSyatnxException("��С���Ű�����ϵ����");
		nex = null;
		Doubled right = null;
		while(it.hasNext()) {
			Sign sign = it.next();
			if(sign instanceof Doubled) {
				right = (Doubled) sign;
				if(right.getDoubleCount() == prority + 1) {
					break;
				}else {
					right = null;
				}
			}
		}
		if(right == null)
			throw new InterpreterSyatnxException("�Ҳ���������");
		System.out.println("�ҵ�������;");
		MathList<Sign> sub = list.subList(left, right);
		System.out.println("������������ȡ" + sub);
		Expression son = new MathExpression(new ExpressionInterpreterImpl(), sub);
		list.replaceNodeListToElement(sub, (Num)son);
		son.setPrority(prority);
		list.iteratorRePos(it, (Sign) son);
		System.out.println("��Expression����������,����������Ϊ:" + son + " ��ǰ�ַ�������СΪ:" + list.getSize());
		if(it.hasNext()){
			it.next();
			System.out.println("�������α��ƶ���һ�¸�λ��");
		}else {
			System.out.println("�����һ��λ��׼��,�������α�λ�ò��ƶ�");
		}
		if(bef == null){
			bef = (Sign) son;
		}else {
			lat = (Sign) son;
		}
		System.out.println("bef = " + bef + "  cur = " + cur + "   lat = " + lat + "  size :" + list.getSize());
	}
	private void replaceList() throws InterpreterSyatnxException {
		Double r1 = comput();
		Sign sign = new Num(r1);
		MathList<Sign> sub = getSub();
	//	System.out.println("�������,��ȡ���ַ��������");
		boolean flag = list.replaceNodeListToElement(sub, sign);
		if(!flag) {
			System.out.println("sub :" + sub + "  sign :" + sign);
			throw new RuntimeException("�滻�쳣");
		}else {
			//�滻�ɹ���Ĳ���.
			System.out.println("�滻�ɹ�,��ʼ���µ�����.");
			System.out.println("computed :  bef = " + bef + "  cur = " + cur + "   lat = " + lat + " = " + r1 + "  size :" + list.getSize());
			cur = nex;
			bef = sign;
			lat = null;
			nex = null;
			list.iteratorRePos(it,sign);
			System.out.println("�Ե����������������");
			if(it.hasNext()){
				it.next();
				System.out.println("�������α��ƶ���һ�¸�λ��");
			}else {
				System.out.println("�����һ��λ��׼��");
			}
		}
	}
	private MathList<Sign> getSub() {
		Sign s = null;
		Sign e = null;
		if(lat == null) {
			s = cur;
			e = bef;
		}else {
			s = bef;
			e = lat;
		}
		return list.subNodeList(s, e);
	}
	private Double comput() throws InterpreterSyatnxException {
		return  cur.getArithmetic().compute((Num)bef, (Num)lat);
	}

}
