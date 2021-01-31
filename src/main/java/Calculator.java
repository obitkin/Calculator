import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

    static double calculate(String expression) {
        return parse(new StringBuilder("(" + expression + ")"));
    }

    static private double parse(StringBuilder expression) { //expression == (2+3+1*4-(3/2))
        int indexHookOpen, indexHookClose;
        do {
            indexHookOpen = expression.indexOf("(", 1);
            indexHookClose = expression.indexOf(")");
            double d;
            if (indexHookOpen < indexHookClose && indexHookOpen != -1 && indexHookClose != -1) {
                d = parse(new StringBuilder(expression.substring(indexHookOpen,indexHookClose+1)));
                expression.delete(indexHookOpen,indexHookClose+1);
                expression.insert(indexHookOpen, Double.toString(d));
            }
        } while (indexHookOpen < indexHookClose && indexHookOpen != -1);
        return calculateHooks(expression);
    }

    static private double calculateHooks(StringBuilder expressionWithoutHooks) { // expressionWithoutHooks == (-1+3*2)
        ArrayList<String> terms = parseTerms(expressionWithoutHooks); // '-1' '+' '3' '*' '2'
        while (terms.contains("*") || terms.contains("/")) {
            for (int i = 0; i < terms.size(); i++) {
                if (terms.get(i).equals("*")) {
                    double resOfMul = Double.parseDouble(terms.get(i-1)) * Double.parseDouble(terms.get(i+1));
                    terms.set(i, Double.toString(resOfMul));
                    terms.remove(i+1);
                    terms.remove(i-1);
                    break;
                }
                else if (terms.get(i).equals("/")) {
                    double resOfDiv = Double.parseDouble(terms.get(i-1)) / Double.parseDouble(terms.get(i+1));
                    terms.set(i, Double.toString(resOfDiv));
                    terms.remove(i+1);
                    terms.remove(i-1);
                    break;
                }
            }
        }
        while (terms.contains("+") || terms.contains("-")) {
            for (int i = 0; i < terms.size(); i++) {
                if (terms.get(i).equals("+")) {
                    double resOfMul = Double.parseDouble(terms.get(i-1)) + Double.parseDouble(terms.get(i+1));
                    terms.set(i, Double.toString(resOfMul));
                    terms.remove(i+1);
                    terms.remove(i-1);
                    break;
                }
                else if (terms.get(i).equals("-")) {
                    double resOfDiv = Double.parseDouble(terms.get(i-1)) - Double.parseDouble(terms.get(i+1));
                    terms.set(i, Double.toString(resOfDiv));
                    terms.remove(i+1);
                    terms.remove(i-1);
                    break;
                }
            }
        }
        return Double.parseDouble(terms.get(0));
    }


    static private ArrayList<String> parseTerms(StringBuilder expressionWithoutHooks) {  // expressionWithoutHooks == (1+3*2)
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
