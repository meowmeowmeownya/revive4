package net.revivers.reviverstwo.cmd.core.suggestion;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Suggestion<S> {
  @NotNull
  List<String> getSuggestions(@NotNull S paramS, @NotNull String paramString, @NotNull SuggestionContext paramSuggestionContext);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\suggestion\Suggestion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */