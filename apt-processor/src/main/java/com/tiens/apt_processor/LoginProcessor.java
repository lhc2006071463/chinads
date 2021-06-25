package com.tiens.apt_processor;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tiens.apt_annotation.Login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class LoginProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Elements mElementUtils;
    private ProcessingEnvironment processingEnvironment;
    private List<String> classList = new ArrayList<>();
    private String fullClassName;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Login.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for(TypeElement typeElement: set) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
            classList.clear();
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Login.class);

            for(Element element: elements) {
                TypeElement variableElement = (TypeElement) element;
                fullClassName = variableElement.getQualifiedName().toString();
                mMessager.printMessage(Diagnostic.Kind.NOTE, fullClassName);
                Route routeAnnotation = variableElement.getAnnotation(Route.class);
                String path = routeAnnotation.path();
                classList.add(path);
            }

            ClassName stringType = ClassName.get("java.lang", "String");
            ClassName list = ClassName.get("java.util", "ArrayList");
            TypeName listOfString = ParameterizedTypeName.get(list, stringType);

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getPathList")
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.STATIC)
                    .returns(listOfString)
                    .addStatement("ArrayList<String> pathList = new ArrayList<>()");

            for(String name : classList) {
                methodBuilder.addStatement("pathList.add($S)",name);
            }

            methodBuilder.addStatement("return pathList");
            Map<String,String> options = processingEnvironment.getOptions();
            TypeSpec typeSpec = TypeSpec.classBuilder("PathUtil$"+options.get("moduleName"))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodBuilder.build())
                    .build();
            try {
                JavaFile.builder("com.tiens.vshare",typeSpec).build().writeTo(processingEnvironment.getFiler());
            }catch (IOException e) {
                e.printStackTrace();
            }
            mMessager.printMessage(Diagnostic.Kind.NOTE, "process finish ...");
        }
        return true;
    }
}
