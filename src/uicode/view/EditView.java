package uicode.view;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import bean.Code;
import dao.CodeDao;
import uicode.Controller;
import uicode.listener.view.edit.EditViewCancelListener;

public class EditView implements UiCodeView{
	private Font font;
	private CodeDao codeDao;
	private Label labelLabel;
	private Text labelText;
	private Label recordLabel;
	private Text recordText;
	private Label codeLabel;
	private Text codeText;
	private Button confirm;
	private Button cancel;
	private String name;
	private Text selectText;
	public EditView(CodeDao codeDao,String name,Text selectText) {
		this.codeDao = codeDao;
		controls = new ArrayList<>();
		this.name = name;
		this.selectText = selectText;
	}
	private List<Control> controls;
	public List<Control> getControls() {
		return controls;
	}
	@Override
	public void show(Display display, Shell shell) {
		labelLabel = new Label(shell, SWT.LEFT_TO_RIGHT|SWT.NONE);
		controls.add(labelLabel);
		labelLabel.setText("快速查找标签");
		labelLabel.setBounds(Controller.PARDDING, Controller.VIEW_HEAD + 15, 119, Controller.COMMON_HEIGHT);
		font = new Font(display, "华文行楷", 14, SWT.NO);
		labelLabel.setFont(font);
		labelLabel.setBackground(Controller.COLOR);
		
		labelText = new Text(shell, SWT.LEFT);
		controls.add(labelText);
		int ltX = Controller.PARDDING + 2 + 120;
		int ltY = Controller.VIEW_HEAD + 15;
		labelText.setBounds(ltX, ltY,Controller.WINDOWS_WIDTH - ltX - Controller.PARDDING * 2, Controller.COMMON_HEIGHT);
		labelText.setFocus();
		
		recordLabel = new Label(shell,SWT.LEFT_TO_RIGHT|SWT.NONE);
		controls.add(recordLabel);
		recordLabel.setFont(font);
		recordLabel.setText("描       述");
		recordLabel.setBackground(Controller.COLOR);
		recordLabel.setBounds(Controller.PARDDING, ltY + Controller.COMMON_HEIGHT + Controller.SPACING, 120, Controller.COMMON_HEIGHT);
		
		recordText = new Text(shell, SWT.MULTI|SWT.WRAP);
		controls.add(recordText);
		recordText.setBounds(Controller.PARDDING, ltY + Controller.COMMON_HEIGHT * 2 + Controller.SPACING, 
				Controller.WINDOWS_WIDTH - 3 * Controller.PARDDING ,	100);
		
		codeLabel = new Label(shell,SWT.LEFT_TO_RIGHT|SWT.NONE);
		controls.add(codeLabel);
		codeLabel.setFont(font);
		codeLabel.setText("代       码");
		codeLabel.setBackground(Controller.COLOR);
		codeLabel.setBounds(Controller.PARDDING, ltY + Controller.COMMON_HEIGHT * 2 + Controller.SPACING * 2 + 100, 120, Controller.COMMON_HEIGHT);
		
		codeText = new Text(shell, SWT.MULTI|SWT.WRAP);
		controls.add(codeText);
		int cY = ltY + Controller.COMMON_HEIGHT * 3 + 100 + Controller.SPACING * 2;
		codeText.setBounds(Controller.PARDDING,cY, 
				Controller.WINDOWS_WIDTH - 3 * Controller.PARDDING ,	210);
		
		confirm = new Button(shell, SWT.NONE|SWT.FLAT);
		controls.add(confirm);
		confirm.setFont(font);
		confirm.setText("确认");
		
		cancel = new Button(shell,  SWT.NONE|SWT.FLAT);
		controls.add(cancel);
		cancel.setFont(font);
		cancel.setText("取消");
		int cX = (Controller.WINDOWS_WIDTH -  Controller.MAIN_WIDTH * 2) / 3;
		confirm.setBounds(cX,
				Controller.WINDOWS_HEIGHT - Controller.PARDDING * 4, Controller.MAIN_WIDTH, Controller.MAIN_HEIGHT);
		cancel.setBounds(cX + Controller.MAIN_WIDTH + cX,Controller.WINDOWS_HEIGHT - Controller.PARDDING * 4, 
				Controller.MAIN_WIDTH, Controller.MAIN_HEIGHT);
		cancel.addMouseListener(new EditViewCancelListener(this));
		Method method = null;
		if(name.equals("update")) {
			Code code = (Code) display.getData("update");
			if(code == null) {
				MessageBox messageBox = new MessageBox(shell,SWT.APPLICATION_MODAL | SWT.CANCEL);
				messageBox.setMessage("程序出错了");
				messageBox.setText("警告");
				messageBox.open();
				return;
			}
			codeText.setText(code.getCode());
			recordText.setText(code.getRecode());
			labelText.setText(code.getLabel());
		}
		try {
			method = codeDao.getClass().getDeclaredMethod(name,Code.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		confirm.addMouseListener(new ConfirmListener(method,codeDao,this));
	}
	public Text getLabelText() {
		return labelText;
	}
	public void setLabelText(Text labelText) {
		this.labelText = labelText;
	}
	public Text getRecordText() {
		return recordText;
	}
	public void setRecordText(Text recordText) {
		this.recordText = recordText;
	}
	public Text getCodeText() {
		return codeText;
	}
	public void setCodeText(Text codeText) {
		this.codeText = codeText;
	}
	@Override
	public void closeView() {
		for (Control control : controls) {
			control.dispose();
		}
		if(name.equals("update")) {
			Display display = Display.getCurrent();
			UiCodeView view = Controller.getCurrentView();
			view = new SelectView(codeDao, selectText);
			Controller.setCurrentView(view);
			view.show(display, display.getActiveShell());
		}
		Display.getCurrent().getActiveShell().redraw();
	}

}
