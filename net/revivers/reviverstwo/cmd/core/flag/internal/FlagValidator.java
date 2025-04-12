/*    */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.exceptions.SubCommandRegistrationException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FlagValidator
/*    */ {
/*    */   private FlagValidator() {
/* 40 */     throw new AssertionError("Util class must not be initialized.");
/*    */   }
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
/*    */   public static void validate(@Nullable String flag, @NotNull Method method, @NotNull BaseCommand baseCommand) {
/* 54 */     if (flag == null) {
/*    */       return;
/*    */     }
/* 57 */     if (flag.length() == 1) {
/* 58 */       char character = flag.charAt(0);
/*    */       
/* 60 */       if (!isValidFlag(character)) {
/* 61 */         throw new SubCommandRegistrationException("Illegal flag name \"" + character + "\"", method, baseCommand.getClass());
/*    */       }
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 68 */     for (char character : flag.toCharArray()) {
/* 69 */       if (!isValidChar(character)) {
/* 70 */         throw new SubCommandRegistrationException("The flag \"" + flag + "\" contains an illegal character \"" + character + "\"", method, baseCommand
/*    */ 
/*    */             
/* 73 */             .getClass());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isValidFlag(char c) {
/* 86 */     return (isValidChar(c) || c == '?' || c == '@');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isValidChar(char c) {
/* 96 */     return Character.isJavaIdentifierPart(c);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */