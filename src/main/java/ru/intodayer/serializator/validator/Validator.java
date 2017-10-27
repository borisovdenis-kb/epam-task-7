package ru.intodayer.serializator.validator;


import ru.intodayer.models.Gender;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Validator {
    private NestingStack nestingStack = new NestingStack();

    public void validateJson(String json) {
        for (int i = 0; i < json.length(); i++) {
            Character x = json.charAt(i);
            nestingStack.handleNextElement(x, i);
        }
        if (!nestingStack.isEmpty()) {
            nestingStack.throwIncorrectNestingException();
        }
    }

    public static String validateNameOrTitle(String str) {
        if (str.length() == 0) {
            throw new DeserializationException("Author's name or Book's title cant be empty string.");
        }
        if (str.equals("null")) {
            throw new DeserializationException("Author's name or Book's title cant be null.");
        }
        return str;
    }

    public static LocalDate validateLocalDate(String str, boolean canBeNull) {
        if (canBeNull && str.equals("null")) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, dtf);
    }

    public static Gender validateGender(String str) {
        if (str.equals("MAN")) {
            return Gender.MAN;
        } else if (str.equals("WOMAN")) {
            return Gender.WOMAN;
        } else {
            throw new DeserializationException("Gender can be MAN or WOMAN.");
        }
    }
}
