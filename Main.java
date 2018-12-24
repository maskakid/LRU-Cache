package lru;

import java.util.HashMap;

public class Main {

	private int cacheSize;
	private HashMap<Integer, Node> pageMap;
	private DLinkedList dLinkedList;

	public Main() {
		cacheSize = 3;
		pageMap = new HashMap<Integer, Node>();
		dLinkedList = new DLinkedList(3);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.init(1);
		main.init(2);
		main.init(3);
		main.init(4);
		
		main.init(2);
		main.init(3);
	}

	public void init(int x) {

		if (!pageMap.containsKey(x)) {
			if (dLinkedList.getCurrentSize() == dLinkedList.getTotalSize()) {
				pageMap.remove(dLinkedList.getRear().getPageNumber());
				Node tempnode = dLinkedList.addpageAtHead(x);
				pageMap.put(x, tempnode);
			} else {
				Node tempnode = dLinkedList.addpageAtHead(x);
				pageMap.put(x, tempnode);
			}
		} else {
			Node node = pageMap.get(x);
			dLinkedList.movePageToHead(node);
		}
		System.out.println("Start printing...........");
		dLinkedList.printList();
		System.out.println("End printing...........");
	}

}

class DLinkedList {

	Node head;
	Node tail;
	int currentSize = 0;
	int totalSize;

	public int getTotalSize() {
		return totalSize;
	}

	public DLinkedList(int size) {
		this.totalSize = size;
	}

	public Node getFront() {
		return head;
	}

	public void setFront(Node front) {
		this.head = front;
	}

	public Node getRear() {
		return tail;
	}

	public void setRear(Node rear) {
		this.tail = rear;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public Node addpageAtHead(int page) {

		// System.out.println(this.totalSize);

		Node node = new Node(page);

		if (head == null && tail == null) {
			head = node;
			tail = node;
			System.out.println("Intial: " + head.getPageNumber());
			currentSize = currentSize + 1;
		} else {
			if (currentSize < totalSize) {
				currentSize = currentSize + 1;
			} else {
				Node tempNode = tail.prev;
				tail = tempNode;
				tail.next = null;
			}
			head.prev = node;
			node.next = head;
			head = node;
		}

		return node;
	}

	public void addpageAtTail(int page) {
		Node node = new Node(page);

		if (head == null && tail == null) {
			head = node;
			tail = node;
			return;
		}

		tail.next = node;
		node.prev = tail;

		tail = node;
	}

	public void movePageToHead(Node node) {

		if (node == null || node == head) {
			return;
		}

		if (node == tail) {
			tail = tail.prev;
			tail.next = null;
		}

		Node left = node.getPrev();
		Node right = node.getNext();

		left.next = right;
		if (right != null) {
			right.prev = left;
		}

		node.next = head;
		head.prev = node;
		node.prev = null;
		head = node;

	}

	public void printList() {
		Node teNode = head;
		while (teNode != null) {
			System.out.println(teNode.getPageNumber());
			teNode = teNode.getNext();
		}
	}
}

class Node {

	Node prev;
	Node next;
	int pageNumber;

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Node(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
