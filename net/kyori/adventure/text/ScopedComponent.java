/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.kyori.adventure.text.event.ClickEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.format.TextColor;
/*     */ import net.kyori.adventure.text.format.TextDecoration;
/*     */ import net.kyori.adventure.util.MonkeyBars;
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
/*     */ public interface ScopedComponent<C extends Component>
/*     */   extends Component
/*     */ {
/*     */   @NotNull
/*     */   C children(@NotNull List<? extends ComponentLike> paramList);
/*     */   
/*     */   @NotNull
/*     */   C style(@NotNull Style paramStyle);
/*     */   
/*     */   @NotNull
/*     */   default C style(@NotNull Consumer<Style.Builder> style) {
/*  56 */     return (C)super.style(style);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C style(Style.Builder style) {
/*  62 */     return (C)super.style(style);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C mergeStyle(@NotNull Component that) {
/*  68 */     return (C)super.mergeStyle(that);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   C mergeStyle(@NotNull Component that, Style.Merge... merges) {
/*  74 */     return (C)super.mergeStyle(that, merges);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull Component component) {
/*  80 */     if (component == Component.empty()) return (C)this; 
/*  81 */     List<Component> oldChildren = children();
/*  82 */     return children(MonkeyBars.addOne(oldChildren, Objects.<Component>requireNonNull(component, "component")));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull ComponentLike component) {
/*  88 */     return (C)super.append(component);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull ComponentBuilder<?, ?> builder) {
/*  94 */     return (C)super.append(builder);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/* 100 */     return (C)super.mergeStyle(that, merges);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C color(@Nullable TextColor color) {
/* 106 */     return (C)super.color(color);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C colorIfAbsent(@Nullable TextColor color) {
/* 112 */     return (C)super.colorIfAbsent(color);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Component decorate(@NotNull TextDecoration decoration) {
/* 118 */     return super.decorate(decoration);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 124 */     return (C)super.decoration(decoration, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 130 */     return (C)super.decoration(decoration, state);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C clickEvent(@Nullable ClickEvent event) {
/* 136 */     return (C)super.clickEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C hoverEvent(@Nullable HoverEventSource<?> event) {
/* 142 */     return (C)super.hoverEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C insertion(@Nullable String insertion) {
/* 148 */     return (C)super.insertion(insertion);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\ScopedComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */