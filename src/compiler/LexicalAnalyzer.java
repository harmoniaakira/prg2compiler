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
		presentVerbs.add("say");
		presentVerbs.add("do");
		presentVerbs.add("have");
		presentVerbs.add("get");
		presentVerbs.add("make");
		presentVerbs.add("think");
		presentVerbs.add("take");
		presentVerbs.add("see");
		presentVerbs.add("come");
		presentVerbs.add("want");
		presentVerbs.add("use");
		presentVerbs.add("find");
		presentVerbs.add("give");
		presentVerbs.add("tell");
		presentVerbs.add("work");
		presentVerbs.add("call");
		presentVerbs.add("ask");
		presentVerbs.add("need");
		presentVerbs.add("feel");
		presentVerbs.add("become");
		presentVerbs.add("graduate");
		presentVerbs.add("leave");
		presentVerbs.add("keep");
		presentVerbs.add("begin");
		presentVerbs.add("help");
		presentVerbs.add("show");
		presentVerbs.add("run");
		presentVerbs.add("believe");
		presentVerbs.add("write");
		
		return presentVerbs;
	}
	
	public static ArrayList<String> loadAdverbs() {
		ArrayList<String> adverbs = new ArrayList<String>();
		
		adverbs.add("not");
		adverbs.add("very");
		adverbs.add("quite");
		adverbs.add("probably");
		adverbs.add("just");
		adverbs.add("most");
		adverbs.add("really");
		adverbs.add("almost");
		adverbs.add("actually");
		adverbs.add("finally");
		adverbs.add("simply");
		adverbs.add("nearly");
		adverbs.add("certainly");
		adverbs.add("exactly");
		adverbs.add("pretty");
		adverbs.add("clearly");
		adverbs.add("indeed");
		adverbs.add("rather");
		adverbs.add("eventually");
		
		return adverbs;
	}

	
	public static ArrayList<String> loadAdjectives() {
		ArrayList<String> adjectives = new ArrayList<String>();
		
		adjectives.add("smart");
		adjectives.add("tall");
		adjectives.add("great");
		adjectives.add("new");
		adjectives.add("good");
		adjectives.add("high");
		adjectives.add("old");
		adjectives.add("big");
		adjectives.add("large");
		adjectives.add("young");
		adjectives.add("different");
		adjectives.add("long");
		adjectives.add("little");
		adjectives.add("important");
		adjectives.add("political");
		adjectives.add("bad");
		adjectives.add("real");
		adjectives.add("best");
		adjectives.add("right");
		adjectives.add("low");
		adjectives.add("early");
		adjectives.add("able");
		adjectives.add("human");
		adjectives.add("late");
		adjectives.add("hard");
		adjectives.add("major");
		adjectives.add("better");
		adjectives.add("strong");
		adjectives.add("free");
		adjectives.add("true");
		adjectives.add("full");
		adjectives.add("special");
		adjectives.add("easy");
		adjectives.add("difficult");
		adjectives.add("open");
		adjectives.add("happy");
		
		return adjectives;
	}

	public static ArrayList<String> loadSubstantives() {
		ArrayList<String> substantives = new ArrayList<String>();
		
		substantives.add("chair");
		substantives.add("computer");
		substantives.add("car");
		substantives.add("child");
		substantives.add("son");
		substantives.add("time");
		substantives.add("year");
		substantives.add("semester");
		substantives.add("way");
		substantives.add("day");
		substantives.add("man");
		substantives.add("thing");
		substantives.add("woman");
		substantives.add("life");
		substantives.add("world");
		substantives.add("school");
		substantives.add("state");
		substantives.add("family");
		substantives.add("student");
		substantives.add("group");
		substantives.add("country");
		substantives.add("problem");
		substantives.add("hand");
		substantives.add("part");
		substantives.add("place");
		substantives.add("case");
		substantives.add("week");
		substantives.add("company");
		substantives.add("system");
		substantives.add("program");
		substantives.add("question");
		substantives.add("work");
		substantives.add("government");
		substantives.add("number");
		substantives.add("night");
		substantives.add("point");
		substantives.add("home");
		substantives.add("water");
		substantives.add("room");
		substantives.add("mother");
		substantives.add("area");
		substantives.add("money");
		substantives.add("story");
		substantives.add("fact");
		substantives.add("month");
		substantives.add("lot");
		substantives.add("book");
		substantives.add("eye");
		substantives.add("job");
		substantives.add("word");
		substantives.add("business");
		
		return substantives;
	}
	
	public static ArrayList<String> loadPluralSubstantives() {
		ArrayList<String> pluralSubstantives = new ArrayList<String>();
		
		pluralSubstantives.add("chairs");
		pluralSubstantives.add("computers");
		pluralSubstantives.add("cars");
		pluralSubstantives.add("children");
		pluralSubstantives.add("sons");
		pluralSubstantives.add("times");
		pluralSubstantives.add("years");
		pluralSubstantives.add("people");
		pluralSubstantives.add("semesters");
		pluralSubstantives.add("ways");
		pluralSubstantives.add("days");
		pluralSubstantives.add("men");
		pluralSubstantives.add("things");
		pluralSubstantives.add("women");
		pluralSubstantives.add("lives");
		pluralSubstantives.add("worlds");
		pluralSubstantives.add("schools");
		pluralSubstantives.add("states");
		pluralSubstantives.add("families");
		pluralSubstantives.add("students");
		pluralSubstantives.add("groups");
		pluralSubstantives.add("countries");
		pluralSubstantives.add("problems");
		pluralSubstantives.add("hands");
		pluralSubstantives.add("parts");
		pluralSubstantives.add("places");
		pluralSubstantives.add("cases");
		pluralSubstantives.add("weeks");
		pluralSubstantives.add("companies");
		pluralSubstantives.add("systems");
		pluralSubstantives.add("programs");
		pluralSubstantives.add("questions");
		pluralSubstantives.add("works");
		pluralSubstantives.add("governments");
		pluralSubstantives.add("numbers");
		pluralSubstantives.add("nights");
		pluralSubstantives.add("points");
		pluralSubstantives.add("homes");
		pluralSubstantives.add("waters");
		pluralSubstantives.add("rooms");
		pluralSubstantives.add("mothers");
		pluralSubstantives.add("areas");
		pluralSubstantives.add("stories");
		pluralSubstantives.add("facts");
		pluralSubstantives.add("months");
		pluralSubstantives.add("lots");
		pluralSubstantives.add("books");
		pluralSubstantives.add("eyes");
		pluralSubstantives.add("jobs");
		pluralSubstantives.add("words");
		
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
