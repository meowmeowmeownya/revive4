package net.revivers.reviverstwo.cmd.core.flag;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Flags {
  boolean hasFlag(@NotNull String paramString);
  
  @NotNull
  <T> Optional<T> getValue(@NotNull String paramString, @NotNull Class<T> paramClass);
  
  @NotNull
  Optional<String> getValue(@NotNull String paramString);
  
  @NotNull
  String getText();
  
  @NotNull
  String getText(@NotNull String paramString);
  
  @NotNull
  List<String> getArgs();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\Flags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */