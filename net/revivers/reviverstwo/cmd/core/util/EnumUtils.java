/*    */ package net.revivers.reviverstwo.cmd.core.util;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandRegistrationException;
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
/*    */ public final class EnumUtils
/*    */ {
/* 36 */   private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> ENUM_CONSTANT_CACHE = new WeakHashMap<>();
/*    */   private EnumUtils() {
/* 38 */     throw new AssertionError("Util must not be initialized");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(@NotNull Class<? extends Enum<?>> enumClass) {
/* 48 */     synchronized (ENUM_CONSTANT_CACHE) {
/* 49 */       Map<String, WeakReference<? extends Enum<?>>> constants = ENUM_CONSTANT_CACHE.get(enumClass);
/* 50 */       if (constants == null) constants = populateCache(enumClass); 
/* 51 */       return constants;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Map<String, WeakReference<? extends Enum<?>>> populateCache(@NotNull Class<? extends Enum<?>> enumClass) {
/* 63 */     Map<String, WeakReference<? extends Enum<?>>> result = new HashMap<>();
/*    */     
/* 65 */     for (Enum<?> enumInstance : (Enum[])enumClass.getEnumConstants()) {
/* 66 */       String name = enumInstance.name().toUpperCase();
/* 67 */       if (result.containsKey(name)) {
/* 68 */         throw new CommandRegistrationException("Provided enum \"" + enumClass
/* 69 */             .getSimpleName() + "\" has multiple values with the name \"" + name + "\"");
/*    */       }
/*    */       
/* 72 */       result.put(name, new WeakReference<>(enumInstance));
/*    */     } 
/*    */     
/* 75 */     ENUM_CONSTANT_CACHE.put(enumClass, result);
/* 76 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\cor\\util\EnumUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */