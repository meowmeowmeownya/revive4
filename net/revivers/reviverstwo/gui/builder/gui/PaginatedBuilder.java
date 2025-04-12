/*    */ package net.revivers.reviverstwo.gui.builder.gui;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*    */ import net.revivers.reviverstwo.gui.guis.BaseGui;
/*    */ import net.revivers.reviverstwo.gui.guis.PaginatedGui;
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
/*    */ public class PaginatedBuilder
/*    */   extends BaseGuiBuilder<PaginatedGui, PaginatedBuilder>
/*    */ {
/* 38 */   private int pageSize = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   @Contract("_ -> this")
/*    */   public PaginatedBuilder pageSize(int pageSize) {
/* 49 */     this.pageSize = pageSize;
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   @Contract(" -> new")
/*    */   public PaginatedGui create() {
/* 62 */     PaginatedGui gui = new PaginatedGui(getRows(), this.pageSize, Legacy.SERIALIZER.serialize(getTitle()), getModifiers());
/*    */     
/* 64 */     Consumer<PaginatedGui> consumer = getConsumer();
/* 65 */     if (consumer != null) consumer.accept(gui);
/*    */     
/* 67 */     return gui;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\gui\PaginatedBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */