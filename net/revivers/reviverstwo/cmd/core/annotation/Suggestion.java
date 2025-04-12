package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(Suggestions.class)
public @interface Suggestion {
  String value();
  
  boolean strict() default false;
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\Suggestion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */