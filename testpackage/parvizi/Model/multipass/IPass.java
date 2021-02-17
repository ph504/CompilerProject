package Model.multipass;

public abstract class IPass {
    //public static IPass pass = null;
    public static String pass = null;

    /*Program ::= Decl DeclPlus;
    DeclPlus ::= Decl DeclPlus | ;
    Decl ::= VariableDecl | FunctionDecl | ClassDecl | InterfaceDecl;
    FunctionDecl ::= Type ID LPAREN Formals RPAREN StmtBlock | VOID ID LPAREN Formals RPAREN
            StmtBlock ;
    Formals ::= Variable VariableComma | ;
    VariableComma ::= COMMA Variable VariableComma | ;
    ClassDecl ::= CLASS ID ExtendsEps ImplementsEps LCURLY FieldStar RCURLY;
    ExtendsEps ::= EXTENDS ID | ;
    ImplementsEps ::= IMPLEMENTS ID ImplemetsComma | ;
    ImplemetsComma ::= COMMA ID ImplemetsComma | ;
    FieldStar ::= Field FieldStar | ;
    Field ::= AccessMode VariableDecl | AccessMode FunctionDecl ;
    AccessMode ::= PRIVATE | PROTECTED | PUBLIC | ;
    InterfaceDecl ::= INTERFACE ID LCURLY PrototypeStar RCURLY ;
    PrototypeStar ::= Prototype PrototypeStar | ;
    Prototype ::= Type ID LPAREN Formals RPAREN SEMI | VOID ID LPAREN Formals RPAREN SEMI;
    StmtBlock ::= LCURLY VariableDeclStar RCURLY;
    VariableDeclStar ::= VariableDecl VariableDeclStar | StmtStar;
    StmtStar ::= Stmt StmtStar | ;
    Stmt ::= ExprEps SEMI | ForStmt | WhileStmt | IfStmt | BreakStmt | ContinueStmt | ReturnStmt | PrintStmt | StmtBlock;

    VariableDecl ::= Variable SEMI;
    Variable ::= Type:t ID:idName{: if (IPass.pass.equals("first")){SymbolTable.st.addEntry(idName, t);}
        if (IPass.pass.equals("second")){
            if(t instanceof IntType || t instanceof BoolType){CodeGen.declIntCgen(idName);}
            if (t instanceof DoubleType){CodeGen.declDoubleCgen(idName);}}:};

    ForStmt ::= FOR LPAREN ExprEps SEMI Expr SEMI ExprEps RPAREN Stmt;
    ExprEps ::= Expr | ;

    ReturnStmt ::= RETURN ExprEps SEMI;
    BreakStmt ::= BREAK SEMI;
    ContinueStmt ::= CONTINUE SEMI;

    Call ::= ID LPAREN Actuals RPAREN | Expr DOT ID LPAREN Actuals RPAREN;
    WhileStmt ::= WHILE LPAREN Expr RPAREN Stmt;
    IfStmt ::= IF LPAREN Expr RPAREN {:*//*bne $t1, $t2, else1*//*:} Stmt {:*//*jump to c1*//* *//* (branch) else label put here.*//* :} ElseStmtEps {:*//* put here C1:*//*:};
    ElseStmtEps ::= ELSE Stmt | ;
    PrintStmt ::= PRINT LPAREN Expr ExprComma RPAREN SEMI;
    ExprComma ::= COMMA ExprComma | ;


    Expr ::= LValue:lval ASSIGN Expr:e1
    {:if (IPass.pass.equals("second")) {RESULT = OpHandler.assign(lval, e1); } :}
      | Constant:c
    {:if (IPass.pass.equals("second")) {RESULT = c;}:}
      | THIS
      | Call
      | LPAREN Expr RPAREN
      | Expr:e1 PLUS Expr:e2
    {:if (IPass.pass.equals("second")) {RESULT = OpHandler.add(e1, e2);}
        IPass.pass.operate();
      :}
      | Expr:e1 MINUS Expr:e2
    {:if (IPass.pass.equals("second")){RESULT = OpHandler.subtract(e1, e2);} IPass.pass.operate();:}
      | Expr:e1 TIMES Expr:e2
    {:if (IPass.pass.equals("second")) {RESULT = OpHandler.multiply(e1, e2);} IPass.pass.operate();:}
      | Expr:e1 DIVIDE Expr:e2
    {:if (IPass.pass.equals("second")) {RESULT = OpHandler.divide(e1, e2);} IPass.pass.operate();:}
      | Expr:e1 MOD Expr:e2
    {:if (IPass.pass.equals("second")) {RESULT = OpHandler.mod(e1, e2);} IPass.pass.operate();:}
      | MINUS Expr:e1
    {: if (IPass.pass.equals("second")) {RESULT = OpHandler.negate(e1);} IPass.pass.operate();:}
      | Expr:e1 GT:op Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op);}:}
      | Expr:e1 LT:op2 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op2);}:}
      | Expr:e1 LE:op3 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op3);} :}
      | Expr:e1 GE:op4 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op4);} :}
      | Expr:e1 EQ:op5 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op5);} :}
      | Expr:e1 NE:op6 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.compare(e1, e2, op6);}:}
      | Expr:e1 AND:op7 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.opBool(e1, e2, op7);} :}
      | Expr:e1 OR:op8 Expr:e2 {:if (IPass.pass.equals("second")){RESULT = OpHandler.opBool(e1, e2, op8);}:}
      | NOT:op9 Expr:e1 {:if (IPass.pass.equals("second")){RESULT = OpHandler.opBool(e1, new BoolType(), op9);}:}
      | READINTEGER LPAREN RPAREN
      | READLINE LPAREN RPAREN
      | NEW ID
      | NEWARRAY LPAREN Expr COMMA Type RPAREN
     | ITOD LPAREN Expr RPAREN
     | DTOI LPAREN Expr RPAREN
      | ITOB LPAREN Expr:intVal RPAREN {:if (IPass.pass.equals("second")) {RESULT = OpHandler.itob(intVal);} :}
      | BTOI LPAREN Expr:boolVal RPAREN {:if (IPass.pass.equals("second")) {RESULT = OpHandler.btoi(boolVal);}:}
      | RValue:e
    {:RESULT = e;:} ;

    Type ::= INT
    {:RESULT = new IntType(); :}
      | DOUBLE
    {:RESULT = new DoubleType();:}
      | BOOL
    {:RESULT = new BoolType();:}
      | STRING
    {:RESULT = new StringType();:}
      | ID
      | Type BRACKETS;

    RValue ::= ID:idName2
    {:*//*read from memory and push tu stack the value of id*//*

        DSCP variable = SymbolTable.st.getDSCP(idName2);

        if (IPass.pass.equals("second"))
        {
            if((variable) instanceof IntType)
            {
                RESULT = variable;
                CodeGen.fetchRVal(variable);
            }

            if(variable instanceof DoubleType)
            {
                RESULT = variable;
                // CodeGen.fetchRValDouble(variable);
            }
        }
        :}
| Expr DOT ID | Expr LBRACK Expr RBRACK;

    LValue ::= ID:idName
    {:if(IPass.pass.equals("second")){ RESULT = SymbolTable.st.getDSCP(idName);}:}
      | Expr DOT ID
      | Expr LBRACK Expr RBRACK;

    Constant ::= INTLIT:idnumber
    {:if(IPass.pass.equals("second")){RESULT = new IntType() ; CodeGen.loadConstantIntCgen(Integer.parseInt(idnumber));  CodeGen.pushMips();} IPass.pass.operate();:}
          | DOUBLELIT:doubleNumber {:if(IPass.pass.equals("second")){RESULT = new DoubleType() ; CodeGen.loadConstantDoubleCgen(Double.parseDouble(doubleNumber));  CodeGen.pushMipsDouble();} IPass.pass.operate();:}
          | BooleanConstant:boolVal{: if(IPass.pass.equals("second")){RESULT = new BoolType(); CodeGen.loadConstantBoolCgen( Boolean.parseBoolean(boolVal)); CodeGen.pushMips();} IPass.pass.operate();:}
          | STRINGLIT
          | NULL;

    Actuals ::= Expr ExprComma | ;
    BooleanConstant ::= TRUE:t {:RESULT = t;:} | FALSE:f {:RESULT = f;:};*/
}
