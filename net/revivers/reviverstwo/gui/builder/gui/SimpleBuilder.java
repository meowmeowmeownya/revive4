/*    */ package net.revivers.reviverstwo.gui.builder.gui;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.revivers.reviverstwo.gui.components.GuiType;
/*    */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*    */ import net.revivers.reviverstwo.gui.guis.BaseGui;
/*    */ import net.revivers.reviverstwo.gui.guis.Gui;
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
/*    */ public final class SimpleBuilder
/*    */   extends BaseGuiBuilder<Gui, SimpleBuilder>
/*    */ {
/*    */   private GuiType guiType;
/*    */   
/*    */   public SimpleBuilder(@NotNull GuiType guiType) {
/* 47 */     this.guiType = guiType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   @Contract("_ -> this")
/*    */   public SimpleBuilder type(@NotNull GuiType guiType) {
/* 60 */     this.guiType = guiType;
/* 61 */     return this;
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
/*    */   public Gui create() {
/*    */     Gui gui;
/* 74 */     String title = Legacy.SERIALIZER.serialize(getTitle());
/* 75 */     if (this.guiType == null || this.guiType == GuiType.CHEST) {
/* 76 */       gui = new Gui(getRows(), title, getModifiers());
/*    */     } else {
/* 78 */       gui = new Gui(this.guiType, title, getModifiers());
/*    */     } 
/*    */     
/* 81 */     Consumer<Gui> consumer = getConsumer();
/* 82 */     if (consumer != null) consumer.accept(gui);
/*    */     
/* 84 */     return gui;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\gui\SimpleBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */