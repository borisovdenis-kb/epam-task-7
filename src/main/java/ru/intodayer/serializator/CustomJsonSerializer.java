package ru.intodayer.serializator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.*;


public class CustomJsonSerializer implements Serializer {
    private StringBuilder resultJson;
    private Object serializableObject;

    public CustomJsonSerializer(Object serializableObject) {
        this.serializableObject = serializableObject;
        this.resultJson = new StringBuilder();
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
        if (resultJson.length() > 0 && resultJson.charAt(resultJson.length()-1) == ',') {
            resultJson.append("");
        } else {
            resultJson.append("{");
        }
    }

    private void addComma(boolean mustAdd) {
        if (mustAdd) {
            resultJson.append(",");
        }
    }

//    private void objToJson(Object object) throws IllegalAccessException {
//        Class classObj = object.getClass();
//
//        addOpenedBracket();
//        resultJson.append("\"" + classObj.getName() + "\":{");
//
//        Field[] objFields = classObj.getDeclaredFields();
//        setFieldsAccessible(objFields);
//
//        for (Field field: objFields) {
//            if (isSimple(field)) {
//                Object fieldValue = field.get(object);
//                String fieldValueStr = fieldValue != null ? fieldValue.toString() : null;
//                resultJson.append(String.format("\"%s\":\"%s\"", field.getName(), fieldValueStr));
//                resultJson.append(",");
//            } else if (isIterable(field)) {
//                resultJson.append("\"" + field.getType().getName() + "\":[");
//                for (Object obj: (Iterable) field.get(object)) {
//                    objToJson(obj);
//                }
//                resultJson.append("}]");
//            } else {
//                objToJson(field.get(object));
//            }
//        }
//
//        resultJson.append("}");
//    }

    private void objToJson(Object object) throws IllegalAccessException {
        Class classObj = object.getClass();

        addOpenedBracket();
        resultJson.append("\"" + classObj.getName() + "\":{");

        List<Field> fieldList = new ArrayList<>();
        Collections.addAll(fieldList, classObj.getDeclaredFields());

        setFieldsAccessible(fieldList);

        Iterator<Field> itr = fieldList.iterator();
        while (itr.hasNext()){
            Field field = itr.next();
            if (isSimple(field)) {
                Object fieldValue = field.get(object);
                String fieldValueStr = fieldValue != null ? fieldValue.toString() : null;
                resultJson.append(String.format("\"%s\":\"%s\"", field.getName(), fieldValueStr));
                addComma(itr.hasNext());
            } else if (isIterable(field)) {
                resultJson.append("\"" + field.getType().getName() + "\":[");
                for (Object obj: (Iterable) field.get(object)) {
                    objToJson(obj);
                }
                resultJson.append("}]");
            } else {
                objToJson(field.get(object));
            }
        }

        resultJson.append("}");
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {
        try (FileWriter fileWriter = new FileWriter(new File(outFilePath))) {
            objToJson(serializableObject);
            resultJson.append("}");
            fileWriter.write(resultJson.toString());
        } catch (IOException | IllegalAccessException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public Object deserialize(String inFilePath) throws SerializationException {
        return null;
    }
}
