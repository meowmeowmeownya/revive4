package org.intellij.lang.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NonNls;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface MagicConstant {
  long[] intValues() default {};
  
  @NonNls
  String[] stringValues() default {};
  
  long[] flags() default {};
  
  Class<?> valuesFromClass() default void.class;
  
  Class<?> flagsFromClass() default void.class;
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\intellij\lang\annotations\MagicConstant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */