package net.revivers.reviverstwo.gui.components;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Serializable {
  List<String> encodeGui();
  
  void decodeGui(@NotNull List<String> paramList);
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\components\Serializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */