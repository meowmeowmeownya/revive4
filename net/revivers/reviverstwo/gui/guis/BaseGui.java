/*      */ package net.revivers.reviverstwo.gui.guis;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import net.kyori.adventure.text.Component;
/*      */ import net.revivers.reviverstwo.gui.components.GuiAction;
/*      */ import net.revivers.reviverstwo.gui.components.GuiType;
/*      */ import net.revivers.reviverstwo.gui.components.InteractionModifier;
/*      */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*      */ import net.revivers.reviverstwo.gui.components.util.GuiFiller;
/*      */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.entity.HumanEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.inventory.InventoryClickEvent;
/*      */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*      */ import org.bukkit.event.inventory.InventoryDragEvent;
/*      */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryHolder;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.java.JavaPlugin;
/*      */ import org.jetbrains.annotations.Contract;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BaseGui
/*      */   implements InventoryHolder
/*      */ {
/*   67 */   private static final Plugin plugin = (Plugin)JavaPlugin.getProvidingPlugin(BaseGui.class); private Inventory inventory;
/*      */   private String title;
/*      */   
/*      */   static {
/*   71 */     Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
/*      */     
/*   73 */     Bukkit.getPluginManager().registerEvents(new InteractionModifierListener(), plugin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   private final GuiFiller filler = new GuiFiller(this);
/*      */   
/*   85 */   private int rows = 1;
/*      */ 
/*      */   
/*   88 */   private GuiType guiType = GuiType.CHEST;
/*      */ 
/*      */   
/*      */   private final Map<Integer, GuiItem> guiItems;
/*      */ 
/*      */   
/*      */   private final Map<Integer, GuiAction<InventoryClickEvent>> slotActions;
/*      */ 
/*      */   
/*      */   private final Set<InteractionModifier> interactionModifiers;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryClickEvent> defaultClickAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryClickEvent> defaultTopClickAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryClickEvent> playerInventoryAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryDragEvent> dragAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryCloseEvent> closeGuiAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryOpenEvent> openGuiAction;
/*      */ 
/*      */   
/*      */   private GuiAction<InventoryClickEvent> outsideClickAction;
/*      */   
/*      */   private boolean updating;
/*      */   
/*      */   private boolean runCloseAction = true;
/*      */   
/*      */   private boolean runOpenAction = true;
/*      */ 
/*      */   
/*      */   public BaseGui(int rows, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  128 */     int finalRows = rows;
/*  129 */     if (rows < 1 || rows > 6) finalRows = 1; 
/*  130 */     this.rows = finalRows;
/*  131 */     this.interactionModifiers = safeCopyOf(interactionModifiers);
/*  132 */     this.title = title;
/*  133 */     int inventorySize = this.rows * 9;
/*  134 */     this.inventory = Bukkit.createInventory(this, inventorySize, title);
/*  135 */     this.slotActions = new LinkedHashMap<>(inventorySize);
/*  136 */     this.guiItems = new LinkedHashMap<>(inventorySize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BaseGui(@NotNull GuiType guiType, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  148 */     this.guiType = guiType;
/*  149 */     this.interactionModifiers = safeCopyOf(interactionModifiers);
/*  150 */     this.title = title;
/*  151 */     int inventorySize = guiType.getLimit();
/*  152 */     this.inventory = Bukkit.createInventory(this, guiType.getInventoryType(), title);
/*  153 */     this.slotActions = new LinkedHashMap<>(inventorySize);
/*  154 */     this.guiItems = new LinkedHashMap<>(inventorySize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private EnumSet<InteractionModifier> safeCopyOf(@NotNull Set<InteractionModifier> set) {
/*  165 */     if (set.isEmpty()) return EnumSet.noneOf(InteractionModifier.class); 
/*  166 */     return EnumSet.copyOf(set);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BaseGui(int rows, @NotNull String title) {
/*  178 */     int finalRows = rows;
/*  179 */     if (rows < 1 || rows > 6) finalRows = 1; 
/*  180 */     this.rows = finalRows;
/*  181 */     this.interactionModifiers = EnumSet.noneOf(InteractionModifier.class);
/*  182 */     this.title = title;
/*      */     
/*  184 */     this.inventory = Bukkit.createInventory(this, this.rows * 9, title);
/*  185 */     this.slotActions = new LinkedHashMap<>();
/*  186 */     this.guiItems = new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BaseGui(@NotNull GuiType guiType, @NotNull String title) {
/*  198 */     this.guiType = guiType;
/*  199 */     this.interactionModifiers = EnumSet.noneOf(InteractionModifier.class);
/*  200 */     this.title = title;
/*      */     
/*  202 */     this.inventory = Bukkit.createInventory(this, this.guiType.getInventoryType(), title);
/*  203 */     this.slotActions = new LinkedHashMap<>();
/*  204 */     this.guiItems = new LinkedHashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NotNull
/*      */   public String getTitle() {
/*  215 */     return this.title;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Component title() {
/*  225 */     return (Component)Legacy.SERIALIZER.deserialize(this.title);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItem(int slot, @NotNull GuiItem guiItem) {
/*  235 */     validateSlot(slot);
/*  236 */     this.guiItems.put(Integer.valueOf(slot), guiItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(@NotNull GuiItem item) {
/*  248 */     Optional<Map.Entry<Integer, GuiItem>> entry = this.guiItems.entrySet().stream().filter(it -> ((GuiItem)it.getValue()).equals(item)).findFirst();
/*      */     
/*  250 */     entry.ifPresent(it -> {
/*      */           this.guiItems.remove(it.getKey());
/*      */           this.inventory.remove(((GuiItem)it.getValue()).getItemStack());
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(@NotNull ItemStack item) {
/*  265 */     Optional<Map.Entry<Integer, GuiItem>> entry = this.guiItems.entrySet().stream().filter(it -> ((GuiItem)it.getValue()).getItemStack().equals(item)).findFirst();
/*      */     
/*  267 */     entry.ifPresent(it -> {
/*      */           this.guiItems.remove(it.getKey());
/*      */           this.inventory.remove(item);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(int slot) {
/*  279 */     validateSlot(slot);
/*  280 */     this.guiItems.remove(Integer.valueOf(slot));
/*  281 */     this.inventory.setItem(slot, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(int row, int col) {
/*  291 */     removeItem(getSlotFromRowCol(row, col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItem(@NotNull List<Integer> slots, @NotNull GuiItem guiItem) {
/*  301 */     for (Iterator<Integer> iterator = slots.iterator(); iterator.hasNext(); ) { int slot = ((Integer)iterator.next()).intValue();
/*  302 */       setItem(slot, guiItem); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItem(int row, int col, @NotNull GuiItem guiItem) {
/*  314 */     setItem(getSlotFromRowCol(row, col), guiItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(@NotNull GuiItem... items) {
/*  324 */     addItem(false, items);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(boolean expandIfFull, @NotNull GuiItem... items) {
/*  336 */     List<GuiItem> notAddedItems = new ArrayList<>();
/*      */     
/*  338 */     for (GuiItem guiItem : items) {
/*  339 */       for (int slot = 0; slot < this.rows * 9; slot++) {
/*  340 */         if (this.guiItems.get(Integer.valueOf(slot)) != null) {
/*  341 */           if (slot == this.rows * 9 - 1) {
/*  342 */             notAddedItems.add(guiItem);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  347 */           this.guiItems.put(Integer.valueOf(slot), guiItem);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  352 */     if (!expandIfFull || this.rows >= 6 || notAddedItems
/*  353 */       .isEmpty() || (this.guiType != null && this.guiType != GuiType.CHEST)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  358 */     this.rows++;
/*  359 */     this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
/*  360 */     update();
/*  361 */     addItem(true, notAddedItems.<GuiItem>toArray(new GuiItem[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultClickAction(@Nullable GuiAction<InventoryClickEvent> defaultClickAction) {
/*  371 */     this.defaultClickAction = defaultClickAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultTopClickAction(@Nullable GuiAction<InventoryClickEvent> defaultTopClickAction) {
/*  382 */     this.defaultTopClickAction = defaultTopClickAction;
/*      */   }
/*      */   
/*      */   public void setPlayerInventoryAction(@Nullable GuiAction<InventoryClickEvent> playerInventoryAction) {
/*  386 */     this.playerInventoryAction = playerInventoryAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutsideClickAction(@Nullable GuiAction<InventoryClickEvent> outsideClickAction) {
/*  396 */     this.outsideClickAction = outsideClickAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDragAction(@Nullable GuiAction<InventoryDragEvent> dragAction) {
/*  406 */     this.dragAction = dragAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCloseGuiAction(@Nullable GuiAction<InventoryCloseEvent> closeGuiAction) {
/*  416 */     this.closeGuiAction = closeGuiAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOpenGuiAction(@Nullable GuiAction<InventoryOpenEvent> openGuiAction) {
/*  426 */     this.openGuiAction = openGuiAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSlotAction(int slot, @Nullable GuiAction<InventoryClickEvent> slotAction) {
/*  437 */     validateSlot(slot);
/*  438 */     this.slotActions.put(Integer.valueOf(slot), slotAction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSlotAction(int row, int col, @Nullable GuiAction<InventoryClickEvent> slotAction) {
/*  450 */     addSlotAction(getSlotFromRowCol(row, col), slotAction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public GuiItem getGuiItem(int slot) {
/*  461 */     return this.guiItems.get(Integer.valueOf(slot));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUpdating() {
/*  471 */     return this.updating;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpdating(boolean updating) {
/*  480 */     this.updating = updating;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void open(@NotNull HumanEntity player) {
/*  489 */     if (player.isSleeping())
/*      */       return; 
/*  491 */     this.inventory.clear();
/*  492 */     populateGui();
/*  493 */     player.openInventory(this.inventory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(@NotNull HumanEntity player) {
/*  502 */     close(player, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(@NotNull HumanEntity player, boolean runCloseAction) {
/*  512 */     Bukkit.getScheduler().runTaskLater(plugin, () -> { this.runCloseAction = runCloseAction; player.closeInventory(); this.runCloseAction = true; }2L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  523 */     this.inventory.clear();
/*  524 */     populateGui();
/*  525 */     for (HumanEntity viewer : new ArrayList(this.inventory.getViewers())) ((Player)viewer).updateInventory();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> this")
/*      */   @NotNull
/*      */   public BaseGui updateTitle(@NotNull String title) {
/*  538 */     this.updating = true;
/*      */     
/*  540 */     List<HumanEntity> viewers = new ArrayList<>(this.inventory.getViewers());
/*      */     
/*  542 */     this.inventory = Bukkit.createInventory(this, this.inventory.getSize(), title);
/*      */     
/*  544 */     for (HumanEntity player : viewers) {
/*  545 */       open(player);
/*      */     }
/*      */     
/*  548 */     this.updating = false;
/*  549 */     this.title = title;
/*  550 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItem(int slot, @NotNull ItemStack itemStack) {
/*  560 */     GuiItem guiItem = this.guiItems.get(Integer.valueOf(slot));
/*      */     
/*  562 */     if (guiItem == null) {
/*  563 */       updateItem(slot, new GuiItem(itemStack));
/*      */       
/*      */       return;
/*      */     } 
/*  567 */     guiItem.setItemStack(itemStack);
/*  568 */     updateItem(slot, guiItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItem(int row, int col, @NotNull ItemStack itemStack) {
/*  579 */     updateItem(getSlotFromRowCol(row, col), itemStack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItem(int slot, @NotNull GuiItem item) {
/*  589 */     this.guiItems.put(Integer.valueOf(slot), item);
/*  590 */     this.inventory.setItem(slot, item.getItemStack());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItem(int row, int col, @NotNull GuiItem item) {
/*  601 */     updateItem(getSlotFromRowCol(row, col), item);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableItemPlace() {
/*  614 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_PLACE);
/*  615 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableItemTake() {
/*  628 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_TAKE);
/*  629 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableItemSwap() {
/*  642 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_SWAP);
/*  643 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableItemDrop() {
/*  655 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_DROP);
/*  656 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableOtherActions() {
/*  669 */     this.interactionModifiers.add(InteractionModifier.PREVENT_OTHER_ACTIONS);
/*  670 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui disableAllInteractions() {
/*  683 */     this.interactionModifiers.addAll(InteractionModifier.VALUES);
/*  684 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableItemPlace() {
/*  697 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_PLACE);
/*  698 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableItemTake() {
/*  711 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_TAKE);
/*  712 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableItemSwap() {
/*  725 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_SWAP);
/*  726 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableItemDrop() {
/*  738 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_DROP);
/*  739 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableOtherActions() {
/*  752 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_OTHER_ACTIONS);
/*  753 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   @Contract(" -> this")
/*      */   public BaseGui enableAllInteractions() {
/*  766 */     this.interactionModifiers.clear();
/*  767 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceItems() {
/*  778 */     return !this.interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_PLACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canTakeItems() {
/*  789 */     return !this.interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_TAKE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSwapItems() {
/*  800 */     return !this.interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_SWAP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDropItems() {
/*  810 */     return !this.interactionModifiers.contains(InteractionModifier.PREVENT_ITEM_DROP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean allowsOtherActions() {
/*  820 */     return !this.interactionModifiers.contains(InteractionModifier.PREVENT_OTHER_ACTIONS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public GuiFiller getFiller() {
/*  830 */     return this.filler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Map<Integer, GuiItem> getGuiItems() {
/*  840 */     return this.guiItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Inventory getInventory() {
/*  851 */     return this.inventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRows() {
/*  860 */     return this.rows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public GuiType guiType() {
/*  870 */     return this.guiType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryClickEvent> getDefaultClickAction() {
/*  878 */     return this.defaultClickAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryClickEvent> getDefaultTopClickAction() {
/*  886 */     return this.defaultTopClickAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryClickEvent> getPlayerInventoryAction() {
/*  894 */     return this.playerInventoryAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryDragEvent> getDragAction() {
/*  902 */     return this.dragAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryCloseEvent> getCloseGuiAction() {
/*  910 */     return this.closeGuiAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryOpenEvent> getOpenGuiAction() {
/*  918 */     return this.openGuiAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryClickEvent> getOutsideClickAction() {
/*  926 */     return this.outsideClickAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   GuiAction<InventoryClickEvent> getSlotAction(int slot) {
/*  936 */     return this.slotActions.get(Integer.valueOf(slot));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void populateGui() {
/*  943 */     for (Map.Entry<Integer, GuiItem> entry : this.guiItems.entrySet()) {
/*  944 */       this.inventory.setItem(((Integer)entry.getKey()).intValue(), ((GuiItem)entry.getValue()).getItemStack());
/*      */     }
/*      */   }
/*      */   
/*      */   boolean shouldRunCloseAction() {
/*  949 */     return this.runCloseAction;
/*      */   }
/*      */   
/*      */   boolean shouldRunOpenAction() {
/*  953 */     return this.runOpenAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getSlotFromRowCol(int row, int col) {
/*  964 */     return col + (row - 1) * 9 - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInventory(@NotNull Inventory inventory) {
/*  973 */     this.inventory = inventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateSlot(int slot) {
/* 1001 */     int limit = this.guiType.getLimit();
/*      */     
/* 1003 */     if (this.guiType == GuiType.CHEST) {
/* 1004 */       if (slot < 0 || slot >= this.rows * limit) throwInvalidSlot(slot);
/*      */       
/*      */       return;
/*      */     } 
/* 1008 */     if (slot < 0 || slot > limit) throwInvalidSlot(slot);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void throwInvalidSlot(int slot) {
/* 1017 */     if (this.guiType == GuiType.CHEST) {
/* 1018 */       throw new GuiException("Slot " + slot + " is not valid for the gui type - " + this.guiType.name() + " and rows - " + this.rows + "!");
/*      */     }
/*      */     
/* 1021 */     throw new GuiException("Slot " + slot + " is not valid for the gui type - " + this.guiType.name() + "!");
/*      */   }
/*      */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\BaseGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */