/*     */ package net.revivers.reviverstwo.gui.components.util;
/*     */ 
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public final class VersionHelper
/*     */ {
/*  40 */   private static final String NMS_VERSION = getNmsVersion();
/*     */ 
/*     */   
/*     */   private static final int V1_11 = 1110;
/*     */   
/*     */   private static final int V1_13 = 1130;
/*     */   
/*     */   private static final int V1_14 = 1140;
/*     */   
/*     */   private static final int V1_16_5 = 1165;
/*     */   
/*     */   private static final int V1_12 = 1120;
/*     */   
/*  53 */   private static final int CURRENT_VERSION = getCurrentVersion();
/*     */   
/*  55 */   private static final boolean IS_PAPER = checkPaper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final boolean IS_COMPONENT_LEGACY = (!IS_PAPER || CURRENT_VERSION < 1165);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final boolean IS_ITEM_LEGACY = (CURRENT_VERSION < 1130);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final boolean IS_UNBREAKABLE_LEGACY = (CURRENT_VERSION < 1110);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final boolean IS_PDC_VERSION = (CURRENT_VERSION >= 1140);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final boolean IS_SKULL_OWNER_LEGACY = (CURRENT_VERSION < 1120);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final boolean IS_CUSTOM_MODEL_DATA = (CURRENT_VERSION >= 1140);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkPaper() {
/*     */     try {
/*  98 */       Class.forName("com.destroystokyo.paper.PaperConfig");
/*  99 */       return true;
/* 100 */     } catch (ClassNotFoundException ignored) {
/* 101 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getCurrentVersion() {
/* 112 */     Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());
/*     */     
/* 114 */     StringBuilder stringBuilder = new StringBuilder();
/* 115 */     if (matcher.find()) {
/* 116 */       stringBuilder.append(matcher.group("version").replace(".", ""));
/* 117 */       String patch = matcher.group("patch");
/* 118 */       if (patch == null) { stringBuilder.append("0"); }
/* 119 */       else { stringBuilder.append(patch.replace(".", "")); }
/*     */     
/*     */     } 
/*     */     
/* 123 */     Integer version = Ints.tryParse(stringBuilder.toString());
/*     */ 
/*     */     
/* 126 */     if (version == null) throw new GuiException("Could not retrieve server version!");
/*     */     
/* 128 */     return version.intValue();
/*     */   }
/*     */   
/*     */   private static String getNmsVersion() {
/* 132 */     String version = Bukkit.getServer().getClass().getPackage().getName();
/* 133 */     return version.substring(version.lastIndexOf('.') + 1);
/*     */   }
/*     */   
/*     */   public static Class<?> craftClass(@NotNull String name) throws ClassNotFoundException {
/* 137 */     return Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + "." + name);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\component\\util\VersionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */