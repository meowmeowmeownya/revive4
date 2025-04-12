package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface MustBeInvokedByOverriders {}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\org\jetbrains\annotations\MustBeInvokedByOverriders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */