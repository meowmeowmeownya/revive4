/*    */ package net.kyori.adventure.util;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.kyori.examination.Examiner;
/*    */ import net.kyori.examination.string.StringExaminer;
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
/*    */ final class HSVLikeImpl
/*    */   implements HSVLike
/*    */ {
/*    */   private final float h;
/*    */   private final float s;
/*    */   private final float v;
/*    */   
/*    */   HSVLikeImpl(float h, float s, float v) {
/* 36 */     this.h = h;
/* 37 */     this.s = s;
/* 38 */     this.v = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public float h() {
/* 43 */     return this.h;
/*    */   }
/*    */ 
/*    */   
/*    */   public float s() {
/* 48 */     return this.s;
/*    */   }
/*    */ 
/*    */   
/*    */   public float v() {
/* 53 */     return this.v;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 58 */     if (this == other) return true; 
/* 59 */     if (!(other instanceof HSVLikeImpl)) return false; 
/* 60 */     HSVLikeImpl that = (HSVLikeImpl)other;
/* 61 */     return (ShadyPines.equals(that.h, this.h) && ShadyPines.equals(that.s, this.s) && ShadyPines.equals(that.v, this.v));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return Objects.hash(new Object[] { Float.valueOf(this.h), Float.valueOf(this.s), Float.valueOf(this.v) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\HSVLikeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */