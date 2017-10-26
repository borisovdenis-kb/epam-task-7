package ru.intodayer.serializator;

import ru.intodayer.duplicatemodels.UniqueObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.*;


public class CustomJsonSerializer implements Serializer {
    private StringBuilder json;
    private Object serializableObject;
    private Set<UniqueObject> visited = new HashSet<>();

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

    private void goDeeper(Object obj, boolean mustAddComma) throws IllegalAccessException {
        UniqueObject object = (UniqueObject) obj;
        if (!visited.contains(object)) {
            visited.add(object);
            objToJson(object);
        } else {
            addOpenedBracket();
            json.append("\"" + object.getClass().getName() + "\":");
            json.append("\"" + object.getId() + "\"");
            addComma(mustAddComma);
        }
        return;
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
            } else if (isIterable(field)) {
                json.append("\"" + field.getType().getName() + "\":[");
                for (Object obj: (Iterable) field.get(object)) {
//                    objToJson(obj);
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

    @Override
    public Object deserialize(String inFilePath) throws SerializationException {
        return null;
    }
}
