/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.PaginatedBuilder;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.ScrollingBuilder;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.SimpleBuilder;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.StorageBuilder;
/*     */ import net.revivers.reviverstwo.gui.components.GuiType;
/*     */ import net.revivers.reviverstwo.gui.components.InteractionModifier;
/*     */ import net.revivers.reviverstwo.gui.components.ScrollType;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Gui
/*     */   extends BaseGui
/*     */ {
/*     */   public Gui(int rows, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  53 */     super(rows, title, interactionModifiers);
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
/*     */   public Gui(@NotNull GuiType guiType, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers) {
/*  66 */     super(guiType, title, interactionModifiers);
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
/*     */   public Gui(int rows, @NotNull String title) {
/*  78 */     super(rows, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Gui(@NotNull String title) {
/*  89 */     super(1, title);
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
/*     */   public Gui(@NotNull GuiType guiType, @NotNull String title) {
/* 101 */     super(guiType, title);
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
/*     */   @Contract("_ -> new")
/*     */   public static SimpleBuilder gui(@NotNull GuiType type) {
/* 114 */     return new SimpleBuilder(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static SimpleBuilder gui() {
/* 126 */     return gui(GuiType.CHEST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static StorageBuilder storage() {
/* 138 */     return new StorageBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static PaginatedBuilder paginated() {
/* 150 */     return new PaginatedBuilder();
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
/*     */   @Contract("_ -> new")
/*     */   public static ScrollingBuilder scrolling(@NotNull ScrollType scrollType) {
/* 163 */     return new ScrollingBuilder(scrollType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static ScrollingBuilder scrolling() {
/* 175 */     return scrolling(ScrollType.VERTICAL);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */