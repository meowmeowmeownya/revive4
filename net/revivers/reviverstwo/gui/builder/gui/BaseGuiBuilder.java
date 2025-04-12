/*     */ package net.revivers.reviverstwo.gui.builder.gui;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.revivers.reviverstwo.gui.components.InteractionModifier;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.guis.BaseGui;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseGuiBuilder<G extends BaseGui, B extends BaseGuiBuilder<G, B>>
/*     */ {
/*  47 */   private Component title = null;
/*  48 */   private int rows = 1;
/*  49 */   private final EnumSet<InteractionModifier> interactionModifiers = EnumSet.noneOf(InteractionModifier.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Consumer<G> consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public B rows(int rows) {
/*  63 */     this.rows = rows;
/*  64 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B title(@NotNull Component title) {
/*  77 */     this.title = title;
/*  78 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B disableItemPlace() {
/*  91 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_PLACE);
/*  92 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B disableItemTake() {
/* 105 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_TAKE);
/* 106 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B disableItemSwap() {
/* 119 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_SWAP);
/* 120 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> this")
/*     */   public B disableItemDrop() {
/* 132 */     this.interactionModifiers.add(InteractionModifier.PREVENT_ITEM_DROP);
/* 133 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B disableOtherActions() {
/* 146 */     this.interactionModifiers.add(InteractionModifier.PREVENT_OTHER_ACTIONS);
/* 147 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B disableAllInteractions() {
/* 160 */     this.interactionModifiers.addAll(InteractionModifier.VALUES);
/* 161 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B enableItemPlace() {
/* 174 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_PLACE);
/* 175 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B enableItemTake() {
/* 188 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_TAKE);
/* 189 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B enableItemSwap() {
/* 202 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_SWAP);
/* 203 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> this")
/*     */   public B enableItemDrop() {
/* 215 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_DROP);
/* 216 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B enableOtherActions() {
/* 229 */     this.interactionModifiers.remove(InteractionModifier.PREVENT_OTHER_ACTIONS);
/* 230 */     return (B)this;
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
/*     */   @Contract(" -> this")
/*     */   public B enableAllInteractions() {
/* 243 */     this.interactionModifiers.clear();
/* 244 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B apply(@NotNull Consumer<G> consumer) {
/* 257 */     this.consumer = consumer;
/* 258 */     return (B)this;
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
/*     */   public abstract G create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component getTitle() {
/* 278 */     if (this.title == null) {
/* 279 */       throw new GuiException("GUI title is missing!");
/*     */     }
/*     */     
/* 282 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRows() {
/* 291 */     return this.rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Consumer<G> getConsumer() {
/* 301 */     return this.consumer;
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
/*     */   protected Set<InteractionModifier> getModifiers() {
/* 313 */     return this.interactionModifiers;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\gui\BaseGuiBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */