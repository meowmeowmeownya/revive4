/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.UUID;
/*     */ import net.revivers.reviverstwo.gui.components.util.SkullUtil;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ 
/*     */ 
/*     */ public class ItemBuilder
/*     */   extends BaseItemBuilder<ItemBuilder>
/*     */ {
/*     */   ItemBuilder(@NotNull ItemStack itemStack) {
/*  50 */     super(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static ItemBuilder from(@NotNull ItemStack itemStack) {
/*  62 */     return new ItemBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static ItemBuilder from(@NotNull Material material) {
/*  75 */     return new ItemBuilder(new ItemStack(material));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static BannerBuilder banner() {
/*  87 */     return new BannerBuilder();
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
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static BannerBuilder banner(@NotNull ItemStack itemStack) {
/* 101 */     return new BannerBuilder(itemStack);
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
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static BookBuilder book(@NotNull ItemStack itemStack) {
/* 117 */     return new BookBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static FireworkBuilder firework() {
/* 129 */     return new FireworkBuilder(new ItemStack(Material.FIREWORK_ROCKET));
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
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static FireworkBuilder firework(@NotNull ItemStack itemStack) {
/* 143 */     return new FireworkBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static MapBuilder map() {
/* 155 */     return new MapBuilder();
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
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static MapBuilder map(@NotNull ItemStack itemStack) {
/* 169 */     return new MapBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static SkullBuilder skull() {
/* 180 */     return new SkullBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static SkullBuilder skull(@NotNull ItemStack itemStack) {
/* 193 */     return new SkullBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public static FireworkBuilder star() {
/* 205 */     return new FireworkBuilder(new ItemStack(Material.FIREWORK_STAR));
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
/*     */   @NotNull
/*     */   @Contract("_ -> new")
/*     */   public static FireworkBuilder star(@NotNull ItemStack itemStack) {
/* 219 */     return new FireworkBuilder(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemBuilder setSkullTexture(@NotNull String texture) {
/* 231 */     if (!SkullUtil.isPlayerSkull(getItemStack())) return this;
/*     */     
/* 233 */     SkullMeta skullMeta = (SkullMeta)getMeta();
/* 234 */     GameProfile profile = new GameProfile(UUID.randomUUID(), null);
/* 235 */     profile.getProperties().put("textures", new Property("textures", texture));
/*     */ 
/*     */     
/*     */     try {
/* 239 */       Field profileField = skullMeta.getClass().getDeclaredField("profile");
/* 240 */       profileField.setAccessible(true);
/* 241 */       profileField.set(skullMeta, profile);
/* 242 */     } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException ex) {
/* 243 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 246 */     setMeta((ItemMeta)skullMeta);
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemBuilder setSkullOwner(@NotNull OfflinePlayer player) {
/* 259 */     if (!SkullUtil.isPlayerSkull(getItemStack())) return this;
/*     */     
/* 261 */     SkullMeta skullMeta = (SkullMeta)getMeta();
/* 262 */     skullMeta.setOwningPlayer(player);
/*     */     
/* 264 */     setMeta((ItemMeta)skullMeta);
/* 265 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */