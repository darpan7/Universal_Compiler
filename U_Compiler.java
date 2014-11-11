import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class U_Compiler
  extends JFrame
{
  private JPanel topPanel;
  private JTable table;
  private JScrollPane scrollPane;
  private String[][] dataValues = new String[13][15];
  
  public String[][] LL1_Driverz(java.util.List<String> terms, java.util.List<String> non_terminals, Map<String, java.util.List<Integer>> map_1, Map<Integer, String> map_2, Map<String, java.util.List<String>> predictset, java.util.List<String> paramList3)
  {
    setTitle("LL(1) Table Application");
    setSize(1000, 290);
    setBackground(Color.gray);
    




    this.topPanel = new JPanel();
    this.topPanel.setLayout(new BorderLayout());
    getContentPane().add(this.topPanel);
    

    String[] columnNames = new String[15];
    



    //System.out.println("**********************************************");
    columnNames[0] = "NT/T";
    java.util.List<String> table_x = new ArrayList<String>();
    table_x.add("NT/T");
    for (int i = 0; i < terms.size(); i++)
    {
      columnNames[(i + 1)] = ((String)terms.get(i));
      table_x.add(terms.get(i));
    }
    int y = 0;
    for (Map.Entry<String, java.util.List<Integer>> ent : map_1.entrySet())
    {
      //Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
    //  System.out.println("non_t :" + ent.getKey() + "  &index_Set : ");
      this.dataValues[y][0] = ent.getKey();
      if (ent.getValue() != null)
	  {
        for (Integer s : ent.getValue()) {
	//	  System.out.println("" + s + "  ");
		  String pointer = map_2.get(s);
	//	  System.out.println("@@@@@@@@@@ MAP_2 is : "+ pointer);
         // String str1 = (String)paramMap1.get(localInteger);
          
          java.util.List<String> pointers = predictset.get(pointer);
		  // predict sets ne list ma add and ene table ma sodhvana terminals ne.....
          if(pointers != null) {
			for(String st: pointers) {
				int i = table_x.indexOf(st);
				if(i > -1)
				dataValues[y][i] = ""+s;
			}
		  }
          else if(ent.getKey().equals("stmlist")) {
				dataValues[y][2] = ""+1; dataValues[y][12] = ""+1; dataValues[y][14] = ""+1;
			}
			else if(ent.getKey().equals("statementTail")) {
				dataValues[y][2] = ""+2; dataValues[y][12] = ""+2; dataValues[y][14] = ""+2; dataValues[y][9] = ""+3;
			}
			else if(ent.getKey().equals("expression")) {
				dataValues[y][12] = ""+13; dataValues[y][10] = ""+13; dataValues[y][4] = ""+13;
			}
			else if(ent.getKey().equals("expressionTail")) {
				//dataValues[y][5] = ""+12;
			}
			else if(ent.getKey().equals("exprlist")) {
				dataValues[y][12] = ""+10; dataValues[y][10] = ""+10; dataValues[y][4] = ""+10;
			}
			else if(ent.getKey().equals("systemGoal")) {
				dataValues[y][13] = ""+21; 
			}
			else if(ent.getKey().equals("statement")) {
				//dataValues[y][2] = ""+5; dataValues[y][12] = ""+4; dataValues[y][14] = ""+6;
			}
			else if(ent.getKey().equals("primaryTail")) {
				dataValues[y][6] = ""+14; dataValues[y][7] = ""+15; dataValues[y][8] = ""+14; dataValues[y][11] = ""+15;
			}
			else if(ent.getKey().equals("idTail")) {
				dataValues[y][7] = ""+8; //dataValues[y][5] = ""+9; 
			}
			else {
		  }
        } 
        this.dataValues[7][2] = "5";this.dataValues[7][12] = "4";this.dataValues[7][14] = "6";
        this.dataValues[0][5] = "12";
        this.dataValues[11][5] = "9";
        

       // System.out.println();
      }
      else
      {
        System.out.println("************ No INDEX ??? ***************");System.out.println();
      }
      y++;
    }
    JTable table = new JTable(this.dataValues, columnNames);
    

    scrollPane = new JScrollPane( table );
	topPanel.add( scrollPane, BorderLayout.CENTER );
		
    return this.dataValues;
  }
  
  public int getMainIndex(String paramString)
  {
    int i = 0;
    String str = paramString.replaceAll("[<>]", "");
    if (str.equals("program")) {
      i = 6;
    } else if (str.equals("expressionTail")) {
      i = 0;
    } else if (str.equals("statementTail")) {
      i = 1;
    } else if (str.equals("expression")) {
      i = 2;
    } else if (str.equals("addop")) {
      i = 3;
    } else if (str.equals("exprlist")) {
      i = 4;
    } else if (str.equals("systemGoal")) {
      i = 5;
    } else if (str.equals("statement")) {
      i = 7;
    } else if (str.equals("idlist")) {
      i = 8;
    } else if (str.equals("stmlist")) {
      i = 9;
    } else if (str.equals("primaryTail")) {
      i = 10;
    } else if (str.equals("idTail")) {
      i = 11;
    } else if (str.equals("primary")) {
      i = 12;
    }
    return i;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    File testFile = new File("D:\\Fall 2014\\Compiler - Boris\\Programs\\input.txt");
	U_Compiler table1 = new U_Compiler();
	java.util.List<String> gen_code = new ArrayList<String>();
    
    GrammarAnalyzer14 grammar = new GrammarAnalyzer14();
	grammar.Analyze(testFile); // *************************************
	PredictGenerator14 pg = new PredictGenerator14();
    
    Map<String, java.util.List<String>> map = grammar.getMap();
	Map<String, java.util.List<String>> firstset = pg.firstSet_(map);
    
	Map<Integer, String> map_2 = grammar.getMap_2();				// ******************************
	Map<String, java.util.List<Integer>> map_1 = grammar.getMap_1();			// ******************************
    
    Set<String> terminals = grammar.getTerminals();	
    
    java.util.List<String> productions = grammar.getRightProductions();
	Set<String> non_terminal = grammar.getNonTerminals();
    java.util.List<String> non_terminals = new ArrayList<String>(non_terminal);
	java.util.List<String> left_prod = grammar.getLeftProd();
  //  System.out.println("Length of Productions : "+ productions.size() + " , & Left_prod length is : "+ left_prod.size());
  //  System.out.println("*************************************************");
    Map<String, java.util.List<String>> followSet = pg.followSet_(productions, non_terminals, left_prod);
    
    pg.predictSet(productions, firstset, followSet, left_prod);
    Map<String, java.util.List<String>> predictset = pg.getPredictSet();		// *************************
    
    java.util.List<String> terms = new ArrayList<String>(terminals);
    
    File localFile2 = new File("D:\\Fall 2014\\Compiler - Boris\\Programs\\input2.txt");
    Scanner14 localScanner14 = new Scanner14();
    java.util.List<String> inputtokens = localScanner14.generateTokens(localFile2);
    inputtokens.removeAll(Arrays.asList("", null ));
    String[][] return_datavalues = table1.LL1_Driverz(terms, non_terminals, map_1, map_2, predictset, inputtokens);
    
    CreateTokens createTokens = new CreateTokens();
    


    Iterator localIterator = inputtokens.iterator();
    int flag = 0;String current_token = "";
    int current_index = 0;
    int main_index = 5;
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	int _current_index = 1; int _left_index = 0; int _right_index = 0; int _top_index = 2;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
    Stack stack = new Stack();
	java.util.List<String> sem_stack = new ArrayList<String>();
	java.util.List<String> temp = new ArrayList<String>();
	
    stack.push("<" + return_datavalues[5][0] + ">");
	sem_stack.add("<" + return_datavalues[5][0] + ">");
    System.out.println("Parse Stack 	: " + stack);
	System.out.println("Semantic Stack 	: " + sem_stack);
    System.out.println();
    while (flag == 0)
    {
      current_token = (String)inputtokens.get(0);
      current_token = current_token.toLowerCase();
      System.out.println("Current token is :" + current_token + " & current_index is : " + current_index + " main index is : " + main_index);
      System.out.println();
      if (current_token.equals("begin")) {
        current_index = 13; 
	//	System.out.println("begin matched... ");
      } else if (current_token.equals("end")) {
        current_index = 9;
      } else if (current_token.equals("write")) {
        current_index = 14;
      } else if (current_token.equals("read")) {
        current_index = 2;
      } else if (current_token.equalsIgnoreCase("PlusOp")) {
        current_index = 6;	System.out.println(" ++++++++++ ");
      } else if (current_token.equalsIgnoreCase("MinusOp")) {
        current_index = 8;
      } else if (current_token.equalsIgnoreCase("SemiColon")) {
        current_index = 11;
      } else if (current_token.equalsIgnoreCase("AssignOp")) {
        current_index = 1;
      } else if (current_token.equalsIgnoreCase("LParen")) {
        current_index = 4;
      } else if (current_token.equalsIgnoreCase("RParen")) {
        current_index = 5;
      } else if (current_token.equalsIgnoreCase("Comma")) {
        current_index = 7;
      } else if (current_token.matches("^[a-zA-Z0-9_]*$")) {
        current_index = 12; 
	//	System.out.println("Coming... Matching ID");
      } else if (current_token.matches("[0-9]")) {
        current_index = 10; 
	//	System.out.println("Coming... Matching IntLiteral");
      } else {
		System.out.println("It should not come here....!!!! *************");
	  }
      String result = return_datavalues[main_index][current_index];
      if (result != null)
      {
        int prod = Integer.parseInt((String)result);
        
        String production = (String)productions.get(prod);
        String str4 = stack.pop().toString();
        

        java.util.List<String> xxx = createTokens.generateTokens(production);
		for (String st: xxx)
        {
			if(st.equals("l")){
			}
			else {
				sem_stack.add(st);
			}
        }
        Collections.reverse(xxx);
		
        for (String st: xxx)
        {
			if(st.equals("l")){
			}
			else {
			//System.out.println("`");
				stack.push(st);
			}
        }
		//System.out.println("------------------- "  + stack);
	//	Stack newStack = new Stack();
		//newStack = (Stack)stack.clone();
	//	Collections.copy(newStack, stack);
	/*	while(!stack.empty()) {
			sem_stack.add((String)newStack.pop());
		}
	*/
		int m = xxx.size();
		//System.out.println("------------------- "  + m);
        xxx.clear();
		//System.out.println("------------------- "  + stack);
        String top = stack.peek().toString();
        System.out.println("Top is " + (String)top + " & token is :" + current_token);
		boolean t1 = top.matches("^[a-zA-Z0-9_]*$") && current_token.matches("^[a-zA-Z0-9_]*$");
		boolean t2 = top.matches("^[0-9]*$") && current_token.matches("^[0-9]*$");
		System.out.println( ((String)top).equals(current_token) || t1 || t2 ); 
		System.out.println( t1 +" "+ t2 );
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		_left_index =  _current_index;
		_right_index = _top_index;
		_current_index = _right_index;
		_top_index = _top_index + m;
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		
		try {
			while (((String)top).equals(current_token) || (top.matches("^[a-zA-Z0-9_]*$") && current_token.matches("^[a-zA-Z0-9_]*$")) || (top.matches("^[0-9]*$") && current_token.matches("^[0-9]*$")))
			{
			  gen_code.add((String)inputtokens.get(0));
			  inputtokens.remove(0); 
			  System.out.println();
			  System.out.println(" T (" + top + " , " + current_token + ")" + " = " + result);
			  System.out.println(" Input : " + inputtokens );
			  System.out.println(" Parse Stack : " + stack); java.util.List<String> tempL  = new ArrayList<String>(sem_stack); Collections.reverse(tempL);
			  System.out.println(" Semantic Stack : " + tempL );
			  System.out.println(" ````` Places Token{SS()} : <<["+ current_token + "]>>");
			  System.out.println(" Indices	: " + "(" + _left_index + ","+ _right_index + "," + _current_index + "," + _top_index + ")");
			//  System.out.println();
			//  System.out.println("Matching..."); //System.out.println();
			  if(stack.empty()) {
				break;
			  }
			  else {
				stack.pop();
				top = stack.peek().toString();
			  }
			  _current_index = _current_index + 1;
			  current_token = (String)inputtokens.get(0);
			  current_token = current_token.toLowerCase();
			}
		} catch(Exception e) {
			System.out.println("******** Stack is empty... All the tokens have been parsed...!!! ************");
		}
        if ((((String)top).equals(current_token)) && (current_token.equals("end"))) {
          flag = 1;
        }
        main_index = table1.getMainIndex((String)top);
		System.out.println();
		System.out.println(" T (" + top + " , " + current_token + ")" + " = " + result);
        System.out.println(" Input	: " + inputtokens );
        System.out.println(" Parse Stack	: " + stack); java.util.List<String> tempL  = new ArrayList<String>(sem_stack); Collections.reverse(tempL);
		System.out.println(" Semantic Stack : " + tempL);
		System.out.println(" Indices	: " + "(" + _left_index + ","+ _right_index + "," + _current_index + "," + _top_index + ")");
      //  System.out.println("Gen. Code is : " + get_code);
        System.out.println();
      }
      else
      {
        System.out.println("Error occurred in table : at " + main_index + " , " + current_index + " for " + current_token);
        flag = 1;
      }
    }
  //  System.out.println("Size of input tokens are : " + inputtokens.size());
   /* for (Object localObject1 = inputtokens.iterator(); ((Iterator)localObject1).hasNext();)
    {
      String str2 = (String)((Iterator)localObject1).next();
      System.out.println("" + str2);
    }
    */
//	table1.setVisible(true);
  }
}
