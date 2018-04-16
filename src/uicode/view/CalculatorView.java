package uicode.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import calculator.data.MathList;
import calculator.expression.Expression;
import calculator.expression.MathExpression;
import calculator.interpreter.ExpressionInterpreter;
import calculator.interpreter.SignInterpreter;
import calculator.interpreter.SignManager;
import calculator.interpreter.impl.ExpressionInterpreterImpl;
import calculator.interpreter.impl.SignInterpreterImpl;
import calculator.interpreter.impl.SignManagerImpl;
import calculator.sign.Sign;
import calculator.sign.impl.Devide;
import calculator.sign.impl.Dot;
import calculator.sign.impl.LeftSmallBracket;
import calculator.sign.impl.Minus;
import calculator.sign.impl.Multiply;
import calculator.sign.impl.Num;
import calculator.sign.impl.Plus;
import calculator.sign.impl.RightSmallBracket;
import calculator.words.Word;
import uicode.Controller;

/**
 * 为了训练下 面向对象编程思路
 * 
 * @author zhyu
 * @createdtime 2018年2月11日下午10:32:11
 */
public class CalculatorView implements UiCodeView {
	private Text t1;
	private List<Control> list = new ArrayList<>();

	@Override
	public void show(Display display, Shell shell) {
		Label l1 = new Label(shell, SWT.LEFT_TO_RIGHT | SWT.NONE);
		l1.setText("该模块仅为了练习面向对象编程而编写!请多包涵!");
		Font f1 = new Font(display, "宋体", 8, SWT.BOLD);
		l1.setFont(f1);
		l1.setForeground(new Color(display, new RGB(255, 0, 0)));
		l1.setBounds(Controller.PARDDING, Controller.VIEW_HEAD, 210, 16);
		l1.setBackground(Controller.COLOR);
		list.add(l1);

		Label l2 = new Label(shell, SWT.LEFT_TO_RIGHT | SWT.NONE);
		l2.setText("请输入表达式,只支持四则运算");
		Font f2 = new Font(display, "华文行楷", 19, SWT.NULL);
		l2.setFont(f2);
		l2.setBounds(Controller.PARDDING, Controller.VIEW_HEAD + 20, 340, 30);
		l2.setBackground(Controller.COLOR);
		list.add(l2);

		t1 = new Text(shell, SWT.LEFT_TO_RIGHT | SWT.NONE);
		t1.setFocus();
		t1.setFont(f2);
		t1.setBounds(Controller.PARDDING, Controller.VIEW_HEAD + 20 + 34, 300, 30);
		list.add(t1);

		Label l3 = new Label(shell, SWT.LEFT_TO_RIGHT | SWT.NONE);
		l3.setText("=");
		Font f3 = new Font(display, "黑体", 24, SWT.BOLD);
		l3.setFont(f3);
		l3.setBounds(Controller.PARDDING, Controller.VIEW_HEAD + 20 + 34 + 40, 400, 30);
		l3.setBackground(Controller.COLOR);
		list.add(l3);

		t1.addKeyListener(new CalculatorKeyListener());
	}

	private boolean judge() {
		return false;
	}

	private class CalculatorKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			Shell shell = Display.getCurrent().getActiveShell();
			if (e.keyCode - 3 == java.awt.event.KeyEvent.VK_ENTER) {
				Text text = (Text) list.get(2);
				String label = text.getText().trim();
				if (label.equals("") || judge()) {
					MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.OK);
					messageBox.setText("错误提示");
					messageBox.setMessage("请输入内容后在搜索");
					if (messageBox.open() == SWT.OK) {
						return;
					}
					System.out.println(1);
				}

				Label re = (Label) list.get(3);
				Word word = new Word(t1.getText());
				double result = 0;
				try {
					SignManager signManager = new SignManagerImpl();
					signManager.registerSign(new Plus());
					signManager.registerSign(new Minus());
					signManager.registerSign(new Devide());
					signManager.registerSign(new Multiply());
					signManager.registerSign(new Num());
					signManager.registerSign(new Dot());
					signManager.registerSign(new LeftSmallBracket());
					signManager.registerSign(new RightSmallBracket());
					SignInterpreter signInterpreter = new SignInterpreterImpl(signManager);
					MathList<Sign> list = signInterpreter.interpreter(word);
					System.out.println(list);
					ExpressionInterpreter eInterpreter = new ExpressionInterpreterImpl();
					Expression expression = new MathExpression(eInterpreter, list);
					result = expression.result();
				} catch (Exception e1) {
					MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.OK);
					messageBox.setText("错误提示");
					messageBox.setMessage(e1.getMessage());
					if (messageBox.open() == SWT.OK) {
						return;
					}
				}
				re.setText(String.valueOf(result));
				shell.redraw();

			}
		}

	}

	@Override
	public void closeView() {
		list.stream().forEach(x -> x.dispose());
		Display.getDefault().getActiveShell().redraw();
	}

}
