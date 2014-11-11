
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;

public class CreateTokens {
	List<String> output = new ArrayList<String>();
	List<String> reserved = new ArrayList<String>();
	String LParen = "LParen";
	String RParen = "RParen";
	String Comma = "Comma";
	String SemiColon = "SemiColon";
	String AssignOp = "AssignOp";
	String MinusOp = "MinusOp";
	String PlusOp = "PlusOp";
	int counts = 0;
	
	
	public CreateTokens() {
		reserved.add("BEGIN");
		reserved.add("READ");
		reserved.add("WRITE");
		reserved.add("END");
	}
	public List<String> generateTokens(String input) {
				
						String temp = input;
						String word = "";
							
							
								for(int i=0; i<temp.length(); i++){
									char check = temp.charAt(i);
									if(Character.isLetter(check)) {
										word = word + check;
									}
									else if(check == '_') word = word + check;
									else if(check == ' ') word = word + check;
									else if(Character.isDigit(check)) word = word + check;
									else {
										
										if(word.contains("<")) {
											if(check == '>')
												word = word + ">";
										}
										if(word.length() >0 )
											output.add(word); word = "";
										if(check == '(') output.add(LParen);
										if(check == ')') output.add(RParen);
										if(check == ',') output.add(Comma);
										if(check == ';') {output.add(SemiColon); ++counts;}
										if(check == ':' && temp.charAt(i+1) == '=') output.add(AssignOp);
										if(check == '-') output.add(MinusOp);
										if(check == '+') output.add(PlusOp);
										if(check == '<') {
											word = word + "<";
										}
										
										
									}
									
									//System.out.print(""+ check + " . ");
								}
								if(word.length() >0) 
									output.add(word);
							
				
		return output;
	}
	
	public void displayTokens(List<String> output) {
		//System.out.println("******* Generated Tokens *******");
		List<String> list = new ArrayList<String>();
		for(String st: output) {
			if(st.length() >0) list.add(st);
			
		}
		
	}
	
	public int getCounts() {
		return counts;
	}
	
	/*public static void main(String[] args) {
		CreateTokens ct = new CreateTokens();
		List<String> out = ct.generateTokens("<program>$");
		String st = "<program>";
		st = st.replaceAll("[<>]" , "");
		System.out.println("The result is : " + out);
		System.out.println("The String : " + st);
	}*/
}