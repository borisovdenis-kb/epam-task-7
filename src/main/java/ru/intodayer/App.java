package ru.intodayer;

import ru.intodayer.serializator.SerializationException;
import ru.intodayer.serializator.SerializatorTester;


public class App {
    public static void main(String[] args) throws SerializationException {
//        Analyzer.demonstrateWorkOfAnalyzer();
        SerializatorTester.testStandardSerializator();
    }
}
