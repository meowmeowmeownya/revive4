/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.adventure.util.Nag;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TextComponentImpl
/*     */   extends AbstractComponent
/*     */   implements TextComponent
/*     */ {
/*  40 */   private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.getBoolean(String.join(".", new CharSequence[] { "net", "kyori", "adventure", "text", "warnWhenLegacyFormattingDetected" }));
/*     */   
/*     */   @VisibleForTesting
/*     */   static final char SECTION_CHAR = '§';
/*  44 */   static final TextComponent EMPTY = createDirect("");
/*  45 */   static final TextComponent NEWLINE = createDirect("\n");
/*  46 */   static final TextComponent SPACE = createDirect(" ");
/*     */   @NotNull
/*     */   private static TextComponent createDirect(@NotNull String content) {
/*  49 */     return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
/*     */   }
/*     */   
/*     */   private final String content;
/*     */   
/*     */   TextComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String content) {
/*  55 */     super(children, style);
/*  56 */     this.content = Objects.<String>requireNonNull(content, "content");
/*     */     
/*  58 */     if (WARN_WHEN_LEGACY_FORMATTING_DETECTED) {
/*  59 */       LegacyFormattingDetected nag = warnWhenLegacyFormattingDetected();
/*  60 */       if (nag != null)
/*  61 */         Nag.print(nag); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   @Nullable
/*     */   final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
/*  68 */     if (this.content.indexOf('§') != -1) {
/*  69 */       return new LegacyFormattingDetected(this);
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String content() {
/*  76 */     return this.content;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent content(@NotNull String content) {
/*  81 */     if (Objects.equals(this.content, content)) return this; 
/*  82 */     return new TextComponentImpl((List)this.children, this.style, Objects.<String>requireNonNull(content, "content"));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent children(@NotNull List<? extends ComponentLike> children) {
/*  87 */     return new TextComponentImpl(children, this.style, this.content);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent style(@NotNull Style style) {
/*  92 */     return new TextComponentImpl((List)this.children, style, this.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  97 */     if (this == other) return true; 
/*  98 */     if (!(other instanceof TextComponentImpl)) return false; 
/*  99 */     if (!super.equals(other)) return false; 
/* 100 */     TextComponentImpl that = (TextComponentImpl)other;
/* 101 */     return Objects.equals(this.content, that.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int result = super.hashCode();
/* 107 */     result = 31 * result + this.content.hashCode();
/* 108 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 113 */     return Stream.concat(
/* 114 */         Stream.of(
/* 115 */           ExaminableProperty.of("content", this.content)), super
/*     */         
/* 117 */         .examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public TextComponent.Builder toBuilder() {
/* 123 */     return new BuilderImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class BuilderImpl
/*     */     extends AbstractComponentBuilder<TextComponent, TextComponent.Builder>
/*     */     implements TextComponent.Builder
/*     */   {
/* 132 */     private String content = "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BuilderImpl(@NotNull TextComponent component) {
/* 138 */       super(component);
/* 139 */       this.content = component.content();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TextComponent.Builder content(@NotNull String content) {
/* 144 */       this.content = Objects.<String>requireNonNull(content, "content");
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String content() {
/* 150 */       return this.content;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TextComponent build() {
/* 155 */       if (isEmpty()) {
/* 156 */         return Component.empty();
/*     */       }
/* 158 */       return new TextComponentImpl((List)this.children, buildStyle(), this.content);
/*     */     }
/*     */     
/*     */     private boolean isEmpty() {
/* 162 */       return (this.content.isEmpty() && this.children.isEmpty() && !hasStyle());
/*     */     }
/*     */     
/*     */     BuilderImpl() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TextComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */