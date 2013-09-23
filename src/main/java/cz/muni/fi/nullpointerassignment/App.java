package cz.muni.fi.nullpointerassignment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author honzaq
 */
public class App {
    public static void main(String[] args) {
        TestingClass t = new TestingClass();
        
        BugPatternDetector b = new BugPatternDetectorImpl();
        boolean result = b.methodContainsBugPattern("cz.muni.fi.nullpointerassignment.TestingClass", "oneContitionOk");
        System.out.println(result);
    }
}