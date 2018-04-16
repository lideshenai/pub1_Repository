package calculator.interpreter.impl;

import java.util.Iterator;

import calculator.data.DoubleUtil;
import calculator.data.MathList;
import calculator.interpreter.SignInterpreter;
import calculator.interpreter.SignManager;
import calculator.sign.Sign;
import calculator.sign.impl.Num;
import calculator.words.Word;

public class SignInterpreterImpl implements SignInterpreter {

	private MathList<Sign> list;
	private SignManager signManager;

	public SignInterpreterImpl(SignManager signManager) {
		list = new MathList<>();
		this.signManager = signManager;
	}

	private void combine(int pos, int length) {
		if (length <= 1)
			return;
		System.out.println("pos " + pos + " length " + length);
		MathList<Sign> sub = list.subNodeList(pos, length);
		System.out.println(sub);
		String s = "";
		for (Sign sign : sub) {
			// if(sign instanceof Num) {
			s = s + DoubleUtil.getValidValue((double) sign.getSign());
			// } else {
			// System.out.println(sign.getSign());
			// s = s + sign;
			// }
		}
		// System.out.println(s);
		double value = Double.valueOf(s);

		Num num = new Num(value);
		num.setCombined(true);
		// System.out.println("sub = " + sub + " num = " + num);
		list.replaceNodeListToElement(sub, num);
		// System.out.println(list);
	}

	@Override
	public MathList<Sign> interpreter(Word word) {
		putInList(word);
	//	System.out.println(list);
		Iterator<Sign> it = list.iterator();
		int pos = 0;
		int length = 0;
		while (it.hasNext()) {
			Sign sign = it.next();
			pos++;
			//System.out.println("pos:" + pos + " sign:" + sign);
			if (sign instanceof Num) {
				length++;
				//System.out.println("length " + length);
			} else {
				if (length > 0) {
					//System.out.println("a: " + list);
					combine(pos - length, length);
					pos = pos - length + 1;
					length = 0;
				}
			}
			if (sign == list.getLast()) {
			//	System.out.println("b: " + list);
				combine(pos - length + 1, length);
			}
		}

		return list;
	}

	private void putInList(Word word) {
		String unInterpreter = word.getUnInterpreterWords();
		char[] chars = unInterpreter.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c != ' ') {
				Sign sign = signManager.instanceSign(c);
				list.addLast(sign);
			}
		}
	}

}
