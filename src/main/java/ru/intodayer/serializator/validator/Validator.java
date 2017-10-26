package ru.intodayer.serializator.validator;

import java.util.Stack;


public class Validator {
    private Stack<Character> stack = new Stack<>();

    private void stackPop(Character c, int charPosition) {
        if (stack.peek() == c) {
            stack.pop();
        } else {
            throw new IncorrectJsonException(
                String.format("Unexpected symbol '%s' at %s position", c, charPosition)
            );
        }
    }

    public void validateJson(String json) {
        for (int i = 0; i < json.length(); i++) {
            Character x = json.charAt(i);
            switch (x) {
                case '{':
                    stack.push(x);
                    break;
                case '}':
                    stackPop(x, i);
                    break;
                case '[':
                    stack.push(x);
                    break;
                case ']':
                    stackPop(x, i);
                    break;
                case '"':
                    if (stack.peek() == x) {
                        stack.pop();
                    } else {
                        stack.push(x);
                    }
                default: break;
            }
        }
        if (!stack.empty()) {
            throw new IncorrectJsonException("Incorrect nesting.");
        }
    }
}
