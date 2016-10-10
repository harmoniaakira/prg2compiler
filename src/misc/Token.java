package misc;

import enums.WordClass;

public class Token {
	
	//ATRIBUTOS
	private String image;
	private WordClass category;
	private int column;

	
	//CONSTRUTOR
	public Token() {
		super();
	}

	public Token(String image, WordClass category, int column) {
		super();
		this.image = image;
		this.category = category;
		this.column = column;
	}
	
	
	//GETTERS & SETTERS
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public WordClass getCategory() {
		return category;
	}

	public void setCategory(WordClass category) {
		this.category = category;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	//toString
	public String toString() {
		return "Token '" + image + "', " + category + ", Column = " + column;
	}
}
