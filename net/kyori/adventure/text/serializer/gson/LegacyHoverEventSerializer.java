package net.kyori.adventure.text.serializer.gson;

import java.io.IOException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.util.Codec;
import org.jetbrains.annotations.NotNull;

public interface LegacyHoverEventSerializer {
  HoverEvent.ShowItem deserializeShowItem(@NotNull Component paramComponent) throws IOException;
  
  HoverEvent.ShowEntity deserializeShowEntity(@NotNull Component paramComponent, Codec.Decoder<Component, String, ? extends RuntimeException> paramDecoder) throws IOException;
  
  @NotNull
  Component serializeShowItem(HoverEvent.ShowItem paramShowItem) throws IOException;
  
  @NotNull
  Component serializeShowEntity(HoverEvent.ShowEntity paramShowEntity, Codec.Encoder<Component, String, ? extends RuntimeException> paramEncoder) throws IOException;
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\LegacyHoverEventSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */