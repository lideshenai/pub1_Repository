package uicode.view;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import bean.Code;
import dao.CodeDao;
import uicode.Controller;
import uicode.view.table.MyActionGroup;
import uicode.view.table.TableViewerContentProvider;
import uicode.view.table.TableViewerLabelProvider;

public class SelectView implements UiCodeView {
	CodeDao codeDao;
	Text selectText;
	private TableViewer tableViewer;
	private Table table;
	private String currentSelected;
	public SelectView(CodeDao codeDao, Text selectText) {
		this.codeDao = codeDao;
		this.selectText = selectText;
	}
	public SelectView(CodeDao codeDao, String currentSelected) {
		this.codeDao = codeDao;
		this.currentSelected = currentSelected;
	}
	@Override
	public void show(Display display, Shell shell) {
		if(selectText != null) {
			currentSelected = selectText.getText();
		}
		List<Code> codeList = codeDao.getList(currentSelected);
		Controller.setCurrentSelected(currentSelected);
		createTableViewer(shell);
		tableViewer.setContentProvider(new TableViewerContentProvider());
		tableViewer.setLabelProvider(new TableViewerLabelProvider());
		tableViewer.setInput(codeList);
		addListener();
		MyActionGroup actionGroup = new MyActionGroup(tableViewer,codeDao,selectText);
		actionGroup.fillContextMenu(new MenuManager());
	}
	private void createTableViewer(Composite parent) {
		/***
		 * ��һ��;����һ��TableViewer����,
		 * ͬʱ�ڹ��췽���ж�������ʽ,���óɿ��Ե�ѡ
		 * �ӹ�����:SWT.H.SCROLL ˮƽ,��ֱ������:SWT.V.SCROLL
		 * �߿�SWT.BORDER , ����ѡ��SWT.FULL.SELECTION
		 */
		tableViewer = new TableViewer(parent, SWT.SINGLE|SWT.BORDER|SWT.FULL_SELECTION
				/*| SWT.V_SCROLL | SWT.FULL_SELECTION*/);
		/**
		 * �ڶ� ��:ͨ��tableViewer�е�table���䲼��
		 */
		table = tableViewer.getTable();
		table.setBounds(Controller.PARDDING, Controller.VIEW_HEAD + 15, 
				Controller.WINDOWS_WIDTH - Controller.PARDDING * 3 , 400);
		table.setHeaderVisible(true);//���ñ�ͷ
		table.setLinesVisible(true);//���ñ����
		
		/***
		 * ������:����TableViewer�е���
		 */
		TableColumn tb1 = new TableColumn(table, SWT.NONE);
		tb1.setText("ID");
		tb1.setResizable(false);
		tb1.setMoveable(false);
		int w1 = 28;
		tb1.setWidth(w1);
		
		TableColumn tb2 = new TableColumn(table, SWT.NONE);
		tb2.setText("��ǩ");
		tb2.setMoveable(false);
		int w2 = 80;
		tb2.setWidth(w2);
		
		
		//tLayout.addColumnData(new ColumnWeightData(0));
		TableColumn tb3 = new TableColumn(table, SWT.NONE);
		tb3.setText("����");
		tb3.setMoveable(false);
		int w3 = 120;
		tb3.setWidth(w3);
		
		//tLayout.addColumnData(new ColumnWeightData(0));
		TableColumn tb4 = new TableColumn(table, SWT.NONE);
		tb4.setText("����ʱ��");
		tb4.setMoveable(false);
		int w4 = 105;
		tb4.setWidth(w4);
		tb4.setResizable(false);
		
		TableColumn tb5 = new TableColumn(table, SWT.NONE);
		tb5.setText("����");
		tb5.setMoveable(false);
		tb5.setWidth(Controller.WINDOWS_WIDTH - 
				Controller.PARDDING * 3 - w1 - w2 - w3 -w4 - 4);
		tb5.setResizable(false);
		
	}
	public void addListener() {
		//����ѡ���¼�������
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Code code = (Code) selection.getFirstElement();
				Display.getCurrent().setData("selected", code);
			}
		});
	}
	@Override
	public void closeView() {
		table.removeAll();
		table.dispose();
		Display.getCurrent().getActiveShell().redraw();
	}
}
