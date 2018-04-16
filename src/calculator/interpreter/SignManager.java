package calculator.interpreter;

import calculator.sign.Sign;

public interface SignManager extends Iterable<Sign> {
	
	void registerSign(Sign sign);
	
	Sign instanceSign(char c);
}
