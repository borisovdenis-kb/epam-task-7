package ru.intodayer.serializator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CustomJsonSerializer implements Serializer {
    private StringBuilder resultJson;
    private Object serializableObject = new ArrayList<>();

    public CustomJsonSerializer(List<? extends Object> serializableObject) {
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
        return (field.getType().isPrimitive() || field.getType().isEnum()
                || isPrimitiveWrapper(field));
    }

    private boolean isIterable(Field field) {
        List<Class> interfaces = new ArrayList<>();
        Collections.addAll(interfaces, field.getClass().getInterfaces());
        return interfaces.contains(Iterable.class);
    }

    private void setFieldsAccessible(Field[] fields) {
        for (Field field: fields) {
            field.setAccessible(true);
        }
    }

    private void objToJson(Object object) throws IllegalAccessException {
        Class classObj = object.getClass();
        resultJson.append("{\"" + classObj.getName() + "\"}");

        Field[] objFields = classObj.getDeclaredFields();
        setFieldsAccessible(objFields);

        for (Field field: objFields) {
            if (isSimple(field)) {
                resultJson.append(
                    String.format("\"%s\":\"%s\"", field.getName(), field.get(object))
                );
            } else if (isIterable(field)) {
                for (Object obj: (Iterable) field.get(object)) {
                    objToJson(obj);
                }
            } else {
                objToJson(object);
            }
        }

        resultJson.append("}");
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {
        objToJson(serializableObject);

    }

    @Override
    public Object deserialize(String inFilePath) throws SerializationException {
        return null;
    }
}
