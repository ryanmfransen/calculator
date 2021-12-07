package com.ryan.fransen;

import javax.management.MBeanOperationInfo;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    /**
     * Constructor for Expression class
     */
    public Expression() {

    }

    /**
     * Evaluate the provided expression using the BEDMAS rules
     * For a given expression : 1+(2+(2*3*(6+1)))*4+(5+6)+7
     *  evaluate each sub expression according to the BEDMAS rules
     *
     * Return the evaluated expression:
     *  195
     *
     * @param expression    The expression to evaluate
     * @return              The result of the evaluation
     */
    public Double evaluate(String expression) {
        BigDecimal result;
        try {
            result = new BigDecimal(this.evaluate_expression(expression.replaceAll("\\s","")));
        } catch(Exception e) {
            throw new ArithmeticException(Constants.msg_invalid_expression.formatted(expression));
        }

        return result.doubleValue();
    }

    /**
     * Evaluate the provided expression using the BEDMAS rules
     * For a given expression : 1+(2+(2*3*(6+1)))*4+(5+6)+7
     *  evaluate each sub expression according to the BEDMAS rules
     *
     * Return the evaluated expression:
     *  195
     *
     * @param expression    The expression to evaluate
     * @return              The result of the evaluation
     */
    private String evaluate_expression(String expression) {
        String new_expression = expression;

        // First recursively evaluate the bracketed expressions, according to their [B]EDMAS rules
        new_expression = evaluate_bracketed_expressions(Pattern.compile(Constants.b_bedmas_pattern), new_expression);

        // Now go through and evaluate the remaining atomic expressions according to their B[EDMAS] rules
        for(String evaluation_pattern: Constants.edmas_patterns) {
            new_expression = evaluate_sub_expressions(Pattern.compile(evaluation_pattern), new_expression);
        }

        return new_expression;
    }

    /**
     * Evaluate each bracketed sub expression in the provided expression
     * For a given expression : 1+(2*3+7)+2*(1+1)
     *  and given pattern to match '(...)'
     *  evaluate each of the sub expressions:
     *  (2*3+7)
     *  (1+1)
     *
     * Return the updated expression:
     *  1+13+2*2
     *
     * @param pattern_compiled  The compiled regex Pattern to match on
     * @param expression        The expression to evaluate
     * @return                  The result of the evaluation
     */
    private String evaluate_bracketed_expressions(Pattern pattern_compiled, String expression) {
        Matcher matcher = pattern_compiled.matcher(expression);

        while (matcher.find()) {
            Expression sub_expression = new Expression();
            String evaluation_result = sub_expression.evaluate_expression(matcher.group(1));
            expression = update_expression(expression, evaluation_result, matcher.start(), matcher.end());
            matcher = pattern_compiled.matcher(expression);
        }

        return expression;
    }

    /**
     * Evaluate each atomic sub expression in the provided expression
     * For a given expression : 1+2*3/4+5
     *  and given pattern to match '+'
     *  evaluate each of the atomic expressions:
     *  1+2
     *  4+5
     *
     * Return the updated expression:
     *  3*3/9
     *
     * @param pattern_compiled  The compiled regex Pattern to match on
     * @param expression        The expression to evaluate
     * @return                  The result of the evaluation
     */
    private String evaluate_sub_expressions(Pattern pattern_compiled, String expression ) {
        Matcher matcher = pattern_compiled.matcher(expression);

        while (matcher.find()) {
            String evaluation_result = evaluate_atomic_expression(matcher.group(1));
            expression = update_expression(expression, evaluation_result, matcher.start(), matcher.end());
            matcher = pattern_compiled.matcher(expression);
        }

        return expression;
    }

    /**
     * Updates the expression with the evaluation result
     *  ie:
     *  Expression: xxxxxxx(2+3)xxxxxxx
     *  Response:   xxxxxxx6xxxxxxx
     *
     * The expression defined by start and end will be replaced
     * with the evaluation result.
     *
     * Returns a new String.
     *
     * @param expression        The expression to update
     * @param evaluation_result The evaluation result to swap in
     * @param start             The start position of the expression to swap in
     * @param end               The end position of the expression to swap in
     * @return              The new expression
     */
    private String update_expression(String expression, String evaluation_result, int start, int end) {
        StringBuilder updated_expression = new StringBuilder();

        String before = expression.substring(0, start);
        String after = expression.substring(end);
        updated_expression.append(before).append(evaluation_result).append(after);

        return updated_expression.toString();
    }

    /**
     * Evaluate an atomic expression in the form:
     *      [operand] [operator] [operand]
     *      ie [2+3]
     *
     * Throws an IllegalStateException for an unknown operator.
     *
     * @param expression                The expression to evaluate
     * @return                          The result of the evaluation
     * @throws IllegalStateException    An unrecognized operator
     */
    private String evaluate_atomic_expression(String expression)
    throws IllegalStateException {
        Pattern pattern_c = Pattern.compile(Constants.atomic_pattern);
        Matcher matcher = pattern_c.matcher(expression);

        String operator = "";

        if (!matcher.matches()) {
            return "";
        }

        MathContext m_context = new MathContext(5);

        BigDecimal operand_1 = new BigDecimal(matcher.group(1));
        operator = matcher.group(2);
        BigDecimal operand_2 = new BigDecimal(matcher.group(3));

        BigDecimal result = switch(operator) {
            case "^" -> operand_1.pow(Integer.parseInt(matcher.group(3)));
            case "r" -> BigDecimal.valueOf(
                    Math.pow(
                           Double.parseDouble(matcher.group(3)),
                           1.0 / Double.parseDouble(matcher.group(1))));
            case "+" -> operand_1.add(operand_2);
            case "-" -> operand_1.subtract(operand_2);
            case "/" -> operand_1.divide(operand_2, 5, RoundingMode.FLOOR);
            case "*" -> operand_1.multiply(operand_2);
            default -> throw new IllegalStateException(Constants.msg_operator_error.formatted(operator));
        };

        return result.toString();
    }
}
