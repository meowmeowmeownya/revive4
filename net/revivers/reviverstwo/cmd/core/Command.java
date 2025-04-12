package net.revivers.reviverstwo.cmd.core;

import org.jetbrains.annotations.NotNull;

public interface Command<S, SC extends SubCommand<S>> {
  void addSubCommand(@NotNull String paramString, @NotNull SC paramSC);
  
  void addSubCommandAlias(@NotNull String paramString, @NotNull SC paramSC);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */