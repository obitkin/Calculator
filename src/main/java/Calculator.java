import java.util.ArrayList;

public class Calculator {

    static double calculate(String expression) {
        return parse(new StringBuilder("(" + expression + ")"));
    }

    //expression == (3+(-1+3*-2))
    static private double parse(StringBuilder expression) {
        int indexOpen, indexClose;
        do {
            indexClose = expression.indexOf(")");
            if (indexClose != -1)
                indexOpen  = expression.substring(0,indexClose).lastIndexOf("(");
            else
                indexOpen  = expression.lastIndexOf("(");

            if (indexClose != -1 && indexOpen != -1) {
                System.out.println(expression.toString());
                double d = calculateWithoutBracket(new StringBuilder(expression.substring(indexOpen+1,indexClose)));
                expression.delete(indexOpen,indexClose+1);
                expression.insert(indexOpen, Double.toString(d));
            }
            else if (indexClose == -1 && indexOpen == -1)
                break;
            else if (indexClose == -1)
                throw new RuntimeException("Requare )");
            else if (indexOpen == -1)
                throw new RuntimeException("Requare (");

        } while (true);

        System.out.println(expression.toString());
        return Double.parseDouble(expression.toString());
    }

    // expressionWithoutBracket == -1+3*-2
    static private double calculateWithoutBracket(StringBuilder expressionWithoutBracket) {
        ArrayList<String> terms = parseTerms(expressionWithoutBracket); // '-1' '+' '3' '*' '-2'
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

    // expressionWithoutBracket == -1+3*-2
    static private ArrayList<String> parseTerms(StringBuilder expressionWithoutBracket) {
        if (!checkExpressionWithoutHooks(expressionWithoutBracket.toString()))
            throw new RuntimeException("Illegal expression: " + expressionWithoutBracket.toString());

        ArrayList<String> terms = new ArrayList<>();
        char[] chars = expressionWithoutBracket.toString().toCharArray();
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
