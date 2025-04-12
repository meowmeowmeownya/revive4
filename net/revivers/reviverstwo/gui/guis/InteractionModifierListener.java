/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryAction;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
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
/*     */ public final class InteractionModifierListener
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onGuiClick(InventoryClickEvent event) {
/*  57 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/*  60 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/*  63 */     if ((!gui.canPlaceItems() && isPlaceItemEvent(event)) || (!gui.canTakeItems() && isTakeItemEvent(event)) || (!gui.canSwapItems() && isSwapItemEvent(event)) || (!gui.canDropItems() && isDropItemEvent(event)) || (!gui.allowsOtherActions() && isOtherEvent(event))) {
/*  64 */       event.setCancelled(true);
/*  65 */       event.setResult(Event.Result.DENY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onGuiDrag(InventoryDragEvent event) {
/*  78 */     if (!(event.getInventory().getHolder() instanceof BaseGui)) {
/*     */       return;
/*     */     }
/*  81 */     BaseGui gui = (BaseGui)event.getInventory().getHolder();
/*     */ 
/*     */     
/*  84 */     if (gui.canPlaceItems() || !isDraggingOnGui(event)) {
/*     */       return;
/*     */     }
/*  87 */     event.setCancelled(true);
/*  88 */     event.setResult(Event.Result.DENY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTakeItemEvent(InventoryClickEvent event) {
/* 100 */     Preconditions.checkNotNull(event, "event cannot be null");
/*     */     
/* 102 */     Inventory inventory = event.getInventory();
/* 103 */     Inventory clickedInventory = event.getClickedInventory();
/* 104 */     InventoryAction action = event.getAction();
/*     */ 
/*     */     
/* 107 */     if ((clickedInventory != null && clickedInventory.getType() == InventoryType.PLAYER) || inventory.getType() == InventoryType.PLAYER) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     return (action == InventoryAction.MOVE_TO_OTHER_INVENTORY || isTakeAction(action));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPlaceItemEvent(InventoryClickEvent event) {
/* 123 */     Preconditions.checkNotNull(event, "event cannot be null");
/*     */     
/* 125 */     Inventory inventory = event.getInventory();
/* 126 */     Inventory clickedInventory = event.getClickedInventory();
/* 127 */     InventoryAction action = event.getAction();
/*     */ 
/*     */     
/* 130 */     if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY && clickedInventory != null && clickedInventory
/* 131 */       .getType() == InventoryType.PLAYER && inventory
/* 132 */       .getType() != clickedInventory.getType()) {
/* 133 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 137 */     return (isPlaceAction(action) && (clickedInventory == null || clickedInventory
/* 138 */       .getType() != InventoryType.PLAYER) && inventory
/* 139 */       .getType() != InventoryType.PLAYER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSwapItemEvent(InventoryClickEvent event) {
/* 151 */     Preconditions.checkNotNull(event, "event cannot be null");
/*     */     
/* 153 */     Inventory inventory = event.getInventory();
/* 154 */     Inventory clickedInventory = event.getClickedInventory();
/* 155 */     InventoryAction action = event.getAction();
/*     */     
/* 157 */     return (isSwapAction(action) && (clickedInventory == null || clickedInventory
/* 158 */       .getType() != InventoryType.PLAYER) && inventory
/* 159 */       .getType() != InventoryType.PLAYER);
/*     */   }
/*     */   
/*     */   private boolean isDropItemEvent(InventoryClickEvent event) {
/* 163 */     Preconditions.checkNotNull(event, "event cannot be null");
/*     */     
/* 165 */     Inventory inventory = event.getInventory();
/* 166 */     Inventory clickedInventory = event.getClickedInventory();
/* 167 */     InventoryAction action = event.getAction();
/*     */     
/* 169 */     return (isDropAction(action) && (clickedInventory != null || inventory
/* 170 */       .getType() != InventoryType.PLAYER));
/*     */   }
/*     */   
/*     */   private boolean isOtherEvent(InventoryClickEvent event) {
/* 174 */     Preconditions.checkNotNull(event, "event cannot be null");
/*     */     
/* 176 */     Inventory inventory = event.getInventory();
/* 177 */     Inventory clickedInventory = event.getClickedInventory();
/* 178 */     InventoryAction action = event.getAction();
/*     */     
/* 180 */     return (isOtherAction(action) && (clickedInventory != null || inventory
/* 181 */       .getType() != InventoryType.PLAYER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDraggingOnGui(InventoryDragEvent event) {
/* 193 */     Preconditions.checkNotNull(event, "event cannot be null");
/* 194 */     int topSlots = event.getView().getTopInventory().getSize();
/*     */     
/* 196 */     return event.getRawSlots().stream().anyMatch(slot -> (slot.intValue() < topSlots));
/*     */   }
/*     */   
/*     */   private boolean isTakeAction(InventoryAction action) {
/* 200 */     Preconditions.checkNotNull(action, "action cannot be null");
/* 201 */     return ITEM_TAKE_ACTIONS.contains(action);
/*     */   }
/*     */   
/*     */   private boolean isPlaceAction(InventoryAction action) {
/* 205 */     Preconditions.checkNotNull(action, "action cannot be null");
/* 206 */     return ITEM_PLACE_ACTIONS.contains(action);
/*     */   }
/*     */   
/*     */   private boolean isSwapAction(InventoryAction action) {
/* 210 */     Preconditions.checkNotNull(action, "action cannot be null");
/* 211 */     return ITEM_SWAP_ACTIONS.contains(action);
/*     */   }
/*     */   
/*     */   private boolean isDropAction(InventoryAction action) {
/* 215 */     Preconditions.checkNotNull(action, "action cannot be null");
/* 216 */     return ITEM_DROP_ACTIONS.contains(action);
/*     */   }
/*     */   
/*     */   private boolean isOtherAction(InventoryAction action) {
/* 220 */     Preconditions.checkNotNull(action, "action cannot be null");
/* 221 */     return (action == InventoryAction.CLONE_STACK || action == InventoryAction.UNKNOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   private static final Set<InventoryAction> ITEM_TAKE_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.PICKUP_ONE, new InventoryAction[] { InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_ALL, InventoryAction.COLLECT_TO_CURSOR, InventoryAction.HOTBAR_SWAP, InventoryAction.MOVE_TO_OTHER_INVENTORY }));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   private static final Set<InventoryAction> ITEM_PLACE_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME, InventoryAction.PLACE_ALL));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   private static final Set<InventoryAction> ITEM_SWAP_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.HOTBAR_SWAP, InventoryAction.SWAP_WITH_CURSOR, InventoryAction.HOTBAR_MOVE_AND_READD));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   private static final Set<InventoryAction> ITEM_DROP_ACTIONS = Collections.unmodifiableSet(EnumSet.of(InventoryAction.DROP_ONE_SLOT, InventoryAction.DROP_ALL_SLOT, InventoryAction.DROP_ONE_CURSOR, InventoryAction.DROP_ALL_CURSOR));
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\InteractionModifierListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */