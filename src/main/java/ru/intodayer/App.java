package ru.intodayer;

import ru.intodayer.serializator.SerializationException;
import ru.intodayer.serializator.SerializerTester;


public class App {
    public static void main(String[] args) throws SerializationException {
//        Analyzer.demonstrateWorkOfAnalyzer();
        SerializerTester.testStandardSerializer();
        SerializerTester.testCustomJsonSerializer();
    }
}
