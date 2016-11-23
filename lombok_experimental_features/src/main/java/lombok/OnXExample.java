package lombok;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@AllArgsConstructor(onConstructor = @__(@OnXExample.ConstructorAnnotation))
public class OnXExample {
    @Setter(onParam = @__({@Min(10), @Max(20)}))
    public String name;

    @Getter(onMethod = @__({@MethodAnnotation}))
    private int num;

    @NotNull
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface MethodAnnotation {
    }

    @Target(CONSTRUCTOR)
    @Retention(RUNTIME)
    @interface ConstructorAnnotation {
    }
}
