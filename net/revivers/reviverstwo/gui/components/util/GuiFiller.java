/*     */ package net.revivers.reviverstwo.gui.components.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.revivers.reviverstwo.gui.components.GuiType;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.guis.BaseGui;
/*     */ import net.revivers.reviverstwo.gui.guis.GuiItem;
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
/*     */ public final class GuiFiller
/*     */ {
/*     */   private final BaseGui gui;
/*     */   
/*     */   public GuiFiller(BaseGui gui) {
/*  45 */     this.gui = gui;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillTop(@NotNull GuiItem guiItem) {
/*  54 */     fillTop(Collections.singletonList(guiItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillTop(@NotNull List<GuiItem> guiItems) {
/*  63 */     List<GuiItem> items = repeatList(guiItems, this.gui.getRows() * 9);
/*  64 */     for (int i = 0; i < 9; i++) {
/*  65 */       if (!this.gui.getGuiItems().containsKey(Integer.valueOf(i))) this.gui.setItem(i, items.get(i));
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillBottom(@NotNull GuiItem guiItem) {
/*  75 */     fillBottom(Collections.singletonList(guiItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillBottom(@NotNull List<GuiItem> guiItems) {
/*  84 */     int rows = this.gui.getRows();
/*  85 */     List<GuiItem> items = repeatList(guiItems, rows * 9);
/*  86 */     for (int i = 9; i > 0; i--) {
/*  87 */       if (this.gui.getGuiItems().get(Integer.valueOf(rows * 9 - i)) == null) {
/*  88 */         this.gui.setItem(rows * 9 - i, items.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillBorder(@NotNull GuiItem guiItem) {
/*  99 */     fillBorder(Collections.singletonList(guiItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillBorder(@NotNull List<GuiItem> guiItems) {
/* 108 */     int rows = this.gui.getRows();
/* 109 */     if (rows <= 2)
/*     */       return; 
/* 111 */     List<GuiItem> items = repeatList(guiItems, rows * 9);
/*     */     
/* 113 */     for (int i = 0; i < rows * 9; i++) {
/* 114 */       if (i <= 8 || i >= rows * 9 - 9 || i == 9 || i == 18 || i == 27 || i == 36 || i == 17 || i == 26 || i == 35 || i == 44)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 119 */         this.gui.setItem(i, items.get(i));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillBetweenPoints(int rowFrom, int colFrom, int rowTo, int colTo, @NotNull GuiItem guiItem) {
/* 135 */     fillBetweenPoints(rowFrom, colFrom, rowTo, colTo, Collections.singletonList(guiItem));
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
/*     */   
/*     */   public void fillBetweenPoints(int rowFrom, int colFrom, int rowTo, int colTo, @NotNull List<GuiItem> guiItems) {
/* 149 */     int minRow = Math.min(rowFrom, rowTo);
/* 150 */     int maxRow = Math.max(rowFrom, rowTo);
/* 151 */     int minCol = Math.min(colFrom, colTo);
/* 152 */     int maxCol = Math.max(colFrom, colTo);
/*     */     
/* 154 */     int rows = this.gui.getRows();
/* 155 */     List<GuiItem> items = repeatList(guiItems, rows * 9);
/*     */     
/* 157 */     for (int row = 1; row <= rows; row++) {
/* 158 */       for (int col = 1; col <= 9; col++) {
/* 159 */         int slot = getSlotFromRowCol(row, col);
/* 160 */         if (row >= minRow && row <= maxRow && col >= minCol && col <= maxCol)
/*     */         {
/*     */           
/* 163 */           this.gui.setItem(slot, items.get(slot));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(@NotNull GuiItem guiItem) {
/* 174 */     fill(Collections.singletonList(guiItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(@NotNull List<GuiItem> guiItems) {
/*     */     int fill;
/* 183 */     if (this.gui instanceof net.revivers.reviverstwo.gui.guis.PaginatedGui) {
/* 184 */       throw new GuiException("Full filling a GUI is not supported in a Paginated GUI!");
/*     */     }
/*     */     
/* 187 */     GuiType type = this.gui.guiType();
/*     */ 
/*     */     
/* 190 */     if (type == GuiType.CHEST) {
/* 191 */       fill = this.gui.getRows() * type.getLimit();
/*     */     } else {
/* 193 */       fill = type.getLimit();
/*     */     } 
/*     */     
/* 196 */     int rows = this.gui.getRows();
/* 197 */     List<GuiItem> items = repeatList(guiItems, rows * 9);
/* 198 */     for (int i = 0; i < fill; i++) {
/* 199 */       if (this.gui.getGuiItems().get(Integer.valueOf(i)) == null) this.gui.setItem(i, items.get(i));
/*     */     
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
/*     */   private List<GuiItem> repeatList(@NotNull List<GuiItem> guiItems, int newLength) {
/* 212 */     List<GuiItem> repeated = new ArrayList<>();
/* 213 */     Objects.requireNonNull(repeated); Collections.<List<GuiItem>>nCopies(this.gui.getRows() * 9, guiItems).forEach(repeated::addAll);
/* 214 */     return repeated;
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
/*     */   private int getSlotFromRowCol(int row, int col) {
/* 226 */     return col + (row - 1) * 9 - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\component\\util\GuiFiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */