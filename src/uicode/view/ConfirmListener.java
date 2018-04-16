package uicode.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import bean.Code;
import dao.CodeDao;
import uicode.listener.common.ButtonSimpleClickListener;
import util.ShellUtil;

public class ConfirmListener extends ButtonSimpleClickListener {
	List<Control> controls;
	Method method;
	CodeDao codeDao;
	UiCodeView view;

	public ConfirmListener(Method method, CodeDao codeDao, EditView view) {
		this.controls = view.getControls();
		this.codeDao = codeDao;
		this.method = method;
		this.view = view;
	}

	@Override
	public void service(Display display, Shell shell, MouseEvent e) {
		if (!ShellUtil.confirmAllNotNull(controls)) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.CANCEL);
			messageBox.setMessage("你还有内容未填写完");
			messageBox.setText("警告");
			messageBox.open();
			return;
		}
		EditView viewer = (EditView) view;
		Text codeText = viewer.getCodeText();
		String codes = codeText.getText();
		Text recordText = viewer.getRecordText();
		String recode = recordText.getText();
		Text labelText = viewer.getLabelText();
		String label = labelText.getText();
		Code code = (Code) display.getData("update");
		if (code == null)
			code = new Code();
		code.setCode(codes);
		code.setLabel(label);
		code.setRecode(recode);
		display.setData("update", null);
		try {
			boolean isOk = (boolean) method.invoke(codeDao, code);
			if (isOk) {
				MessageBox box = new MessageBox(shell, SWT.OK);
				box.setMessage("操作成功");
				box.setText("提示");
				if (box.open() == SWT.OK) {
					view.closeView();
				}
				return;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}
