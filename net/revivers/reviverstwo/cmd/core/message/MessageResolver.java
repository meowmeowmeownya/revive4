package net.revivers.reviverstwo.cmd.core.message;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageResolver<S, C extends net.revivers.reviverstwo.cmd.core.message.context.MessageContext> {
  void resolve(@NotNull S paramS, @NotNull C paramC);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\MessageResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */