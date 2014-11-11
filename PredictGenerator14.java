
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;

public class PredictGenerator14 {
	
	Map<String, List<String>> firstset = new HashMap<String, List<String>>();
	Map<String, List<String>> followSet = new HashMap<String, List<String>>();
	List<String> sets = new ArrayList<String>();
	public Map<String, List<String>> predictset = new HashMap<String, List<String>>();
	
	int count_prod = 0;
	
	public PredictGenerator14() {
		
	}
	public void computeFirst(String v, Map<String, List<String>> map) {
		String word = ""; int counter = 0; List<String> list = new ArrayList<String>(); 
		for(int i=0; i<v.length(); i++){
			char check = v.charAt(i);
			if(check == '<') {
				counter = 1;
			}
			else if(check != '<' && counter == 1 && check != '>') {
				word = word + check;
			}
			else if(check == '>') {
				counter = 0; list.add("<" + word + ">");  /*System.out.println("*** " + word); */ word = "";
			}
			else {}
		}
		for(String w: list) {
			int x = 0;
			for (Map.Entry<String,List<String>> entry : map.entrySet()) {
				
				String cmp = entry.getKey();
				//System.out.println("key is "+ cmp + " && w is " + w);
				if(cmp.equals(w)) {
					String key = entry.getKey();
					List<String> value = entry.getValue();
					//System.out.println("Inside......!!!");
					for(String v1: value) {
						//System.out.println("String is ::: " + v1);System.out.println();
						if(v1.startsWith("l")) {
							this.sets.add("l");
						}
						else if(v1.startsWith("<")) {
							computeFirst(v1, map); x = 1;
						} else {
							
							x = 1;
							if(v1.contains("<")) {
								String[] st = v1.split("<"); //System.out.println("Ave che ahi..." + st[0]);
								this.sets.add(st[0]);
							}
							else {
								this.sets.add(v1); //System.out.println("---- "+ v1);
							}
						}
					}
				}
				else {
					//System.out.println(" System Error!");
				}
			
			}
			if(x == 1) {
				break;
			}
		
		}
			
	}
	
	public boolean derivesLamda(String non_t) {
		return true;
	}
	
	public Map<String, List<String>> firstSet_(Map<String, List<String>> map) {
		
		for (Map.Entry<String,List<String>> entry : map.entrySet()) {
			
			String key = entry.getKey();
			List<String> value = entry.getValue();
			for(String v: value) {
				if(v.startsWith("l")) {
					this.sets.add("l");
				}
				else if(v.startsWith("<")) {
				//System.out.println("String is :::" + v);
					computeFirst(v, map);
										
				} else {
					if(v.contains("<")) {
						String[] st = v.split("<");
						this.sets.add(st[0]);
					}
					else
						this.sets.add(v);
				}
			}
			List<String> dummy = new ArrayList<String>(this.sets);
			Collections.copy(dummy, this.sets);
			this.firstset.put(key, dummy);
		/*	System.out.println("First Set of : " + key + " is");
			for(String ddd : dummy) {
				System.out.print(" "+ ddd + " ");
			}
			System.out.println(); System.out.println(); */
			this.sets.clear();
		}
		/*for(Map.Entry<String, List<String>> ent : this.firstset.entrySet()) {
			System.out.println("non_t :" + ent.getKey() + "First_Set : ");
			for(String s : ent.getValue()) {
				System.out.print("" + s + " / ");
			}System.out.println(); System.out.println();
		}*/
		/*for (Map.Entry<String,List<String>> entry1 : this.firstset.entrySet()) {
		  String key1 = entry1.getKey();
		  List<String> value1 = entry1.getValue();
		  System.out.print("NonT :" + key1 + "  FirstSet :");
		  for(String i1 : value1)
			System.out.print("" + i1 +"|"); 
		  System.out.println();
		  System.out.println();
		  
		}*/
		return this.firstset;
	}
	
	public Map<String, List<String>> followSet_(List<String> productions, List<String> non_terminals, List<String> left_prod) {
		for(String nonT : non_terminals) {
			if(nonT.equals("systemGoal")) {
				List<String> xyz = new ArrayList<String>(); xyz.add("l");
				followSet.put(nonT, xyz);
			}
			else {
				for(String prod : productions) {
					//count_prod++;
					if(prod.contains(nonT)) {
						int l = prod.indexOf(nonT);
						int index = l + nonT.length(); 
						//System.out.println("String is : "+ prod + " , NonT is : "+ nonT+ " , index is "+ index + " , Prod Length is : "+ prod.length());
						if(prod.length() - index > 1 ) {
							prod = prod.substring(index+2, prod.length());
							if(prod.length() > 0) {
								if(prod.startsWith("<")) {
									int ll = prod.indexOf(">");
								//	System.out.println("prod is : "+ prod);
									prod = prod.substring(1, ll);
									prod = "<"+ prod + ">";
									List<String> follow = new ArrayList<String>();
							//		System.out.println("Key is  : "+ prod + " , length of firstset is : "+ firstset.containsKey(prod));
									follow = firstset.get(prod); 	// if firstste contains lambda, check
									
									for(String flw : follow) {
										if(flw.equals("l")) {
											List<String> follow_left = fillfollow(left_prod.get(count_prod), productions, left_prod);
											for(String gh : follow_left) {
												follow.add(gh);
											}
											List<String> checking = followSet.get(nonT);
											if(checking.isEmpty())
												followSet.put(nonT, follow);
											else {
												for(String ch : checking) {
													follow.add(ch);
												}
												followSet.put(nonT, follow);
											}
										}
										else {
											List<String> checking = followSet.get(nonT);
											if(checking.isEmpty())
												followSet.put(nonT, follow);
											else {
												for(String ch : checking) {
													follow.add(ch);
												}
												followSet.put(nonT, follow);
											}
											
										}
									}
								}
								else {
									List<String> xyz = new ArrayList<String>(); xyz.add(prod);
									List<String> checking = new ArrayList<String>();
									if(followSet.get(nonT) != null) {
										checking = followSet.get(nonT);
									}
									if(checking.isEmpty())
										followSet.put(nonT, xyz);
									else {
										for(String ch : checking) {
											xyz.add(ch);
										}
										followSet.put(nonT, xyz);
									}
									
								}
							}
						}
						else {
							// if there is no production, then follow(nonT)
							//System.out.println("## String is : "+ prod + " , NonT is : "+ nonT+ " , index is "+ index + " , Prod Length is : "+ prod.length());
							List<String> left_f = new ArrayList<String>();
							//System.out.println("Index count_prod is : "+ count_prod + " , length of left_prod is : " + left_prod.size());
							String check = left_prod.get(count_prod);
							if(fillfollow(check, productions, left_prod) != null)
								left_f = fillfollow(check, productions, left_prod);
							List<String> checking = new ArrayList<String>();
							if(followSet.get(nonT) != null) {
								checking = followSet.get(nonT);
							}
							if(checking.isEmpty())
								followSet.put(nonT, left_f);
							else {
								for(String ch : checking) {
									left_f.add(ch);
								}
								followSet.put(nonT, left_f);
							}
							
						}
					}
					int lll = left_prod.size();
					if(count_prod > lll-2){
						//System.out.println("jay che andar.......");
					}
					else {
						//System.out.println("Else ma .... "+ count_prod);
						count_prod++;
					}
					//System.out.println("Count is : "+ count_prod);
				}
			}
		}
	/*	for(Map.Entry<String, List<String>> ent : this.followSet.entrySet()) {
			System.out.println("non_t :" + ent.getKey() + "  &Follow_Set : ");
			for(String s : ent.getValue()) {
				System.out.print("" + s + "  ");
			}System.out.println(); System.out.println();
		} */
		return followSet;
	}
	
	public List<String> fillfollow(String nont, List<String> productions, List<String> left_prod) {
		int fill_c = 0;
		List<String> list_fill = new ArrayList<String>();
		for(String prod : productions) {
			//count_prod++;
			if(prod.contains(nont)) {
				int l = prod.indexOf(nont);
				int index = l + nont.length();
				//System.out.println("*** String is : "+ prod + " , NonT is : "+ nont+ " , index is "+ index + " , Prod Length is : "+ prod.length());
				if(prod.length() - index > 1) {
					//System.out.println(" INside....");
					prod = prod.substring(index+2, prod.length());
					if(prod.length() > 0) {
						/*if(prod.startsWith("<")) {
							int ll = prod.indexOf(">");
							prod.substring(1, ll-1);
							List<String> follow = firstset.get(prod); // if firstste contains lambda, check
							for(String flw : follow) {
								if(flw.equals("l")) {
									List<String> follow_left = fillfollow(left_prod.get(count_prod), productions, left_prod);
									for(String gh : follow_left) {
										follow.add(gh);
									}
									followSet.put(nonT, follow);
								}
								else {
									followSet.put(nonT, follow);
								}
							}
						}*/
						//else {
							
							list_fill.add(prod);
						//}
					}
				}
				else {
					//System.out.println("Outside...!!!");
					// if there is no production, then follow(nonT)
					/*List<String> left_f = fillfollow(left_prod.get(count_prod), productions, left_prod);
					followSet.put(nonT, left_f);*/
				}
			}
		}
		return list_fill;
	}
	
	public void predictSet(List<String> productions, Map<String, List<String>> firstSet, Map<String, List<String>> followSet, List<String> left_prod) {
		int index_pred = 0; int check_l = 0;
		for(String production : productions) {
			++index_pred;
			if(production.equals("l")) {
				++check_l ;
				String find = left_prod.get(index_pred);
				List<String> predict = this.followSet.get(find);
				if(check_l > 1) {
					String prd = "l"+check_l;
					this.predictset.put(prd, predict);
				}
				else this.predictset.put(production, predict);
			}
			else if(production.startsWith("<")){
				String org = production;
				int ind = production.indexOf(">");
				production = production.substring(1, ind-1);
				List<String> predict = this.firstset.get(production);
				this.predictset.put(org, predict);
			}
			else {
				String[] find = production.split("<");
				List<String> predict = new ArrayList<String>();
				predict.add(find[0]);
				this.predictset.put(production, predict);
			}
		}
		System.out.println("*******************************************************");
		/* for(Map.Entry<String, List<String>> ent : this.predictset.entrySet()) {
			System.out.print("non_t: " + ent.getKey() + "  & Predict_Set: ");
			if(ent.getValue() != null) {
				for(String s : ent.getValue()) {
					System.out.print("" + s + "  ");
				}System.out.println(); System.out.println();
			}
			else {
				System.out.println("List is getting Null");System.out.println(); 
			}
		} */
		
	}
	
	public Map<String, List<String>> getPredictSet() {
		return predictset;
	}
	
		
	/*public static void main(String[] args){
		File testFile = new File("D:\\Fall 2014\\Compiler - Boris\\Programs\\input.txt");
		PredictGenerator14 pg = new PredictGenerator14();
		GrammarAnalyzer14 grammar = new GrammarAnalyzer14();
		grammar.Analyze(testFile); // *************************************
		Map<String, List<String>> map = grammar.getMap();
		Map<String, List<String>> firstset = pg.firstSet_(map);
		
		List<String> productions = grammar.getRightProductions();
		Set<String> non_terminal = grammar.getNonTerminals();
		List<String> non_terminals = new ArrayList<String>(non_terminal);
		List<String> left_prod = grammar.getLeftProd();
		System.out.println("Length of Productions : "+ productions.size() + " , & Left_prod length is : "+ left_prod.size());
		System.out.println("*************************************************");
		Map<String, List<String>> followSet = pg.followSet_(productions, non_terminals, left_prod);
		
		pg.predictSet(productions, firstset, followSet, left_prod);
	}*/
}