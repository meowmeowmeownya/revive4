/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import net.revivers.reviverstwo.gui.components.GuiAction;
/*     */ import net.revivers.reviverstwo.gui.components.util.ItemNbt;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GuiListener
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onGuiClick(InventoryClickEvent event) {
/*     */     GuiItem guiItem;
/*  47 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/*  50 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/*  53 */     GuiAction<InventoryClickEvent> outsideClickAction = gui.getOutsideClickAction();
/*  54 */     if (outsideClickAction != null && event.getClickedInventory() == null) {
/*  55 */       outsideClickAction.execute((Event)event);
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     if (event.getClickedInventory() == null) {
/*     */       return;
/*     */     }
/*  62 */     GuiAction<InventoryClickEvent> defaultTopClick = gui.getDefaultTopClickAction();
/*  63 */     if (defaultTopClick != null && event.getClickedInventory().getType() != InventoryType.PLAYER) {
/*  64 */       defaultTopClick.execute((Event)event);
/*     */     }
/*     */ 
/*     */     
/*  68 */     GuiAction<InventoryClickEvent> playerInventoryClick = gui.getPlayerInventoryAction();
/*  69 */     if (playerInventoryClick != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
/*  70 */       playerInventoryClick.execute((Event)event);
/*     */     }
/*     */ 
/*     */     
/*  74 */     GuiAction<InventoryClickEvent> defaultClick = gui.getDefaultClickAction();
/*  75 */     if (defaultClick != null) defaultClick.execute((Event)event);
/*     */ 
/*     */     
/*  78 */     GuiAction<InventoryClickEvent> slotAction = gui.getSlotAction(event.getSlot());
/*  79 */     if (slotAction != null && event.getClickedInventory().getType() != InventoryType.PLAYER) {
/*  80 */       slotAction.execute((Event)event);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (gui instanceof PaginatedGui) {
/*  87 */       PaginatedGui paginatedGui = (PaginatedGui)gui;
/*     */ 
/*     */       
/*  90 */       guiItem = paginatedGui.getGuiItem(event.getSlot());
/*  91 */       if (guiItem == null) guiItem = paginatedGui.getPageItem(event.getSlot());
/*     */     
/*     */     } else {
/*     */       
/*  95 */       guiItem = gui.getGuiItem(event.getSlot());
/*     */     } 
/*     */     
/*  98 */     if (!isGuiItem(event.getCurrentItem(), guiItem)) {
/*     */       return;
/*     */     }
/* 101 */     GuiAction<InventoryClickEvent> itemAction = guiItem.getAction();
/* 102 */     if (itemAction != null) itemAction.execute((Event)event);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onGuiDrag(InventoryDragEvent event) {
/* 112 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/* 115 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/* 118 */     GuiAction<InventoryDragEvent> dragAction = gui.getDragAction();
/* 119 */     if (dragAction != null) dragAction.execute((Event)event);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onGuiClose(InventoryCloseEvent event) {
/* 129 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/* 132 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/* 135 */     if (gui instanceof PersistentPaginatedGui) {
/* 136 */       ((PersistentPaginatedGui)gui).savePage();
/*     */     }
/*     */ 
/*     */     
/* 140 */     GuiAction<InventoryCloseEvent> closeAction = gui.getCloseGuiAction();
/*     */ 
/*     */     
/* 143 */     if (closeAction != null && !gui.isUpdating() && gui.shouldRunCloseAction()) closeAction.execute((Event)event);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onGuiOpen(InventoryOpenEvent event) {
/* 153 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/* 156 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/* 159 */     GuiAction<InventoryOpenEvent> openAction = gui.getOpenGuiAction();
/*     */ 
/*     */     
/* 162 */     if (openAction != null && !gui.isUpdating()) openAction.execute((Event)event);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isGuiItem(@Nullable ItemStack currentItem, @Nullable GuiItem guiItem) {
/* 173 */     if (currentItem == null || guiItem == null) return false;
/*     */     
/* 175 */     String nbt = ItemNbt.getString(currentItem, "mf-gui");
/* 176 */     if (nbt == null) return false; 
/* 177 */     return nbt.equals(guiItem.getUuid().toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\GuiListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */