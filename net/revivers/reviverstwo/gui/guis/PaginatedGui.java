/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.revivers.reviverstwo.gui.components.InteractionModifier;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public class PaginatedGui
/*     */   extends BaseGui
/*     */ {
/*  49 */   private final List<GuiItem> pageItems = new ArrayList<>();
/*     */   
/*     */   private final Map<Integer, GuiItem> currentPage;
/*     */   
/*     */   private int pageSize;
/*  54 */   private int pageNum = 1;
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
/*     */   public PaginatedGui(int rows, int pageSize, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  67 */     super(rows, title, interactionModifiers);
/*  68 */     this.pageSize = pageSize;
/*  69 */     int inventorySize = rows * 9;
/*  70 */     this.currentPage = new LinkedHashMap<>(inventorySize);
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
/*     */   @Deprecated
/*     */   public PaginatedGui(int rows, int pageSize, @NotNull String title) {
/*  83 */     super(rows, title);
/*  84 */     this.pageSize = pageSize;
/*  85 */     int inventorySize = rows * 9;
/*  86 */     this.currentPage = new LinkedHashMap<>(inventorySize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PaginatedGui(int rows, @NotNull String title) {
/*  98 */     this(rows, 0, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PaginatedGui(@NotNull String title) {
/* 109 */     this(2, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseGui setPageSize(int pageSize) {
/* 119 */     this.pageSize = pageSize;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(@NotNull GuiItem item) {
/* 129 */     this.pageItems.add(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(@NotNull GuiItem... items) {
/* 139 */     this.pageItems.addAll(Arrays.asList(items));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 147 */     getInventory().clear();
/* 148 */     populateGui();
/*     */     
/* 150 */     updatePage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePageItem(int slot, @NotNull ItemStack itemStack) {
/* 161 */     if (!this.currentPage.containsKey(Integer.valueOf(slot)))
/* 162 */       return;  GuiItem guiItem = this.currentPage.get(Integer.valueOf(slot));
/* 163 */     guiItem.setItemStack(itemStack);
/* 164 */     getInventory().setItem(slot, guiItem.getItemStack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePageItem(int row, int col, @NotNull ItemStack itemStack) {
/* 175 */     updateItem(getSlotFromRowCol(row, col), itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePageItem(int slot, @NotNull GuiItem item) {
/* 185 */     if (!this.currentPage.containsKey(Integer.valueOf(slot)))
/*     */       return; 
/* 187 */     GuiItem oldItem = this.currentPage.get(Integer.valueOf(slot));
/* 188 */     int index = this.pageItems.indexOf(this.currentPage.get(Integer.valueOf(slot)));
/*     */ 
/*     */     
/* 191 */     this.currentPage.put(Integer.valueOf(slot), item);
/* 192 */     this.pageItems.set(index, item);
/* 193 */     getInventory().setItem(slot, item.getItemStack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePageItem(int row, int col, @NotNull GuiItem item) {
/* 204 */     updateItem(getSlotFromRowCol(row, col), item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePageItem(@NotNull GuiItem item) {
/* 213 */     this.pageItems.remove(item);
/* 214 */     updatePage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePageItem(@NotNull ItemStack item) {
/* 223 */     Optional<GuiItem> guiItem = this.pageItems.stream().filter(it -> it.getItemStack().equals(item)).findFirst();
/* 224 */     guiItem.ifPresent(this::removePageItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(@NotNull HumanEntity player) {
/* 234 */     open(player, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(@NotNull HumanEntity player, int openPage) {
/* 245 */     if (player.isSleeping())
/* 246 */       return;  if (openPage <= getPagesNum() || openPage > 0) this.pageNum = openPage;
/*     */     
/* 248 */     getInventory().clear();
/* 249 */     this.currentPage.clear();
/*     */     
/* 251 */     populateGui();
/*     */     
/* 253 */     if (this.pageSize == 0) this.pageSize = calculatePageSize();
/*     */     
/* 255 */     populatePage();
/*     */     
/* 257 */     player.openInventory(getInventory());
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
/*     */   
/*     */   @NotNull
/*     */   public BaseGui updateTitle(@NotNull String title) {
/* 271 */     setUpdating(true);
/*     */     
/* 273 */     List<HumanEntity> viewers = new ArrayList<>(getInventory().getViewers());
/*     */     
/* 275 */     setInventory(Bukkit.createInventory(this, getInventory().getSize(), title));
/*     */     
/* 277 */     for (HumanEntity player : viewers) {
/* 278 */       open(player, getPageNum());
/*     */     }
/*     */     
/* 281 */     setUpdating(false);
/*     */     
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<Integer, GuiItem> getCurrentPageItems() {
/* 293 */     return Collections.unmodifiableMap(this.currentPage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<GuiItem> getPageItems() {
/* 303 */     return Collections.unmodifiableList(this.pageItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentPageNum() {
/* 313 */     return this.pageNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextPageNum() {
/* 322 */     if (this.pageNum + 1 > getPagesNum()) return this.pageNum; 
/* 323 */     return this.pageNum + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrevPageNum() {
/* 332 */     if (this.pageNum - 1 == 0) return this.pageNum; 
/* 333 */     return this.pageNum - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 342 */     if (this.pageNum + 1 > getPagesNum()) return false;
/*     */     
/* 344 */     this.pageNum++;
/* 345 */     updatePage();
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean previous() {
/* 355 */     if (this.pageNum - 1 == 0) return false;
/*     */     
/* 357 */     this.pageNum--;
/* 358 */     updatePage();
/* 359 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GuiItem getPageItem(int slot) {
/* 369 */     return this.currentPage.get(Integer.valueOf(slot));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<GuiItem> getPageNum(int givenPage) {
/* 379 */     int page = givenPage - 1;
/*     */     
/* 381 */     List<GuiItem> guiPage = new ArrayList<>();
/*     */     
/* 383 */     int max = page * this.pageSize + this.pageSize;
/* 384 */     if (max > this.pageItems.size()) max = this.pageItems.size();
/*     */     
/* 386 */     for (int i = page * this.pageSize; i < max; i++) {
/* 387 */       guiPage.add(this.pageItems.get(i));
/*     */     }
/*     */     
/* 390 */     return guiPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPagesNum() {
/* 399 */     return (int)Math.ceil(this.pageItems.size() / this.pageSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populatePage() {
/* 407 */     for (GuiItem guiItem : getPageNum(this.pageNum)) {
/* 408 */       for (int slot = 0; slot < getRows() * 9; ) {
/* 409 */         if (getGuiItem(slot) != null || getInventory().getItem(slot) != null) { slot++; continue; }
/* 410 */          this.currentPage.put(Integer.valueOf(slot), guiItem);
/* 411 */         getInventory().setItem(slot, guiItem.getItemStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Map<Integer, GuiItem> getMutableCurrentPageItems() {
/* 423 */     return this.currentPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearPage() {
/* 430 */     for (Map.Entry<Integer, GuiItem> entry : this.currentPage.entrySet()) {
/* 431 */       getInventory().setItem(((Integer)entry.getKey()).intValue(), null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPageItems(boolean update) {
/* 439 */     this.pageItems.clear();
/* 440 */     if (update) update(); 
/*     */   }
/*     */   
/*     */   public void clearPageItems() {
/* 444 */     clearPageItems(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getPageSize() {
/* 454 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getPageNum() {
/* 463 */     return this.pageNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPageNum(int pageNum) {
/* 472 */     this.pageNum = pageNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updatePage() {
/* 479 */     clearPage();
/* 480 */     populatePage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int calculatePageSize() {
/* 489 */     int counter = 0;
/*     */     
/* 491 */     for (int slot = 0; slot < getRows() * 9; slot++) {
/* 492 */       if (getInventory().getItem(slot) == null) counter++;
/*     */     
/*     */     } 
/* 495 */     return counter;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\PaginatedGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */