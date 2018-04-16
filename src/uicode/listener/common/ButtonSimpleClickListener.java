package uicode.listener.common;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class ButtonSimpleClickListener implements MouseListener{
	private boolean mouseDoubleClick = false;

	public void mouseDoubleClick(MouseEvent e) {
		mouseDoubleClick = true;
	}

	public void mouseDown(MouseEvent e) {
		Display display = Display.getDefault();
		mouseDoubleClick = false;
		display.timerExec(display.getDoubleClickTime(), new Runnable() {
			public void run() {
				if (mouseDoubleClick) {
					return;
				} else {
					Shell shell = display.getShells()[0];
					service(display, shell,e);
				}
			}
		});
	}

	public void mouseUp(MouseEvent e) {
	}
	public abstract void service(Display display, Shell shell,MouseEvent e);
}
