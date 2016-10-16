package compiler;
import java.util.ArrayList;
import java.util.List;

import enums.NodeType;
import enums.WordClass;
import misc.Node;
import misc.Token;

public class SintaticAnalyzer {
	
	private List<Token> tokens;
	private List<String> errors = new ArrayList<>();
	private Token token;
	private int tokenIndex;
	private Node root;

	public SintaticAnalyzer(List<Token> tokens) {
		this.tokens = tokens;
	}

	private void readToken() {
		token = tokens.get(tokenIndex++);
	}
	
	public void analyze() {
		tokenIndex = 0;
		
		readToken();
		
		root = Sentence();
		if(!token.getImage().equals("$")) {
			errors.add("Expected EOF. Found: "+token);
		}
		
		showTree();
	}
	
	
	//-------------------------------------------------------
	//<sentence> ::= <subject> <predicate>
	private Node Sentence() {
		Node node = new Node(NodeType.Sentence);
		
		node.addSon(Subject());
		node.addSon(Predicate());
		
		return node;
	}
	
	//<subject> ::= <pronoun> | <article> <substantive>
	private Node Subject() {
		Node node = new Node(NodeType.Subject);
		
		if(token.getCategory() == WordClass.PRONOUN) {
			node.addSon(Pronoun());
		}else {
			node.addSon(Article());
			node.addSon(Substantive());
		}
		
		return node;
	}
	
	//<predicate> ::= <toBe> <adverb> | <toBe> <adverb> <adjective> | <toBe> <adverb> <present> | <toBe> <adverb> <presentContinuous>
	private Node Predicate() {
		Node node = new Node(NodeType.Predicate);
		Token lookAhead = lookAhead();
		if(lookAhead.getCategory() == WordClass.ADVERB){
			lookAhead = lookEvenFurtherAhead();
		}

		node.addSon(ToBe());
		node.addSon(Adverb());
		
		if(lookAhead.getCategory() == WordClass.ADJECTIVE) {
			node.addSon(Adjective());
		} else if(lookAhead.getCategory() == WordClass.PRESENT_VERB) {
			node.addSon(Present());
		} else if(lookAhead.getCategory() == WordClass.PRESENT_CONTINUOUS_VERB) {
			node.addSon(PresentContinuous());
		} 
		
		return node;
	}
	
	//<adverb> ::= (database) | 
	private Node Adverb() {
		Node node = new Node(NodeType.Adverb);
		
		if(token.getCategory() == WordClass.ADVERB){
			node.addSon(new Node(token));
			readToken();
		}
		
		return node;
	}
		
	//<toBe> ::= 'am' | 'will' | 'was' | 'is' | 'are' | 'were'
	private Node ToBe() {
		Node node = new Node(NodeType.ToBe);
		String tokenImage = token.getImage();
		
		if(tokenImage.equals("am") || tokenImage.equals("will") || tokenImage.equals("was")|| 
				tokenImage.equals("is") || tokenImage.equals("are") || tokenImage.equals("were")) {
			node.addSon(new Node(token));
			readToken();
		}else {
			errors.add("Expected a To/Be Verb. Found: " + token);
		}
		
		return node;
	}
		
	//<article> ::= 'the' | 'this' | 'that' | 'those' | 'a' | 'an' | 'your' | 'their'
	private Node Article() {
		Node node = new Node(NodeType.Article);
		String tokenImage = token.getImage();
		
		if(tokenImage.equals("the") || tokenImage.equals("this") || 
				tokenImage.equals("that") || tokenImage.equals("those") || 
				tokenImage.equals("your") || tokenImage.equals("their")) {
				node.addSon(new Node(token));
				readToken();
		} else {
			errors.add("Expected an Article. Found: " + token);
		}
		
		return node;
	}
	
	//<pronoun> ::= 'I' | 'you' | 'he' | 'she' | 'it' | 'we' | 'they'
	private Node Pronoun() {
		Node node = new Node(NodeType.Pronoun);
		String tokenImage = token.getImage();
		
		if(tokenImage.equals("i") || tokenImage.equals("you") || tokenImage.equals("he") || tokenImage.equals("she") || 
			tokenImage.equals("it") || tokenImage.equals("we") || tokenImage.equals("they")) {
			node.addSon(new Node(token));
			readToken();
		} else {
			errors.add("Expected a Pronoun. Found: " + token);
		}
		
		return node;
	}
	
	//<substantive> ::= (database) | <plural_substantive>
	private Node Substantive() {
		Node node = new Node(NodeType.Substantive);
		
		if(token.getCategory() == WordClass.SUBSTANTIVE) {
			node.addSon(new Node(token));
			readToken();
		} else if(token.getCategory() == WordClass.PLURAL_SUBSTANTIVE){
			node.addSon(Plural_Substantive());
		} else {
			errors.add("Expected a Substantive. Found: " + token);
		}
		
		return node;
	}
	
	//<plural_substantive> ::= (database)
	private Node Plural_Substantive() {
		Node node = new Node(NodeType.Plural_Substantive);
		
		if(token.getCategory() == WordClass.PLURAL_SUBSTANTIVE) {
			node.addSon(new Node(token));
			readToken();
		} else {
			errors.add("Expected a Substantive. Found: " + token);
		}
		
		return node;
	}
	
	//<adjective> ::= (database)
	private Node Adjective() {
		Node node = new Node(NodeType.Adjective);
		
		if(token.getCategory() == WordClass.ADJECTIVE) {
			node.addSon(new Node(token));
			readToken();
		} else {
			errors.add("Expected an Adjective. Found: " + token);
		}
		
		return node;
	}
	
	//<present> ::= (database)
	private Node Present() {
		Node node = new Node(NodeType.Present);
		
		if(token.getCategory() == WordClass.PRESENT_VERB) {
			node.addSon(new Node(token));
			readToken();
		} else {
			errors.add("Expected a Present Verb. Found: " + token);
		}
		
		return node;
	}
	
	///<presentcontinuous> ::= (database)
	private Node PresentContinuous() {
		Node node = new Node(NodeType.PresentContinuous);
		
		if(token.getCategory() == WordClass.PRESENT_CONTINUOUS_VERB) {
			node.addSon(new Node(token));
			readToken();
		} else {
			errors.add("Expected a Present Continuous Verb. Found: " + token);
		}
		
		return node;
	}
	
	//-------------------------------------------------------------------------------
	
	
	private Token lookAhead() {
		return tokens.get(tokenIndex);
	}
	
	private Token lookEvenFurtherAhead(){
		return tokens.get(tokenIndex + 1);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public void showErrors() {
		System.out.println("\n--   COMPILATION ERROR  --");
		errors.forEach(erro -> System.err.println(errors));
	}
	
	public void showTree() {
		System.out.println("\n\n---   EXPANSION TREE   ---");
		showNode(root, "");
	}

	public void showNode(Node No, String space) {
		System.out.println(space + No);
		for(Node NoSon: No.getSons()) {
			showNode(NoSon, space + "   ");
		}
	}
	
	public Node getRoot(){
		return this.root;
	}

}
