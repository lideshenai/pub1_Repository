package uicode.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public interface UiCodeView {
	public void show(Display display, Shell shell);
	public void closeView();
}
