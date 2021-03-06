import java.io.*;
import java.util.*;


public class TinyGenerator{
    public static ArrayList<String> TinyList = new ArrayList<String>();
    private static  ArrayList<String> TinyRL= new ArrayList<String>();
    public static Function currentFun;


    public static String addTinyRegister(String name){
	TinyRL.add(name);
	//System.out.println("Added: "+name);
	return "r"+Integer.toString(TinyRL.size()-1);
    }

    public static boolean isReg(String name){
	int i = 0;
	for(i=0;i<TinyRL.size();i++)
	    {
		if(TinyRL.get(i).equals(name))
		    return true;
	    }
	//System.out.println("not reg: "+name);
	return false;
    }

    public static String getRegNum(String name){
	int i = 0;
	if(name.startsWith("$L"))
	    return "$-"+name.substring(2);

	if(name.startsWith("$P"))
	    return "$"+Integer.toString(5+Integer.parseInt(name.substring(2)));


	if(name.startsWith("$R"))
	    return "$"+Integer.toString(currentFun.param_num+6);

	for(i=0;i<TinyRL.size();i++)
	    {
		if(TinyRL.get(i).equals(name))
		    return "r"+Integer.toString(i);
	    }
	return "";
    }

    public static void generateTiny(){
	
	int j = 0;
	Function F1;
	int i = 0;
	IRnodelist.buildMaster();
	IRnodelist.printList();
	System.out.println(";tiny code");
	symboltable.tinySymbols();
	

	//System.out.println(";IR code");
		TinyList.add("push");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("push r0");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("push r1");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("push r2");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("push r3");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("jsr main");
	System.out.println(TinyList.get(TinyList.size()-1));
	TinyList.add("sys halt");
	System.out.println(TinyList.get(TinyList.size()-1));

	for( i=0;i< NodeManager.FunctionStack.size();i++){
	    F1 = NodeManager.FunctionStack.get(i);
	    currentFun = F1;
	    for(j = 0;j<F1.nodeList.size();j++){
		//	F1.nodeList.get(j).printNode();
		parseIR(F1.nodeList.get(j));
		
	    }
	    TinyRL.clear();
	    //System.out.println();
	    
	}
	TinyList.add("end");
	System.out.println(TinyList.get(TinyList.size()-1));

	
    }

