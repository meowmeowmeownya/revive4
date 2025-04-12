/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*     */ import net.revivers.reviverstwo.gui.components.GuiAction;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.components.util.ItemNbt;
/*     */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*     */ import net.revivers.reviverstwo.gui.components.util.VersionHelper;
/*     */ import net.revivers.reviverstwo.gui.guis.GuiItem;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.persistence.PersistentDataContainer;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ 
/*     */ public abstract class BaseItemBuilder<B extends BaseItemBuilder<B>>
/*     */ {
/*  66 */   private static final EnumSet<Material> LEATHER_ARMOR = EnumSet.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();
/*     */   private static final Field DISPLAY_NAME_FIELD;
/*     */   private static final Field LORE_FIELD;
/*     */   
/*     */   static {
/*     */     try {
/*  76 */       Class<?> metaClass = VersionHelper.craftClass("inventory.CraftMetaItem");
/*     */       
/*  78 */       DISPLAY_NAME_FIELD = metaClass.getDeclaredField("displayName");
/*  79 */       DISPLAY_NAME_FIELD.setAccessible(true);
/*     */       
/*  81 */       LORE_FIELD = metaClass.getDeclaredField("lore");
/*  82 */       LORE_FIELD.setAccessible(true);
/*  83 */     } catch (NoSuchFieldException|ClassNotFoundException exception) {
/*  84 */       exception.printStackTrace();
/*  85 */       throw new GuiException("Could not retrieve displayName nor lore field for ItemBuilder.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack itemStack;
/*     */   private ItemMeta meta;
/*     */   
/*     */   protected BaseItemBuilder(@NotNull ItemStack itemStack) {
/*  93 */     Validate.notNull(itemStack, "Item can't be null!");
/*     */     
/*  95 */     this.itemStack = itemStack;
/*  96 */     this.meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
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
/*     */   @Contract("_ -> this")
/*     */   public B name(@NotNull Component name) {
/* 109 */     if (this.meta == null) return (B)this;
/*     */     
/* 111 */     if (VersionHelper.IS_ITEM_LEGACY) {
/* 112 */       this.meta.setDisplayName(Legacy.SERIALIZER.serialize(name));
/* 113 */       return (B)this;
/*     */     } 
/*     */     
/*     */     try {
/* 117 */       DISPLAY_NAME_FIELD.set(this.meta, GSON.serialize(name));
/* 118 */     } catch (IllegalAccessException exception) {
/* 119 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 122 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B amount(int amount) {
/* 135 */     this.itemStack.setAmount(amount);
/* 136 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B lore(@Nullable Component... lore) {
/* 149 */     return lore(Arrays.asList(lore));
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
/*     */   @Contract("_ -> this")
/*     */   public B lore(@NotNull List<Component> lore) {
/* 162 */     if (this.meta == null) return (B)this;
/*     */     
/* 164 */     if (VersionHelper.IS_ITEM_LEGACY) {
/* 165 */       Objects.requireNonNull(Legacy.SERIALIZER); this.meta.setLore((List)lore.stream().filter(Objects::nonNull).map(Legacy.SERIALIZER::serialize).collect(Collectors.toList()));
/* 166 */       return (B)this;
/*     */     } 
/*     */     
/* 169 */     Objects.requireNonNull(GSON); List<String> jsonLore = (List<String>)lore.stream().filter(Objects::nonNull).map(GSON::serialize).collect(Collectors.toList());
/*     */     
/*     */     try {
/* 172 */       LORE_FIELD.set(this.meta, jsonLore);
/* 173 */     } catch (IllegalAccessException exception) {
/* 174 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 177 */     return (B)this;
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
/*     */   public B lore(@NotNull Consumer<List<Component>> lore) {
/*     */     List<Component> components;
/* 190 */     if (this.meta == null) return (B)this;
/*     */ 
/*     */     
/* 193 */     if (VersionHelper.IS_ITEM_LEGACY) {
/* 194 */       List<String> stringLore = this.meta.getLore();
/* 195 */       if (stringLore == null) return (B)this; 
/* 196 */       Objects.requireNonNull(Legacy.SERIALIZER); components = (List<Component>)stringLore.stream().map(Legacy.SERIALIZER::deserialize).collect(Collectors.toList());
/*     */     } else {
/*     */       try {
/* 199 */         List<String> jsonLore = (List<String>)LORE_FIELD.get(this.meta);
/* 200 */         Objects.requireNonNull(GSON); components = (List<Component>)jsonLore.stream().map(GSON::deserialize).collect(Collectors.toList());
/* 201 */       } catch (IllegalAccessException exception) {
/* 202 */         components = new ArrayList<>();
/* 203 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     lore.accept(components);
/* 208 */     return lore(components);
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
/*     */   @NotNull
/*     */   @Contract("_, _, _ -> this")
/*     */   public B enchant(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
/* 223 */     this.meta.addEnchant(enchantment, level, ignoreLevelRestriction);
/* 224 */     return (B)this;
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
/*     */   @Contract("_, _ -> this")
/*     */   public B enchant(@NotNull Enchantment enchantment, int level) {
/* 238 */     return enchant(enchantment, level, true);
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
/*     */   @Contract("_ -> this")
/*     */   public B enchant(@NotNull Enchantment enchantment) {
/* 251 */     return enchant(enchantment, 1, true);
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
/*     */   @Contract("_ -> this")
/*     */   public B disenchant(@NotNull Enchantment enchantment) {
/* 264 */     this.itemStack.removeEnchantment(enchantment);
/* 265 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B flags(@NotNull ItemFlag... flags) {
/* 278 */     this.meta.addItemFlags(flags);
/* 279 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> this")
/*     */   public B unbreakable() {
/* 291 */     return unbreakable(true);
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
/*     */   public B unbreakable(boolean unbreakable) {
/* 303 */     if (VersionHelper.IS_UNBREAKABLE_LEGACY) {
/* 304 */       return setNbt("Unbreakable", unbreakable);
/*     */     }
/*     */     
/* 307 */     this.meta.setUnbreakable(unbreakable);
/* 308 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> this")
/*     */   public B glow() {
/* 320 */     return glow(true);
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
/*     */   public B glow(boolean glow) {
/* 332 */     if (glow) {
/* 333 */       this.meta.addEnchant(Enchantment.LURE, 1, false);
/* 334 */       this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
/* 335 */       return (B)this;
/*     */     } 
/*     */     
/* 338 */     for (Enchantment enchantment : this.meta.getEnchants().keySet()) {
/* 339 */       this.meta.removeEnchant(enchantment);
/*     */     }
/*     */     
/* 342 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B pdc(@NotNull Consumer<PersistentDataContainer> consumer) {
/* 356 */     consumer.accept(this.meta.getPersistentDataContainer());
/* 357 */     return (B)this;
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
/*     */   @Contract("_ -> this")
/*     */   public B model(int modelData) {
/* 371 */     if (VersionHelper.IS_CUSTOM_MODEL_DATA) {
/* 372 */       this.meta.setCustomModelData(Integer.valueOf(modelData));
/*     */     }
/*     */     
/* 375 */     return (B)this;
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
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public B color(@NotNull Color color) {
/* 390 */     if (LEATHER_ARMOR.contains(this.itemStack.getType())) {
/* 391 */       LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)getMeta();
/*     */       
/* 393 */       leatherArmorMeta.setColor(color);
/* 394 */       setMeta((ItemMeta)leatherArmorMeta);
/*     */     } 
/*     */     
/* 397 */     return (B)this;
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
/*     */   public B setNbt(@NotNull String key, @NotNull String value) {
/* 410 */     this.itemStack.setItemMeta(this.meta);
/* 411 */     this.itemStack = ItemNbt.setString(this.itemStack, key, value);
/* 412 */     this.meta = this.itemStack.getItemMeta();
/* 413 */     return (B)this;
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
/*     */   public B setNbt(@NotNull String key, boolean value) {
/* 426 */     this.itemStack.setItemMeta(this.meta);
/* 427 */     this.itemStack = ItemNbt.setBoolean(this.itemStack, key, value);
/* 428 */     this.meta = this.itemStack.getItemMeta();
/* 429 */     return (B)this;
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
/*     */   public B removeNbt(@NotNull String key) {
/* 441 */     this.itemStack.setItemMeta(this.meta);
/* 442 */     this.itemStack = ItemNbt.removeTag(this.itemStack, key);
/* 443 */     this.meta = this.itemStack.getItemMeta();
/* 444 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ItemStack build() {
/* 454 */     this.itemStack.setItemMeta(this.meta);
/* 455 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(" -> new")
/*     */   public GuiItem asGuiItem() {
/* 466 */     return new GuiItem(build());
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
/*     */   public GuiItem asGuiItem(@NotNull GuiAction<InventoryClickEvent> action) {
/* 478 */     return new GuiItem(build(), action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected ItemStack getItemStack() {
/* 488 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setItemStack(@NotNull ItemStack itemStack) {
/* 497 */     this.itemStack = itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected ItemMeta getMeta() {
/* 507 */     return this.meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setMeta(@NotNull ItemMeta meta) {
/* 516 */     this.meta = meta;
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
/*     */   @Deprecated
/*     */   public B setName(@NotNull String name) {
/* 531 */     getMeta().setDisplayName(name);
/* 532 */     return (B)this;
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
/*     */   public B setAmount(int amount) {
/* 544 */     getItemStack().setAmount(amount);
/* 545 */     return (B)this;
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
/*     */   public B addLore(@NotNull String... lore) {
/* 557 */     return addLore(Arrays.asList(lore));
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
/*     */   public B addLore(@NotNull List<String> lore) {
/* 569 */     List<String> newLore = getMeta().hasLore() ? getMeta().getLore() : new ArrayList<>();
/*     */     
/* 571 */     newLore.addAll(lore);
/* 572 */     return setLore(newLore);
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
/*     */   public B setLore(@NotNull String... lore) {
/* 584 */     return setLore(Arrays.asList(lore));
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
/*     */   public B setLore(@NotNull List<String> lore) {
/* 596 */     getMeta().setLore(lore);
/* 597 */     return (B)this;
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
/*     */   @Deprecated
/*     */   public B addEnchantment(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
/* 611 */     getMeta().addEnchant(enchantment, level, ignoreLevelRestriction);
/* 612 */     return (B)this;
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
/*     */   @Deprecated
/*     */   public B addEnchantment(@NotNull Enchantment enchantment, int level) {
/* 625 */     return addEnchantment(enchantment, level, true);
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
/*     */   public B addEnchantment(@NotNull Enchantment enchantment) {
/* 637 */     return addEnchantment(enchantment, 1, true);
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
/*     */   public B removeEnchantment(@NotNull Enchantment enchantment) {
/* 649 */     getItemStack().removeEnchantment(enchantment);
/* 650 */     return (B)this;
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
/*     */   public B addItemFlags(@NotNull ItemFlag... flags) {
/* 662 */     getMeta().addItemFlags(flags);
/* 663 */     return (B)this;
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
/*     */   public B setUnbreakable(boolean unbreakable) {
/* 675 */     return unbreakable(unbreakable);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\BaseItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */