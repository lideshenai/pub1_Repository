package calculator.data;

import java.util.Iterator;

/****
 * 专为封装四则运算中符号组的容器 主要提供遍历,添加, 下一个,前一个,删除后pos不变(方便继续向前删除),替换单个,获取子容器,移除子容器. pos
 * 说明,pos的合法大小是[0,size]; 0代表头位(空的) size代表最后一个元素 空 - 头 - -- -- 尾 0 1 ?
 * 
 * 
 * @version 1.0没实现Collection,仅实现Iterable
 * @author zhyu
 * @createdtime 2018年2月12日上午11:49:16
 * @param <E>
 */
public class MathList<E> implements Iterable<E> {

	private int size;
	private Node<E> first;
	private int pos;
	private Node<E> last;
	private Node<E> before;

	public MathList() {
	}

	public MathList(E[] objs) {
		first = new Node<E>(null, objs[0], null);
		size++;
		Node<E> before = first;
		for (int i = 1; i < objs.length; i++) {
			Node<E> node = new Node<E>(before, objs[i], null);
			before.next = node;
			before = node;
			size++;
		}
		last = before;
	}

	public void clear() {
		size = 0;
		pos = 0;
		first = null;
		last = null;
	}

	public boolean contain(E e) {
		Node<E> node = lookUp(e);
		return node == null ? false : true;
	}

