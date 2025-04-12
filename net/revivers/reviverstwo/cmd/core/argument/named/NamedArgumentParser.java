/*    */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.PrimitiveIterator;
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
/*    */ public final class NamedArgumentParser
/*    */ {
/*    */   private static final char SPACE = ' ';
/*    */   private static final char ESCAPE = '\\';
/*    */   private static final char SEPARATOR = ':';
/*    */   
/*    */   public static Map<String, String> parse(@NotNull String literal) {
/* 39 */     PrimitiveIterator.OfInt iterator = literal.chars().iterator();
/*    */     
/* 41 */     Map<String, String> args = new LinkedHashMap<>();
/* 42 */     StringBuilder builder = new StringBuilder();
/*    */ 
/*    */     
/* 45 */     boolean escape = false;
/* 46 */     String argument = "";
/*    */     
/* 48 */     while (iterator.hasNext()) {
/* 49 */       int current = iterator.next().intValue();
/*    */ 
/*    */       
/* 52 */       if (current == 92 && !argument.isEmpty()) {
/* 53 */         escape = true;
/*    */         
/*    */         continue;
/*    */       } 
/*    */       
/* 58 */       if (current == 58 && argument.isEmpty()) {
/* 59 */         argument = builder.toString();
/* 60 */         builder.setLength(0);
/*    */ 
/*    */         
/*    */         continue;
/*    */       } 
/*    */       
/* 66 */       if (current == 32) {
/*    */         
/* 68 */         if (argument.isEmpty()) {
/* 69 */           builder.setLength(0);
/*    */           
/*    */           continue;
/*    */         } 
/*    */         
/* 74 */         args.put(argument, builder.toString());
/* 75 */         builder.setLength(0);
/* 76 */         argument = "";
/*    */         
/*    */         continue;
/*    */       } 
/*    */       
/* 81 */       if (escape) {
/* 82 */         builder.appendCodePoint(92);
/* 83 */         escape = false;
/*    */       } 
/*    */ 
/*    */       
/* 87 */       builder.appendCodePoint(current);
/*    */     } 
/*    */ 
/*    */     
/* 91 */     if (!argument.isEmpty()) args.put(argument, builder.toString());
/*    */     
/* 93 */     return args;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\NamedArgumentParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */