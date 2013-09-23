/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

/**
 *
 * @author honzaq
 */
public interface BugPatternDetector {
        /**
         * Returns true if and only if the method contains bug pattern.
         * The class is expected to be on classpath  (use
         *  Repository.lookupClass method to load the class)
         *
         * @param fullyQualifiedClassName e.g. cz.muni.fi.MyClass
         * @param methodName e.g. myMethod
         * @return
         */
        public boolean methodContainsBugPattern(String fullyQualifiedClassName, String methodName);
}
