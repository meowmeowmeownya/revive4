package net.revivers.reviverstwo.cmd.core.suggestion;

import java.util.List;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface SuggestionResolver<S> {
  @NotNull
  List<String> resolve(@NotNull S paramS, @NotNull SuggestionContext paramSuggestionContext);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\SuggestionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */