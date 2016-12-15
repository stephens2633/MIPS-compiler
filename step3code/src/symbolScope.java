import java.io.*;
import java.util.*;



public  class symbolScope{
    private String scopeName;
    public LinkedHashMap<String,symbol> scopeMap;

    public symbolScope(String name){
	this.scopeName = name;
	this.scopeMap = new LinkedHashMap<String,symbol>();
    }
    
    public String getscopeName(){return this.scopeName;}
   
    public void addSymbol(String symName,symbol S1){
	//System.out.println(symName);
	//if(scopeMap.get(symName) != null){
	    //System.out.println("DECLARATION ERROR"+symName);
	    
	//}
	//else{    
	this.scopeMap.put(symName,S1);
	//}
    }
    
    public void printSymbols(){
	//System.out.println("\nSymbol table "+scopeName);
	Iterator<String> iter = scopeMap.keySet().iterator();
	while(iter.hasNext()){
	    String currentSymbol = iter.next();
	    
	    scopeMap.get(currentSymbol).symbolPrint();
	}
    }
       
}