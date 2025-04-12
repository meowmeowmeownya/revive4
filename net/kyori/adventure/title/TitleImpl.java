/*     */ package net.kyori.adventure.title;
/*     */ 
/*     */ import java.time.Duration;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.Component;
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
/*     */ final class TitleImpl
/*     */   implements Title
/*     */ {
/*     */   private final Component title;
/*     */   private final Component subtitle;
/*     */   @Nullable
/*     */   private final Title.Times times;
/*     */   
/*     */   TitleImpl(@NotNull Component title, @NotNull Component subtitle, @Nullable Title.Times times) {
/*  42 */     this.title = title;
/*  43 */     this.subtitle = subtitle;
/*  44 */     this.times = times;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component title() {
/*  49 */     return this.title;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component subtitle() {
/*  54 */     return this.subtitle;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Title.Times times() {
/*  59 */     return this.times;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T part(@NotNull TitlePart<T> part) {
/*  65 */     if (part == TitlePart.TITLE)
/*  66 */       return (T)this.title; 
/*  67 */     if (part == TitlePart.SUBTITLE)
/*  68 */       return (T)this.subtitle; 
/*  69 */     if (part == TitlePart.TIMES) {
/*  70 */       return (T)this.times;
/*     */     }
/*     */     
/*  73 */     throw new IllegalArgumentException("Don't know what " + part + " is.");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  78 */     if (this == other) return true; 
/*  79 */     if (other == null || getClass() != other.getClass()) return false; 
/*  80 */     TitleImpl that = (TitleImpl)other;
/*  81 */     return (this.title.equals(that.title) && this.subtitle
/*  82 */       .equals(that.subtitle) && 
/*  83 */       Objects.equals(this.times, that.times));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     int result = this.title.hashCode();
/*  89 */     result = 31 * result + this.subtitle.hashCode();
/*  90 */     result = 31 * result + Objects.hashCode(this.times);
/*  91 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  96 */     return Stream.of(new ExaminableProperty[] {
/*  97 */           ExaminableProperty.of("title", this.title), 
/*  98 */           ExaminableProperty.of("subtitle", this.subtitle), 
/*  99 */           ExaminableProperty.of("times", this.times)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */   
/*     */   static class TimesImpl implements Title.Times {
/*     */     private final Duration fadeIn;
/*     */     private final Duration stay;
/*     */     private final Duration fadeOut;
/*     */     
/*     */     TimesImpl(Duration fadeIn, Duration stay, Duration fadeOut) {
/* 114 */       this.fadeIn = fadeIn;
/* 115 */       this.stay = stay;
/* 116 */       this.fadeOut = fadeOut;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Duration fadeIn() {
/* 121 */       return this.fadeIn;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Duration stay() {
/* 126 */       return this.stay;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Duration fadeOut() {
/* 131 */       return this.fadeOut;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object other) {
/* 136 */       if (this == other) return true; 
/* 137 */       if (other == null || getClass() != other.getClass()) return false; 
/* 138 */       TimesImpl that = (TimesImpl)other;
/* 139 */       return (this.fadeIn.equals(that.fadeIn) && this.stay
/* 140 */         .equals(that.stay) && this.fadeOut
/* 141 */         .equals(that.fadeOut));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 146 */       int result = this.fadeIn.hashCode();
/* 147 */       result = 31 * result + this.stay.hashCode();
/* 148 */       result = 31 * result + this.fadeOut.hashCode();
/* 149 */       return result;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 154 */       return Stream.of(new ExaminableProperty[] {
/* 155 */             ExaminableProperty.of("fadeIn", this.fadeIn), 
/* 156 */             ExaminableProperty.of("stay", this.stay), 
/* 157 */             ExaminableProperty.of("fadeOut", this.fadeOut)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 163 */       return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\title\TitleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */