package net.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NBTComponent<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends BuildableComponent<C, B> {
  @NotNull
  String nbtPath();
  
  @Contract(pure = true)
  @NotNull
  C nbtPath(@NotNull String paramString);
  
  boolean interpret();
  
  @Contract(pure = true)
  @NotNull
  C interpret(boolean paramBoolean);
  
  @Nullable
  Component separator();
  
  @NotNull
  C separator(@Nullable ComponentLike paramComponentLike);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\NBTComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */