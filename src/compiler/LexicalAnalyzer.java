package compiler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import enums.WordClass;
import misc.Token;

public class LexicalAnalyzer {

	private List<Token> tokens = new ArrayList<>();
	ArrayList<String> toBeVerbs = new ArrayList<String>();
	ArrayList<String> articles = new ArrayList<String>();
	ArrayList<String> pronouns = new ArrayList<String>();
	ArrayList<String> substantives = new ArrayList<String>();
	ArrayList<String> pluralSubstantives = new ArrayList<String>();
	ArrayList<String> adjectives = new ArrayList<String>();
	ArrayList<String> adverbs = new ArrayList<String>();
	ArrayList<String> presentVerbs = new ArrayList<String>();
	ArrayList<String> presentContinuousVerbs = new ArrayList<String>();

	public LexicalAnalyzer() {

	}

	public void analyze() throws IOException {
		toBeVerbs.addAll(loadToBeVerbs());
		articles.addAll(loadArticles());
		pronouns.addAll(loadPronouns());
		substantives.addAll(loadSubstantives());
		pluralSubstantives.addAll(loadPluralSubstantives());
		adjectives.addAll(loadAdjectives());
		adverbs.addAll(loadAdverbs());
		presentVerbs.addAll(loadPresentVerbs());
		presentContinuousVerbs.addAll(loadPresentContinuousVerbs());

		int index = 1;
		int col = 0;
		String line = "";
		
		FileReader file = new FileReader("file.txt");
		BufferedReader reader = new BufferedReader(file);
		
		line = reader.readLine().toLowerCase();
			
		//Lexical Analysis
		StringTokenizer tks = new StringTokenizer(line);
		while(tks.hasMoreTokens()){
			String token = tks.nextToken();
			col++;
			
			if(toBeVerbs.contains(token)){
				tokens.add(new Token(token, WordClass.TO_BE_VERB, col));
			}
			else if(articles.contains(token)){
				tokens.add(new Token(token, WordClass.ARTICLE, col));
			}
			else if(pronouns.contains(token)){
				tokens.add(new Token(token, WordClass.PRONOUN, col));
			}
			else if(substantives.contains(token)){
				tokens.add(new Token(token, WordClass.SUBSTANTIVE, col));
			}
			else if(pluralSubstantives.contains(token)){
				tokens.add(new Token(token, WordClass.PLURAL_SUBSTANTIVE, col));
			}
			else if(adjectives.contains(token)){
				tokens.add(new Token(token, WordClass.ADJECTIVE, col));
			}
			else if(adverbs.contains(token)){
				tokens.add(new Token(token, WordClass.ADVERB, col));
			}
			else if(presentVerbs.contains(token)){
				tokens.add(new Token(token, WordClass.PRESENT_VERB, col));
			}
			else if(presentContinuousVerbs.contains(token)){
				tokens.add(new Token(token, WordClass.PRESENT_CONTINUOUS_VERB, col));
			}
		}
	
		
		tokens.add(new Token("$", WordClass.EOF, col));
		
		System.out.println("            --- TOKENS TABLE ---");
		System.out.println("------------------------------------------------------------");
		System.out.println("      IMAGE         |          CATEGORY         ");
		System.out.println("------------------------------------------------------------");
		for(int i = 0; i < tokens.size(); i++){
			System.out.printf("      %-6s        |      %-16s  \n", tokens.get(i).getImage(), tokens.get(i).getCategory());
		}
		System.out.println("------------------------------------------------------------");
	}
	
	
	//------------------------------------------------------------------------------
	
	public static ArrayList<String> loadPresentContinuousVerbs() {
		ArrayList<String> presentContinuousVerbs = new ArrayList<String>();
		
		presentContinuousVerbs.add("studying");
		presentContinuousVerbs.add("playing");
		presentContinuousVerbs.add("trying");
		presentContinuousVerbs.add("going");
		presentContinuousVerbs.add("doing");
		presentContinuousVerbs.add("breaking");
		
		return presentContinuousVerbs;
	}

	public static ArrayList<String> loadPresentVerbs() {
		ArrayList<String> presentVerbs = new ArrayList<String>();
		
		presentVerbs.add("study");
		presentVerbs.add("go");
		presentVerbs.add("try");
		presentVerbs.add("play");
		presentVerbs.add("break");
		
		return presentVerbs;
	}
	
	public static ArrayList<String> loadAdverbs() {
		ArrayList<String> adverbs = new ArrayList<String>();
		
		adverbs.add("not");
		adverbs.add("very");
		adverbs.add("quite");
		adverbs.add("probably");
		
		return adverbs;
	}

	
	public static ArrayList<String> loadAdjectives() {
		ArrayList<String> adjectives = new ArrayList<String>();
		
		adjectives.add("smart");
		adjectives.add("tall");
		adjectives.add("great");
		
		return adjectives;
	}

	public static ArrayList<String> loadSubstantives() {
		ArrayList<String> substantives = new ArrayList<String>();
		
		substantives.add("chair");
		substantives.add("computer");
		substantives.add("car");
		substantives.add("child");
		substantives.add("son");
		
		return substantives;
	}
	
	public static ArrayList<String> loadPluralSubstantives() {
		ArrayList<String> pluralSubstantives = new ArrayList<String>();
		
		pluralSubstantives.add("chairs");
		pluralSubstantives.add("computers");
		pluralSubstantives.add("cars");
		pluralSubstantives.add("children");
		pluralSubstantives.add("sons");
		
		return pluralSubstantives;
	}

	public static ArrayList<String> loadPronouns() {
		ArrayList<String> nouns = new ArrayList<String>();
		
		nouns.add("i");
		nouns.add("you");
		nouns.add("he");
		nouns.add("she");
		nouns.add("it");
		nouns.add("we");
		nouns.add("they");
		
		return nouns;
	}

	public static ArrayList<String> loadArticles() {
		ArrayList<String> articles = new ArrayList<String>();
		
		articles.add("the");
		articles.add("this");
		articles.add("that");
		articles.add("those");
		articles.add("your");
		articles.add("their");
		
		return articles;
	}

	public static ArrayList<String> loadToBeVerbs() {
		ArrayList<String> toBeVerbs = new ArrayList<String>();
		
		toBeVerbs.add("am");
		toBeVerbs.add("will");
		toBeVerbs.add("was");
		toBeVerbs.add("is");
		toBeVerbs.add("are");
		toBeVerbs.add("were");
		
		return toBeVerbs;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	

}