    public static void parseIR(IRnode node1){
	String opcode = node1.opcode;
	String op1 = node1.op1;
	String op2 =node1.op2;
	String result = node1.result;
	String optype = "";

	//	node1.printNode();
	//	System.out.println(opcode);


	if(opcode.startsWith("STORE")){
	    String second = "";
	    String intermediate = grabOp(op1);
	    // System.out.println(intermediate+" "+op1);
	    if(!op1.startsWith("$") && !result.startsWith("$")){
		 intermediate = addTinyRegister(op1);
		 TinyList.add("move "+op1+" "+intermediate);
		  System.out.println(TinyList.get(TinyList.size()-1));
	    }
	    //System.out.println("sda");
	    second = grabOp(intermediate);
	    if(result.startsWith("$R")){
	        second = addTinyRegister(intermediate);
		TinyList.add("move "+intermediate+" "+second);
		System.out.println(TinyList.get(TinyList.size()-1));
		}
	    result = grabOp(result);
	    
	    
	    TinyList.add("move "+second+" "+result);


	}

	//binary ops
	if(opcode.startsWith("ADDI")){
	    //store first op to reg
	    op1 = grabOp(op1);
	    result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
		//add second op to reg
	    op2 = grabOp(op2);	    
	    TinyList.add("addi "+op2+" "+result);
	    //    System.out.println(TinyList.get(TinyList.size()-1));	    
	}
	if(opcode.startsWith("ADDF")){
	    //store first op to reg
	    op1 = grabOp(op1);
	   result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
		//add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("addr "+op2+" "+result);
	    //  System.out.println(TinyList.get(TinyList.size()-1));	    
	}
	//do some

	else if(opcode.startsWith("SUBI")){
	    //store first op to reg
	    op1 = grabOp(op1);
	    result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
		//add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("subi "+op2+" "+result);
	    //  System.out.println(TinyList.get(TinyList.size()-1));	  	
	}

	else if(opcode.startsWith("SUBF")){
	    //store first op to reg
	    op1 = grabOp(op1);
	   result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
		//add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("subr "+op2+" "+result);
	    //   System.out.println(TinyList.get(TinyList.size()-1));	    	
	}
	else if(opcode.startsWith("MULTI")){
	    op1 = grabOp(op1);
	   result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
	    //add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("muli "+op2+" "+result);
	    //    System.out.println(TinyList.get(TinyList.size()-1));
	}

	else if(opcode.startsWith("MULTF")){
	    op1 = grabOp(op1);
	    result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
	    //add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("mulr "+op2+" "+result);
	    //System.out.println(TinyList.get(TinyList.size()-1));

	}


	else if(opcode.startsWith("DIVI")){
	    op1 = grabOp(op1);
	   result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
	    //add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("divi "+op2+" "+result);
	    //System.out.println(TinyList.get(TinyList.size()-1));
	}

	else if(opcode.startsWith("JSR")){
	    TinyList.add("push r0");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("push r1");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("push r2");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("push r3");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("jsr "+result.toLowerCase());
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("pop r3");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("pop r2");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("pop r1");
	    System.out.println(TinyList.get(TinyList.size()-1));
	    TinyList.add("pop r0");
	    
	}

	else if(opcode.startsWith("PUSH")){
	    op1 = grabOp(op1);
	    TinyList.add("push "+op1);
	    //System.out.println(TinyList.get(TinyList.size()-1));
	}

	else if(opcode.startsWith("POP")){
	    op1 = grabOp(op1);
	    TinyList.add("pop "+op1);
	    //System.out.println(TinyList.get(TinyList.size()-1));
	}

	else if(opcode.startsWith("LINK")){
	    TinyList.add("link "+currentFun.getlocalNum());//num of parameters
	}


	else if(opcode.startsWith("DIVF")){
	    op1 = grabOp(op1);
	    result = grabOp(result);
	    TinyList.add("move "+op1+" "+result);
	    System.out.println(TinyList.get(TinyList.size()-1));
	    //add second op to reg
	    op2 = grabOp(op2);
	    
	    TinyList.add("divr "+op2+" "+result);
	    // System.out.println(TinyList.get(TinyList.size()-1));
	}

	else if(opcode.startsWith("WRITEF")){
	   result = grabOp(result);
	    TinyList.add("sys writer "+result);
	}

	else if(opcode.startsWith("WRITEI")){
	    result = grabOp(result);
	    TinyList.add("sys writei "+result);
	}

	else if(opcode.startsWith("WRITES")){
	    result = grabOp(result);
	    TinyList.add("sys writes "+result);
	}

	else if(opcode.startsWith("READF")){
	    if(result.startsWith("$")){
		//	System.out.println(result);//---------------------------------
		if(isReg(result))
		    result = getRegNum(result);
		else
		    result =  getRegNum(result);
	    } //result = grabOp(result);
	    TinyList.add("sys readr "+result);
	}

	else if(opcode.startsWith("READI")){
	    if(result.startsWith("$")){
		//	System.out.println(result);//---------------------------------
		if(isReg(result))
		    result = getRegNum(result);
		else{
		    result =  getRegNum(result);
		    
		}
	    }
	    TinyList.add("sys readi "+result);
	}

	else if (opcode.startsWith("JUMP")){
	    TinyList.add("jmp "+result.toLowerCase());
	}
	
	else if (opcode.startsWith("LABEL")){
	    TinyList.add("label "+result.toLowerCase());
	}
	else if (opcode.startsWith("RET")){
	     TinyList.add("unlnk");
	     System.out.println(TinyList.get(TinyList.size()-1)); 
	     TinyList.add("ret");
	}


	else if (opcode.equals("GT")||opcode.equals("GE")||opcode.equals("EQ")||
		 opcode.equals("LT")||opcode.equals("LE")||opcode.equals("NE")){

	    String type = "";
	    if(op1.startsWith("$")){
		//System.out.println(op1+"in"+currentFun.name);//------------------------
		type = currentFun.getType(op1);
		if(type.matches("F"))
		    type = "r";
		else if(type.matches("I"))
		    type = "i";
	    }
	    else if(op1.contains(".")){
		type = "r";
	    }else if(op1.matches("[0-9]+")){
		type = "i";
	    }else{
		type = currentFun.getType(op1).substring(0,1);
		if(type.matches("F"))
		    type = "r";
		else if(type.matches("I"))
		    type = "i";
	    }

	   

	    String intermediate = grabOp(op2);
	    if(!op1.startsWith("$") && !op2.startsWith("$")){
		intermediate = addTinyRegister(op2);
		TinyList.add("move "+op2+" "+intermediate);
		System.out.println(TinyList.get(TinyList.size()-1));
	    }

	    op1 = grabOp(op1);
	    //op2 = grabOp(op2);
   
	    TinyList.add("cmp"+type+" "+op1+" "+intermediate);
	    System.out.println(TinyList.get(TinyList.size()-1)); 
	    
	    TinyList.add("j"+opcode.toLowerCase()+" "+result);

	}


	System.out.println(TinyList.get(TinyList.size()-1));   
    }


    private static String grabOp(String op){	
	if(op.startsWith("$T")){
	    if(isReg(op))
		return getRegNum(op);
	    else
		return addTinyRegister(op);
	}
	
	if(op.startsWith("$")){
	    return getRegNum(op);
	}
	return op;
    }
}

