/*    */ package net.kyori.adventure.nbt.api;
/*    */ 
/*    */ import net.kyori.adventure.util.Codec;
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
/*    */ public interface BinaryTagHolder
/*    */ {
/*    */   @NotNull
/*    */   static <T, EX extends Exception> BinaryTagHolder encode(@NotNull T nbt, @NotNull Codec<? super T, String, ?, EX> codec) throws EX {
/* 52 */     return new BinaryTagHolderImpl((String)codec.encode(nbt));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static BinaryTagHolder of(@NotNull String string) {
/* 63 */     return new BinaryTagHolderImpl(string);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   String string();
/*    */   
/*    */   @NotNull
/*    */   <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> paramCodec) throws DX;
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\nbt\api\BinaryTagHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */