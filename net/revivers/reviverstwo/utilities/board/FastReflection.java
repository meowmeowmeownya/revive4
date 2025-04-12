/*     */ package net.revivers.reviverstwo.utilities.board;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import org.bukkit.Bukkit;
/*     */ 
/*     */ 
/*     */ public final class FastReflection
/*     */ {
/*     */   private static final String NM_PACKAGE = "net.minecraft";
/*     */   public static final String OBC_PACKAGE = "org.bukkit.craftbukkit";
/*     */   public static final String NMS_PACKAGE = "net.minecraft.server";
/*  18 */   public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit".length() + 1);
/*     */   
/*  20 */   private static final MethodType VOID_METHOD_TYPE = MethodType.methodType(void.class);
/*  21 */   private static final boolean NMS_REPACKAGED = optionalClass("net.minecraft.network.protocol.Packet").isPresent();
/*     */   
/*     */   private static volatile Object theUnsafe;
/*     */   
/*     */   private FastReflection() {
/*  26 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public static boolean isRepackaged() {
/*  30 */     return NMS_REPACKAGED;
/*     */   }
/*     */   
/*     */   public static String nmsClassName(String post1_17package, String className) {
/*  34 */     if (NMS_REPACKAGED) {
/*  35 */       String classPackage = (post1_17package == null) ? "net.minecraft" : ("net.minecraft." + post1_17package);
/*  36 */       return classPackage + "." + classPackage;
/*     */     } 
/*  38 */     return "net.minecraft.server." + VERSION + "." + className;
/*     */   }
/*     */   
/*     */   public static Class<?> nmsClass(String post1_17package, String className) throws ClassNotFoundException {
/*  42 */     return Class.forName(nmsClassName(post1_17package, className));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> nmsOptionalClass(String post1_17package, String className) {
/*  46 */     return optionalClass(nmsClassName(post1_17package, className));
/*     */   }
/*     */   
/*     */   public static String obcClassName(String className) {
/*  50 */     return "org.bukkit.craftbukkit." + VERSION + "." + className;
/*     */   }
/*     */   
/*     */   public static Class<?> obcClass(String className) throws ClassNotFoundException {
/*  54 */     return Class.forName(obcClassName(className));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> obcOptionalClass(String className) {
/*  58 */     return optionalClass(obcClassName(className));
/*     */   }
/*     */   
/*     */   public static Optional<Class<?>> optionalClass(String className) {
/*     */     try {
/*  63 */       return Optional.of(Class.forName(className));
/*  64 */     } catch (ClassNotFoundException e) {
/*  65 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object enumValueOf(Class<?> enumClass, String enumName) {
/*  70 */     return Enum.valueOf((Class)enumClass.asSubclass(Enum.class), enumName);
/*     */   }
/*     */   
/*     */   public static Object enumValueOf(Class<?> enumClass, String enumName, int fallbackOrdinal) {
/*     */     try {
/*  75 */       return enumValueOf(enumClass, enumName);
/*  76 */     } catch (IllegalArgumentException e) {
/*  77 */       Object[] constants = enumClass.getEnumConstants();
/*  78 */       if (constants.length > fallbackOrdinal) {
/*  79 */         return constants[fallbackOrdinal];
/*     */       }
/*  81 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   static Class<?> innerClass(Class<?> parentClass, Predicate<Class<?>> classPredicate) throws ClassNotFoundException {
/*  86 */     for (Class<?> innerClass : parentClass.getDeclaredClasses()) {
/*  87 */       if (classPredicate.test(innerClass)) {
/*  88 */         return innerClass;
/*     */       }
/*     */     } 
/*  91 */     throw new ClassNotFoundException("No class in " + parentClass.getCanonicalName() + " matches the predicate.");
/*     */   }
/*     */   
/*     */   public static PacketConstructor findPacketConstructor(Class<?> packetClass, MethodHandles.Lookup lookup) throws Exception {
/*     */     try {
/*  96 */       MethodHandle constructor = lookup.findConstructor(packetClass, VOID_METHOD_TYPE);
/*  97 */       Objects.requireNonNull(constructor); return constructor::invoke;
/*  98 */     } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {
/*     */ 
/*     */ 
/*     */       
/* 102 */       if (theUnsafe == null) {
/* 103 */         synchronized (FastReflection.class) {
/* 104 */           if (theUnsafe == null) {
/* 105 */             Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
/* 106 */             Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
/* 107 */             theUnsafeField.setAccessible(true);
/* 108 */             theUnsafe = theUnsafeField.get((Object)null);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 113 */       MethodType allocateMethodType = MethodType.methodType(Object.class, Class.class);
/* 114 */       MethodHandle allocateMethod = lookup.findVirtual(theUnsafe.getClass(), "allocateInstance", allocateMethodType);
/* 115 */       return () -> allocateMethod.invoke(theUnsafe, packetClass);
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface PacketConstructor {
/*     */     Object invoke() throws Throwable;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\board\FastReflection.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */