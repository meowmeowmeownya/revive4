package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PACKAGE})
public @interface NonNls {}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\jetbrains\annotations\NonNls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */