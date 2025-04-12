/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.components.util.VersionHelper;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Tag;
/*     */ import org.bukkit.block.banner.Pattern;
/*     */ import org.bukkit.block.banner.PatternType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BannerMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
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
/*     */ public final class BannerBuilder
/*     */   extends BaseItemBuilder<BannerBuilder>
/*     */ {
/*     */   private static final Material DEFAULT_BANNER;
/*     */   private static final EnumSet<Material> BANNERS;
/*     */   
/*     */   static {
/*  55 */     if (VersionHelper.IS_ITEM_LEGACY) {
/*  56 */       DEFAULT_BANNER = Material.valueOf("BANNER");
/*  57 */       BANNERS = EnumSet.of(Material.valueOf("BANNER"));
/*     */     } else {
/*  59 */       DEFAULT_BANNER = Material.WHITE_BANNER;
/*  60 */       BANNERS = EnumSet.copyOf(Tag.BANNERS.getValues());
/*     */     } 
/*     */   }
/*     */   
/*     */   BannerBuilder() {
/*  65 */     super(new ItemStack(DEFAULT_BANNER));
/*     */   }
/*     */   
/*     */   BannerBuilder(@NotNull ItemStack itemStack) {
/*  69 */     super(itemStack);
/*  70 */     if (!BANNERS.contains(itemStack.getType())) {
/*  71 */       throw new GuiException("BannerBuilder requires the material to be a banner!");
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
/*     */   @Contract("_ -> this")
/*     */   public BannerBuilder baseColor(@NotNull DyeColor color) {
/*  85 */     BannerMeta bannerMeta = (BannerMeta)getMeta();
/*     */     
/*  87 */     bannerMeta.setBaseColor(color);
/*  88 */     setMeta((ItemMeta)bannerMeta);
/*  89 */     return this;
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
/*     */   public BannerBuilder pattern(@NotNull DyeColor color, @NotNull PatternType pattern) {
/* 103 */     BannerMeta bannerMeta = (BannerMeta)getMeta();
/*     */     
/* 105 */     bannerMeta.addPattern(new Pattern(color, pattern));
/* 106 */     setMeta((ItemMeta)bannerMeta);
/* 107 */     return this;
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
/*     */   public BannerBuilder pattern(@NotNull Pattern... pattern) {
/* 120 */     return pattern(Arrays.asList(pattern));
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
/*     */   public BannerBuilder pattern(@NotNull List<Pattern> patterns) {
/* 133 */     BannerMeta bannerMeta = (BannerMeta)getMeta();
/*     */     
/* 135 */     for (Pattern it : patterns) {
/* 136 */       bannerMeta.addPattern(it);
/*     */     }
/*     */     
/* 139 */     setMeta((ItemMeta)bannerMeta);
/* 140 */     return this;
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
/*     */   @Contract("_, _, _ -> this")
/*     */   public BannerBuilder pattern(int index, @NotNull DyeColor color, @NotNull PatternType pattern) {
/* 156 */     return pattern(index, new Pattern(color, pattern));
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
/*     */   @Contract("_, _ -> this")
/*     */   public BannerBuilder pattern(int index, @NotNull Pattern pattern) {
/* 171 */     BannerMeta bannerMeta = (BannerMeta)getMeta();
/*     */     
/* 173 */     bannerMeta.setPattern(index, pattern);
/* 174 */     setMeta((ItemMeta)bannerMeta);
/* 175 */     return this;
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
/*     */   public BannerBuilder setPatterns(@NotNull List<Pattern> patterns) {
/* 188 */     BannerMeta bannerMeta = (BannerMeta)getMeta();
/*     */     
/* 190 */     bannerMeta.setPatterns(patterns);
/* 191 */     setMeta((ItemMeta)bannerMeta);
/* 192 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\BannerBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */