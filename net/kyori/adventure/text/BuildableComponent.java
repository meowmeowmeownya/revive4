package net.kyori.adventure.text;

import net.kyori.adventure.util.Buildable;
import org.jetbrains.annotations.NotNull;

public interface BuildableComponent<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>> extends Buildable<C, B>, Component {
  @NotNull
  B toBuilder();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\BuildableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */