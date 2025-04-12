package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Contract {
  @NonNls
  String value() default "";
  
  boolean pure() default false;
  
  @NonNls
  String mutates() default "";
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\jetbrains\annotations\Contract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */