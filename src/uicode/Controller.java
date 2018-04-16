package uicode;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.CodeDao;
import dao.CodeDaoImpl;
import uicode.layout.MainAreaManager;
import uicode.listener.AddButtonMouseListener;
import uicode.listener.CalculatorListener;
import uicode.listener.SelectButtonMouseListener;
import uicode.listener.SelectLabelKeyListener;
import uicode.view.UiCodeView;

public class Controller {
	private CodeDao codeDao;
	
	public static final int WINDOWS_HEIGHT = 600;
	public static final int WINDOWS_WIDTH = 480;
	public static final int MAIN_WIDTH = 50;
	public static final int MAIN_HEIGHT = 30;
	public static final int SELECT_TEXT_WIDTH = 200;
	public static final int COMMON_HEIGHT = 22;
	
	/*** ��� **/
	public static final int SPACING = 20;
	/*** �ڱ߾� ***/
	public static final int PARDDING = 20;
	/** �б߾� **/
	public static final int MARGINE = 10;
	public static final Color COLOR = new Color(Display.getCurrent(), 199, 237, 204);

	public static final int VIEW_HEAD = COMMON_HEIGHT * 2 + MARGINE * 2;
	protected Shell shell;

	
	private  static  UiCodeView currentView;
	private static String currentSelected;
	
	public static String getCurrentSelected() {
		return currentSelected;
	}

	public static void setCurrentSelected(String currentSelected) {
		Controller.currentSelected = currentSelected;
	}

	public static UiCodeView getCurrentView() {
		return currentView;
	}

	public static void setCurrentView(UiCodeView currentView) {
		Controller.currentView = currentView;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Controller window = new Controller();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void showMainUI(Composite parent, Font font) {
		try {
			codeDao = new CodeDaoImpl();
		} catch (SQLException e) {
			MessageBox messageBox = new MessageBox(shell,SWT.APPLICATION_MODAL | SWT.OK);
			messageBox.setText("����");
			messageBox.setMessage("���ݿ�����ʧ��\n" + e.getMessage());
			if(messageBox.open() == SWT.OK) {
				System.exit(1);
			}
		}
		MainAreaManager areaM = new MainAreaManager(shell, 0, 20, WINDOWS_WIDTH, 20);
		areaM.setBottomMargin(5);
		areaM.setTopMargin(5);
		areaM.setPadding(10);
		
		Text selectText = new Text(shell, SWT.LEFT | SWT.BORDER);
		areaM.addControl(selectText, SELECT_TEXT_WIDTH, MAIN_HEIGHT);
		
		Button selectButton = new Button(shell, SWT.NONE | SWT.FLAT);
		selectButton.setText("����");
		selectButton.setFont(font);
		areaM.addControl(selectButton, MAIN_WIDTH, MAIN_HEIGHT);
			
		Button addButton = new Button(shell, SWT.NONE | SWT.FLAT);
		addButton.setText("����");
		addButton.setFont(font);
		areaM.addControl(addButton, MAIN_WIDTH, MAIN_HEIGHT);
		
		Button deleteButton = new Button(shell, SWT.NONE | SWT.FLAT);
		deleteButton.setText("ɾ��");
		deleteButton.setFont(font);
		areaM.addControl(deleteButton, MAIN_WIDTH, MAIN_HEIGHT);
		
		Button calculatorButton = new Button(shell, SWT.NONE | SWT.FLAT);
		calculatorButton.setText("������");
		calculatorButton.setFont(font);
		areaM.addControl(calculatorButton,(int)(MAIN_WIDTH * 1.3), MAIN_HEIGHT);
		
		areaM.compute();
		
		addButton.addMouseListener(new AddButtonMouseListener(codeDao,selectText));
		selectButton.addMouseListener(new SelectButtonMouseListener(selectText,codeDao));
		deleteButton.addMouseListener(new DeleteMouseListener(codeDao));
		selectText.addKeyListener(new SelectLabelKeyListener(selectText, shell, codeDao));
		calculatorButton.addMouseListener(new CalculatorListener());
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Label title = new Label(shell, SWT.NO_TRIM);
		title.setText("UICODE");
		Font titleFont = new Font(Display.getCurrent(), "����", 15, SWT.BOLD);
		title.setFont(titleFont);
		int selectLabel_width = 69;
		int selectLabel_height = 20;
		title.setBounds(WINDOWS_WIDTH / 2 - selectLabel_width / 2, 0, selectLabel_width, selectLabel_height);
		Font mainFont = new Font(Display.getCurrent(), "����", 13, SWT.NO);
		showMainUI(shell, mainFont);
		shell.setBackground(COLOR);
		shell.open();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		shell.setLocation(x, y);
		//һ��ʱ��ת��ѭ��
		       while(!shell.isDisposed()){//���������û�йر�,��һֱѭ��
			           //dispose ��"����,����,�ٵ�"����˼
			           if(!display.readAndDispatch()){//// ���display��æ
			                display.sleep();// display����
			            }
		       }
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		shell.setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		shell.setText("UICODE");
	}

}
