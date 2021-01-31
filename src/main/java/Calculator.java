import java.util.ArrayList;
import java.util.List;

public class Calculator {

    double parse(StringBuilder expression) { //expression == (2+3+1*4-(3/2))
        int indexHookOpen, indexHookClose;
        do {
            indexHookOpen = expression.indexOf("(", 1);
            indexHookClose = expression.indexOf(")");
            if (indexHookOpen < indexHookClose && indexHookOpen != -1) {
                parse(new StringBuilder(expression.substring(indexHookOpen,indexHookClose)));
            }
        } while (indexHookOpen < indexHookClose && indexHookOpen != -1);


        return 0;
    }

    double calculate(StringBuilder expressionWithoutHooks) { // expressionWithoutHooks == (1+3*2)
        ArrayList<String> terms = parseTerms(expressionWithoutHooks);
        String previous;
        for (String term : terms) {

        }
        return 0;
    }


    ArrayList<String> parseTerms(StringBuilder expressionWithoutHooks) {  // expressionWithoutHooks == (1+3*2)
        ArrayList<String> res = new ArrayList<>();
        String tmp = "";
        char[] chars = expressionWithoutHooks.toString().toCharArray();

        for (Character i : chars) {
            if (i == '-' || i == '+' || i == '*' || i == '/') {
                if (tmp.length() != 0) {
                    res.add(tmp);
                    tmp = "";
                }
                res.add(i.toString());
            }
            else if (i != '(' && i != ')'){
                tmp += i;
            }
            else {
                if (tmp.length() != 0)
                    res.add(tmp);
            }
        }
        return res;
    }
}
