package compiler;
import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException {
		
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
		lexicalAnalyzer.analyze();
		
		SintaticAnalyzer sintaticAnalyzer = new SintaticAnalyzer(lexicalAnalyzer.getTokens());
		sintaticAnalyzer.analyze();
		if(sintaticAnalyzer.hasErrors()) {
			sintaticAnalyzer.showErrors();
			System.exit(0);
		}
		
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(sintaticAnalyzer.getRoot());
		semanticAnalyzer.analyze();
		if(semanticAnalyzer.hasErrors()) {
			semanticAnalyzer.showErrors();;
			System.exit(0);
		}
		
		System.out.println("Done.");
		
	}

}
