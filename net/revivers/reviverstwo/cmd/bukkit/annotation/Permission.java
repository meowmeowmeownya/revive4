package net.revivers.reviverstwo.cmd.bukkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.permissions.PermissionDefault;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Permission {
  String[] value();
  
  PermissionDefault def() default PermissionDefault.OP;
  
  String description() default "";
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\annotation\Permission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */