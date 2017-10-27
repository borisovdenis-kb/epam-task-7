package ru.intodayer.serializator;

import ru.intodayer.duplicatemodels.DuplicateAuthor;
import ru.intodayer.duplicatemodels.UniqueObject;
import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Gender;
import ru.intodayer.models.Publisher;
import ru.intodayer.serializator.validator.NestingStack;
import ru.intodayer.serializator.validator.Validator;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public class CustomJsonSerializer implements Serializer {
    private StringBuilder json;
    private Object serializableObject;
    private List<UniqueObject> visited = new ArrayList<>();

    public CustomJsonSerializer(Object serializableObject) {
        this.serializableObject = serializableObject;
        this.json = new StringBuilder();
    }

    private boolean isPrimitiveWrapper(Field field) {
        List<Class> wrappers = Arrays.asList(
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,
            Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE
        );
        return (wrappers.contains(field.getType()));
    }

    private boolean isSimple(Field field) {
        return Arrays.asList(
            isPrimitiveWrapper(field),
            field.getType().isPrimitive(),
            field.getType().isEnum(),
            field.getType().equals(String.class),
            field.getType().equals(LocalDate.class)
        ).contains(true);
    }

    private boolean isIterable(Field field) {
        return Iterable.class.isAssignableFrom(field.getType());
    }

    private boolean isMap(Field field) {
        return field.getType().equals(Map.class);
    }

    private void setFieldsAccessible(List<Field> fields) {
        for (Field field: fields) {
            field.setAccessible(true);
        }
    }

    private void addOpenedBracket() {
        if (json.length() > 0) {
            if (json.charAt(json.length()-1) == ',') {
                json.append("");
            } else if (json.charAt(json.length()-1) == '}') {
                json.append(",");
            } else {
                json.append("{");
            }
        } else {
            json.append("{");
        }
    }

    private void addComma(boolean mustAdd) {
        if (mustAdd) {
            json.append(",");
        }
    }

    private void addQuotedStrToJson(String key) {
        json.append("\"" + key + "\"");
    }

    private void goDeeper(Object obj, boolean mustAddComma) throws IllegalAccessException {
        UniqueObject object = (UniqueObject) obj;
        if (!visited.contains(object)) {
            visited.add(object);
            objToJson(object);
        } else {
            addOpenedBracket();
            addQuotedStrToJson(object.getClass().getName());
            json.append(":");
            addQuotedStrToJson(object.getId());
            addComma(mustAddComma);
        }
        return;
    }

    private void mapToJson(Map<Integer, UniqueObject> map) throws IllegalAccessException {
        Iterator itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, UniqueObject> pair = (Map.Entry) itr.next();
            addQuotedStrToJson(String.valueOf(pair.getKey()));
            json.append(":");
//            objToJson(pair.getValue());
            goDeeper(pair.getValue(), itr.hasNext());
            json.append("}");
            addComma(itr.hasNext());
        }
    }

    private void objToJson(Object object) throws IllegalAccessException {
        Class classObj = object.getClass();

        addOpenedBracket();
        json.append("\"" + classObj.getName() + "\":{");

        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, classObj.getDeclaredFields());

        setFieldsAccessible(fields);

        Iterator<Field> itr = fields.iterator();
        while (itr.hasNext()){
            Field field = itr.next();

            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            if (isSimple(field)) {
                Object fieldValue = field.get(object);
                String fieldValueStr = fieldValue != null ? fieldValue.toString() : null;
                json.append(String.format("\"%s\":\"%s\"", field.getName(), fieldValueStr));
                addComma(itr.hasNext());
            } else if (isMap(field)) {
                addQuotedStrToJson(field.getType().getName());
                json.append(":{");
                mapToJson((Map) field.get(object));
                json.append("},");
            } else if (isIterable(field)) {
                json.append("\"" + field.getType().getName() + "\":[");
                for (Object obj: (Iterable) field.get(object)) {
                    goDeeper(obj, itr.hasNext());
                }
                json.append("}]");
            } else {
                goDeeper(field.get(object), itr.hasNext());
            }
        }

        json.append("}");
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {
        try (FileWriter fileWriter = new FileWriter(new File(outFilePath))) {
            objToJson(serializableObject);
            json.append("}");
            String resultJson = json.toString().replaceAll("\\\"\\{","\",");
            fileWriter.write(resultJson);
        } catch (IOException | IllegalAccessException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    private static String readWholeFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private String deleteExtraSymbols(String json) {
        return json
                .replaceAll(":", "")
                .replaceAll(",", "");
    }

    private boolean isBracket(Character c) {
        List<Character> brackets = Arrays.asList('{', '}', '[', ']');
        return brackets.contains(c);
    }

    private List<String> stringToSymbols(String s) {
        List<String> symbols = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            symbols.add(String.valueOf(s.charAt(i)));
        }
        return symbols;
    }

    private List<String> getTokensFromJson(String json) {
        String[] tokens = deleteExtraSymbols(json).split("\\\"");
        List<String> processed = new ArrayList<>();
        Collections.addAll(processed, tokens);

        List<String> result = processed
            .stream()
            .filter((s) -> !s.equals(""))
            .flatMap((s) -> {
                if (isBracket(s.charAt(0)) && s.length() > 1) {
                    return stringToSymbols(s).stream();
                }
                List<String> singleStr = Arrays.asList(s);
                return singleStr.stream();
            })
            .collect(Collectors.toList());

        System.out.println(result);
        return result;
    }

    private String getPublisherName(List<String> tokens) {
        return tokens.get(tokens.indexOf("name") + 1);
    }

    private List<String> getAuthorsAndBooks(List<String> tokens) {
        int i = tokens.indexOf("java.util.Map");
        List<String> authorsAndBooks = new ArrayList<>();

        NestingStack nestingStack = new NestingStack(tokens.get(++i));
        authorsAndBooks.add(tokens.get(i));
        while (!nestingStack.isEmpty()) {
            i++;
            nestingStack.handleNextElement(tokens.get(i).charAt(0), i);
            authorsAndBooks.add(tokens.get(i));
        }
        return authorsAndBooks;
    }

//    private void setOrskipFieldToAuthor(DuplicateAuthor author, String str) {
//        if (str.equals("name")) {
//            author.setName(Validator.validateNameOrTitle(str));
//        } else if (str.equals("birthDay")) {
//           author.setBirthDay(Validator.validateLocalDate(str, false));
//        } else if (str.equals("deathDay")) {
//            author.setDeathDay(Validator.validateLocalDate(str, true));
//        } else if (str.equals("gender")) {
//            author.setGender(Validator.validateGender(str));
//        }
//    }

    private Map<String, Author> getAuthorsMap(List<String> tokens) {
        Map<String, Author> authorMap = new HashMap<>();
        int i = 0;
        while (!tokens.get(i).contains("DuplicateBook@")) {
            if (tokens.get(i).contains("DuplicateAuthor@")) {
                String key = tokens.get(i);
                DuplicateAuthor dupAuthor = new DuplicateAuthor();

                NestingStack nestingStack = new NestingStack(tokens.get(++i));
                while (!nestingStack.isEmpty()) {
                    i++;
                    String str = tokens.get(i);
                    nestingStack.handleNextElement(str.charAt(0), i);
                    i++;
                    if (str.equals("name")) {
                        dupAuthor.setName(Validator.validateNameOrTitle(tokens.get(i)));
                    } else if (str.equals("birthDay")) {
                        dupAuthor.setBirthDay(Validator.validateLocalDate(tokens.get(i), false));
                    } else if (str.equals("deathDay")) {
                        dupAuthor.setDeathDay(Validator.validateLocalDate(tokens.get(i), true));
                    } else if (str.equals("gender")) {
                        dupAuthor.setGender(Validator.validateGender(tokens.get(i)));
                    }
                }
                authorMap.put(key, dupAuthor.getAuthorFromThis());
            }
            i++;
        }
        return authorMap;
    }

    private List<Book> getPublishedBooks(List<String> tokens) {
        List<String> authorsAndBooks = getAuthorsAndBooks(tokens);
        Map<String, Author> authorsMap = getAuthorsMap(authorsAndBooks);
        return null;
    }

    private Publisher buildPublisherFromJson(String json) {
        List<String> tokens = getTokensFromJson(json);
        String publisherName = getPublisherName(tokens);
        List<Book> publishedBooks = getPublishedBooks(tokens);

        return null;
    }

    @Override
    public Object deserialize(String inFilePath) throws SerializationException {
        Validator validator = new Validator();

        try (FileReader fileReader = new FileReader(new File(inFilePath))) {
            String json = readWholeFile(inFilePath);
            validator.validateJson(json);

            buildPublisherFromJson(json);
            // Publisher publisher = buildPublisherFromJson(json);

            // validator.validateObjectModel(Publisher publisher);

        } catch (IOException e) {
            throw new SerializationException(e.getMessage(), e);
        }

        return null;
    }
}