	public String toString() {
		if (first == null)
			return "[]";
		StringBuilder sb = new StringBuilder("[");
		Node<E> before = first;
		while (true) {
			sb.append(before.item);
			before = before.next;
			if (before == null)
				break;
			sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean isSubList(MathList<E> list) {
		Node<E> s = list.first;
		Node<E> be = lookUp(s.item);
		if (be == null)
			return false;
		for (int i = 0; i < list.size - 1; i++) {
			if (be.next.item != s.next.item)
				return false;
			s = s.next;
			be = be.next;
		}
		return true;

	}

	/****
	 * 删除指定子容器,先复核,再删除.以元素引用相同为标准
	 * 
	 * @param list
	 * @return
	 */
	public boolean removeList(MathList<E> list) {
		if (!isSubList(list))
			return false;
		Node<E> prev = lookUp(list.first.item).prev;
		Node<E> next = lookUp(list.last.item).next;
		prev.next = next;
		next.prev = prev;
		size -= list.size;
		return true;
	}

	public E getCurrent() {
		return before == null ? null : before.item;
	}

	public int getSize() {
		return size;
	}

	public E getFirst() {
		return first.item;
	}

	public int getPos() {
		return pos;
	}

	public E getLast() {
		return last.item;
	}

	public boolean isLast(E e) {
		return last.item == e;
	}

	public boolean isFirst(E e) {
		return first.item == e;
	}

	/***
	 * 获取子容器,任一没有返回null
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public MathList<E> subNodeList(E start, E end) {
		System.out.println("s = " + start + " e = " + end);
		Node<E> s = lookUp(start);
		Node<E> e = lookUp(end);
		if (s == null || e == null)
			return null;
		Node<E> next = s.next;
		MathList<E> sub = new MathList<>();
		sub.addLast(s.item);
		while (next != e) {
			sub.addLast(next.item);
			next = next.next;
		}
		sub.addLast(e.item);
		remark();
		return sub;
	}

	/**
	 * 根据两个元素获取子容器,包括首尾 子容器与父容器包含相同的元素,但不包含相同的节点
	 * 
	 * @param start
	 * @param end
	 * @return 任一没取到返回null
	 */
	public MathList<E> subList(E start, E end) {
		Node<E> s = lookUp(start);
		Node<E> e = lookUp(end);
		if (s == null || e == null)
			return null;
		MathList<E> sub = new MathList<>();
		if (s == e) {
			sub.addLast(e.item);
			return sub;
		}
		Node<E> next = s.next;
		sub.addLast(start);
		while (next != e) {
			sub.addLast(next.item);
			next = next.next;
		}
		sub.addLast(end);
		remark();
		return sub;
	}

	private boolean isItsNode(Node<E> node) {
		Node<E> before = first;
		while (before != null) {
			if (before == node)
				return true;
			before = before.next;
		}
		return false;
	}

	public boolean replaceNodeListToElement(MathList<E> src, E element) {
		if (element == null)
			return false;
		if (src == null) {
			return false;
		}
		Node<E> s = src.first;
		if (!isItsNode(s)) {
			if (!isSubList(src)) {
				return false;
			}
			s = lookUp(s.item);
		}
		Node<E> e = lookUp(src.last.item);
		Node<E> prev = s.prev;
		Node<E> next = e.next;
		Node<E> node = new Node<E>(prev, element, next);
		if (prev != null) {
			prev.next = node;
		} else {
			first = node;
		}
		if (next != null) {
			next.prev = node;
		} else {
			last = node;
		}
		size = size + 1 - src.size;
		remark();
		return true;
	}

	private Node<E> getNode(int pos) {
		remark();
		Node<E> node = null;
		while (this.pos != pos) {
			node = nextNode();
		}
		return node;
	}

	/***
	 * 包括pos本来位置
	 * 
	 * @param pos
	 * @param length
	 * @return
	 */
	public MathList<E> subNodeList(int pos, int length) {
		if (pos + length > size + 1 || length <= 0)
			return null;
		Node<E> node = getNode(pos);
		MathList<E> list = new MathList<E>();
		for (int i = 0; i < length; i++) {
			list.addLast(node.item);
			node = node.next;
		}
		return list;
	}

	public boolean replaceNodeList(MathList<E> src, MathList<E> replament) {
		if (replament == null || replament.size == 0)
			return false;
		Node<E> s = src.first;
		if (!isItsNode(s)) {
			if (!isSubList(src)) {
				return false;
			}
			s = lookUp(s.item);
		}
		Node<E> e = lookUp(src.last.item);
		Node<E> prev = s.prev;
		Node<E> next = e.next;
		Node<E> rS = replament.first;
		Node<E> rE = replament.last;
		rS.prev = prev;
		rE.next = next;
		if (prev != null)
			prev.next = rS;
		if (next != null)
			next.prev = rE;
		size = size + replament.size - src.size;
		return true;
	}

	public boolean replace(E src, E replament) {
		Node<E> node = lookUp(src);
		if (node == null)
			return false;
		node.item = replament;
		return true;
	}

	public E prev() {
		if (pos > size || pos <= 1)
			throw new RuntimeException("游标越界异常");
		pos--;
		Node<E> node = before.prev;
		before = node;
		return node.item;
	}

	public E next() {
		if (pos == 0) {
			pos = 1;
			before = first;
			return first.item;
		}
		return before.next.item;
	}

	private Node<E> nextNode() {
		if (pos >= size || pos < 0)
			throw new RuntimeException("游标越界异常");
		if (pos == 0) {
			pos++;
			before = first;
			return first;
		}
		pos++;
		Node<E> node = before.next;
		before = node;
		return node;
	}

	/***
	 * 复位
	 */
	public void remark() {
		pos = 0;
		before = null;
	}

	private void remove(Node<E> node) {
		if(node == first){
			first = first.next;
			first.prev = null;
			size--;
			return;
		}
		if(node == last ){
			last = last.prev;
			last.next = null;
			size--;
			return;
		}
		Node<E> before = node.prev;
		Node<E> next = node.next;
		before.next = next;
		next.prev = before;
		size--;
		this.before = next;
	}

	private void checkPos() {
		if (pos == 0) {
			pos = 1;
			before = first;
		}
	}

	public Node<E> lookUp(E e) {
		if(e == null)
			return null;
		remark();
		for(; pos <= size; ){
			checkPos();
			if(before.item == e)
				return before;
			before = before.next;
			pos++;
		}
		return null;
	}

	/***
	 * 删除元素
	 * 
	 * @param e
	 */
	public boolean remove(E e) {
		System.out.println(e);
		Node<E> node = lookUp(e);
		System.out.println(node);
		if (node == null)
			return false;
		remove(node);
		if (node.next == null) {
			last = node.prev;
		}
		return true;
	}


	/**
	 * 在pos后面添加元素,游标位置调整为新元素
	 * 
	 * @param e
	 */
	public void addAfterPosCon(E e) {
		addAfterPos(e);
		pos++;
		before = before == null ? first : before.next;
	}

	/***
	 * 删除下一个元素,游标不变,返回this, 出错返回null
	 * 
	 * @return
	 */
	public MathList<E> remove() {
		if (pos < size && pos >= 0) {
			if (pos == 0) {
				first = first.next;
				first.prev = null;
				size--;
				return this;
			}
			if (pos == size - 1) {
				last = last.prev;
				last.next = null;
				size--;
				return this;
			}
			Node<E> node = before.next;
			remove(node);
			before = before.prev;
			return this;
		}
		return null;
	}

	/**
	 * 在游标后面添加元素,游标不变
	 * 
	 * @param e
	 */
	public void addAfterPos(E e) {
		if (pos < 0 || pos > size)
			throw new RuntimeException("游标位置异常");
		if (pos == 0) {
			Node<E> node = new Node<E>(null, e, first);
			first = node;
		} else {
			Node<E> next = before.next;
			Node<E> node = new Node<E>(before, e, next);
			before.next = node;
			if (next == null) {
				last = node;
				return;
			}
			next.prev = node;
		}
		size++;
	}

	public void addLast(E e) {
		if (first == null) {
			first = new Node<E>(null, e, null);
			last = first;
		} else {
			Node<E> node = new Node<E>(last, e, null);
			last.next = node;
			last = node;
		}
		size++;
	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.item = element;
			this.next = next;
		}

		@Override
		public String toString() {
			String s = "( ";
			String ss = prev == null ? null : prev.item.toString();
			String sss = " ;  " + item + " ;";
			String ssss = next == null ? null : next.item.toString();
			String sssss = " )";
			return s + ss + sss + ssss + sssss;
		}
	}

	public void iteratorRemark(Iterator<E> it) {
		if (it instanceof MathList.MathIterator) {
			MathIterator iterator = (MathList<E>.MathIterator) it;
			iterator.reset();
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new MathIterator(this);
	}

	public int iteratorGetPos(Iterator<E> it) {
		if (it instanceof MathList.MathIterator) {
			MathIterator iterator = (MathList<E>.MathIterator) it;
			return iterator.getPos();
		}
		throw new RuntimeException("非法操作");
	}

	private int getNodePos(Node<E> node) {
		Node<E> b = first;
		if (first == node)
			return 1;
		int i = 1;
		while (b.next != null) {
			i++;
			b = b.next;
			if (b == node)
				return i;
		}
		return -1;
	}

	public void iteratorSetPos(Iterator<E> it, int pos) {
		if (it instanceof MathList.MathIterator) {
			MathIterator iterator = (MathList<E>.MathIterator) it;
			iterator.setPos(pos);
			return;
		}
		throw new RuntimeException("非法操作");
	}

	public void iteratorRePos(Iterator<E> it, E sign) {
		if (it instanceof MathList.MathIterator) {
			MathIterator iterator = (MathList<E>.MathIterator) it;
			iterator.rePos(sign);
			return;
		}
		throw new RuntimeException("非法操作");
	}

	/***
	 * 没元素时,游标为0, 1-first
	 * @author zhyu
	 * @createdtime 2018年2月14日下午9:44:12
	 */
	private class MathIterator implements Iterator<E> {
		private void rePos(E sign) {
			Node<E> node = lookUp(sign);
			int i = getNodePos(node);
			if (i == -1)
				throw new RuntimeException("非法节点");
			this.pos = i;
			showPos();
			current = node;
		}

		private void showPos() {
			System.out.println("pos changed : " + this.pos + "  current : " + current);
		}

		private void setPos(int pos2) {
			if (pos2 < 0 || pos2 > list.size)
				throw new RuntimeException("传入参数异常 : pos = " + pos2 + "   size = " + size);
			if (pos2 == 0) {
				current = null;
				this.pos = 0;
				showPos();
				return;
			}
			if (pos2 == list.size) {
				current = last;
				this.pos = size;
				showPos();
				return;
			}
			while (this.pos != pos2 && this.pos < size) {
				pos++;
				current = current.next;
				showPos();
			}
		}

		private MathList<E> list;
		private int pos;
		private Node<E> current;

		private MathIterator(MathList<E> list) {
			remark();
			this.list = list;
			this.pos = 0;
			this.current = null;
		}

		private void reset() {
			pos = 0;
			current = null;
			showPos();
		}

		private int getPos() {
			return pos;
		}

		@Override
		public boolean hasNext() {
			// System.out.println("pos < size && pos >= 0 : pos = " + pos + "
			// size = " + size );
			return pos < size && pos >= 0;
		}

		@Override
		public E next() {
			if (pos < 0 || pos >= size)
				throw new RuntimeException("游标越界异常");
			if ( pos == 0) {
				pos++;
				current = list.first;
				showPos();
				return current.item;
			}
			Node<E> node = current.next;
			pos++;
			current = node;
			showPos();
			return node.item;
		}

	}
}
