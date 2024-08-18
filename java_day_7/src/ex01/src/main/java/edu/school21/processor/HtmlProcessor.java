package edu.school21.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;


@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        roundEnv.getElementsAnnotatedWith(HtmlForm.class)
                .forEach(this::processElement);
        return true;
    }

    private void processElement(Element element) {
        HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);

        try {
            generateHtmlFile(element, htmlForm);
        } catch (IOException e) {
            processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
    }

    private void generateHtmlFile(Element typeElement, HtmlForm htmlForm) throws IOException {
        String fileName = htmlForm.fileName();
        String action = htmlForm.action();
        String method = htmlForm.method();

        FileObject fileObject = processingEnv.getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT,  "", fileName);

        try (BufferedWriter writer = new BufferedWriter(fileObject.openWriter())) {
            writer.write(String.format("<form action=\"%s\" method=\"%s\">%n", action, method));

            generateFormFields(typeElement, writer);

            writer.write("\t<input type=\"submit\" value=\"Send\">\n");
            writer.write("</form>");
        }
    }

    private static void generateFormFields(Element typeElement, BufferedWriter writer) {
        typeElement.getEnclosedElements()
                .forEach(v -> {
                    try {
                        generateInputField(v, writer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void generateInputField(Element v, BufferedWriter writer) throws IOException {
        HtmlInput htmlInput = v.getAnnotation(HtmlInput.class);

        if (htmlInput != null) {
            writer.write("<label>\n");
            writer.write(String.format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">%n",
                    htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
            writer.write("</label>\n");
        }
    }

}
