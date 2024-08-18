package edu.school21.classes.impl;

import edu.school21.classes.PreProcessor;
import edu.school21.classes.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor){
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String s) {
        System.err.println(preProcessor.process(s));
    }
}
