package edu.school21.classes.impl;

import edu.school21.classes.PreProcessor;

public class PreProcessToUpperImpl implements PreProcessor {
    @Override
    public String process(String s) {
        return s.toUpperCase();
    }
}
