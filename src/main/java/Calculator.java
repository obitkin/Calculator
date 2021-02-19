import java.util.ArrayList;

public class Calculator {

    static double calculate(String expression) {
        return parse(new StringBuilder(expression));
    }

    //expression == (3+(-1+3*-2))
    static private double parse(StringBuilder expression) {
        int indexOpen, indexClose;
        Double resOfTerm;
        do {
            indexClose = expression.indexOf(")");
            if (indexClose != -1)
                indexOpen  = expression.substring(0,indexClose).lastIndexOf("(");
            else
                indexOpen  = expression.lastIndexOf("(");

            if (indexClose != -1 && indexOpen != -1) {
                resOfTerm = calculateWithoutBrackets(new StringBuilder(expression.substring(indexOpen+1,indexClose)));
                if (indexOpen-1 >= 0 && resOfTerm < 0) {
                    if (expression.substring(indexOpen-1,indexOpen).equals("+")) {
                        expression.delete(indexOpen-1,indexClose+1);
                        expression.insert(indexOpen-1, Double.toString(resOfTerm));
                    }
                    else if (expression.substring(indexOpen-1,indexOpen).equals("-")){
                        expression.delete(indexOpen-1,indexClose+1);
                        expression.insert(indexOpen-1, "+" + Double.toString(-resOfTerm));
                    }
                    else {
                        expression.delete(indexOpen,indexClose+1);
                        expression.insert(indexOpen, Double.toString(resOfTerm));
                    }
                }
                else {
                    expression.delete(indexOpen,indexClose+1);
                    expression.insert(indexOpen, Double.toString(resOfTerm));
                }
            }
            else if (indexClose == -1 && indexOpen == -1)
                return calculateWithoutBrackets(expression);
            else if (indexClose == -1)
                throw new RuntimeException("Requare \")\"");
            else if (indexOpen == -1)
                throw new RuntimeException("Requare \"(\"");

        } while (true);
    }

    // expressionWithoutBrackets == -1+3*-2
    static private double calculateWithoutBrackets(StringBuilder expressionWithoutBrackets) {
        ArrayList<String> terms = parseTerms(expressionWithoutBrackets); // '-1' '+' '3' '*' '-2'
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

    // expressionWithoutBrackets == -1+3*-2
    static private ArrayList<String> parseTerms(StringBuilder expressionWithoutBrackets) {
        if (!checkExpressionWithoutBrackets(expressionWithoutBrackets.toString()))
            throw new RuntimeException("Illegal expression: \"" + expressionWithoutBrackets.toString() + '\"');

        ArrayList<String> terms = new ArrayList<>();
        char[] chars = expressionWithoutBrackets.toString().toCharArray();
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

    static private boolean checkExpressionWithoutBrackets(String expressionWithoutBrackets) {
        return expressionWithoutBrackets.matches(
          "(\\+|-)?" +
                "((\\d+\\.?\\d+)|(\\d+)|Infinite|NaN){1}" +
                "(\\+|-|\\*|/)" +
                "(\\+|-)?" +
                "((\\d+\\.?\\d+)|(\\d+)|Infinite|NaN)*");
    }
}
