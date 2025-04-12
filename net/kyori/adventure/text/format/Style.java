/*     */ package net.kyori.adventure.text.format;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.text.event.ClickEvent;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.adventure.util.MonkeyBars;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ @NonExtendable
/*     */ public interface Style
/*     */   extends Buildable<Style, Style.Builder>, Examinable
/*     */ {
/*  69 */   public static final Key DEFAULT_FONT = Key.key("default");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style empty() {
/*  78 */     return StyleImpl.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder style() {
/*  88 */     return new StyleImpl.BuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull Consumer<Builder> consumer) {
/*  99 */     return (Style)Buildable.configureAndBuild(style(), consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@Nullable TextColor color) {
/* 110 */     if (color == null) return empty(); 
/* 111 */     return new StyleImpl(null, color, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull TextDecoration decoration) {
/* 122 */     return style().decoration(decoration, true).build();
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
/*     */   static Style style(@Nullable TextColor color, TextDecoration... decorations) {
/* 134 */     Builder builder = style();
/* 135 */     builder.color(color);
/* 136 */     StyleImpl.decorate(builder, decorations);
/* 137 */     return builder.build();
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
/*     */   static Style style(@Nullable TextColor color, Set<TextDecoration> decorations) {
/* 149 */     Builder builder = style();
/* 150 */     builder.color(color);
/* 151 */     if (!decorations.isEmpty()) {
/* 152 */       for (TextDecoration decoration : decorations) {
/* 153 */         builder.decoration(decoration, true);
/*     */       }
/*     */     }
/* 156 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(StyleBuilderApplicable... applicables) {
/* 167 */     if (applicables.length == 0) return empty(); 
/* 168 */     Builder builder = style();
/* 169 */     for (int i = 0, length = applicables.length; i < length; i++) {
/* 170 */       applicables[i].styleApply(builder);
/*     */     }
/* 172 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull Iterable<? extends StyleBuilderApplicable> applicables) {
/* 183 */     Builder builder = style();
/* 184 */     for (StyleBuilderApplicable applicable : applicables) {
/* 185 */       applicable.styleApply(builder);
/*     */     }
/* 187 */     return builder.build();
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
/*     */   @NotNull
/*     */   default Style edit(@NotNull Consumer<Builder> consumer) {
/* 200 */     return edit(consumer, Merge.Strategy.ALWAYS);
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
/*     */   default Style edit(@NotNull Consumer<Builder> consumer, Merge.Strategy strategy) {
/* 212 */     return style(style -> {
/*     */           if (strategy == Merge.Strategy.ALWAYS) {
/*     */             style.merge(this, strategy);
/*     */           }
/*     */           consumer.accept(style);
/*     */           if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
/*     */             style.merge(this, strategy);
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasDecoration(@NotNull TextDecoration decoration) {
/* 278 */     return (decoration(decoration) == TextDecoration.State.TRUE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Style decorate(@NotNull TextDecoration decoration) {
/* 300 */     return decoration(decoration, TextDecoration.State.TRUE);
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
/*     */   @NotNull
/*     */   default Style decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 313 */     return decoration(decoration, TextDecoration.State.byBoolean(flag));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Style merge(@NotNull Style that) {
/* 407 */     return merge(that, Merge.all());
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
/*     */   default Style merge(@NotNull Style that, Merge.Strategy strategy) {
/* 419 */     return merge(that, strategy, Merge.all());
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
/*     */   default Style merge(@NotNull Style that, @NotNull Merge merge) {
/* 431 */     return merge(that, Collections.singleton(merge));
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
/*     */   @NotNull
/*     */   default Style merge(@NotNull Style that, Merge.Strategy strategy, @NotNull Merge merge) {
/* 444 */     return merge(that, strategy, Collections.singleton(merge));
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
/*     */   Style merge(@NotNull Style that, @NotNull Merge... merges) {
/* 456 */     return merge(that, Merge.of(merges));
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
/*     */   @NotNull
/*     */   Style merge(@NotNull Style that, Merge.Strategy strategy, @NotNull Merge... merges) {
/* 469 */     return merge(that, strategy, Merge.of(merges));
/*     */   } @Nullable
/*     */   Key font(); @NotNull
/*     */   Style font(@Nullable Key paramKey); @Nullable
/*     */   TextColor color(); @NotNull
/*     */   Style color(@Nullable TextColor paramTextColor); @NotNull
/*     */   Style colorIfAbsent(@Nullable TextColor paramTextColor); TextDecoration.State decoration(@NotNull TextDecoration paramTextDecoration); @NotNull
/*     */   Style decoration(@NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState); @NotNull
/*     */   Map<TextDecoration, TextDecoration.State> decorations(); @NotNull
/*     */   Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> paramMap);
/*     */   @NotNull
/*     */   default Style merge(@NotNull Style that, @NotNull Set<Merge> merges) {
/* 481 */     return merge(that, Merge.Strategy.ALWAYS, merges);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   ClickEvent clickEvent();
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style clickEvent(@Nullable ClickEvent paramClickEvent);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   HoverEvent<?> hoverEvent();
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style hoverEvent(@Nullable HoverEventSource<?> paramHoverEventSource);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   String insertion();
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style insertion(@Nullable String paramString);
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style merge(@NotNull Style paramStyle, Merge.Strategy paramStrategy, @NotNull Set<Merge> paramSet);
/*     */ 
/*     */   
/*     */   boolean isEmpty();
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Builder toBuilder();
/*     */ 
/*     */   
/*     */   public enum Merge
/*     */   {
/* 523 */     COLOR,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 529 */     DECORATIONS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     EVENTS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     INSERTION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 547 */     FONT;
/*     */     
/* 549 */     static final Set<Merge> ALL = of(values());
/* 550 */     static final Set<Merge> COLOR_AND_DECORATIONS = of(new Merge[] { COLOR, DECORATIONS });
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> all() {
/* 559 */       return ALL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> colorAndDecorations() {
/* 569 */       return COLOR_AND_DECORATIONS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> of(Merge... merges) {
/* 580 */       return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
/*     */     }
/*     */     
/*     */     static boolean hasAll(@NotNull Set<Merge> merges) {
/* 584 */       return (merges.size() == ALL.size());
/*     */     }
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
/*     */     public enum Strategy
/*     */     {
/* 598 */       ALWAYS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 604 */       NEVER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 610 */       IF_ABSENT_ON_TARGET; } } public enum Strategy { ALWAYS, NEVER, IF_ABSENT_ON_TARGET; }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder
/*     */     extends Buildable.Builder<Style>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder decorate(@NotNull TextDecoration decoration) {
/* 660 */       return decoration(decoration, TextDecoration.State.TRUE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder decorate(@NotNull TextDecoration... decorations) {
/* 672 */       for (int i = 0, length = decorations.length; i < length; i++) {
/* 673 */         decorate(decorations[i]);
/*     */       }
/* 675 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 689 */       return decoration(decoration, TextDecoration.State.byBoolean(flag));
/*     */     }
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
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that) {
/* 745 */       return merge(that, Style.Merge.all());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that, Style.Merge.Strategy strategy) {
/* 758 */       return merge(that, strategy, Style.Merge.all());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style that, @NotNull Style.Merge... merges) {
/* 771 */       if (merges.length == 0) return this; 
/* 772 */       return merge(that, Style.Merge.of(merges));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Style.Merge... merges) {
/* 786 */       if (merges.length == 0) return this; 
/* 787 */       return merge(that, strategy, Style.Merge.of(merges));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that, @NotNull Set<Style.Merge> merges) {
/* 800 */       return merge(that, Style.Merge.Strategy.ALWAYS, merges);
/*     */     }
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
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder apply(@NotNull StyleBuilderApplicable applicable) {
/* 824 */       applicable.styleApply(this);
/* 825 */       return this;
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder font(@Nullable Key param1Key);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder color(@Nullable TextColor param1TextColor);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder colorIfAbsent(@Nullable TextColor param1TextColor);
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     Builder decoration(@NotNull TextDecoration param1TextDecoration, TextDecoration.State param1State);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder clickEvent(@Nullable ClickEvent param1ClickEvent);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder hoverEvent(@Nullable HoverEventSource<?> param1HoverEventSource);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder insertion(@Nullable String param1String);
/*     */     
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style param1Style, Style.Merge.Strategy param1Strategy, @NotNull Set<Style.Merge> param1Set);
/*     */     
/*     */     @NotNull
/*     */     Style build();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\Style.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */