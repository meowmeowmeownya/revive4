/*     */ package net.kyori.adventure.text.renderer;
/*     */ 
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import net.kyori.adventure.text.BlockNBTComponent;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.ComponentBuilder;
/*     */ import net.kyori.adventure.text.EntityNBTComponent;
/*     */ import net.kyori.adventure.text.KeybindComponent;
/*     */ import net.kyori.adventure.text.ScoreComponent;
/*     */ import net.kyori.adventure.text.SelectorComponent;
/*     */ import net.kyori.adventure.text.StorageNBTComponent;
/*     */ import net.kyori.adventure.text.TextComponent;
/*     */ import net.kyori.adventure.text.TranslatableComponent;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.translation.Translator;
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
/*     */ public abstract class TranslatableComponentRenderer<C>
/*     */   extends AbstractComponentRenderer<C>
/*     */ {
/*  60 */   private static final Set<Style.Merge> MERGES = Style.Merge.of(new Style.Merge[] { Style.Merge.COLOR, Style.Merge.DECORATIONS, Style.Merge.INSERTION, Style.Merge.FONT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static TranslatableComponentRenderer<Locale> usingTranslationSource(@NotNull final Translator source) {
/*  70 */     Objects.requireNonNull(source, "source");
/*  71 */     return new TranslatableComponentRenderer<Locale>() {
/*     */         @Nullable
/*     */         protected MessageFormat translate(@NotNull String key, @NotNull Locale context) {
/*  74 */           return source.translate(key, context);
/*     */         }
/*     */       };
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
/*     */   @NotNull
/*     */   protected Component renderBlockNbt(@NotNull BlockNBTComponent component, @NotNull C context) {
/*  91 */     BlockNBTComponent.Builder builder = ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), component)).pos(component.pos());
/*  92 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderEntityNbt(@NotNull EntityNBTComponent component, @NotNull C context) {
/*  98 */     EntityNBTComponent.Builder builder = ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), component)).selector(component.selector());
/*  99 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderStorageNbt(@NotNull StorageNBTComponent component, @NotNull C context) {
/* 105 */     StorageNBTComponent.Builder builder = ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), component)).storage(component.storage());
/* 106 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   protected static <C extends net.kyori.adventure.text.NBTComponent<C, B>, B extends net.kyori.adventure.text.NBTComponentBuilder<C, B>> B nbt(B builder, C oldComponent) {
/* 110 */     return (B)builder
/* 111 */       .nbtPath(oldComponent.nbtPath())
/* 112 */       .interpret(oldComponent.interpret());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderKeybind(@NotNull KeybindComponent component, @NotNull C context) {
/* 117 */     KeybindComponent.Builder builder = Component.keybind().keybind(component.keybind());
/* 118 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderScore(@NotNull ScoreComponent component, @NotNull C context) {
/* 127 */     ScoreComponent.Builder builder = Component.score().name(component.name()).objective(component.objective()).value(component.value());
/* 128 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderSelector(@NotNull SelectorComponent component, @NotNull C context) {
/* 133 */     SelectorComponent.Builder builder = Component.selector().pattern(component.pattern());
/* 134 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderText(@NotNull TextComponent component, @NotNull C context) {
/* 139 */     TextComponent.Builder builder = Component.text().content(component.content());
/* 140 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderTranslatable(@NotNull TranslatableComponent component, @NotNull C context) {
/* 145 */     MessageFormat format = translate(component.key(), context);
/* 146 */     if (format == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       TranslatableComponent.Builder builder1 = Component.translatable().key(component.key());
/* 152 */       if (!component.args().isEmpty()) {
/* 153 */         List<Component> list = new ArrayList<>(component.args());
/* 154 */         for (int i = 0, size = list.size(); i < size; i++) {
/* 155 */           list.set(i, render(list.get(i), context));
/*     */         }
/* 157 */         builder1.args(list);
/*     */       } 
/* 159 */       return mergeStyleAndOptionallyDeepRender((Component)component, builder1, context);
/*     */     } 
/*     */     
/* 162 */     List<Component> args = component.args();
/*     */     
/* 164 */     TextComponent.Builder builder = Component.text();
/* 165 */     mergeStyle((Component)component, builder, context);
/*     */ 
/*     */     
/* 168 */     if (args.isEmpty()) {
/* 169 */       builder.content(format.format((Object[])null, new StringBuffer(), (FieldPosition)null).toString());
/* 170 */       return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */     } 
/*     */     
/* 173 */     Object[] nulls = new Object[args.size()];
/* 174 */     StringBuffer sb = format.format(nulls, new StringBuffer(), (FieldPosition)null);
/* 175 */     AttributedCharacterIterator it = format.formatToCharacterIterator(nulls);
/*     */     
/* 177 */     while (it.getIndex() < it.getEndIndex()) {
/* 178 */       int end = it.getRunLimit();
/* 179 */       Integer index = (Integer)it.getAttribute(MessageFormat.Field.ARGUMENT);
/* 180 */       if (index != null) {
/* 181 */         builder.append(render(args.get(index.intValue()), context));
/*     */       } else {
/* 183 */         builder.append((Component)Component.text(sb.substring(it.getIndex(), end)));
/*     */       } 
/* 185 */       it.setIndex(end);
/*     */     } 
/*     */     
/* 188 */     return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */   }
/*     */   
/*     */   protected <O extends net.kyori.adventure.text.BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O mergeStyleAndOptionallyDeepRender(Component component, B builder, C context) {
/* 192 */     mergeStyle(component, (ComponentBuilder<?, ?>)builder, context);
/* 193 */     return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */   }
/*     */   
/*     */   protected <O extends net.kyori.adventure.text.BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O optionallyRenderChildrenAppendAndBuild(List<Component> children, B builder, C context) {
/* 197 */     if (!children.isEmpty()) {
/* 198 */       children.forEach(child -> builder.append(render(child, (C)context)));
/*     */     }
/* 200 */     return (O)builder.build();
/*     */   }
/*     */   
/*     */   protected <B extends ComponentBuilder<?, ?>> void mergeStyle(Component component, B builder, C context) {
/* 204 */     builder.mergeStyle(component, MERGES);
/* 205 */     builder.clickEvent(component.clickEvent());
/* 206 */     HoverEvent<?> hoverEvent = component.hoverEvent();
/* 207 */     if (hoverEvent != null)
/* 208 */       builder.hoverEvent((HoverEventSource)hoverEvent.withRenderedValue(this, context)); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected abstract MessageFormat translate(@NotNull String paramString, @NotNull C paramC);
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\renderer\TranslatableComponentRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */