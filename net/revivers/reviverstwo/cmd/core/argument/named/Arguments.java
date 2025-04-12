package net.revivers.reviverstwo.cmd.core.argument.named;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface Arguments {
  @NotNull
  <T> Optional<T> get(@NotNull String paramString, @NotNull Class<T> paramClass);
  
  @NotNull
  <T> Optional<List<T>> getAsList(@NotNull String paramString, @NotNull Class<T> paramClass);
  
  @NotNull
  <T> Optional<Set<T>> getAsSet(@NotNull String paramString, @NotNull Class<T> paramClass);
  
  @NotNull
  Map<String, Object> getArguments();
  
  boolean isEmpty();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\Arguments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */