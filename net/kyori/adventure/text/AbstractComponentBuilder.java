/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.text.event.ClickEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.format.TextColor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractComponentBuilder<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
/*     */   implements ComponentBuilder<C, B>
/*     */ {
/*  52 */   protected List<Component> children = Collections.emptyList();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Style style;
/*     */   
/*     */   private Style.Builder styleBuilder;
/*     */ 
/*     */   
/*     */   protected AbstractComponentBuilder() {}
/*     */ 
/*     */   
/*     */   protected AbstractComponentBuilder(@NotNull C component) {
/*  65 */     List<Component> children = component.children();
/*  66 */     if (!children.isEmpty()) {
/*  67 */       this.children = new ArrayList<>(children);
/*     */     }
/*  69 */     if (component.hasStyling()) {
/*  70 */       this.style = component.style();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Component component) {
/*  77 */     if (component == Component.empty()) return (B)this; 
/*  78 */     prepareChildren();
/*  79 */     this.children.add(Objects.<Component>requireNonNull(component, "component"));
/*  80 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Component... components) {
/*  86 */     Objects.requireNonNull(components, "components");
/*  87 */     boolean prepared = false;
/*  88 */     for (int i = 0, length = components.length; i < length; i++) {
/*  89 */       Component component = components[i];
/*  90 */       if (component != Component.empty()) {
/*  91 */         if (!prepared) {
/*  92 */           prepareChildren();
/*  93 */           prepared = true;
/*     */         } 
/*  95 */         this.children.add(Objects.<Component>requireNonNull(component, "components[?]"));
/*     */       } 
/*     */     } 
/*  98 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull ComponentLike... components) {
/* 104 */     Objects.requireNonNull(components, "components");
/* 105 */     boolean prepared = false;
/* 106 */     for (int i = 0, length = components.length; i < length; i++) {
/* 107 */       Component component = components[i].asComponent();
/* 108 */       if (component != Component.empty()) {
/* 109 */         if (!prepared) {
/* 110 */           prepareChildren();
/* 111 */           prepared = true;
/*     */         } 
/* 113 */         this.children.add(Objects.<Component>requireNonNull(component, "components[?]"));
/*     */       } 
/*     */     } 
/* 116 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Iterable<? extends ComponentLike> components) {
/* 122 */     Objects.requireNonNull(components, "components");
/* 123 */     boolean prepared = false;
/* 124 */     for (ComponentLike like : components) {
/* 125 */       Component component = like.asComponent();
/* 126 */       if (component != Component.empty()) {
/* 127 */         if (!prepared) {
/* 128 */           prepareChildren();
/* 129 */           prepared = true;
/*     */         } 
/* 131 */         this.children.add(Objects.<Component>requireNonNull(component, "components[?]"));
/*     */       } 
/*     */     } 
/* 134 */     return (B)this;
/*     */   }
/*     */   
/*     */   private void prepareChildren() {
/* 138 */     if (this.children == Collections.emptyList()) {
/* 139 */       this.children = new ArrayList<>();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B applyDeep(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer) {
/* 146 */     apply(consumer);
/* 147 */     if (this.children == Collections.emptyList()) {
/* 148 */       return (B)this;
/*     */     }
/* 150 */     ListIterator<Component> it = this.children.listIterator();
/* 151 */     while (it.hasNext()) {
/* 152 */       Component child = it.next();
/* 153 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 156 */       ComponentBuilder<?, ?> childBuilder = (ComponentBuilder<?, ?>)((BuildableComponent)child).toBuilder();
/* 157 */       childBuilder.applyDeep(consumer);
/* 158 */       it.set((Component)childBuilder.build());
/*     */     } 
/* 160 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mapChildren(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
/* 166 */     if (this.children == Collections.emptyList()) {
/* 167 */       return (B)this;
/*     */     }
/* 169 */     ListIterator<Component> it = this.children.listIterator();
/* 170 */     while (it.hasNext()) {
/* 171 */       Component child = it.next();
/* 172 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 175 */       BuildableComponent<?, ?> mappedChild = Objects.<BuildableComponent<?, ?>>requireNonNull(function.apply((BuildableComponent<?, ?>)child), "mappedChild");
/* 176 */       if (child == mappedChild) {
/*     */         continue;
/*     */       }
/* 179 */       it.set(mappedChild);
/*     */     } 
/* 181 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mapChildrenDeep(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
/* 187 */     if (this.children == Collections.emptyList()) {
/* 188 */       return (B)this;
/*     */     }
/* 190 */     ListIterator<Component> it = this.children.listIterator();
/* 191 */     while (it.hasNext()) {
/* 192 */       Component child = it.next();
/* 193 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 196 */       BuildableComponent<?, ?> mappedChild = Objects.<BuildableComponent<?, ?>>requireNonNull(function.apply((BuildableComponent<?, ?>)child), "mappedChild");
/* 197 */       if (mappedChild.children().isEmpty()) {
/* 198 */         if (child == mappedChild) {
/*     */           continue;
/*     */         }
/* 201 */         it.set(mappedChild); continue;
/*     */       } 
/* 203 */       ComponentBuilder<?, ?> builder = (ComponentBuilder<?, ?>)mappedChild.toBuilder();
/* 204 */       builder.mapChildrenDeep(function);
/* 205 */       it.set((Component)builder.build());
/*     */     } 
/*     */     
/* 208 */     return (B)this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Component> children() {
/* 213 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B style(@NotNull Style style) {
/* 219 */     this.style = style;
/* 220 */     this.styleBuilder = null;
/* 221 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B style(@NotNull Consumer<Style.Builder> consumer) {
/* 227 */     consumer.accept(styleBuilder());
/* 228 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B font(@Nullable Key font) {
/* 234 */     styleBuilder().font(font);
/* 235 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B color(@Nullable TextColor color) {
/* 241 */     styleBuilder().color(color);
/* 242 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B colorIfAbsent(@Nullable TextColor color) {
/* 248 */     styleBuilder().colorIfAbsent(color);
/* 249 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 255 */     styleBuilder().decoration(decoration, state);
/* 256 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B clickEvent(@Nullable ClickEvent event) {
/* 262 */     styleBuilder().clickEvent(event);
/* 263 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B hoverEvent(@Nullable HoverEventSource<?> source) {
/* 269 */     styleBuilder().hoverEvent(source);
/* 270 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B insertion(@Nullable String insertion) {
/* 276 */     styleBuilder().insertion(insertion);
/* 277 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/* 283 */     styleBuilder().merge(that.style(), merges);
/* 284 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B resetStyle() {
/* 290 */     this.style = null;
/* 291 */     this.styleBuilder = null;
/* 292 */     return (B)this;
/*     */   }
/*     */   
/*     */   private Style.Builder styleBuilder() {
/* 296 */     if (this.styleBuilder == null) {
/* 297 */       if (this.style != null) {
/* 298 */         this.styleBuilder = this.style.toBuilder();
/* 299 */         this.style = null;
/*     */       } else {
/* 301 */         this.styleBuilder = Style.style();
/*     */       } 
/*     */     }
/* 304 */     return this.styleBuilder;
/*     */   }
/*     */   
/*     */   protected final boolean hasStyle() {
/* 308 */     return (this.styleBuilder != null || this.style != null);
/*     */   }
/*     */   @NotNull
/*     */   protected Style buildStyle() {
/* 312 */     if (this.styleBuilder != null)
/* 313 */       return this.styleBuilder.build(); 
/* 314 */     if (this.style != null) {
/* 315 */       return this.style;
/*     */     }
/* 317 */     return Style.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\AbstractComponentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */