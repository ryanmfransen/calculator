package com.ryan.fransen;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Grab the user input
        System.out.print("Expression: ");
        Expression expression = new Expression();
        System.out.println(expression.evaluate(input.nextLine()));
    }

}