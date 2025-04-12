/*     */ package net.revivers.reviverstwo.gui.components.nbt;
/*     */ 
/*     */ import org.bukkit.NamespacedKey;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.persistence.PersistentDataType;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
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
/*     */ 
/*     */ public final class Pdc
/*     */   implements NbtWrapper
/*     */ {
/*  44 */   private static final Plugin PLUGIN = (Plugin)JavaPlugin.getProvidingPlugin(Pdc.class);
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
/*  56 */     ItemMeta meta = itemStack.getItemMeta();
/*  57 */     if (meta == null) return itemStack; 
/*  58 */     meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING, value);
/*  59 */     itemStack.setItemMeta(meta);
/*  60 */     return itemStack;
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
/*  72 */     ItemMeta meta = itemStack.getItemMeta();
/*  73 */     if (meta == null) return itemStack; 
/*  74 */     meta.getPersistentDataContainer().remove(new NamespacedKey(PLUGIN, key));
/*  75 */     itemStack.setItemMeta(meta);
/*  76 */     return itemStack;
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
/*  90 */     ItemMeta meta = itemStack.getItemMeta();
/*  91 */     if (meta == null) return itemStack; 
/*  92 */     meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.BYTE, Byte.valueOf(value ? 1 : 0));
/*  93 */     itemStack.setItemMeta(meta);
/*  94 */     return itemStack;
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
/* 107 */     ItemMeta meta = itemStack.getItemMeta();
/* 108 */     if (meta == null) return null; 
/* 109 */     return (String)meta.getPersistentDataContainer().get(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\components\nbt\Pdc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */