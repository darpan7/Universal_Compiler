
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;

public class GrammarAnalyzer14 {

	List<String> productions = new ArrayList<String>();
	List<String> right_productions = new ArrayList<String>();
	List<String> terminals = new ArrayList<String>();
	Map<String, List<String>> map = new HashMap<String, List<String>>();
	Set<String> non_terminals = new HashSet<String>();
	Map<String, List<Integer>> map_1 = new HashMap<String, List<Integer>>();
	List<Integer> list_1  = new ArrayList<Integer>();
	
	Map<Integer, String> map_2 = new HashMap<Integer, String>();
	Set<String> terminals_set = new HashSet<String>();
	int counts = 0; int counter = 0; int init = 0;
	
	public GrammarAnalyzer14() {
		
	}
	
	public void Analyze(File testFile) {
		try{
			BufferedReader input = new BufferedReader(new FileReader(testFile));
			try {
				String line = null;
				while (( line = input.readLine()) != null){
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreElements()) {
						String map_key = "";
						List<String> map_value = new ArrayList<String>();
						String temp = st.nextElement().toString(); // ---------- Left Hand side ----------------
						String word = "";
						if(temp.startsWith("<") && temp.endsWith(">")) {
									productions.add(temp.substring(1, temp.length()-1));
									map_key = temp;							
						}
						String seperator = st.nextElement().toString(); // -----------  for -> ----------------
						
						String temp1 = st.nextElement().toString(); // ------------ Right hand side -------------
						right_productions.add(temp1);
						if(map.get(map_key) != null){
							map_value = map.get(map_key);
							map_value.add(temp1);
							map.put(map_key, map_value);
						}
						else {
							map_value.add(temp1);
							map.put(map_key, map_value);
						}
						
						
						for(int i=0; i<temp1.length(); i++){
							char check = temp1.charAt(i);
							if(check == '<') {
								counter = 1;	init = 1;
								if(word != "") {
									terminals.add(word);
									word = "";
								}
							}
							else if(check == ';') {
								terminals.add(";");
							}
							else if(check != '<' && counter == 0) {
								word = word + check;
							}
							else if(check == '>') {
								counter = 0;
							}		
						}
						if(counter == 0) {
							if(word != "")
								terminals.add(word);
						}
						word = "";
						break;
					}
				}
			} catch(IOException ie) {
				ie.printStackTrace();
			} finally {
				input.close();
			} 
		} catch(IOException ie) {
			ie.printStackTrace();
		}
		Set<String> productions_unique = new HashSet<String>(productions);
		non_terminals = productions_unique;
	//	System.out.println();
		//System.out.println("List of Non-Terminals");
		for(String pr: productions_unique) {
			//System.out.println("<" + pr + ">");
		}
	//	System.out.println();
		//System.out.println("List of Symbols");
		terminals.removeAll(Collections.singleton(null));
		for(String pr: terminals) {
			//System.out.print(""+ pr + "  ");
		}
	//	System.out.println(); System.out.println();
		//System.out.println("List of Terminals");
		int remove_i = 0;
		Iterator<String> itr = terminals.iterator();
		terminals.remove("Id:=");
		terminals.add("Id");
		terminals.add(":=");
		terminals.remove("read(");
		terminals.add("read"); terminals.add("(");
		terminals.remove("write(");
		terminals.add("write"); terminals.add("(");
		Iterator<String> it = terminals.iterator();
		while (it.hasNext() ) {
		   String value = it.next();
		   if (value.equals("l") ) {
			 it.remove();
		   }
		}
        
		Set<String> set_terminals = new HashSet<String>(terminals);
		terminals_set = set_terminals;
		for(String pr: set_terminals) {
			//System.out.print(""+ pr + "  ");
		}
	//	System.out.println();System.out.println();
		//System.out.println("List of Left Hand side Productions");
		for(String pr: productions) {
			//System.out.println("" + pr );
		}
	//	System.out.println();
		//System.out.println("List of Right Hand side Productions");
		for(String pr: right_productions) {
			//System.out.println("-> " + pr);
		}
		//////////////////////////////////    Map (Non_T, List(index))    ////////////////////////////////////
		
		for(String pr: productions_unique) {
			int iii = 0;
			for(String pr1: productions) {
				if(pr.equals(pr1)) {
					list_1.add(iii);
				}
				else {	
				}
				++iii;
			}
			List<Integer> ddd = new ArrayList<Integer>(list_1);
			//System.out.println("Size of ddd is ::: " + ddd.size());
			Collections.copy(ddd, list_1);
			map_1.put(pr, ddd);
			list_1.clear();
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////  Map(index, RHS_prod) ////////////////////////////////////////////
		
		int right_i = 0;
		for(String pr: right_productions) {
			map_2.put(right_i, pr);
			++right_i;
		}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		//System.out.println("********************************************************");
		for (Map.Entry<String,List<String>> entry : map.entrySet()) {
		  String key = entry.getKey();
		  List<String> value = entry.getValue();
		 // System.out.print("NonT :" + key + " & Prod are :");
		 // for(String i : value) 			******* CHANGED *****************************************************************************
			//System.out.print("" + i +"|"); 
		 // System.out.println();
		  //System.out.println();
		  
		}
		
	}
	public Set<String> getNonTerminals() {
		return non_terminals;
	}
	
	public List<String> getRightProductions() {
		return right_productions;
	}
	
	public Set<String> getTerminals() {
		return terminals_set;
	}
	public List<String> getLeftProd() {
		return productions;
	}
	
	public Map<String, List<String>> getMap() {
		return map;
	}
	
	public Map<String, List<Integer>> getMap_1() {
		return map_1;
	}
	
	public Map<Integer, String> getMap_2() {
		return map_2;
	}
		
		
	/*public static void main(String[] args){
		File testFile = new File("D:\\Fall 2014\\Compiler - Boris\\Programs\\input.txt");
		GrammarAnalyzer14 grammar = new GrammarAnalyzer14();
		grammar.Analyze(testFile); // *************************************
	} */
}