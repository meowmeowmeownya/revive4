/*    */ package net.revivers.reviverstwo.gui.components;
/*    */ 
/*    */ import org.bukkit.event.inventory.InventoryType;
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
/*    */ public enum GuiType
/*    */ {
/* 32 */   CHEST(InventoryType.CHEST, 9),
/* 33 */   WORKBENCH(InventoryType.WORKBENCH, 9),
/* 34 */   HOPPER(InventoryType.HOPPER, 5),
/* 35 */   DISPENSER(InventoryType.DISPENSER, 8),
/* 36 */   BREWING(InventoryType.BREWING, 4);
/*    */   
/*    */   @NotNull
/*    */   private final InventoryType inventoryType;
/*    */   private final int limit;
/*    */   
/*    */   GuiType(InventoryType inventoryType, int limit) {
/* 43 */     this.inventoryType = inventoryType;
/* 44 */     this.limit = limit;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public InventoryType getInventoryType() {
/* 49 */     return this.inventoryType;
/*    */   }
/*    */   
/*    */   public int getLimit() {
/* 53 */     return this.limit;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\components\GuiType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */