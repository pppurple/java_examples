package reflect;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pppurple on 2016/07/26.
 */
public class ReflectAnnotationExample {
    @FieldAnnotation
    public String name;

    @ConstructorAnnotaion
    public ReflectAnnotationExample(String name) {
        this.name = name;
    }

    @MethodAnnotation
    public String getName() {
        return name;
    }

    @ClassAnnotation
    public static class DeprecatedClass {

    }

    @Target(FIELD)
    @Retention(RUNTIME)
    public @interface FieldAnnotation {

    }

    @Target(CONSTRUCTOR)
    @Retention(RUNTIME)
    public @interface ConstructorAnnotaion {

    }

    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface MethodAnnotation {

    }

    @Target(TYPE)
    @Retention(RUNTIME)
    @Inherited
    public @interface ClassAnnotation {

    }
}
