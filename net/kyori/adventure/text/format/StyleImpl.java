/*     */ package net.kyori.adventure.text.format;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.text.event.ClickEvent;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import net.kyori.examination.Examiner;
/*     */ import net.kyori.examination.string.StringExaminer;
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
/*     */ final class StyleImpl
/*     */   implements Style
/*     */ {
/*  43 */   static final StyleImpl EMPTY = new StyleImpl(null, null, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);
/*  44 */   private static final TextDecoration[] DECORATIONS = TextDecoration.values();
/*     */   
/*     */   @Nullable
/*     */   final Key font;
/*     */   
/*     */   @Nullable
/*     */   final TextColor color;
/*     */   
/*     */   final TextDecoration.State obfuscated;
/*     */   
/*     */   final TextDecoration.State bold;
/*     */   final TextDecoration.State strikethrough;
/*     */   
/*     */   static void decorate(Style.Builder builder, TextDecoration[] decorations) {
/*  58 */     for (int i = 0, length = decorations.length; i < length; i++) {
/*  59 */       TextDecoration decoration = decorations[i];
/*  60 */       builder.decoration(decoration, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final TextDecoration.State underlined;
/*     */   
/*     */   final TextDecoration.State italic;
/*     */   @Nullable
/*     */   final ClickEvent clickEvent;
/*     */   @Nullable
/*     */   final HoverEvent<?> hoverEvent;
/*     */   @Nullable
/*     */   final String insertion;
/*     */   
/*     */   StyleImpl(@Nullable Key font, @Nullable TextColor color, TextDecoration.State obfuscated, TextDecoration.State bold, TextDecoration.State strikethrough, TextDecoration.State underlined, TextDecoration.State italic, @Nullable ClickEvent clickEvent, @Nullable HoverEvent<?> hoverEvent, @Nullable String insertion) {
/*  76 */     this.font = font;
/*  77 */     this.color = color;
/*  78 */     this.obfuscated = obfuscated;
/*  79 */     this.bold = bold;
/*  80 */     this.strikethrough = strikethrough;
/*  81 */     this.underlined = underlined;
/*  82 */     this.italic = italic;
/*  83 */     this.clickEvent = clickEvent;
/*  84 */     this.hoverEvent = hoverEvent;
/*  85 */     this.insertion = insertion;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Key font() {
/*  90 */     return this.font;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style font(@Nullable Key font) {
/*  95 */     if (Objects.equals(this.font, font)) return this; 
/*  96 */     return new StyleImpl(font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TextColor color() {
/* 101 */     return this.color;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style color(@Nullable TextColor color) {
/* 106 */     if (Objects.equals(this.color, color)) return this; 
/* 107 */     return new StyleImpl(this.font, color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style colorIfAbsent(@Nullable TextColor color) {
/* 112 */     if (this.color == null) {
/* 113 */       return color(color);
/*     */     }
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextDecoration.State decoration(@NotNull TextDecoration decoration) {
/* 120 */     if (decoration == TextDecoration.BOLD)
/* 121 */       return this.bold; 
/* 122 */     if (decoration == TextDecoration.ITALIC)
/* 123 */       return this.italic; 
/* 124 */     if (decoration == TextDecoration.UNDERLINED)
/* 125 */       return this.underlined; 
/* 126 */     if (decoration == TextDecoration.STRIKETHROUGH)
/* 127 */       return this.strikethrough; 
/* 128 */     if (decoration == TextDecoration.OBFUSCATED) {
/* 129 */       return this.obfuscated;
/*     */     }
/* 131 */     throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 136 */     Objects.requireNonNull(state, "state");
/* 137 */     if (decoration == TextDecoration.BOLD)
/* 138 */       return new StyleImpl(this.font, this.color, this.obfuscated, state, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion); 
/* 139 */     if (decoration == TextDecoration.ITALIC)
/* 140 */       return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, state, this.clickEvent, this.hoverEvent, this.insertion); 
/* 141 */     if (decoration == TextDecoration.UNDERLINED)
/* 142 */       return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, state, this.italic, this.clickEvent, this.hoverEvent, this.insertion); 
/* 143 */     if (decoration == TextDecoration.STRIKETHROUGH)
/* 144 */       return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, state, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion); 
/* 145 */     if (decoration == TextDecoration.OBFUSCATED) {
/* 146 */       return new StyleImpl(this.font, this.color, state, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
/*     */     }
/* 148 */     throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Map<TextDecoration, TextDecoration.State> decorations() {
/* 153 */     Map<TextDecoration, TextDecoration.State> decorations = new EnumMap<>(TextDecoration.class);
/* 154 */     for (int i = 0, length = DECORATIONS.length; i < length; i++) {
/* 155 */       TextDecoration decoration = DECORATIONS[i];
/* 156 */       TextDecoration.State value = decoration(decoration);
/* 157 */       decorations.put(decoration, value);
/*     */     } 
/* 159 */     return decorations;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
/* 164 */     TextDecoration.State obfuscated = decorations.getOrDefault(TextDecoration.OBFUSCATED, this.obfuscated);
/* 165 */     TextDecoration.State bold = decorations.getOrDefault(TextDecoration.BOLD, this.bold);
/* 166 */     TextDecoration.State strikethrough = decorations.getOrDefault(TextDecoration.STRIKETHROUGH, this.strikethrough);
/* 167 */     TextDecoration.State underlined = decorations.getOrDefault(TextDecoration.UNDERLINED, this.underlined);
/* 168 */     TextDecoration.State italic = decorations.getOrDefault(TextDecoration.ITALIC, this.italic);
/* 169 */     return new StyleImpl(this.font, this.color, obfuscated, bold, strikethrough, underlined, italic, this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ClickEvent clickEvent() {
/* 174 */     return this.clickEvent;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style clickEvent(@Nullable ClickEvent event) {
/* 179 */     return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, event, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public HoverEvent<?> hoverEvent() {
/* 184 */     return this.hoverEvent;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style hoverEvent(@Nullable HoverEventSource<?> source) {
/* 189 */     return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String insertion() {
/* 194 */     return this.insertion;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style insertion(@Nullable String insertion) {
/* 199 */     if (Objects.equals(this.insertion, insertion)) return this; 
/* 200 */     return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
/* 205 */     if (that.isEmpty() || strategy == Style.Merge.Strategy.NEVER || merges.isEmpty())
/*     */     {
/* 207 */       return this;
/*     */     }
/*     */     
/* 210 */     if (isEmpty() && Style.Merge.hasAll(merges))
/*     */     {
/*     */       
/* 213 */       return that;
/*     */     }
/*     */     
/* 216 */     Style.Builder builder = toBuilder();
/* 217 */     builder.merge(that, strategy, merges);
/* 218 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 223 */     return (this == EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Style.Builder toBuilder() {
/* 233 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 238 */     return Stream.of(new ExaminableProperty[] {
/* 239 */           ExaminableProperty.of("color", this.color), 
/* 240 */           ExaminableProperty.of("obfuscated", this.obfuscated), 
/* 241 */           ExaminableProperty.of("bold", this.bold), 
/* 242 */           ExaminableProperty.of("strikethrough", this.strikethrough), 
/* 243 */           ExaminableProperty.of("underlined", this.underlined), 
/* 244 */           ExaminableProperty.of("italic", this.italic), 
/* 245 */           ExaminableProperty.of("clickEvent", this.clickEvent), 
/* 246 */           ExaminableProperty.of("hoverEvent", this.hoverEvent), 
/* 247 */           ExaminableProperty.of("insertion", this.insertion), 
/* 248 */           ExaminableProperty.of("font", this.font)
/*     */         });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 254 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 259 */     if (this == other) return true; 
/* 260 */     if (!(other instanceof StyleImpl)) return false; 
/* 261 */     StyleImpl that = (StyleImpl)other;
/* 262 */     return (Objects.equals(this.color, that.color) && this.obfuscated == that.obfuscated && this.bold == that.bold && this.strikethrough == that.strikethrough && this.underlined == that.underlined && this.italic == that.italic && 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 268 */       Objects.equals(this.clickEvent, that.clickEvent) && 
/* 269 */       Objects.equals(this.hoverEvent, that.hoverEvent) && 
/* 270 */       Objects.equals(this.insertion, that.insertion) && 
/* 271 */       Objects.equals(this.font, that.font));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 276 */     int result = Objects.hashCode(this.color);
/* 277 */     result = 31 * result + this.obfuscated.hashCode();
/* 278 */     result = 31 * result + this.bold.hashCode();
/* 279 */     result = 31 * result + this.strikethrough.hashCode();
/* 280 */     result = 31 * result + this.underlined.hashCode();
/* 281 */     result = 31 * result + this.italic.hashCode();
/* 282 */     result = 31 * result + Objects.hashCode(this.clickEvent);
/* 283 */     result = 31 * result + Objects.hashCode(this.hoverEvent);
/* 284 */     result = 31 * result + Objects.hashCode(this.insertion);
/* 285 */     result = 31 * result + Objects.hashCode(this.font);
/* 286 */     return result;
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements Style.Builder {
/*     */     @Nullable
/*     */     Key font;
/* 292 */     TextDecoration.State obfuscated = TextDecoration.State.NOT_SET; @Nullable
/* 293 */     TextColor color; TextDecoration.State bold = TextDecoration.State.NOT_SET;
/* 294 */     TextDecoration.State strikethrough = TextDecoration.State.NOT_SET;
/* 295 */     TextDecoration.State underlined = TextDecoration.State.NOT_SET;
/* 296 */     TextDecoration.State italic = TextDecoration.State.NOT_SET;
/*     */     @Nullable
/*     */     ClickEvent clickEvent;
/*     */     @Nullable
/*     */     HoverEvent<?> hoverEvent;
/*     */     @Nullable
/*     */     String insertion;
/*     */     
/*     */     BuilderImpl(@NotNull StyleImpl style) {
/* 305 */       this.color = style.color;
/* 306 */       this.obfuscated = style.obfuscated;
/* 307 */       this.bold = style.bold;
/* 308 */       this.strikethrough = style.strikethrough;
/* 309 */       this.underlined = style.underlined;
/* 310 */       this.italic = style.italic;
/* 311 */       this.clickEvent = style.clickEvent;
/* 312 */       this.hoverEvent = style.hoverEvent;
/* 313 */       this.insertion = style.insertion;
/* 314 */       this.font = style.font;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder font(@Nullable Key font) {
/* 319 */       this.font = font;
/* 320 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder color(@Nullable TextColor color) {
/* 325 */       this.color = color;
/* 326 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder colorIfAbsent(@Nullable TextColor color) {
/* 331 */       if (this.color == null) {
/* 332 */         this.color = color;
/*     */       }
/* 334 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder decorate(@NotNull TextDecoration decoration) {
/* 339 */       return decoration(decoration, TextDecoration.State.TRUE);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder decorate(@NotNull TextDecoration... decorations) {
/* 344 */       for (int i = 0, length = decorations.length; i < length; i++) {
/* 345 */         decorate(decorations[i]);
/*     */       }
/* 347 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 352 */       Objects.requireNonNull(state, "state");
/* 353 */       if (decoration == TextDecoration.BOLD) {
/* 354 */         this.bold = state;
/* 355 */         return this;
/* 356 */       }  if (decoration == TextDecoration.ITALIC) {
/* 357 */         this.italic = state;
/* 358 */         return this;
/* 359 */       }  if (decoration == TextDecoration.UNDERLINED) {
/* 360 */         this.underlined = state;
/* 361 */         return this;
/* 362 */       }  if (decoration == TextDecoration.STRIKETHROUGH) {
/* 363 */         this.strikethrough = state;
/* 364 */         return this;
/* 365 */       }  if (decoration == TextDecoration.OBFUSCATED) {
/* 366 */         this.obfuscated = state;
/* 367 */         return this;
/*     */       } 
/* 369 */       throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     Style.Builder decorationIfAbsent(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 374 */       Objects.requireNonNull(state, "state");
/* 375 */       if (decoration == TextDecoration.BOLD) {
/* 376 */         if (this.bold == TextDecoration.State.NOT_SET) {
/* 377 */           this.bold = state;
/*     */         }
/* 379 */         return this;
/* 380 */       }  if (decoration == TextDecoration.ITALIC) {
/* 381 */         if (this.italic == TextDecoration.State.NOT_SET) {
/* 382 */           this.italic = state;
/*     */         }
/* 384 */         return this;
/* 385 */       }  if (decoration == TextDecoration.UNDERLINED) {
/* 386 */         if (this.underlined == TextDecoration.State.NOT_SET) {
/* 387 */           this.underlined = state;
/*     */         }
/* 389 */         return this;
/* 390 */       }  if (decoration == TextDecoration.STRIKETHROUGH) {
/* 391 */         if (this.strikethrough == TextDecoration.State.NOT_SET) {
/* 392 */           this.strikethrough = state;
/*     */         }
/* 394 */         return this;
/* 395 */       }  if (decoration == TextDecoration.OBFUSCATED) {
/* 396 */         if (this.obfuscated == TextDecoration.State.NOT_SET) {
/* 397 */           this.obfuscated = state;
/*     */         }
/* 399 */         return this;
/*     */       } 
/* 401 */       throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder clickEvent(@Nullable ClickEvent event) {
/* 406 */       this.clickEvent = event;
/* 407 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder hoverEvent(@Nullable HoverEventSource<?> source) {
/* 412 */       this.hoverEvent = HoverEventSource.unbox(source);
/* 413 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder insertion(@Nullable String insertion) {
/* 418 */       this.insertion = insertion;
/* 419 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
/* 424 */       if (strategy == Style.Merge.Strategy.NEVER || that.isEmpty() || merges.isEmpty())
/*     */       {
/* 426 */         return this;
/*     */       }
/*     */       
/* 429 */       Merger merger = merger(strategy);
/*     */       
/* 431 */       if (merges.contains(Style.Merge.COLOR)) {
/* 432 */         TextColor color = that.color();
/* 433 */         if (color != null) merger.mergeColor(this, color);
/*     */       
/*     */       } 
/* 436 */       if (merges.contains(Style.Merge.DECORATIONS)) {
/* 437 */         for (int i = 0, length = StyleImpl.DECORATIONS.length; i < length; i++) {
/* 438 */           TextDecoration decoration = StyleImpl.DECORATIONS[i];
/* 439 */           TextDecoration.State state = that.decoration(decoration);
/* 440 */           if (state != TextDecoration.State.NOT_SET) merger.mergeDecoration(this, decoration, state);
/*     */         
/*     */         } 
/*     */       }
/* 444 */       if (merges.contains(Style.Merge.EVENTS)) {
/* 445 */         ClickEvent clickEvent = that.clickEvent();
/* 446 */         if (clickEvent != null) merger.mergeClickEvent(this, clickEvent);
/*     */         
/* 448 */         HoverEvent<?> hoverEvent = that.hoverEvent();
/* 449 */         if (hoverEvent != null) merger.mergeHoverEvent(this, hoverEvent);
/*     */       
/*     */       } 
/* 452 */       if (merges.contains(Style.Merge.INSERTION)) {
/* 453 */         String insertion = that.insertion();
/* 454 */         if (insertion != null) merger.mergeInsertion(this, insertion);
/*     */       
/*     */       } 
/* 457 */       if (merges.contains(Style.Merge.FONT)) {
/* 458 */         Key font = that.font();
/* 459 */         if (font != null) merger.mergeFont(this, font);
/*     */       
/*     */       } 
/* 462 */       return this;
/*     */     }
/*     */     
/*     */     private static Merger merger(Style.Merge.Strategy strategy) {
/* 466 */       if (strategy == Style.Merge.Strategy.ALWAYS)
/* 467 */         return AlwaysMerger.INSTANCE; 
/* 468 */       if (strategy == Style.Merge.Strategy.NEVER)
/* 469 */         throw new UnsupportedOperationException(); 
/* 470 */       if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
/* 471 */         return IfAbsentOnTargetMerger.INSTANCE;
/*     */       }
/* 473 */       throw new IllegalArgumentException(strategy.name());
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public StyleImpl build() {
/* 478 */       if (isEmpty()) {
/* 479 */         return StyleImpl.EMPTY;
/*     */       }
/* 481 */       return new StyleImpl(this.font, this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
/*     */     }
/*     */     
/*     */     private boolean isEmpty() {
/* 485 */       return (this.color == null && this.obfuscated == TextDecoration.State.NOT_SET && this.bold == TextDecoration.State.NOT_SET && this.strikethrough == TextDecoration.State.NOT_SET && this.underlined == TextDecoration.State.NOT_SET && this.italic == TextDecoration.State.NOT_SET && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null);
/*     */     }
/*     */     
/*     */     BuilderImpl() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\StyleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */