package org.intellij.lang.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NonNls;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
public @interface Language {
  @NonNls
  String value();
  
  @NonNls
  String prefix() default "";
  
  @NonNls
  String suffix() default "";
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\intellij\lang\annotations\Language.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */