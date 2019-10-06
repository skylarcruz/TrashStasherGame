package TrashStasher;

import java.util.*;

public class Dikjstra {
	
	public Node[][] graph;
	int xSize;
	int ySize;
	
	ArrayList<Node> closedList;
	PriorityQueue<Node> pq;
	
	private String levelText;
	private int raccX;
	private int raccY;
	
	public Dikjstra(int x, int y) {
		graph = new Node[x][y];
		xSize = x;
		ySize = y;

		for (int i = 0; i < y; i++) {	
			for (int j = 0; j < x; j++) {
				graph[j][i] = new Node(100000, 1, j, i);
			}
		}
		
		closedList = new ArrayList<Node>(x * y);
		pq = new PriorityQueue<Node>((x * y), new Node());
	}
	
	public void setGrid() {
		
		//Read Text File
		levelText = getLevelText();
		
		int k = -1;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {	
				k += 1;
				if (levelText.charAt(k) == 'X' || levelText.charAt(k) == 'M') { 
					graph[j][i] = new Node(1000, 1000, j, i);
				}
				else {
					graph[j][i] = new Node(1000, 1, j, i);
				}
			}
		}	
	}
	
	public void runDikjstra() {
		initSource();
		closedList.clear();
		
		for (int i = 0; i < ySize; i++) {	
			for (int j = 0; j < xSize; j++) {
				pq.add(graph[j][i]);
			}
		}
		
		while (pq.peek() != null) {
			int c;
			Node u = pq.poll();
			Node v;
			closedList.add(u);
			
			// Work with neighbors
			
			// Up
			if (u.yVal > 0) {
				v = graph[u.xVal][u.yVal - 1];
				c = (u.weight + v.weight)/2;
				relax(u, v, c, pq);
			}
			
			// Down
			if (u.yVal < ySize - 1) {
				v = graph[u.xVal][u.yVal + 1];
				c = (u.weight + v.weight)/2;
				relax(u, v, c, pq);
			}
			
			// Left
			if (u.xVal > 0) {
				v = graph[u.xVal - 1][u.yVal];
				c = (u.weight + v.weight)/2;
				relax(u, v, c, pq);
			}
			
			// Right
			if (u.xVal < xSize - 1) {
				v = graph[u.xVal + 1][u.yVal];
				c = (u.weight + v.weight)/2;
				relax(u, v, c, pq);
			}
			
		}
	}
	
	public void relax(Node u, Node v, int c, PriorityQueue<Node> p) {
		if (v.cost > (u.cost + c)) {
			p.remove(v);
			v.cost = u.cost + c;
			v.pi.add(0, u);
			p.add(v);
			
		}
	}
	
	public void initSource() {
		for (int i = 0; i < ySize; i++) {	
			for (int j = 0; j < xSize; j++) {
				graph[j][i].cost = 1000;
				graph[j][i].pi.clear();
			}
		}
		
		graph[raccX][raccY].cost = 0;
	}
	
	public String getBestDir(int x, int y) {
		Node u = graph[x][y];
		Node v;
		if (u.pi.size() > 0)
			v = u.pi.get(0);
		else 
			return null;

		if (v.yVal < u.yVal) {
			return "Up";
		}
		else if (v.yVal > u.yVal) {
			return "Down";
		}
		else if (v.xVal < u.xVal) {
			return "Left";
		}
		else if (v.xVal > u.xVal) {
			return "Right";
		}
		else
			return null;
		
	}
	
	public boolean setRaccLoc(int x, int y) {
		boolean change = false;
		if (raccX != x || raccY != y)
			change = true;
		raccX = x;
		raccY = y;
		
		return change;
	}
	
	public String getLevelText() {
		if (TrashStasherGame.currLevel == 1)
			return TrashStasherGame.Lvl1;
		else
			return TrashStasherGame.Lvl1;
	}
}

class Node implements Comparator<Node> { 
    public int weight;
    public int cost;
    int xVal;
    int yVal;
    ArrayList<Node> pi;
  
    public Node() { 
    } 
  
    public Node(int cost, int w, int x, int y) {  
        this.cost = cost;
        this.weight = w;
        this.xVal = x;
        this.yVal = y;
        pi = new ArrayList<Node>(450);
    } 
  
    @Override
    public int compare(Node node1, Node node2)  { 
        if (node1.cost < node2.cost) 
            return -1; 
        if (node1.cost > node2.cost) 
            return 1; 
        return 0; 
    } 
} 
