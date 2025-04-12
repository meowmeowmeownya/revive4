package net.revivers.reviverstwo.cmd.core.message.context;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageContextFactory<C extends MessageContext> {
  @Contract("_, _ -> new")
  @NotNull
  C create(@NotNull String paramString1, @NotNull String paramString2);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\context\MessageContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */