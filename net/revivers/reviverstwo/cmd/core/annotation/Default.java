package net.revivers.reviverstwo.cmd.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Default {
  public static final String DEFAULT_CMD_NAME = "TH_DEFAULT";
  
  @NotNull
  String[] alias() default {};
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\annotation\Default.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */