package org.intellij.lang.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface Subst {
  String value();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\intellij\lang\annotations\Subst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */