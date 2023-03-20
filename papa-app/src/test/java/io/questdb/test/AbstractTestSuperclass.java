package io.questdb.test;

public class AbstractTestSuperclass {
    protected String someUsefulState;

    protected int someUsefulMethod(String s) {
        return s != null ? s.length() : -1;
    }
}
