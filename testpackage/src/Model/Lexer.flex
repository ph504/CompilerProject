package test_phase3.parvizi.Model;
import java_cup.runtime.Symbol;import testpackage.parvizi.Model.sym;
%%

%public
%class DecafScanner
%standalone
%unicode

%type Symbol
%char
%cup


%{

public Symbol token(int tokenType, String mySym){

    //System.out.println(yytext());
    return new Symbol(tokenType, mySym);
}

%}

intLit = [0-9]+
intLit_cmx = [0][x|X][0-9a-fA-F]+
doubleLit = [0-9]+[.][0-9]*
doubleLit_cmx = [0-9]+[.][0-9]*[e|E][+|-]?[0-9]+
identifier = [A-Za-z_$][A-Za-z_$0-9]*
stringLit = \" ~\"
cer = [\n|\r|\n\r]

%%
// -----------------------------------------------------------rule part ------------------------------------------------
// ------------------------------------------------------Key word Token rules ------------------------------------------

int             {return token(sym.INT,  yytext());}
double          {return token(sym.DOUBLE,  yytext());}
void            {return token(sym.VOID,  yytext());}
bool            {return token(sym.BOOL,  yytext());}
string          {return token(sym.STRING,  yytext());}
class           {return token(sym.CLASS,  yytext());}
interface       {return token(sym.INTERFACE,  yytext());}
null            {return token(sym.NULL,  yytext());}
this            {return token(sym.THIS,  yytext());}
extends         {return token(sym.EXTENDS,  yytext());}
implements      {return token(sym.IMPLEMENTS,  yytext());}
for             {return token(sym.FOR, yytext());}
while           {return token(sym.WHILE,  yytext());}
if              {return token(sym.IF,  yytext());}
else            {return token(sym.ELSE,  yytext());}
return          {return token(sym.RETURN,  yytext());}
break           {return token(sym.BREAK,  yytext());}
continue        {return token(sym.CONTINUE,  yytext());}
new             {return token(sym.NEW,  yytext());}
NewArray        {return token(sym.NEWARRAY,  yytext());}
Print           {return token(sym.PRINT,  yytext());}
ReadInteger     {return token(sym.READINTEGER,  yytext());}
ReadLine        {return token(sym.READLINE,  yytext());}

dtoi            {return token(sym.DTOI,  yytext());}
itod            {return token(sym.ITOD,  yytext());}
btoi            {return token(sym.BTOI,  yytext());}
itob            {return token(sym.ITOB,  yytext());}
private         {return token(sym.PRIVATE,  yytext());}
protected       {return token(sym.PROTECTED,  yytext());}
public          {return token(sym.PUBLIC,  yytext());}

// ----------------------------------------------- Keywords and boolean literals ---------------------------------------

true            {return token(sym.TRUE,  yytext());}
false           {return token(sym.FALSE,  yytext());}

"="             {return token(sym.ASSIGN,  yytext());}
"+"             {return token(sym.PLUS,  yytext());}
"-"             {return token(sym.MINUS,  yytext());}
"*"             {return token(sym.TIMES,  yytext());}
"/"             {return token(sym.DIVIDE,  yytext());}
"%"             {return token(sym.MOD,  yytext());}

">"             {return token(sym.GT,  yytext());}
">="            {return token(sym.GE,  yytext());}
"<"             {return token(sym.LT,  yytext());}
"<="            {return token(sym.LE,  yytext());}
"=="            {return token(sym.EQ,  yytext());}
"!="            {return token(sym.NE,  yytext());}
"!"             {return token(sym.NOT,  yytext());}
"&&"            {return token(sym.AND,  yytext());}
"||"            {return token(sym.OR,  yytext());}

";"             {return token(sym.SEMI,  yytext());}
","             {return token(sym.COMMA,  yytext());}
"."             {return token(sym.DOT,  yytext());}

"[]"            {return token(sym.BRACKETS,  yytext());}
"["             {return token(sym.LBRACK,  yytext());}
"]"             {return token(sym.RBRACK,  yytext());}
"("             {return token(sym.LPAREN,  yytext());}
")"             {return token(sym.RPAREN,  yytext());}
"{"             {return token(sym.LCURLY,  yytext());}
"}"             {return token(sym.RCURLY,  yytext());}


// ---------------------------------------------------- Lexer ignored rules --------------------------------------------

[" "]           {/* no operation*/}
"/*" ~"*/"      {/* no operation*/}
\/\/ ~{cer}     {/* no operation*/}
{cer}           {/* no operation*/}

//// ----------------------------------------------------String literals -----------------------------------------------

{stringLit}         {return token(sym.STRINGLIT,  yytext());}

//// --------------------------------------------- Integer and double literals -----------------------------------------

{intLit_cmx}        {return token(sym.INTLIT,  yytext());}
{intLit}            {return token(sym.INTLIT,  yytext());}
{doubleLit_cmx}     {return token(sym.DOUBLELIT,  yytext());}
{doubleLit}         {return token(sym.DOUBLELIT, yytext());}

//// ------------------------------------------------- Identifier ------------------------------------------------------

{identifier}        {return token(sym.ID,  yytext());}

//to Generate lexical Scanner :
//cd src
//java -jar jflex-full-1.8.2.jar Lexer.flex
//java -jar java-cup-11b.jar DecafParser.cup