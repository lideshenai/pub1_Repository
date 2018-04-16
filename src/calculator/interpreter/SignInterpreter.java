package calculator.interpreter;

import calculator.data.MathList;
import calculator.sign.Sign;
import calculator.words.Word;

public interface SignInterpreter {
	
	MathList<Sign> interpreter(Word word);
}
