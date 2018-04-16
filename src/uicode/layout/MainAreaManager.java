package uicode.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/***
 * 一个矩形管理区域,用于自动单行布局一部分控件
 * @author zhyu
 * @createdtime 2018年2月11日下午7:50:25
 */
public class MainAreaManager {
	/***
	 * 初始位置
	 * 宽
	 * 长
	 */
	private Point headPoint;
	private int width;
	private int height;
	
	/****
	 * 是否超出边界
	 * 真实宽
	 * 真实长
	 */
	private boolean overBound;
	private int rW;
	private int rH;
	
	private int spacing;
	private int padding;
	private int leftPadding;
	private int rightPadding;
	private int topMargin;
	private int bottomMargin;
	
	private int sH;
	private int sW;
	private List<Control> controlList = new ArrayList<>();
	
	public boolean addControl(Control control, int width, int height) {
		int total = getTotelHeight();
		if(total + width + padding * 2 > sW) 
			return false;
		control.setSize(width, height);
		controlList.add(control);
		return true;
	}
	
	public List<Control> getControlList() {
		return controlList;
	}

	public void compute() {
		index = 0;
		int leftPadding = 0;
		int rightPadding = 0;
		if(padding != 0 ) 
			leftPadding = rightPadding = padding;
		if(this.leftPadding != 0)
			leftPadding = this.leftPadding;
		if(this.rightPadding != 0)
			rightPadding =this.rightPadding;
		int spacing = (width - getTotelWidth() - leftPadding - rightPadding - 14)/ 
				(controlList.size() - 1);
		index = leftPadding;
		controlList.stream().forEach( x -> {
			int w = x.getSize().x;
			int h = x.getSize().y;
			if(x == controlList.get(0)) {
				x.setBounds(index , height + topMargin, w, h);
			} else {
				x.setBounds(index + spacing - 3 , height + topMargin, w, h);
			}
			index += w + spacing;
		});
	}
	
	private int index = 0;
	private int getTotelWidth() {
		index = 0;
		controlList.stream().forEach(x -> {index += x.getSize().x;});
		return index;
	}
	private int getTotelHeight() {
		index = 0;
		controlList.stream().forEach(x -> {index += x.getSize().y;});
		return index;
	}
	public boolean isOverBound() {
		return overBound;
	}

	public Point getHeadPoint() {
		return headPoint;
	}

	public void setHeadPoint(Point headPoint) {
		this.headPoint = headPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getrW() {
		return rW;
	}

	public void setrW(int rW) {
		this.rW = rW;
	}

	public int getrH() {
		return rH;
	}

	public void setrH(int rH) {
		this.rH = rH;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public int getLeftPadding() {
		return leftPadding;
	}

	public void setLeftPadding(int leftPadding) {
		this.leftPadding = leftPadding;
	}

	public int getRightPadding() {
		return rightPadding;
	}

	public void setRightPadding(int rightPadding) {
		this.rightPadding = rightPadding;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public MainAreaManager(Shell shell,int x, int y, int width, int height) {
		headPoint = new Point(x, y);
		this.width = width;
		this.height = height;
		Rectangle re = shell.getBounds();
		sH = re.height;
		sW = re.width;
	}
	
	/***
	 * 返回下一个外部组件将开始的Y坐标
	 * @return
	 */
	public int getRestStartY() {
		if(overBound) {
			return sH - rH - headPoint.y -topMargin - bottomMargin;
		}
		return sH - height - headPoint.y - topMargin -bottomMargin;
	}
}
