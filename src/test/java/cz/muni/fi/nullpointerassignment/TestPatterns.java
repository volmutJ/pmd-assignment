/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

/**
 *
 * @author honzaq
 */
public class TestPatterns {

    

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

        if (hellosString == null) {
            hellosString.length();
        }

        //hellosString.length();

        return 1;
    }

    public int oneContitionOk(String s) {
        if (s == null) {
            s = "3";
        }

        s.length();

        return 1;
    }

    public int oneContitionDangerous(String s) {
        if (s != null) {
            s = "3";
        }

        s.length();

        return 1;
    }

    public int twoConditions2(String hellosString) {
        if (hellosString == null) {
            hellosString = "a";
        }

        if (hellosString == null) {
            hellosString.length();
        }

        return 1;
    }

    public int nestedConditionsOk(String s) {
        if (s == null) {
            s = "s";
            if (s == null) {
                s = null;
                s.length();
            }
        }

        return 1;
    }

    public int nestedConditionsDangerous(String s) {
        if (s == null) {
            s = "s";
            if (s != null) {
                s = null;
                s.length();
            }
        }

        return 1;
    }

    public int threeConditionsOk(String s) {
        if (s == null) {
            s = "a";
        }

        if (s != null) {
            s = "a";
        }

        if (s == null) {
            s.length();
        }

        return 1;
    }

    public int threeConditionsDangerous(String s) {
        if (s != null) {
            s = "a";
        }

        if (s != null) {
            s = "a";
        }

        if (s == null) {
            s.length();
        }

        return 1;
    }

    public int threeConditionsDangerous2(String s) {
        if (s == null) {
            s = "a";
        }

        if (s != null) {
            s = null;
        }

        if (s == null) {
            s.length();
        }

        return 1;
    }

    public int arbitraryArgumentsBasicOk(String[] strings) {
        strings[1] = "a";

        strings[1].length();

        return 1;
    }

    public int arbitraryArgumentsBasicDangerous(String[] strings) {
        strings[1].length();
        return 1;
    }

    public int arbitraryArgumentsAssignNullOk(String[] strings) {
        strings[1] = null;
        if(strings[1] != null) {
            strings[1].length();
        }

        return 1;
    }

    public int arbitraryArgumentsAssignNullDangerous(String[] strings) {
        strings[1] = null;

        strings[1].length();

        return 1;
    }

    public int arbitraryArgumentsDangerous(String[] strings) {
        strings[2] = "a";
        strings[1].length();
        return 1;
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

    public int aritmeticConditionSafe(String helloString, int x) {
        if (x > 0) {
            helloString = "x";
        }
        if (x <= 0) {
            helloString = "y";
        }
        return helloString.length();
    }
}
