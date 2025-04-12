package net.revivers.reviverstwo.cmd.core.argument;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ArgumentResolver<S> {
  @Nullable
  Object resolve(@NotNull S paramS, @NotNull String paramString);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\ArgumentResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */