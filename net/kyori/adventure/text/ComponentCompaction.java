/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.format.TextDecoration;
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
/*     */ final class ComponentCompaction
/*     */ {
/*  36 */   private static final TextDecoration[] DECORATIONS = TextDecoration.values();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Component compact(@NotNull Component self, @Nullable Style parentStyle) {
/*  42 */     List<Component> children = self.children();
/*  43 */     Component optimized = self.children(Collections.emptyList());
/*  44 */     if (parentStyle != null) {
/*  45 */       optimized = optimized.style(simplifyStyle(self.style(), parentStyle));
/*     */     }
/*     */     
/*  48 */     int childrenSize = children.size();
/*     */     
/*  50 */     if (childrenSize == 0)
/*     */     {
/*  52 */       return optimized;
/*     */     }
/*     */ 
/*     */     
/*  56 */     if (childrenSize == 1 && self instanceof TextComponent) {
/*  57 */       TextComponent textComponent = (TextComponent)self;
/*     */       
/*  59 */       if (textComponent.content().isEmpty()) {
/*  60 */         Component child = children.get(0);
/*     */ 
/*     */         
/*  63 */         return child.style(child.style().merge(optimized.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).compact();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     Style childParentStyle = optimized.style();
/*  70 */     if (parentStyle != null) {
/*  71 */       childParentStyle = childParentStyle.merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */     }
/*     */ 
/*     */     
/*  75 */     List<Component> childrenToAppend = new ArrayList<>(children.size()); int i;
/*  76 */     for (i = 0; i < children.size(); i++) {
/*  77 */       childrenToAppend.add(compact(children.get(i), childParentStyle));
/*     */     }
/*     */ 
/*     */     
/*  81 */     while (!childrenToAppend.isEmpty()) {
/*  82 */       Component child = childrenToAppend.get(0);
/*  83 */       Style childStyle = child.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */       
/*  85 */       if (optimized instanceof TextComponent && child instanceof TextComponent && Objects.equals(childStyle, childParentStyle)) {
/*     */ 
/*     */         
/*  88 */         optimized = joinText((TextComponent)optimized, (TextComponent)child);
/*  89 */         childrenToAppend.remove(0);
/*     */ 
/*     */         
/*  92 */         childrenToAppend.addAll(0, child.children());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     for (i = 0; i + 1 < childrenToAppend.size(); ) {
/* 102 */       Component child = childrenToAppend.get(i);
/* 103 */       Component neighbor = childrenToAppend.get(i + 1);
/*     */ 
/*     */       
/* 106 */       Style childStyle = child.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/* 107 */       Style neighborStyle = neighbor.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */       
/* 109 */       if (child instanceof TextComponent && neighbor instanceof TextComponent && childStyle.equals(neighborStyle)) {
/* 110 */         Component combined = joinText((TextComponent)child, (TextComponent)neighbor);
/*     */ 
/*     */         
/* 113 */         childrenToAppend.set(i, combined);
/* 114 */         childrenToAppend.remove(i + 1);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 119 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return optimized.children((List)childrenToAppend);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static Style simplifyStyle(@NotNull Style style, @NotNull Style parentStyle) {
/* 134 */     if (style.isEmpty())
/*     */     {
/* 136 */       return style;
/*     */     }
/*     */     
/* 139 */     Style.Builder builder = style.toBuilder();
/* 140 */     if (Objects.equals(style.font(), parentStyle.font())) {
/* 141 */       builder.font(null);
/*     */     }
/*     */     
/* 144 */     if (Objects.equals(style.color(), parentStyle.color())) {
/* 145 */       builder.color(null);
/*     */     }
/*     */     
/* 148 */     for (TextDecoration decoration : DECORATIONS) {
/* 149 */       if (style.decoration(decoration) == parentStyle.decoration(decoration)) {
/* 150 */         builder.decoration(decoration, TextDecoration.State.NOT_SET);
/*     */       }
/*     */     } 
/*     */     
/* 154 */     if (Objects.equals(style.clickEvent(), parentStyle.clickEvent())) {
/* 155 */       builder.clickEvent(null);
/*     */     }
/*     */     
/* 158 */     if (Objects.equals(style.hoverEvent(), parentStyle.hoverEvent())) {
/* 159 */       builder.hoverEvent(null);
/*     */     }
/*     */     
/* 162 */     if (Objects.equals(style.insertion(), parentStyle.insertion())) {
/* 163 */       builder.insertion(null);
/*     */     }
/*     */     
/* 166 */     return builder.build();
/*     */   }
/*     */   
/*     */   private static TextComponent joinText(TextComponent one, TextComponent two) {
/* 170 */     return Component.text(one.content() + two.content(), one.style());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\ComponentCompaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */