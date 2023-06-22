package com.ling.lib_processor

import com.ling.lib_annotation.BindView
import com.squareup.javapoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.WARNING


class BindingProcessor : AbstractProcessor() {
    private var filer: Filer? = null
    private var mMessager: Messager? = null

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        filer = processingEnv?.filer
        mMessager = processingEnv?.messager
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        p1?.let {
            bind(it)
        }
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(BindView::class.java.canonicalName)
    }

    private fun bind(round: RoundEnvironment) {
        for (element in round.rootElements) {
            var methodSpec: MethodSpec.Builder? = null
            val packageStr = element.enclosingElement.toString()
            val classStr = element.simpleName.toString()
            for (item in element.enclosedElements) {
                when (item.kind) {
                    ElementKind.FIELD -> {
                        val field = item.getAnnotation(BindView::class.java)
                        if (field != null) {
                            mMessager?.printMessage(WARNING, "process:class:${packageStr}__${classStr}")
                            if (methodSpec == null) {
                                methodSpec = MethodSpec.constructorBuilder().apply {
                                    modifiers.add(Modifier.PUBLIC)
                                    parameters.add(ParameterSpec.builder(ClassName.get(packageStr, classStr), "view").build())
                                }
                            }
                            methodSpec?.addStatement("view.\$N = view.findViewById(\$L)", item.simpleName, field.value)
                        }
                    }
                    ElementKind.CLASS -> {

                    }
                    else -> {

                    }
                }
            }
            methodSpec?.let {
                mMessager?.printMessage(WARNING, "process:methodSpec:${methodSpec}")
                val builtClass = TypeSpec.classBuilder(ClassName.get(packageStr, classStr + "LingMyBinding"))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethods(listOf(methodSpec.build()))
                    .build()
                mMessager?.printMessage(WARNING, "process:builtClass:${builtClass}")
                JavaFile.builder(packageStr, builtClass).build().writeTo(filer)
                mMessager?.printMessage(WARNING, "process:end")
            }
        }
    }
}