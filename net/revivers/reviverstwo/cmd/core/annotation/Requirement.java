package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(Requirements.class)
public @interface Requirement {
  @NotNull
  String value();
  
  @NotNull
  String messageKey() default "";
  
  boolean invert() default false;
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\Requirement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */