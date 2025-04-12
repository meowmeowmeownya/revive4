package net.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EntityNBTComponent extends NBTComponent<EntityNBTComponent, EntityNBTComponent.Builder>, ScopedComponent<EntityNBTComponent> {
  @NotNull
  String selector();
  
  @Contract(pure = true)
  @NotNull
  EntityNBTComponent selector(@NotNull String paramString);
  
  public static interface Builder extends NBTComponentBuilder<EntityNBTComponent, Builder> {
    @Contract("_ -> this")
    @NotNull
    Builder selector(@NotNull String param1String);
  }
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\EntityNBTComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */