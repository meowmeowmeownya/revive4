/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.revivers.reviverstwo.gui.components.InteractionModifier;
/*     */ import net.revivers.reviverstwo.gui.components.ScrollType;
/*     */ import org.bukkit.entity.HumanEntity;
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
/*     */ public class ScrollingGui
/*     */   extends PaginatedGui
/*     */ {
/*     */   private final ScrollType scrollType;
/*  42 */   private int scrollSize = 0;
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
/*     */   public ScrollingGui(int rows, int pageSize, @NotNull String title, @NotNull ScrollType scrollType, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  56 */     super(rows, pageSize, title, interactionModifiers);
/*     */     
/*  58 */     this.scrollType = scrollType;
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
/*     */   @Deprecated
/*     */   public ScrollingGui(int rows, int pageSize, @NotNull String title, @NotNull ScrollType scrollType) {
/*  72 */     super(rows, pageSize, title);
/*  73 */     this.scrollType = scrollType;
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
/*     */   public ScrollingGui(int rows, int pageSize, @NotNull String title) {
/*  86 */     this(rows, pageSize, title, ScrollType.VERTICAL);
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
/*     */   public ScrollingGui(int rows, @NotNull String title) {
/*  98 */     this(rows, 0, title, ScrollType.VERTICAL);
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
/*     */   public ScrollingGui(int rows, @NotNull String title, @NotNull ScrollType scrollType) {
/* 111 */     this(rows, 0, title, scrollType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ScrollingGui(@NotNull String title) {
/* 122 */     this(2, title);
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
/*     */   public ScrollingGui(@NotNull String title, @NotNull ScrollType scrollType) {
/* 134 */     this(2, title, scrollType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 142 */     if (getPageNum() * this.scrollSize + getPageSize() > getPageItems().size() + this.scrollSize) return false;
/*     */     
/* 144 */     setPageNum(getPageNum() + 1);
/* 145 */     updatePage();
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean previous() {
/* 154 */     if (getPageNum() - 1 == 0) return false;
/*     */     
/* 156 */     setPageNum(getPageNum() - 1);
/* 157 */     updatePage();
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(@NotNull HumanEntity player) {
/* 168 */     open(player, 1);
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
/* 179 */     if (player.isSleeping())
/* 180 */       return;  getInventory().clear();
/* 181 */     getMutableCurrentPageItems().clear();
/*     */     
/* 183 */     populateGui();
/*     */     
/* 185 */     if (getPageSize() == 0) setPageSize(calculatePageSize()); 
/* 186 */     if (this.scrollSize == 0) this.scrollSize = calculateScrollSize(); 
/* 187 */     if (openPage > 0 && openPage * this.scrollSize + getPageSize() <= getPageItems().size() + this.scrollSize) {
/* 188 */       setPageNum(openPage);
/*     */     }
/*     */     
/* 191 */     populatePage();
/*     */     
/* 193 */     player.openInventory(getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updatePage() {
/* 201 */     clearPage();
/* 202 */     populatePage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populatePage() {
/* 210 */     for (GuiItem guiItem : getPage(getPageNum())) {
/* 211 */       if (this.scrollType == ScrollType.HORIZONTAL) {
/* 212 */         putItemHorizontally(guiItem);
/*     */         
/*     */         continue;
/*     */       } 
/* 216 */       putItemVertically(guiItem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateScrollSize() {
/* 226 */     int counter = 0;
/*     */     
/* 228 */     if (this.scrollType == ScrollType.VERTICAL) {
/* 229 */       boolean foundCol = false;
/*     */       
/* 231 */       for (int row = 1; row <= getRows(); row++) {
/* 232 */         for (int i = 1; i <= 9; i++) {
/* 233 */           int slot = getSlotFromRowCol(row, i);
/* 234 */           if (getInventory().getItem(slot) == null) {
/* 235 */             if (!foundCol) foundCol = true; 
/* 236 */             counter++;
/*     */           } 
/*     */         } 
/*     */         
/* 240 */         if (foundCol) return counter;
/*     */       
/*     */       } 
/* 243 */       return counter;
/*     */     } 
/*     */     
/* 246 */     boolean foundRow = false;
/*     */     
/* 248 */     for (int col = 1; col <= 9; col++) {
/* 249 */       for (int row = 1; row <= getRows(); row++) {
/* 250 */         int slot = getSlotFromRowCol(row, col);
/* 251 */         if (getInventory().getItem(slot) == null) {
/* 252 */           if (!foundRow) foundRow = true; 
/* 253 */           counter++;
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       if (foundRow) return counter;
/*     */     
/*     */     } 
/* 260 */     return counter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void putItemVertically(GuiItem guiItem) {
/* 269 */     for (int slot = 0; slot < getRows() * 9; ) {
/* 270 */       if (getInventory().getItem(slot) != null) { slot++; continue; }
/* 271 */        getMutableCurrentPageItems().put(Integer.valueOf(slot), guiItem);
/* 272 */       getInventory().setItem(slot, guiItem.getItemStack());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void putItemHorizontally(GuiItem guiItem) {
/* 283 */     for (int col = 1; col < 10; col++) {
/* 284 */       for (int row = 1; row <= getRows(); ) {
/* 285 */         int slot = getSlotFromRowCol(row, col);
/* 286 */         if (getInventory().getItem(slot) != null) {
/*     */           row++; continue;
/* 288 */         }  getMutableCurrentPageItems().put(Integer.valueOf(slot), guiItem);
/* 289 */         getInventory().setItem(slot, guiItem.getItemStack());
/*     */         return;
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
/*     */   private List<GuiItem> getPage(int givenPage) {
/* 302 */     int page = givenPage - 1;
/* 303 */     int pageItemsSize = getPageItems().size();
/*     */     
/* 305 */     List<GuiItem> guiPage = new ArrayList<>();
/*     */     
/* 307 */     int max = page * this.scrollSize + getPageSize();
/* 308 */     if (max > pageItemsSize) max = pageItemsSize;
/*     */     
/* 310 */     for (int i = page * this.scrollSize; i < max; i++) {
/* 311 */       guiPage.add(getPageItems().get(i));
/*     */     }
/*     */     
/* 314 */     return guiPage;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\ScrollingGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */