package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.TYPE_USE})
public @interface PropertyKey {
  @NonNls
  String resourceBundle();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\jetbrains\annotations\PropertyKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */