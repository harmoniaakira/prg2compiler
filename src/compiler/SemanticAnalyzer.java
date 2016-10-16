package compiler;
import java.util.ArrayList;

import enums.NodeType;
import enums.Plurality;
import enums.VerbalTime;
import misc.Node;
import misc.Token;

public class SemanticAnalyzer {
	
	//ATTRIBUTES
	private Node tree;

	private ArrayList<String> errors = new ArrayList<String>();
	
	//CONSTRUCTOR
	public SemanticAnalyzer(Node tree){
		this.tree = tree;
	}
	
	//METHODS
	public void analyze() {
		analyze(tree);
	}

	private Object analyze(Node node){
		switch (node.getType()) {
			case Sentence:
				return Sentence(node);
			case Subject:
				return Subject(node);
			case Predicate:
				return Predicate(node);
			case Adverb:
				return Adverb(node);
			case ToBe:
				return ToBe(node);
			case Article:
				return Article(node);
			case Pronoun:
				return Pronoun(node);
			case Substantive:
				return Substantive(node);
			case Adjective:
				return Adjective(node);
			case Present:
				return Present(node);
			case PresentContinuous:
				return PresentContinuous(node);
			default:
				System.err.println("Unknown node: " + node);
				return null;
		}
	}
	
	//-----------------------------------------------------------
	                      
	//<sentence> ::= <subject> <predicate>
		private Object Sentence(Node node) {
			Plurality subjectRet = (Plurality) analyze(node.getSon(0));
			VerbalTime predicateRet = (VerbalTime) analyze(node.getSon(1));
							
			if(node.getSon(1).getSons().size() > 2) {
				
				if(node.getSon(1).getSon(2).getType() == NodeType.Adjective){
					validadeSentence(subjectRet, predicateRet);
				}
				
				if(node.getSon(1).getSon(2).getType() == NodeType.Present){
					validatePresentSentence(predicateRet);
				}
				
				if(node.getSon(1).getSon(2).getType() == NodeType.PresentContinuous){
					validatePresentContinuousSentence(subjectRet, predicateRet);
				}
			} else {
				validadeSentence(subjectRet, predicateRet);
			}	
	
			return null;
		}
		
		//<subject> ::= <pronoun> | <article> <substantive>
		private Object Subject(Node node) {
			if(node.getSons().size() == 1) {
				Plurality pronomeRet = (Plurality) analyze(node.getSon(0));
				return pronomeRet;
			}else if (node.getSons().size() == 2) {
				Plurality articleRet = (Plurality) analyze(node.getSon(0));
				Plurality substantiveRet = (Plurality) analyze(node.getSon(1));
				if(articleRet == substantiveRet || articleRet == Plurality.BOTH){
					return substantiveRet;
				} else {
					errors.add("Semantic Error: The substantive's plurality does not match the article's.");
				}
			}else {
				throw new RuntimeException("Unknown Node.");
			}
			
			return null;
		}
		
		//<predicate> ::= <toBe> <adverb> | <toBe> <adverb> <adjective> | <toBe> <adverb> <present> | <toBe> <adverb> <presentContinuous>
		private Object Predicate(Node node) {
			if(node.getSons().size() == 3) {
				
				if(node.getSon(2).getType() == NodeType.Adjective) {
					VerbalTime toBeRet = (VerbalTime) analyze(node.getSon(0));
					return toBeRet;
				}
				
				if(node.getSon(2).getType() == NodeType.Present) {
					VerbalTime toBeRet = (VerbalTime) analyze(node.getSon(0));
					return toBeRet;
				}
				
				if(node.getSon(2).getType() == NodeType.PresentContinuous) {
					VerbalTime toBeRet = (VerbalTime) analyze(node.getSon(0));
					return toBeRet;
				}
				
			}else if(node.getSons().size() == 2) {
				VerbalTime toBeRet = (VerbalTime) analyze(node.getSon(0));
				return toBeRet;
			}
			return null;
		}
		
		//<adverb> ::= (database) | &
		private Object Adverb(Node node) {

			return null;
		}
			
		//<toBe> ::= 'am' | 'will' | 'was' | 'is' | 'are' | 'were'
		private Object ToBe(Node no) {
			Token toBe = no.getSon(0).getToken();
			
			if(toBe.getImage().equals("am")) {
				return VerbalTime.FIRST_PERSON_PRESENT_SINGULAR_VERB;
			} else if(toBe.getImage().equals("will")) {
				return VerbalTime.FUTURE_PLURAL_VERB;
			} else if(toBe.getImage().equals("was")) {
				return VerbalTime.PAST_VERB;
			} else if(toBe.getImage().equals("is")) {
				return VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB;
			} else if(toBe.getImage().equals("are")) {
				return VerbalTime.PRESENT_PLURAL_VERB;
			}else if(toBe.getImage().equals("were")) {
				return VerbalTime.PAST_PLURAL_VERB;
			}
			
			return null;
		}
			
		//<article> ::= 'the' | 'this' | 'that' | 'those' | 'your' | 'their'
		private Object Article(Node node) {
			Token article = node.getSon(0).getToken();
			
			if(article.getImage().equals("this") ||
				article.getImage().equals("that")) {
				return Plurality.SINGULAR;
			} else if(article.getImage().equals("those") ||
					  article.getImage().equals("their")){
				return Plurality.PLURAL;
			} else if(article.getImage().equals("the") ||
					article.getImage().equals("your")){
				return Plurality.BOTH;
			}
			
			return null;
		}
		
		//<pronoun> ::= 'I' | 'he' | 'she' | 'we' | 'you' | 'they'
		private Object Pronoun(Node node) {
			Token pronoun = node.getSon(0).getToken();
			
			if(pronoun.getImage().equals("i")) {
				return Plurality.FIRST_PERSON_SINGULAR_PRONOUN;
			} else if(pronoun.getImage().equals("he") || pronoun.getImage().equals("she")) {
				return Plurality.THIRD_PERSON_SINGULAR_PRONOUN;
			} else if(pronoun.getImage().equals("we")) {
				return Plurality.FIRST_PERSON_PLURAL_PRONOUN;
			} else if(pronoun.getImage().equals("you")) {
				return Plurality.SECOND_PERSON_PLURAL_PRONOUN;
			} else if(pronoun.getImage().equals("they")) {
				return Plurality.THIRD_PERSON_PLURAL_PRONOUN;
			}
			
			return null;
		}
		
		//<substantive> ::= (database) | <plural_substantive>
		private Object Substantive(Node node) {
			
			if(node.getSon(0).getToken() != null) {
				return Plurality.SINGULAR;
			} else if(node.getSon(0).getType() == NodeType.Plural_Substantive) {
				return Plurality.PLURAL;
			}
			
			return null;
		}
		
		//<plural_substantive> ::= (database)
		private Object Plural_Substantive(Node node) {

			return null;
		}
		
		//<adjective> ::= (database)
		private Object Adjective(Node node) {

			return null;
		}
		
		//<present> ::= (database)
		private Object Present(Node node) {

			return null;
		}
		
		///<presentContinuous> ::= (database)
		private Object PresentContinuous(Node node) {

			return null;
		}
	
		
	//--------------------------------------------
	//VALIDATION METHODS
	public void validadeSentence(Plurality subjectRet, VerbalTime predicateRet) {
		if(subjectRet == Plurality.FIRST_PERSON_SINGULAR_PRONOUN) {
			if(predicateRet == VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PRESENT_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.THIRD_PERSON_SINGULAR_PRONOUN) {
			if(predicateRet == VerbalTime.FIRST_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PRESENT_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		//It's OR because 'We', 'You' and 'they' accept the same verbal times
		if(subjectRet == Plurality.FIRST_PERSON_PLURAL_PRONOUN || 
			subjectRet == Plurality.SECOND_PERSON_PLURAL_PRONOUN ||
			subjectRet == Plurality.THIRD_PERSON_PLURAL_PRONOUN) {
			if(predicateRet == VerbalTime.FIRST_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.SINGULAR){
			if(predicateRet != VerbalTime.FUTURE_PLURAL_VERB && 
				predicateRet != VerbalTime.PAST_VERB &&
				predicateRet != VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB){
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.PLURAL) {
			if(predicateRet != VerbalTime.FUTURE_PLURAL_VERB &&
				predicateRet != VerbalTime.PRESENT_PLURAL_VERB &&
				predicateRet != VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
	}

	public void validatePresentSentence(VerbalTime predicateRet) {
		
		if(predicateRet != VerbalTime.FUTURE_PLURAL_VERB) {
			errors.add("Semantic Error: Verbal Time does not match the sucject.");
		}
	}
	
	public void validatePresentContinuousSentence(Plurality subjectRet, VerbalTime predicateRet) {
		
		if(subjectRet == Plurality.FIRST_PERSON_SINGULAR_PRONOUN) {
			if(predicateRet == VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PRESENT_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.FUTURE_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.THIRD_PERSON_SINGULAR_PRONOUN) {
			if(predicateRet == VerbalTime.FIRST_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PRESENT_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.FUTURE_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		//It's OR because 'We', 'You' and 'they' accept the same verbal times
		if(subjectRet == Plurality.FIRST_PERSON_PLURAL_PRONOUN || 
			subjectRet == Plurality.SECOND_PERSON_PLURAL_PRONOUN ||
			subjectRet == Plurality.THIRD_PERSON_PLURAL_PRONOUN) {
			if(predicateRet == VerbalTime.FIRST_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.PAST_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			} else if(predicateRet == VerbalTime.FUTURE_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.SINGULAR){
			if(predicateRet != VerbalTime.PAST_VERB &&
				predicateRet != VerbalTime.THIRD_PERSON_PRESENT_SINGULAR_VERB){
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
		
		if(subjectRet == Plurality.PLURAL) {
			if(predicateRet != VerbalTime.PRESENT_PLURAL_VERB &&
				predicateRet != VerbalTime.PAST_PLURAL_VERB) {
				errors.add("Semantic Error: Verbal Time does not match the sucject.");
			}
		}
	}
	
	
	//--------------------------------------------
	//MISC
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public void showErrors() {
		for(String erro: errors) {
			System.err.println(erro);
		}
	}

}
