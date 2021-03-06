grammar Micro;


@rulecatch {
   catch (RecognitionException e) {
    throw e;
   }
}

program:
	PROGRAM_KEYWORD 
	id 
	BEGIN_KEYWORD 
	pgm_body 
	END_KEYWORD;
id: 
    IDENTIFIER;
pgm_body:
	decl 
	func_declarations;
decl:
	string_decl 
	decl 
	| var_decl 
	decl 
	| ;

/* Global String Declaration */
string_decl:
	STRING_KEYWORD 
	id 
	':=' 
	str
	';';
str:
	STRINGLITERAL;

/* Variable Declaration */
var_decl:
	var_type 
	id_list
	';';
any_type:
	var_type 
	| VOID_KEYWORD; 
var_type:
	 FLOAT_KEYWORD 
	 | INT_KEYWORD;

id_list:
	id 
	id_tail;
id_tail:
	',' 
	id 
	id_tail 
	| ;

/* Function Paramater List */
param_decl_list:
	param_decl 
	param_decl_tail		
	| ;
param_decl:
	var_type 
	id
	;
param_decl_tail:
	',' 
	param_decl 
	param_decl_tail		
	| ;

/* Function Declarations */
func_declarations:
	func_decl 
	func_declarations 
	| ;
func_decl:
	FUNCTION_KEYWORD
	any_type 
	id 
	'('
	param_decl_list
	')' 
	BEGIN_KEYWORD
	func_body 
	END_KEYWORD;
func_body:
	decl 
	stmt_list; 

/* Statement List */
stmt_list:
	stmt 
	stmt_list 
	| ;
stmt:
	base_stmt 
	| if_stmt 
	| do_while_stmt;
base_stmt:
	assign_stmt 
	| read_stmt 
	| write_stmt 
	| return_stmt;

/* Basic Statements */
assign_stmt:
	assign_expr;
assign_expr:
	id 
	':=' 
	expr
	SEMI_OP;
read_stmt:
	'READ' 
	'('
	 id_list
	 ')'
	 ';';
write_stmt:
	WRITE_KEYWORD
	'(' 
	id_list
	')'
	';';
return_stmt:
	RETURN_KEYWORD 
	expr
	';';

/* Expressions */
expr:
	expr_prefix 
	factor;
expr_prefix:
	expr_prefix 
	factor 
	addop 
	| ;
factor:
	factor_prefix 
	postfix_expr;
factor_prefix:
	factor_prefix 
	postfix_expr 
	mulop 
	| ;
postfix_expr:
	primary 
	| call_expr;
call_expr:
	id 
	'(' 
	expr_list 
	')';
expr_list:
	expr 
	expr_list_tail 
	| ;
expr_list_tail:
	',' 
	expr 
	expr_list_tail 
	| ;
primary:
	'('
	expr
	')' 
	| id 
	| INTLITERAL 
	| FLOATLITERAL;
addop:
	'+' 
	| '-';
mulop:
	'*' 
	| '/'
	;

/* Complex Statements and Condition */ 
if_stmt:
	IF_KEYWORD 
	'(' 
	cond 
	')' 
	decl 
	stmt_list 
	else_part 
	ENDIF_KEYWORD;
else_part:
	ELSIF_KEYWORD
	'(' 
	cond 
	')' 
	decl 
	stmt_list 
	else_part 
	| ;
cond:
	expr 
	compop 
	expr 
	| TRUE_KEYWORD 
	| FALSE_KEYWORD;
compop:
	'<' 
	| '>' 
	| '=' 
	| '!=' 
	| '<=' 
	| '>=';

do_while_stmt:
	DO_KEYWORD 
	decl 
	stmt_list 
	WHILE_KEYWORD
	'(' 
	cond 
	')'
	';';


PROGRAM_KEYWORD:
	'PROGRAM';
BEGIN_KEYWORD:
	'BEGIN';
END_KEYWORD:
	'END';
FUNCTION_KEYWORD:
	'FUNCTION';
READ_KEYWORD:
	'READ';
WRITE_KEYWORD:
	'WRITE';
IF_KEYWORD:
	'IF';
ELSIF_KEYWORD:
	'ELSIF';
ENDIF_KEYWORD:
	'ENDIF';
DO_KEYWORD:
	'DO';
WHILE_KEYWORD:
	'WHILE';
CONTINUE_KEYWORD:
	'CONTINUE';
BREAK_KEYWORD:
	'BREAK';
RETURN_KEYWORD:
	'RETURN';
INT_KEYWORD:
	'INT';
VOID_KEYWORD:
	'VOID';
STRING_KEYWORD:
	'STRING';
FLOAT_KEYWORD:
	'FLOAT';
TRUE_KEYWORD:
	'TRUE';
FALSE_KEYWORD:
	'FALSE';

ASSIGN_OP: ':=';

ADD_OP:  '+';

SUB_OP:  '-';

DIV_OP:  '/';

EQ_OP:  '=';

MUL_OP:  '*';

NEQ_OP:  '!=';

LT_OP:  '<';

GT_OP:  '>';

PAREN_OP:  '(';

CPAREN_OP:  ')';

SEMI_OP:  ';';

COM_OP:  ',';

LTE_OP:  '<=';

GTE_OP:  '>=';

WHITESPACE:  
	     (' ' | '\t' | '\n' | '\r' | '\f')+ -> skip; 

IDENTIFIER: 
	    ('+')|[A-z_][A-z0-9_]*;

INTLITERAL: 
	    [0-9]+;

FLOATLITERAL: 
	      [0-9]*?['.'][0-9]*;

STRINGLITERAL: 
	       '"'(~('\n'|'\r'))*?'"';

COMMENT: 
	 '--'(~('\r'|'\n'))* -> skip;
	
