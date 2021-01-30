public class Calculator {

    double parse(StringBuilder expression) { //expression == (2+3+1*4-(3/2))
        int indexHookOpen, indexHookClose;
        do {
            indexHookOpen = expression.indexOf("(", 1);
            indexHookClose = expression.indexOf(")");
            if (indexHookOpen < indexHookClose && indexHookOpen != -1) {
                parse(new StringBuilder(expression.toString().substring(indexHookOpen,indexHookClose)));
            }
        } while (indexHookOpen < indexHookClose && indexHookOpen != -1);


        return 0;
    }

    double calculate(StringBuilder expressionWithoutHooks) { // expressionWithoutHooks == (1+3*2)
        int indexMul = expressionWithoutHooks.indexOf("*");
        int indexDiv = expressionWithoutHooks.indexOf("/");
        while (indexMul != -1 || indexDiv != -1) {
            if (indexMul < indexDiv) {

            }
        }
        return 0;
    }



}
