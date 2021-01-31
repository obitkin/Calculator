import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

    static double calculate(String expression) {
        return parse(new StringBuilder("(" + expression + ")"));
    }

    static private double parse(StringBuilder expression) { //expression == 2+3+1*4-(3/2)
        int indexHookOpen, indexHookClose;
        indexHookClose = expression.indexOf(")");
        indexHookOpen  = expression.substring(0,indexHookClose).lastIndexOf("(");

        while (indexHookClose != -1 && indexHookOpen != -1) {
            System.out.println(expression.toString());
            double d = calculateWithoutHooks(new StringBuilder(expression.substring(indexHookOpen+1,indexHookClose)));
            expression.delete(indexHookOpen,indexHookClose+1);
            expression.insert(indexHookOpen, Double.toString(d));
            indexHookClose = expression.indexOf(")");
            if (indexHookClose != -1)
                indexHookOpen  = expression.substring(0,indexHookClose).lastIndexOf("(");
        }
        System.out.println(expression.toString());
        return Double.parseDouble(expression.toString());
    }

    static private double calculateWithoutHooks(StringBuilder expressionWithoutHooks) { // expressionWithoutHooks == (-1+3*2)
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

    static private ArrayList<String> parseTerms(StringBuilder expressionWithoutHooks) {  // expressionWithoutHooks == 1+3*2
        if (!checkExpressionWithoutHooks(expressionWithoutHooks.toString()))
            throw new RuntimeException("Illegal expression: " + expressionWithoutHooks.toString());

        ArrayList<String> terms = new ArrayList<>();
        char[] chars = expressionWithoutHooks.toString().toCharArray();
        StringBuilder term = new StringBuilder();

        for (Character i : chars) {
            if (i == '+' || i == '-' || i == '*' || i == '/') {
                if (term.length() == 0) {
                    if (i == '-') {
                        term.append(i);
                    }
                } else {
                    terms.add(term.toString());
                    terms.add(Character.toString(i));
                    term = new StringBuilder();
                }
            }
            else { // i == '0-9', '.'
                term.append(i);
            }
        }
        terms.add(term.toString());
        return terms;
    }

    static private boolean checkExpressionWithoutHooks(String expressionWithoutHooks) {
        return expressionWithoutHooks.matches(
                "(((\\+|-)?\\d+\\.?\\d+)|((\\+|-)?\\d+)){1}" +
                "(((\\+|-|\\*|/)(\\+|-)?\\d+\\.?\\d+)|((\\+|-|\\*|/)(\\+|-)?\\d+))*");
    }
}
