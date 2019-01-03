package com.smart.android.javalib;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.smart.android.javalib.VInjector")
public class MyInjectPrecessor extends AbstractProcessor {

    Filer filer;
    Messager messager;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnv.getFiler();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for (Element element:roundEnvironment.getElementsAnnotatedWith(VInjector.class)){
            analysisAnnotated(element);
        }

        return false;
    }
    private void analysisAnnotated(Element element){
        VInjector vInjector=element.getAnnotation(VInjector.class);
        System.out.println("************analysisAnnotated start********************");
        int id= vInjector.id();
        String name=vInjector.name();
        String text=vInjector.text();

        String className=name;
        StringBuilder builder = new StringBuilder()
                .append("package com.autotestdemo.maomao.autotestdemo.auto;\n\n")
                .append("public class ")
                .append(className)
                .append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");
// this is appending to the return statement
        builder.append(id).append(text).append(className).append(" !\\n");


        builder.append("\";\n") // end returne
                .append("\t}\n") // close method
                .append("}\n"); // close class

        try {
            JavaFileObject source = filer.createSourceFile("om.autotestdemo.maomao.autotestdemo.auto."+className);
            Writer writer=source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
