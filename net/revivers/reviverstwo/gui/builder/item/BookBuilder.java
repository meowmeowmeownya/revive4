/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import net.revivers.reviverstwo.gui.components.util.Legacy;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
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
/*     */ public class BookBuilder
/*     */   extends BaseItemBuilder<BookBuilder>
/*     */ {
/*  48 */   private static final EnumSet<Material> BOOKS = EnumSet.of(Material.WRITABLE_BOOK, Material.WRITTEN_BOOK);
/*     */   
/*     */   BookBuilder(@NotNull ItemStack itemStack) {
/*  51 */     super(itemStack);
/*  52 */     if (!BOOKS.contains(itemStack.getType())) {
/*  53 */       throw new GuiException("BookBuilder requires the material to be a WRITABLE_BOOK/WRITTEN_BOOK!");
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
/*     */   public BookBuilder author(@Nullable Component author) {
/*  67 */     BookMeta bookMeta = (BookMeta)getMeta();
/*     */     
/*  69 */     if (author == null) {
/*  70 */       bookMeta.setAuthor(null);
/*  71 */       setMeta((ItemMeta)bookMeta);
/*  72 */       return this;
/*     */     } 
/*     */     
/*  75 */     bookMeta.setAuthor(Legacy.SERIALIZER.serialize(author));
/*  76 */     setMeta((ItemMeta)bookMeta);
/*  77 */     return this;
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
/*     */   public BookBuilder generation(@Nullable BookMeta.Generation generation) {
/*  90 */     BookMeta bookMeta = (BookMeta)getMeta();
/*     */     
/*  92 */     bookMeta.setGeneration(generation);
/*  93 */     setMeta((ItemMeta)bookMeta);
/*  94 */     return this;
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
/*     */   public BookBuilder page(@NotNull Component... pages) {
/* 108 */     return page(Arrays.asList(pages));
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
/*     */   public BookBuilder page(@NotNull List<Component> pages) {
/* 122 */     BookMeta bookMeta = (BookMeta)getMeta();
/*     */     
/* 124 */     for (Component page : pages) {
/* 125 */       bookMeta.addPage(new String[] { Legacy.SERIALIZER.serialize(page) });
/*     */     } 
/*     */     
/* 128 */     setMeta((ItemMeta)bookMeta);
/* 129 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_, _ -> this")
/*     */   public BookBuilder page(int page, @NotNull Component data) {
/* 149 */     BookMeta bookMeta = (BookMeta)getMeta();
/*     */     
/* 151 */     bookMeta.setPage(page, Legacy.SERIALIZER.serialize(data));
/* 152 */     setMeta((ItemMeta)bookMeta);
/* 153 */     return this;
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
/*     */   public BookBuilder title(@Nullable Component title) {
/* 168 */     BookMeta bookMeta = (BookMeta)getMeta();
/*     */     
/* 170 */     if (title == null) {
/* 171 */       bookMeta.setTitle(null);
/* 172 */       setMeta((ItemMeta)bookMeta);
/* 173 */       return this;
/*     */     } 
/*     */     
/* 176 */     bookMeta.setTitle(Legacy.SERIALIZER.serialize(title));
/* 177 */     setMeta((ItemMeta)bookMeta);
/* 178 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\BookBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */