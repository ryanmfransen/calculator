package com.ryan.fransen;

import org.junit.jupiter.api.Assertions;

class ExpressionTest {

    @org.junit.jupiter.api.Test
    void test_addition() {
        Expression expression = new Expression();
        Assertions.assertEquals (5, expression.evaluate("2+3"));
    }

    @org.junit.jupiter.api.Test
    void test_subtraction() {
        Expression expression = new Expression();
        Assertions.assertEquals (1, expression.evaluate("3-2"));
    }

    @org.junit.jupiter.api.Test
    void test_multiplication() {
        Expression expression = new Expression();
        Assertions.assertEquals (6, expression.evaluate("2*3"));
    }

    @org.junit.jupiter.api.Test
    void test_division() {
        Expression expression = new Expression();
        Assertions.assertEquals (3, expression.evaluate("6/2"));
    }

    @org.junit.jupiter.api.Test
    void test_exponent() {
        Expression expression = new Expression();
        Assertions.assertEquals (8, expression.evaluate("2^3"));
    }

    @org.junit.jupiter.api.Test
    void test_decimal() {
        Expression expression = new Expression();
        Assertions.assertEquals (3.6, expression.evaluate("1.2*3"));
    }

    @org.junit.jupiter.api.Test
    void test_negative_multiplication() {
        Expression expression = new Expression();
        Assertions.assertEquals (-3, expression.evaluate("3*-1"));
    }

    @org.junit.jupiter.api.Test
    void test_negative_addition() {
        Expression expression = new Expression();
        Assertions.assertEquals (4, expression.evaluate("3--1"));
    }

    @org.junit.jupiter.api.Test
    void test_2_root() {
        Expression expression = new Expression();
        Assertions.assertEquals (2, expression.evaluate("2r4"));
    }

    @org.junit.jupiter.api.Test
    void test_3_root() {
        Expression expression = new Expression();
        Assertions.assertEquals (2, expression.evaluate("3r8"));
    }

    @org.junit.jupiter.api.Test
    void test_4_root() {
        Expression expression = new Expression();
        Assertions.assertEquals (2, expression.evaluate("4r16"));
    }

    @org.junit.jupiter.api.Test
    void test_2_root_decimal() {
        Expression expression = new Expression();
        Assertions.assertEquals (2.1213203435596424, expression.evaluate("2r4.5"));
    }

    @org.junit.jupiter.api.Test
    void test_multi_atomic_expressions() {
        Expression expression = new Expression();
        Assertions.assertEquals (27, expression.evaluate("1+2*3+4/2*6+12-4"));
    }

    @org.junit.jupiter.api.Test
    void test_brackets() {
        Expression expression = new Expression();
        Assertions.assertEquals (6, expression.evaluate("2*(1+2)"));
    }

    @org.junit.jupiter.api.Test
    void test_nested_brackets() {
        Expression expression = new Expression();
        Assertions.assertEquals (16, expression.evaluate("2*(1+(3+4))"));
    }

    @org.junit.jupiter.api.Test
    void test_nasty_expression() {
        Expression expression = new Expression();
        Assertions.assertEquals (188, expression.evaluate("1+(2+(2*3*(6+1)))*4+(5+6)+7/(7*(6/3*2^3))"));
    }

    @org.junit.jupiter.api.Test
    void test_whitespace() {
        Expression expression = new Expression();
        Assertions.assertEquals (5, expression.evaluate(" 2  + 3 "));
    }

    @org.junit.jupiter.api.Test
    void test_invalid_expression() {
        Expression expression = new Expression();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            expression.evaluate("2+3//4((+*^a");
        });

        Assertions.assertEquals ("Invalid expression: 2+3//4((+*^a",
            exception.getMessage());
    }
}