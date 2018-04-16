package calculator.interpreter.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import calculator.interpreter.SignManager;
import calculator.sign.Sign;
import calculator.sign.impl.Num;

public class SignManagerImpl implements SignManager {

	Set<Sign> signSet;

	public SignManagerImpl() {
		signSet = new HashSet<>();
	}

	@Override
	public Iterator<Sign> iterator() {
		return signSet.iterator();
	}

	@Override
	public void registerSign(Sign sign) {
		signSet.add(sign);
	}

	@Override
	public Sign instanceSign(char c) {
		for (Sign sign : signSet) {
			if (sign.isMe(c)) {
				if (sign instanceof Num) {
					return new Num(Double.valueOf(String.valueOf(c)));
				}
				return sign;
			}
		}
		throw new RuntimeException("¸Ã·ûºÅÃ»ÓÐ×¢²á : '" + c + "'");
	}

}
