package Model;

import Model.typeSystem.DSCP;

import java.util.Stack;

public class SemanticStack {
    public static final Stack <DSCP> stack = new Stack<>();

    public SemanticStack() {
        System.out.println("stack created");
    }

    public static void push(DSCP obj){
        stack.push(obj);
    }

    public static DSCP pop(){
        return stack.pop();
    }

    public static DSCP peek(){  // top of stack
        return stack.peek();
    }

    public static void printStack(){

        /*for (var val: stack) {
            System.out.println("-> "+val);
        }*/
    }
}