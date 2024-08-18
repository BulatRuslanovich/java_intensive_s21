package edu.school21.classes.impl;

import edu.school21.classes.PreProcessor;
import edu.school21.classes.Renderer;

public class RendererStandardImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor){
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String s) {
        System.out.println(preProcessor.process(s));
    }
}
