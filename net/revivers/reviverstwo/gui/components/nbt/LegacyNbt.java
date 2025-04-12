/*     */ package net.revivers.reviverstwo.gui.components.nbt;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Objects;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LegacyNbt
/*     */   implements NbtWrapper
/*     */ {
/*  43 */   public static final String PACKAGE_NAME = Bukkit.getServer().getClass().getPackage().getName();
/*  44 */   public static final String NMS_VERSION = PACKAGE_NAME.substring(PACKAGE_NAME.lastIndexOf('.') + 1);
/*     */   
/*     */   private static Method getStringMethod;
/*     */   
/*     */   private static Method setStringMethod;
/*     */   private static Method setBooleanMethod;
/*     */   private static Method hasTagMethod;
/*     */   private static Method getTagMethod;
/*     */   private static Method setTagMethod;
/*     */   private static Method removeTagMethod;
/*     */   private static Method asNMSCopyMethod;
/*     */   private static Method asBukkitCopyMethod;
/*     */   private static Constructor<?> nbtCompoundConstructor;
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       getStringMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("NBTTagCompound"))).getMethod("getString", new Class[] { String.class });
/*  61 */       removeTagMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("NBTTagCompound"))).getMethod("remove", new Class[] { String.class });
/*  62 */       setStringMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("NBTTagCompound"))).getMethod("setString", new Class[] { String.class, String.class });
/*  63 */       setBooleanMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("NBTTagCompound"))).getMethod("setBoolean", new Class[] { String.class, boolean.class });
/*  64 */       hasTagMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("ItemStack"))).getMethod("hasTag", new Class[0]);
/*  65 */       getTagMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("ItemStack"))).getMethod("getTag", new Class[0]);
/*  66 */       setTagMethod = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("ItemStack"))).getMethod("setTag", new Class[] { getNMSClass("NBTTagCompound") });
/*  67 */       nbtCompoundConstructor = ((Class)Objects.<Class<?>>requireNonNull(getNMSClass("NBTTagCompound"))).getDeclaredConstructor(new Class[0]);
/*  68 */       asNMSCopyMethod = ((Class)Objects.<Class<?>>requireNonNull(getCraftItemStackClass())).getMethod("asNMSCopy", new Class[] { ItemStack.class });
/*  69 */       asBukkitCopyMethod = ((Class)Objects.<Class<?>>requireNonNull(getCraftItemStackClass())).getMethod("asBukkitCopy", new Class[] { getNMSClass("ItemStack") });
/*  70 */     } catch (NoSuchMethodException e) {
/*  71 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setString(@NotNull ItemStack itemStack, String key, String value) {
/*  85 */     if (itemStack.getType() == Material.AIR) return itemStack;
/*     */     
/*  87 */     Object nmsItemStack = asNMSCopy(itemStack);
/*  88 */     Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();
/*     */     
/*  90 */     setString(itemCompound, key, value);
/*  91 */     setTag(nmsItemStack, itemCompound);
/*     */     
/*  93 */     return asBukkitCopy(nmsItemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeTag(@NotNull ItemStack itemStack, String key) {
/* 105 */     if (itemStack.getType() == Material.AIR) return itemStack;
/*     */     
/* 107 */     Object nmsItemStack = asNMSCopy(itemStack);
/* 108 */     Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();
/*     */     
/* 110 */     remove(itemCompound, key);
/* 111 */     setTag(nmsItemStack, itemCompound);
/*     */     
/* 113 */     return asBukkitCopy(nmsItemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack setBoolean(@NotNull ItemStack itemStack, String key, boolean value) {
/* 127 */     if (itemStack.getType() == Material.AIR) return itemStack;
/*     */     
/* 129 */     Object nmsItemStack = asNMSCopy(itemStack);
/* 130 */     Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();
/*     */     
/* 132 */     setBoolean(itemCompound, key, value);
/* 133 */     setTag(nmsItemStack, itemCompound);
/*     */     
/* 135 */     return asBukkitCopy(nmsItemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getString(@NotNull ItemStack itemStack, String key) {
/* 148 */     if (itemStack.getType() == Material.AIR) return null;
/*     */     
/* 150 */     Object nmsItemStack = asNMSCopy(itemStack);
/* 151 */     Object itemCompound = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();
/*     */     
/* 153 */     return getString(itemCompound, key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setString(Object itemCompound, String key, String value) {
/*     */     try {
/* 165 */       setStringMethod.invoke(itemCompound, new Object[] { key, value });
/* 166 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setBoolean(Object itemCompound, String key, boolean value) {
/*     */     try {
/* 172 */       setBooleanMethod.invoke(itemCompound, new Object[] { key, Boolean.valueOf(value) });
/* 173 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void remove(Object itemCompound, String key) {
/*     */     try {
/* 185 */       removeTagMethod.invoke(itemCompound, new Object[] { key });
/* 186 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getString(Object itemCompound, String key) {
/*     */     try {
/* 199 */       return (String)getStringMethod.invoke(itemCompound, new Object[] { key });
/* 200 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 201 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasTag(Object nmsItemStack) {
/*     */     try {
/* 213 */       return ((Boolean)hasTagMethod.invoke(nmsItemStack, new Object[0])).booleanValue();
/* 214 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 215 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getTag(Object nmsItemStack) {
/*     */     try {
/* 227 */       return getTagMethod.invoke(nmsItemStack, new Object[0]);
/* 228 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 229 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setTag(Object nmsItemStack, Object itemCompound) {
/*     */     try {
/* 241 */       setTagMethod.invoke(nmsItemStack, new Object[] { itemCompound });
/* 242 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object newNBTTagCompound() {
/*     */     try {
/* 253 */       return nbtCompoundConstructor.newInstance(new Object[0]);
/* 254 */     } catch (IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException e) {
/* 255 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object asNMSCopy(ItemStack itemStack) {
/*     */     try {
/* 267 */       return asNMSCopyMethod.invoke(null, new Object[] { itemStack });
/* 268 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 269 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack asBukkitCopy(Object nmsItemStack) {
/*     */     try {
/* 281 */       return (ItemStack)asBukkitCopyMethod.invoke(null, new Object[] { nmsItemStack });
/* 282 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 283 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getNMSClass(String className) {
/*     */     try {
/* 294 */       return Class.forName("net.minecraft.server." + NMS_VERSION + "." + className);
/* 295 */     } catch (ClassNotFoundException e) {
/* 296 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getCraftItemStackClass() {
/*     */     try {
/* 307 */       return Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".inventory.CraftItemStack");
/* 308 */     } catch (ClassNotFoundException e) {
/* 309 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\components\nbt\LegacyNbt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */