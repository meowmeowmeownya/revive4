package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(CommandFlags.class)
public @interface Flag {
  @NotNull
  String flag() default "";
  
  @NotNull
  String longFlag() default "";
  
  @NotNull
  Class<?> argument() default void.class;
  
  @NotNull
  String suggestion() default "";
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\Flag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */