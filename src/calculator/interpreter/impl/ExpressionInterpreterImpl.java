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
				System.out.println("当前sign " + sign);
				if(sign instanceof UnTerminalSign) {
					if(cur == null) {
						cur = (UnTerminalSign)sign;
					} else {
						nex = (UnTerminalSign) sign;
						System.out.println("碰到下一个非终结字符,进入优先级比较 bef : " + bef + "   "
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
			System.out.println("已到队列末尾,开始检验末尾字符:" + list.getLast());
			System.out.println(list);
			Sign l = list.getLast();
			if(!(l instanceof Num) )
				throw new InterpreterSyatnxException("语法错误,表达式末尾只能已数字括号结束");
			/*if(l instanceof Expression){
				System.out.println("发现末尾符号是表达式,继续下次循环");
				continue;
			}*/
			System.out.println("在末尾发现数字.可以计算当前数据 bef : " + bef + "    lat : " + lat + "  cur:" 
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
			System.out.println("运算符优先级不够,开始查看是否是成对符号");
			if(nex instanceof Doubled) {
				System.out.println("是成对符号,寻找对应符号");
				dealDoubled();
				
			} else {
				cur = nex;
				bef = lat;
				nex = null;
				lat =null;
				System.out.println("不是成对符号,当前数据改变  bef : " + bef + "  cur : " + cur + "  lat : " + lat);
			}
		}else {
			System.out.println("cur >= nex  cur:" + cur + " nex:" + nex + " 开始计算当前数据 bef : " + bef + "    lat : " + lat);
			replaceList();
		}
	}
	private void dealDoubled() throws InterpreterSyatnxException {
		Doubled left = (Doubled) nex;
		int prority = left.getDoubleCount();
		if((prority & 1) != 0)
			throw new InterpreterSyatnxException("左右括号顺序错误");
		if(expression.getPrority() > prority)
			throw new InterpreterSyatnxException("大小括号包含关系错误");
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
			throw new InterpreterSyatnxException("找不到右括号");
		System.out.println("找到右括号;");
		MathList<Sign> sub = list.subList(left, right);
		System.out.println("符号容器已提取" + sub);
		Expression son = new MathExpression(new ExpressionInterpreterImpl(), sub);
		list.replaceNodeListToElement(sub, (Num)son);
		son.setPrority(prority);
		list.iteratorRePos(it, (Sign) son);
		System.out.println("子Expression对象已生成,其容器队列为:" + son + " 当前字符容器大小为:" + list.getSize());
		if(it.hasNext()){
			it.next();
			System.out.println("迭代器游标移动到一下个位置");
		}else {
			System.out.println("到最后一个位置准备,迭代器游标位置不移动");
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
	//	System.out.println("计算完毕,获取子字符容器完毕");
		boolean flag = list.replaceNodeListToElement(sub, sign);
		if(!flag) {
			System.out.println("sub :" + sub + "  sign :" + sign);
			throw new RuntimeException("替换异常");
		}else {
			//替换成功后的操作.
			System.out.println("替换成功,开始跟新迭代器.");
			System.out.println("computed :  bef = " + bef + "  cur = " + cur + "   lat = " + lat + " = " + r1 + "  size :" + list.getSize());
			cur = nex;
			bef = sign;
			lat = null;
			nex = null;
			list.iteratorRePos(it,sign);
			System.out.println("对迭代器跟新数据完毕");
			if(it.hasNext()){
				it.next();
				System.out.println("迭代器游标移动到一下个位置");
			}else {
				System.out.println("到最后一个位置准备");
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
