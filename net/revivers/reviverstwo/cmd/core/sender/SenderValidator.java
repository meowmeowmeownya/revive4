package net.revivers.reviverstwo.cmd.core.sender;

import java.util.Set;
import net.revivers.reviverstwo.cmd.core.SubCommand;
import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
import org.jetbrains.annotations.NotNull;

public interface SenderValidator<S> {
  @NotNull
  Set<Class<? extends S>> getAllowedSenders();
  
  boolean validate(@NotNull MessageRegistry<S> paramMessageRegistry, @NotNull SubCommand<S> paramSubCommand, @NotNull S paramS);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\sender\SenderValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */