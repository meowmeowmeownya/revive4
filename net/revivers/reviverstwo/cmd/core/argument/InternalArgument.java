package net.revivers.reviverstwo.cmd.core.argument;

import java.util.List;
import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InternalArgument<S, T> {
  @NotNull
  String getName();
  
  int getPosition();
  
  @NotNull
  String getDescription();
  
  @NotNull
  Class<?> getType();
  
  boolean isOptional();
  
  @Nullable
  Object resolve(@NotNull S paramS, @NotNull T paramT);
  
  @NotNull
  List<String> suggestions(@NotNull S paramS, @NotNull List<String> paramList, @NotNull SuggestionContext paramSuggestionContext);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\InternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */