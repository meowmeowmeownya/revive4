package net.revivers.reviverstwo.cmd.core.message.context;

import org.jetbrains.annotations.NotNull;

public interface MessageContext {
  @NotNull
  String getCommand();
  
  @NotNull
  String getSubCommand();
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\message\context\MessageContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */