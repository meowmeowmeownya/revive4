/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.examination.ExaminableProperty;
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
/*     */ abstract class NBTComponentImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
/*     */   extends AbstractComponent
/*     */   implements NBTComponent<C, B>
/*     */ {
/*     */   static final boolean INTERPRET_DEFAULT = false;
/*     */   final String nbtPath;
/*     */   final boolean interpret;
/*     */   @Nullable
/*     */   final Component separator;
/*     */   
/*     */   NBTComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable ComponentLike separator) {
/*  41 */     super(children, style);
/*  42 */     this.nbtPath = nbtPath;
/*  43 */     this.interpret = interpret;
/*  44 */     this.separator = ComponentLike.unbox(separator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String nbtPath() {
/*  49 */     return this.nbtPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interpret() {
/*  54 */     return this.interpret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  59 */     if (this == other) return true; 
/*  60 */     if (!(other instanceof NBTComponent)) return false; 
/*  61 */     if (!super.equals(other)) return false; 
/*  62 */     NBTComponent<?, ?> that = (NBTComponent<?, ?>)other;
/*  63 */     return (Objects.equals(this.nbtPath, that.nbtPath()) && this.interpret == that.interpret() && Objects.equals(this.separator, that.separator()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  68 */     int result = super.hashCode();
/*  69 */     result = 31 * result + this.nbtPath.hashCode();
/*  70 */     result = 31 * result + Boolean.hashCode(this.interpret);
/*  71 */     result = 31 * result + Objects.hashCode(this.separator);
/*  72 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/*  77 */     return Stream.concat(
/*  78 */         Stream.of(new ExaminableProperty[] {
/*  79 */             ExaminableProperty.of("nbtPath", this.nbtPath), 
/*  80 */             ExaminableProperty.of("interpret", this.interpret), 
/*  81 */             ExaminableProperty.of("separator", this.separator)
/*     */           
/*  83 */           }), super.examinablePropertiesWithoutChildren());
/*     */   }
/*     */   
/*     */   static abstract class BuilderImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> extends AbstractComponentBuilder<C, B> implements NBTComponentBuilder<C, B> {
/*     */     @Nullable
/*     */     protected String nbtPath;
/*     */     protected boolean interpret = false;
/*     */     @Nullable
/*     */     protected Component separator;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull C component) {
/*  96 */       super(component);
/*  97 */       this.nbtPath = component.nbtPath();
/*  98 */       this.interpret = component.interpret();
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public B nbtPath(@NotNull String nbtPath) {
/* 104 */       this.nbtPath = nbtPath;
/* 105 */       return (B)this;
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public B interpret(boolean interpret) {
/* 111 */       this.interpret = interpret;
/* 112 */       return (B)this;
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public B separator(@Nullable ComponentLike separator) {
/* 118 */       this.separator = ComponentLike.unbox(separator);
/* 119 */       return (B)this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\NBTComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */