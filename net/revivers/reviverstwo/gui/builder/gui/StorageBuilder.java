/*    */ package net.revivers.reviverstwo.gui.builder.gui;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*    */ import net.revivers.reviverstwo.gui.guis.BaseGui;
/*    */ import net.revivers.reviverstwo.gui.guis.StorageGui;
/*    */ import org.jetbrains.annotations.Contract;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StorageBuilder
/*    */   extends BaseGuiBuilder<StorageGui, StorageBuilder>
/*    */ {
/*    */   @NotNull
/*    */   @Contract(" -> new")
/*    */   public StorageGui create() {
/* 47 */     StorageGui gui = new StorageGui(getRows(), Legacy.SERIALIZER.serialize(getTitle()), getModifiers());
/*    */     
/* 49 */     Consumer<StorageGui> consumer = getConsumer();
/* 50 */     if (consumer != null) consumer.accept(gui);
/*    */     
/* 52 */     return gui;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\gui\StorageBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */