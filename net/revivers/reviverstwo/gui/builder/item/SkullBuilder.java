/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.UUID;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.components.util.SkullUtil;
/*     */ import net.revivers.reviverstwo.gui.components.util.VersionHelper;
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
/*     */ public final class SkullBuilder
/*     */   extends BaseItemBuilder<SkullBuilder>
/*     */ {
/*     */   private static final Field PROFILE_FIELD;
/*     */   
/*     */   static {
/*     */     Field field;
/*     */     try {
/*  52 */       SkullMeta skullMeta = (SkullMeta)SkullUtil.skull().getItemMeta();
/*  53 */       field = skullMeta.getClass().getDeclaredField("profile");
/*  54 */       field.setAccessible(true);
/*  55 */     } catch (NoSuchFieldException e) {
/*  56 */       e.printStackTrace();
/*  57 */       field = null;
/*     */     } 
/*     */     
/*  60 */     PROFILE_FIELD = field;
/*     */   }
/*     */   
/*     */   SkullBuilder() {
/*  64 */     super(SkullUtil.skull());
/*     */   }
/*     */   
/*     */   SkullBuilder(@NotNull ItemStack itemStack) {
/*  68 */     super(itemStack);
/*  69 */     if (!SkullUtil.isPlayerSkull(itemStack)) {
/*  70 */       throw new GuiException("SkullBuilder requires the material to be a PLAYER_HEAD/SKULL_ITEM!");
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
/*     */   @NotNull
/*     */   @Contract("_, _ -> this")
/*     */   public SkullBuilder texture(@NotNull String texture, @NotNull UUID profileId) {
/*  84 */     if (!SkullUtil.isPlayerSkull(getItemStack())) return this;
/*     */     
/*  86 */     if (PROFILE_FIELD == null) {
/*  87 */       return this;
/*     */     }
/*     */     
/*  90 */     SkullMeta skullMeta = (SkullMeta)getMeta();
/*  91 */     GameProfile profile = new GameProfile(profileId, null);
/*  92 */     profile.getProperties().put("textures", new Property("textures", texture));
/*     */     
/*     */     try {
/*  95 */       PROFILE_FIELD.set(skullMeta, profile);
/*  96 */     } catch (IllegalArgumentException|IllegalAccessException ex) {
/*  97 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 100 */     setMeta((ItemMeta)skullMeta);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public SkullBuilder texture(@NotNull String texture) {
/* 113 */     return texture(texture, UUID.randomUUID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public SkullBuilder owner(@NotNull OfflinePlayer player) {
/* 125 */     if (!SkullUtil.isPlayerSkull(getItemStack())) return this;
/*     */     
/* 127 */     SkullMeta skullMeta = (SkullMeta)getMeta();
/*     */     
/* 129 */     if (VersionHelper.IS_SKULL_OWNER_LEGACY) {
/* 130 */       skullMeta.setOwner(player.getName());
/*     */     } else {
/* 132 */       skullMeta.setOwningPlayer(player);
/*     */     } 
/*     */     
/* 135 */     setMeta((ItemMeta)skullMeta);
/* 136 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\SkullBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */