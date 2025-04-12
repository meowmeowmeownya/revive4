/*    */ package net.revivers.reviverstwo.cmd.core.argument;
/*    */ 
/*    */ import com.google.common.primitives.Doubles;
/*    */ import com.google.common.primitives.Floats;
/*    */ import com.google.common.primitives.Ints;
/*    */ import com.google.common.primitives.Longs;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.Registry;
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
/*    */ 
/*    */ 
/*    */ public final class ArgumentRegistry<S>
/*    */   implements Registry
/*    */ {
/* 46 */   private final Map<Class<?>, ArgumentResolver<S>> arguments = new HashMap<>();
/*    */ 
/*    */   
/*    */   public ArgumentRegistry() {
/* 50 */     register(short.class, (sender, arg) -> Ints.tryParse(arg));
/* 51 */     register(Short.class, (sender, arg) -> Ints.tryParse(arg));
/*    */     
/* 53 */     register(int.class, (sender, arg) -> Ints.tryParse(arg));
/* 54 */     register(Integer.class, (sender, arg) -> Ints.tryParse(arg));
/*    */     
/* 56 */     register(long.class, (sender, arg) -> Longs.tryParse(arg));
/* 57 */     register(Long.class, (sender, arg) -> Longs.tryParse(arg));
/*    */     
/* 59 */     register(float.class, (sender, arg) -> Floats.tryParse(arg));
/* 60 */     register(Float.class, (sender, arg) -> Floats.tryParse(arg));
/*    */     
/* 62 */     register(double.class, (sender, arg) -> Doubles.tryParse(arg));
/* 63 */     register(Double.class, (sender, arg) -> Doubles.tryParse(arg));
/*    */     
/* 65 */     register(boolean.class, (sender, arg) -> Boolean.valueOf(arg));
/* 66 */     register(Boolean.class, (sender, arg) -> Boolean.valueOf(arg));
/*    */     
/* 68 */     register(String.class, (sender, arg) -> arg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(@NotNull Class<?> clazz, @NotNull ArgumentResolver<S> argument) {
/* 78 */     this.arguments.put(clazz, argument);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ArgumentResolver<S> getResolver(@NotNull Class<?> clazz) {
/* 88 */     return this.arguments.get(clazz);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\ArgumentRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */