/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.renderer.ComponentRenderer;
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
/*     */ final class TextReplacementRenderer
/*     */   implements ComponentRenderer<TextReplacementRenderer.State>
/*     */ {
/*  42 */   static final TextReplacementRenderer INSTANCE = new TextReplacementRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Component render(@NotNull Component component, @NotNull State state) {
/*  49 */     if (!state.running) return component; 
/*  50 */     boolean prevFirstMatch = state.firstMatch;
/*  51 */     state.firstMatch = true;
/*     */     
/*  53 */     List<Component> oldChildren = component.children();
/*  54 */     int oldChildrenSize = oldChildren.size();
/*  55 */     List<Component> children = null;
/*  56 */     Component modified = component;
/*     */     
/*  58 */     if (component instanceof TextComponent) {
/*  59 */       String content = ((TextComponent)component).content();
/*  60 */       Matcher matcher = state.pattern.matcher(content);
/*  61 */       int replacedUntil = 0;
/*  62 */       while (matcher.find()) {
/*  63 */         PatternReplacementResult result = state.continuer.shouldReplace(matcher, ++state.matchCount, state.replaceCount);
/*  64 */         if (result == PatternReplacementResult.CONTINUE) {
/*     */           continue;
/*     */         }
/*  67 */         if (result == PatternReplacementResult.STOP) {
/*     */           
/*  69 */           state.running = false;
/*     */           
/*     */           break;
/*     */         } 
/*  73 */         if (matcher.start() == 0) {
/*     */           
/*  75 */           if (matcher.end() == content.length()) {
/*  76 */             ComponentLike replacement = state.replacement.apply(matcher, Component.text().content(matcher.group())
/*  77 */                 .style(component.style()));
/*     */             
/*  79 */             modified = (replacement == null) ? Component.empty() : replacement.asComponent();
/*     */ 
/*     */             
/*  82 */             modified = modified.style(modified.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
/*     */             
/*  84 */             if (children == null) {
/*  85 */               children = new ArrayList<>(oldChildrenSize + modified.children().size());
/*  86 */               children.addAll(modified.children());
/*     */             } 
/*     */           } else {
/*     */             
/*  90 */             modified = Component.text("", component.style());
/*  91 */             ComponentLike child = state.replacement.apply(matcher, Component.text().content(matcher.group()));
/*  92 */             if (child != null) {
/*  93 */               if (children == null) {
/*  94 */                 children = new ArrayList<>(oldChildrenSize + 1);
/*     */               }
/*  96 */               children.add(child.asComponent());
/*     */             } 
/*     */           } 
/*     */         } else {
/* 100 */           if (children == null) {
/* 101 */             children = new ArrayList<>(oldChildrenSize + 2);
/*     */           }
/* 103 */           if (state.firstMatch) {
/*     */             
/* 105 */             modified = ((TextComponent)component).content(content.substring(0, matcher.start()));
/* 106 */           } else if (replacedUntil < matcher.start()) {
/* 107 */             children.add(Component.text(content.substring(replacedUntil, matcher.start())));
/*     */           } 
/* 109 */           ComponentLike builder = state.replacement.apply(matcher, Component.text().content(matcher.group()));
/* 110 */           if (builder != null) {
/* 111 */             children.add(builder.asComponent());
/*     */           }
/*     */         } 
/* 114 */         state.replaceCount++;
/* 115 */         state.firstMatch = false;
/* 116 */         replacedUntil = matcher.end();
/*     */       } 
/* 118 */       if (replacedUntil < content.length())
/*     */       {
/* 120 */         if (replacedUntil > 0) {
/* 121 */           if (children == null) {
/* 122 */             children = new ArrayList<>(oldChildrenSize);
/*     */           }
/* 124 */           children.add(Component.text(content.substring(replacedUntil)));
/*     */         }
/*     */       
/*     */       }
/* 128 */     } else if (modified instanceof TranslatableComponent) {
/* 129 */       List<Component> args = ((TranslatableComponent)modified).args();
/* 130 */       List<Component> newArgs = null;
/* 131 */       for (int i = 0, size = args.size(); i < size; i++) {
/* 132 */         Component original = args.get(i);
/* 133 */         Component replaced = render(original, state);
/* 134 */         if (replaced != component && 
/* 135 */           newArgs == null) {
/* 136 */           newArgs = new ArrayList<>(size);
/* 137 */           if (i > 0) {
/* 138 */             newArgs.addAll(args.subList(0, i));
/*     */           }
/*     */         } 
/*     */         
/* 142 */         if (newArgs != null) {
/* 143 */           newArgs.add(replaced);
/*     */         }
/*     */       } 
/* 146 */       if (newArgs != null) {
/* 147 */         modified = ((TranslatableComponent)modified).args((List)newArgs);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     if (state.running) {
/*     */       
/* 153 */       HoverEvent<?> event = modified.style().hoverEvent();
/* 154 */       if (event != null) {
/* 155 */         HoverEvent<?> rendered = event.withRenderedValue(this, state);
/* 156 */         if (event != rendered) {
/* 157 */           modified = modified.style(s -> s.hoverEvent((HoverEventSource)rendered));
/*     */         }
/*     */       } 
/*     */       
/* 161 */       boolean first = true;
/* 162 */       for (int i = 0; i < oldChildrenSize; i++) {
/* 163 */         Component child = oldChildren.get(i);
/* 164 */         Component replaced = render(child, state);
/* 165 */         if (replaced != child) {
/* 166 */           if (children == null) {
/* 167 */             children = new ArrayList<>(oldChildrenSize);
/*     */           }
/* 169 */           if (first) {
/* 170 */             children.addAll(oldChildren.subList(0, i));
/*     */           }
/* 172 */           first = false;
/*     */         } 
/* 174 */         if (children != null) {
/* 175 */           children.add(replaced);
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 180 */     else if (children != null) {
/* 181 */       children.addAll(oldChildren);
/*     */     } 
/*     */ 
/*     */     
/* 185 */     state.firstMatch = prevFirstMatch;
/*     */     
/* 187 */     if (children != null) {
/* 188 */       return modified.children((List)children);
/*     */     }
/* 190 */     return modified;
/*     */   }
/*     */   
/*     */   static final class State {
/*     */     final Pattern pattern;
/*     */     final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
/*     */     final TextReplacementConfig.Condition continuer;
/*     */     boolean running = true;
/* 198 */     int matchCount = 0;
/* 199 */     int replaceCount = 0;
/*     */     boolean firstMatch = true;
/*     */     
/*     */     State(@NotNull Pattern pattern, @NotNull BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement, TextReplacementConfig.Condition continuer) {
/* 203 */       this.pattern = pattern;
/* 204 */       this.replacement = replacement;
/* 205 */       this.continuer = continuer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TextReplacementRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */