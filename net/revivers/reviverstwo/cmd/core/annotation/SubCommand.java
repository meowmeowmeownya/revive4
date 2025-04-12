package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SubCommand {
  @NotNull
  String value();
  
  @NotNull
  String[] alias() default {};
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\SubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */