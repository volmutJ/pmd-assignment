/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

/**
 *
 * @author honzaq
 */
public class TestingClass {

    public int bReturnInt() {
        int a = 1;
        return a;
    }

    public int mReturnString(String s, String a, String c) {
        a = "a";
        return s.length();
    }

    public int conditionalNullCheck(String helloString) {
        if (helloString == null) {
            return -1;
        }
        return helloString.length();
    }

    public int conditionalNullCheckDangerous(String helloString) {
        if (helloString != null) {
            return -1;
        }
        return helloString.length();
    }
    
    public int twoConditions(String hellosString) {
        
        if(hellosString == null) {
            hellosString.length();
        }
        
        //hellosString.length();
        
        return 1;
    }
    
    public int oneContition(String s) {
        if (s == null) {
            s = "3";
        }
        
        s.length();
        
        return 1;
        //s.length();
    }
    
    public int twoConditions2(String hellosString) {
        if(hellosString == null) {
            hellosString = "a";
        }  
        
        if(hellosString == null) {
            hellosString.length();
        }
        
        return 1;
    }
    
    public int nestedConditionsWrong(String s) {
        if(s == null) {
            s = "s";
            if(s == null) {
                s = null;
                s.length();
            }
        }
        
        return 1;
    }
    
    public int oneContitionOk(String s) {
        if (s == null) {
            s = "3";
        }

        s.length();

        return 1;
    }
    
    public int threeConditions(String s) {
        if(s != null) {
            s = "a";
        }
        
        if(s != null) {
            s = "a";
        }
        
        if(s == null) {
            s.length();
        }
        
        return 1;
    }
    
    
    public int i(String c, String a) {
        c = "i";
        if(a != null) {
            c = null;
        }
        
        return c.length();
    }
    
    public int nullPositiveDangerous(String c, String a) {
        if(c == null) {
            c.length();
        }
        
        return 1;
    }
    
    public int complexConditionsDangerous(String c) {
        String a = "i";
        if(c == null) {
            a = null;
        }
        c = null;
        if (a == null) {
            c = "4";
        }
        return c.length();
    }
    
    public int arbitraryArgumentsBasicOk(String[] strings) {
        strings[2] = "a";
        
        strings[2].length();
        
        return 1;
    }
    
    public int arbitraryArgumentsBasicDangerous(String[] strings) {
        strings[1].length();
        return 1;
    }
    
    public int arbitraryArgumentsAssignNullDangerous(String[] strings) {
        strings[1] = null;
        
        strings[1].length();
        
        return 1;
    }
    
    
    public int methodCallDangerous() {
        String s = this.nullReturn();
        s.length();
        return 1;
    }
    
    public String nullReturn() {
        return null;
    }

    /**
     * Malign method. If passed null and 0 it will throw NullPointerException
     *
     * @param helloString
     * @param x
     * @return
     */
    public int aritmeticConditionDangerous(String helloString, int x) {
        if (x > 0) {
            helloString = "x";
        }
        if (x <= 0) {
            helloString = "y";
        }
        return helloString.length();
    }

    public int aritmeticConditionSafe(String helloString, int x){
           if (x>0)
              helloString = "x";
            if (x <= 0)
              helloString = "y";
                return helloString.length();
    } 
}
