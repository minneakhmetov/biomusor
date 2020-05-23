package com.bioinf.demo.newick;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Analyzer {

    private final static char END_SYMBOL = ';';
    private final static char NODE_SEPARATOR = ',';
    private final static char NAME_SEPARATOR = ':';

    private final static String CORRECT_LINE = "Дерево верно";
    private final static String INVALID_LINE = "Отсутствие «;» в конце строки";
    private final static String INVALID_NUMBER = "Неправильный формат";
    private final static String IMBALANCED_BRACKETS = "Несбалансированность скобок";
    private final static String DUPLICATE_NODE = "Одинаковые имена листьев (узлов)";
    private final static String INVALID_NODE_NAME = "Неправильное имя узла";

    private final static List<Character> invalidNumber = Arrays.asList(';', ':', ' ', '(', ')');
    private final static List<Character> invalidNode = Arrays.asList(';', ' ', '(');

    public String processLine(String input) {
        if (input.charAt(input.length() - 1) != END_SYMBOL) {
            return INVALID_LINE;
        }

        Set<String> nodes = new HashSet<>();
        StringBuilder currentNode = new StringBuilder();
        StringBuilder currentNumber = new StringBuilder();
        int state = 0;
        int bracketCount = 0;

        for (int i = 0; i < input.length() - 1; i++) {
            char current = input.charAt(i);
            if (current == '(') {
                if (state == 1) {
                    return INVALID_NUMBER;
                }
                bracketCount++;
                continue;
            }
            if (current == ')') {
                bracketCount--;
                if (bracketCount < 0) {
                    return IMBALANCED_BRACKETS;
                }
                if (state < 2) {
                    if (state == 0) {
                        if (currentNode.length() > 0) {
                            if (nodes.contains(currentNode.toString())) {
                                return DUPLICATE_NODE;
                            }
                            nodes.add(currentNode.toString());
                            currentNode = new StringBuilder();
                        }
                    }
                    currentNumber = new StringBuilder();
                    state = 0;
                }
                continue;
            }
            switch (state) {
                case 0: {
                    if (invalidNode.contains(current) || Character.isDigit(current)) {
                        return INVALID_NODE_NAME;
                    }
                    if (current == NAME_SEPARATOR || current == NODE_SEPARATOR) {
                        state = current == NAME_SEPARATOR ? 1 : 2;
                        if (currentNode.length() > 0) {
                            if (nodes.contains(currentNode.toString())) {
                                return DUPLICATE_NODE;
                            }
                            nodes.add(currentNode.toString());
                        }
                        currentNode = new StringBuilder();
                        if (state == 1) {
                            break;
                        }
                    }
                    if (!Character.isDigit(current) && !invalidNode.contains(current)) {
                        currentNode.append(current);
                        break;
                    }
                }
                case 1: {
                    if (invalidNumber.contains(current)) {
                        return INVALID_NUMBER;
                    }
                    if (current == NODE_SEPARATOR) {
                        state = 2;
                        currentNumber = new StringBuilder();
                    } else {
                        if (Character.isDigit(current) || current == '.') {
                            if (current == '.' && currentNumber.toString().contains(".")) {
                                return INVALID_NUMBER;
                            }
                            if (current == '.' && currentNumber.length() == 0) {
                                return INVALID_NUMBER;
                            }
                            if (current != '.'
                                    && currentNumber.length() == 1
                                    && currentNumber.charAt(0) == '0') {
                                return INVALID_NUMBER;
                            }
                            currentNumber.append(current);
                            break;
                        } else {
                            if (Character.isLetter(current)) {
                                return INVALID_NUMBER;
                            }
                        }
                    }
                }
                default: {
                    if (current == NODE_SEPARATOR || current == ' ') {
                        state = 2;
                    } else {
                        if (Character.isDigit(current) || invalidNode.contains(current)) {
                            return INVALID_NODE_NAME;
                        }
                        state = 0;
                        currentNode.append(current);
                    }
                }
            }
        }

        if (bracketCount != 0) {
            return IMBALANCED_BRACKETS;
        }

        return CORRECT_LINE;
    }
}
