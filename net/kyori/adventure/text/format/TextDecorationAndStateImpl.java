/*    */ package net.kyori.adventure.text.format;
/*    */ 
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
/*    */ final class TextDecorationAndStateImpl
/*    */   implements TextDecorationAndState
/*    */ {
/*    */   private final TextDecoration decoration;
/*    */   private final TextDecoration.State state;
/*    */   
/*    */   TextDecorationAndStateImpl(TextDecoration decoration, TextDecoration.State state) {
/* 35 */     this.decoration = decoration;
/* 36 */     this.state = state;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public TextDecoration decoration() {
/* 41 */     return this.decoration;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextDecoration.State state() {
/* 46 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 56 */     if (this == other) return true; 
/* 57 */     if (other == null || getClass() != other.getClass()) return false; 
/* 58 */     TextDecorationAndStateImpl that = (TextDecorationAndStateImpl)other;
/* 59 */     return (this.decoration == that.decoration && this.state == that.state);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     int result = this.decoration.hashCode();
/* 65 */     result = 31 * result + this.state.hashCode();
/* 66 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\TextDecorationAndStateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */