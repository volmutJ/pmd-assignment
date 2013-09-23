/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author honzaq
 */ 
public class TestBugPatternDetecotr {
    
    private BugPatternDetector bugPattern;
    
    private final String CLASS = "cz.muni.fi.nullpointerassignment.TestPatterns"; 
   
    public TestBugPatternDetecotr() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bugPattern = new BugPatternDetectorImpl();
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

  
    
    @Test
    public void mReturnString() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "mReturnString");
        assertTrue(result);
    }
    
    
    @Test
    public void conditionalNullCheckDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "conditionalNullCheckDangerous");
        assertTrue(result);
    }
    
    @Test
    public void oneContitionOk() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "oneContitionOk");
        assertFalse(result);
    }
    
    @Test
    public void oneContitionDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "oneContitionDangerous");
        assertTrue(result);
    }
    
    @Test
    public void nestedConditionsOk() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "nestedConditionsOk");
        assertFalse(result);
    }
    
    @Test
    public void nestedConditionsDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "nestedConditionsDangerous");
        assertTrue(result);
    }
    
    @Test
    public void threeConditionsOk() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "threeConditionsOk");
        assertFalse(result);
    }
    
    @Test
    public void threeConditionsDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "threeConditionsDangerous");
        assertTrue(result);
    }
    
    @Test
    public void threeConditionsDangerous2() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "threeConditionsDangerous2");
        assertTrue(result);
    }
    
    @Test
    public void arbitraryArgumentsBasicOk() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "arbitraryArgumentsBasicOk");
        assertFalse(result);
    }
    
    @Test
    public void arbitraryArgumentsBasicDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "arbitraryArgumentsBasicDangerous");
        assertTrue(result);
    }
    
    @Test
    public void arbitraryArgumentsDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "arbitraryArgumentsDangerous");
        assertTrue(result);
    }
    
    @Test
    public void arbitraryArgumentsAssignNullDangerous() {
        boolean result = this.bugPattern.methodContainsBugPattern(CLASS, "arbitraryArgumentsAssignNullDangerous");
        assertTrue(result);
    }
    
}
