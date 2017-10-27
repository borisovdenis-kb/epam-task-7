package ru.intodayer.serializator.validator;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class NestingStack {
    private final List<Character> openBrackets = Arrays.asList('{', '[');
    private final List<Character> closeBrackets = Arrays.asList('}', ']');
    Stack<Character> stack;

    public NestingStack() {
        this.stack = new Stack<>();
    }

    public NestingStack(String firstSymbol) {
        this();
        stack.push(strSymbolToCharacter(firstSymbol));
    }

    public NestingStack(Character firstSymbol) {
        this();
        stack.push(firstSymbol);
    }

    private Character strSymbolToCharacter(String symbol) {
        return symbol.toCharArray()[0];
    }

    public void push(String symbol) {
        stack.push(strSymbolToCharacter(symbol));
    }

    public void push(Character c) {
        stack.push(c);
    }

    public void throwIncorrectNestingException() {
        throw new IncorrectJsonException("Incorrect nesting.");
    }

    private Character getOpposite(Character c) {
        switch (c) {
            case '{': return '}';
            case '}': return '{';
            case '[': return ']';
            case ']': return '[';
            default: return null;
        }
    }

    private Character removeTop(Character c, int charPosition) {
        if (stack.empty()) {
            throwIncorrectNestingException();
        }
        if (stack.peek() == getOpposite(c)) {
            return stack.pop();
        } else {
            throw new IncorrectJsonException(
                String.format("Unexpected symbol '%s' at %s position", c, charPosition)
            );
        }
    }

    public Character pop(Character c, int charPosition) {
        return removeTop(c, charPosition);
    }

    public Character pop(String symbol, int charPosition) {
        return removeTop(strSymbolToCharacter(symbol), charPosition);
    }

    private boolean isOpenBracket(Character c) {
        return openBrackets.contains(c);
    }

    private boolean isCloseBracket(Character c) {
        return closeBrackets.contains(c);
    }

    public void handleNextElement(Character element, int handledPosition) {
        if (isOpenBracket(element)) {
            this.push(element);
        } else if (isCloseBracket(element)) {
            this.pop(element, handledPosition);
        } else {
            return;
        }
    }

    public boolean isEmpty() {
        return this.stack.empty();
    }
}
