package net.revivers.reviverstwo.cmd.core;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface SubCommand<S> {
  @NotNull
  String getName();
  
  @NotNull
  String getParentName();
  
  @NotNull
  Class<? extends S> getSenderType();
  
  boolean hasArguments();
  
  boolean isDefault();
  
  void execute(@NotNull S paramS, @NotNull List<String> paramList);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\SubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */