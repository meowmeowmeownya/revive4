package net.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScoreComponent extends BuildableComponent<ScoreComponent, ScoreComponent.Builder>, ScopedComponent<ScoreComponent> {
  @NotNull
  String name();
  
  @Contract(pure = true)
  @NotNull
  ScoreComponent name(@NotNull String paramString);
  
  @NotNull
  String objective();
  
  @Contract(pure = true)
  @NotNull
  ScoreComponent objective(@NotNull String paramString);
  
  @Deprecated
  @Nullable
  String value();
  
  @Deprecated
  @Contract(pure = true)
  @NotNull
  ScoreComponent value(@Nullable String paramString);
  
  public static interface Builder extends ComponentBuilder<ScoreComponent, Builder> {
    @Contract("_ -> this")
    @NotNull
    Builder name(@NotNull String param1String);
    
    @Contract("_ -> this")
    @NotNull
    Builder objective(@NotNull String param1String);
    
    @Deprecated
    @Contract("_ -> this")
    @NotNull
    Builder value(@Nullable String param1String);
  }
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\ScoreComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */