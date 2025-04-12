package net.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SelectorComponent extends BuildableComponent<SelectorComponent, SelectorComponent.Builder>, ScopedComponent<SelectorComponent> {
  @NotNull
  String pattern();
  
  @Contract(pure = true)
  @NotNull
  SelectorComponent pattern(@NotNull String paramString);
  
  @Nullable
  Component separator();
  
  @NotNull
  SelectorComponent separator(@Nullable ComponentLike paramComponentLike);
  
  public static interface Builder extends ComponentBuilder<SelectorComponent, Builder> {
    @Contract("_ -> this")
    @NotNull
    Builder pattern(@NotNull String param1String);
    
    @Contract("_ -> this")
    @NotNull
    Builder separator(@Nullable ComponentLike param1ComponentLike);
  }
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\SelectorComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */