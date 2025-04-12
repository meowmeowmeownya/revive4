/*    */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FlagScanner
/*    */ {
/*    */   private final List<String> tokens;
/* 36 */   private int pointer = -1;
/*    */   
/* 38 */   private String current = null;
/*    */   
/*    */   public FlagScanner(@NotNull List<String> tokens) {
/* 41 */     this.tokens = tokens;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String peek() {
/* 50 */     return this.current;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 59 */     return (this.pointer < this.tokens.size() - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String next() {
/* 66 */     if (this.pointer < this.tokens.size()) this.pointer++; 
/* 67 */     setToken(this.tokens.get(this.pointer));
/* 68 */     return peek();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void previous() {
/* 75 */     if (this.pointer > 0) this.pointer--; 
/* 76 */     setToken(this.tokens.get(this.pointer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void setToken(@NotNull String token) {
/* 85 */     this.current = token;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */