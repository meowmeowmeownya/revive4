/*    */ package net.kyori.adventure.sound;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Stream;
/*    */ import net.kyori.adventure.key.Key;
/*    */ import net.kyori.examination.ExaminableProperty;
/*    */ import net.kyori.examination.Examiner;
/*    */ import net.kyori.examination.string.StringExaminer;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class SoundStopImpl
/*    */   implements SoundStop
/*    */ {
/* 35 */   static final SoundStop ALL = new SoundStopImpl(null) {
/*    */       @Nullable
/*    */       public Key sound() {
/* 38 */         return null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   SoundStopImpl(Sound.Source source) {
/* 44 */     this.source = source;
/*    */   }
/*    */   private final Sound.Source source;
/*    */   
/*    */   public Sound.Source source() {
/* 49 */     return this.source;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 54 */     if (this == other) return true; 
/* 55 */     if (!(other instanceof SoundStopImpl)) return false; 
/* 56 */     SoundStopImpl that = (SoundStopImpl)other;
/* 57 */     return (Objects.equals(sound(), that.sound()) && 
/* 58 */       Objects.equals(this.source, that.source));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 63 */     int result = Objects.hashCode(sound());
/* 64 */     result = 31 * result + Objects.hashCode(this.source);
/* 65 */     return result;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 70 */     return Stream.of(new ExaminableProperty[] {
/* 71 */           ExaminableProperty.of("name", sound()), 
/* 72 */           ExaminableProperty.of("source", this.source)
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\sound\SoundStopImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */