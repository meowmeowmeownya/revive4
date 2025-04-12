/*     */ package net.kyori.adventure.translation;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.key.Key;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface TranslationRegistry
/*     */   extends Translator
/*     */ {
/*  62 */   public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static TranslationRegistry create(Key name) {
/*  71 */     return new TranslationRegistryImpl(Objects.<Key>requireNonNull(name, "name"));
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
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Map<String, MessageFormat> formats) {
/* 140 */     Objects.requireNonNull(formats); registerAll(locale, formats.keySet(), formats::get);
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
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Path path, boolean escapeSingleQuotes) {
/*     */     
/* 154 */     try { BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8); 
/* 155 */       try { registerAll(locale, new PropertyResourceBundle(reader), escapeSingleQuotes);
/* 156 */         if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerAll(@NotNull Locale locale, @NotNull ResourceBundle bundle, boolean escapeSingleQuotes) {
/* 179 */     registerAll(locale, bundle.keySet(), key -> {
/*     */           String format = bundle.getString(key);
/*     */           return new MessageFormat(escapeSingleQuotes ? SINGLE_QUOTE_PATTERN.matcher(format).replaceAll("''") : format, locale);
/*     */         });
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
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Set<String> keys, Function<String, MessageFormat> function) {
/* 200 */     List<IllegalArgumentException> errors = null;
/* 201 */     for (String key : keys) {
/*     */       try {
/* 203 */         register(key, locale, function.apply(key));
/* 204 */       } catch (IllegalArgumentException e) {
/* 205 */         if (errors == null) {
/* 206 */           errors = new LinkedList<>();
/*     */         }
/* 208 */         errors.add(e);
/*     */       } 
/*     */     } 
/* 211 */     if (errors != null) {
/* 212 */       int size = errors.size();
/* 213 */       if (size == 1)
/* 214 */         throw (IllegalArgumentException)errors.get(0); 
/* 215 */       if (size > 1)
/* 216 */         throw new IllegalArgumentException(String.format("Invalid key (and %d more)", new Object[] { Integer.valueOf(size - 1) }), (Throwable)errors.get(0)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean contains(@NotNull String paramString);
/*     */   
/*     */   @Nullable
/*     */   MessageFormat translate(@NotNull String paramString, @NotNull Locale paramLocale);
/*     */   
/*     */   void defaultLocale(@NotNull Locale paramLocale);
/*     */   
/*     */   void register(@NotNull String paramString, @NotNull Locale paramLocale, @NotNull MessageFormat paramMessageFormat);
/*     */   
/*     */   void unregister(@NotNull String paramString);
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\translation\TranslationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */