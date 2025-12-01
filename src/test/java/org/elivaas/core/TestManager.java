package org.elivaas.core;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestManager {
    private static TestManager instance;
    private TestNG testNG;
    private Map<String, Object> testContext;

    private TestManager() {
        testNG = new TestNG();
        testContext = new HashMap<>();
    }

    public static synchronized TestManager getInstance() {
        if (instance == null) {
            instance = new TestManager();
        }
        return instance;
    }

    public void runTests(String suitePath) {
        List<String> suites = new ArrayList<>();
        suites.add(suitePath);
        testNG.setTestSuites(suites);
        testNG.run();
    }

    public void setTestContext(String key, Object value) {
        testContext.put(key, value);
    }

    public Object getTestContext(String key) {
        return testContext.get(key);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the testng.xml file path as an argument");
            return;
        }
        getInstance().runTests(args[0]);
    }
}
