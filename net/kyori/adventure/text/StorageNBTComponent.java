package net.kyori.adventure.text;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface StorageNBTComponent extends NBTComponent<StorageNBTComponent, StorageNBTComponent.Builder>, ScopedComponent<StorageNBTComponent> {
  @NotNull
  Key storage();
  
  @Contract(pure = true)
  @NotNull
  StorageNBTComponent storage(@NotNull Key paramKey);
  
  public static interface Builder extends NBTComponentBuilder<StorageNBTComponent, Builder> {
    @Contract("_ -> this")
    @NotNull
    Builder storage(@NotNull Key param1Key);
  }
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\StorageNBTComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */