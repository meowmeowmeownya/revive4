package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Join {
  @NotNull
  String value() default " ";
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\Join.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */