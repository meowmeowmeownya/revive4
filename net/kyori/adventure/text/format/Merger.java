package net.kyori.adventure.text.format;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

interface Merger {
  void mergeColor(StyleImpl.BuilderImpl paramBuilderImpl, @Nullable TextColor paramTextColor);
  
  void mergeDecoration(StyleImpl.BuilderImpl paramBuilderImpl, @NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState);
  
  void mergeClickEvent(StyleImpl.BuilderImpl paramBuilderImpl, @Nullable ClickEvent paramClickEvent);
  
  void mergeHoverEvent(StyleImpl.BuilderImpl paramBuilderImpl, @Nullable HoverEvent<?> paramHoverEvent);
  
  void mergeInsertion(StyleImpl.BuilderImpl paramBuilderImpl, @Nullable String paramString);
  
  void mergeFont(StyleImpl.BuilderImpl paramBuilderImpl, @Nullable Key paramKey);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\Merger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */