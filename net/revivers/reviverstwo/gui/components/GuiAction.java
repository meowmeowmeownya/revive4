package net.revivers.reviverstwo.gui.components;

@FunctionalInterface
public interface GuiAction<T extends org.bukkit.event.Event> {
  void execute(T paramT);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\components\GuiAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */