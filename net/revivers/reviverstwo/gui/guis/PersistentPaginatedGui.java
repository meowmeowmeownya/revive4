/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.revivers.reviverstwo.gui.components.Serializable;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
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
/*     */ class PersistentPaginatedGui
/*     */   extends PaginatedGui
/*     */   implements Serializable
/*     */ {
/*  46 */   private final List<Page> pages = new ArrayList<>();
/*     */ 
/*     */   
/*  49 */   private final YamlConfiguration yamlConfiguration = new YamlConfiguration();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentPaginatedGui(int rows, int pageSize, @NotNull String title, int pages) {
/*  60 */     super(rows, pageSize, title);
/*     */     
/*  62 */     if (pages <= 0) {
/*  63 */       this.pages.add(new Page());
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     for (int i = 0; i < pages; i++) {
/*  68 */       this.pages.add(new Page());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentPaginatedGui(@NotNull String title) {
/*  79 */     this(1, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentPaginatedGui(int rows, @NotNull String title) {
/*  89 */     this(rows, 0, title, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentPaginatedGui(@NotNull String title, int pages) {
/*  99 */     this(1, 0, title, pages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentPaginatedGui(int rows, @NotNull String title, int pages) {
/* 110 */     this(rows, 0, title, pages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<Integer, ItemStack> addItem(@NotNull ItemStack... items) {
/* 121 */     return addItem(1, items);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<Integer, ItemStack> addItem(int page, @NotNull ItemStack... items) {
/* 133 */     int finalPage = page;
/* 134 */     if (page <= 0 || page > this.pages.size()) finalPage = 1;
/*     */     
/* 136 */     return Collections.unmodifiableMap(getInventory().addItem(items));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(@NotNull HumanEntity player) {
/* 146 */     open(player, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(@NotNull HumanEntity player, int openPage) {
/* 156 */     if (player.isSleeping())
/*     */       return; 
/* 158 */     if (openPage < this.pages.size() || openPage > 0) setPageNum(openPage - 1); 
/* 159 */     getInventory().clear();
/*     */     
/* 161 */     populateGui();
/*     */     
/* 163 */     if (getPageSize() == 0) setPageSize(calculatePageSize());
/*     */     
/* 165 */     ((Page)this.pages.get(getPageNum())).populatePage(getInventory());
/*     */     
/* 167 */     player.openInventory(getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 174 */     if (getPageNum() + 1 >= this.pages.size()) return false;
/*     */     
/* 176 */     savePage();
/*     */     
/* 178 */     setPageNum(getPageNum() + 1);
/* 179 */     updatePage();
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean previous() {
/* 187 */     if (getPageNum() - 1 < 0) return false;
/*     */     
/* 189 */     savePage();
/*     */     
/* 191 */     setPageNum(getPageNum() - 1);
/* 192 */     updatePage();
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentPageNum() {
/* 203 */     return getPageNum() + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updatePage() {
/* 211 */     clearPage();
/* 212 */     populatePage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearPage() {
/* 220 */     for (int i = 0; i < getInventory().getSize(); i++) {
/* 221 */       ItemStack itemStack = getInventory().getItem(i);
/* 222 */       if (itemStack != null && 
/* 223 */         getGuiItems().get(Integer.valueOf(i)) == null)
/*     */       {
/* 225 */         getInventory().setItem(i, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void savePage() {
/* 233 */     ((Page)this.pages.get(getPageNum())).savePage(getInventory(), getGuiItems());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populatePage() {
/* 241 */     ((Page)this.pages.get(getPageNum())).populatePage(getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> encodeGui() {
/* 252 */     int inventorySize = getInventory().getSize();
/* 253 */     List<String> pageItems = new ArrayList<>();
/*     */     
/* 255 */     for (Page page : this.pages) {
/* 256 */       this.yamlConfiguration.set("inventory", page.getContent(inventorySize));
/*     */     }
/*     */ 
/*     */     
/* 260 */     return pageItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decodeGui(@NotNull List<String> encodedItem) {
/* 270 */     for (int i = 0; i < this.pages.size(); i++) {
/* 271 */       Page page = this.pages.get(i);
/*     */ 
/*     */       
/* 274 */       List<ItemStack> content = (List<ItemStack>)this.yamlConfiguration.get("inventory");
/* 275 */       if (content != null) {
/* 276 */         page.loadPageContent(content, getInventory().getSize());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Page
/*     */   {
/* 286 */     private final Map<Integer, ItemStack> pageItems = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void populatePage(@NotNull Inventory inventory) {
/* 295 */       for (Map.Entry<Integer, ItemStack> entry : this.pageItems.entrySet()) {
/* 296 */         inventory.setItem(((Integer)entry.getKey()).intValue(), entry.getValue());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void savePage(@NotNull Inventory inventory, @NotNull Map<Integer, GuiItem> guiItems) {
/* 307 */       for (int i = 0; i < inventory.getSize(); i++) {
/* 308 */         ItemStack itemStack = inventory.getItem(i);
/*     */ 
/*     */         
/* 311 */         if (itemStack == null) {
/* 312 */           this.pageItems.remove(Integer.valueOf(i));
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 317 */         else if (guiItems.get(Integer.valueOf(i)) == null) {
/*     */           
/* 319 */           this.pageItems.put(Integer.valueOf(i), itemStack);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     private ItemStack[] getContent(int inventorySize) {
/* 331 */       ItemStack[] content = new ItemStack[inventorySize];
/* 332 */       for (int i = 0; i < inventorySize; i++) {
/* 333 */         content[i] = this.pageItems.get(Integer.valueOf(i));
/*     */       }
/* 335 */       return content;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void loadPageContent(@NotNull List<ItemStack> items, int inventorySize) {
/* 346 */       this.pageItems.clear();
/* 347 */       for (int i = 0; i < inventorySize; i++) {
/* 348 */         ItemStack item = items.get(i);
/* 349 */         if (item != null)
/*     */         {
/* 351 */           this.pageItems.put(Integer.valueOf(i), item);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private Page() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\PersistentPaginatedGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */