package net.revivers.reviverstwo.cmd.core.requirement;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RequirementResolver<S> {
  boolean resolve(@NotNull S paramS);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\requirement\RequirementResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */