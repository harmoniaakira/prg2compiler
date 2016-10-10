package misc;
import java.util.ArrayList;

import enums.NodeType;


public class Node {
	
	private Node father;
	private ArrayList<Node> sons = new ArrayList<Node>();
	private NodeType type;
	private Token token;

	public Node(NodeType type) {
		this.type = type;
	}

	public Node(Token token) {
		this.token = token;
		this.type = NodeType.Token;
	}

	public void addSon(Node son) {
		son.father = this;
		sons.add(son);
	}

	public Node getSon(int pos) {
		return sons.get(pos);
	}

	public String toString() {
		if(token != null) {
			return token.getImage();
		}
		return type.toString();
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public NodeType getType() {
		return type;
	}

	public void setTipo(NodeType type) {
		this.type = type;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public ArrayList<Node> getSons() {
		return sons;
	}

	public void setSons(ArrayList<Node> sons) {
		this.sons = sons;
	}
	
}
