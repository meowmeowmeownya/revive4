/*     */ package net.kyori.adventure.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
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
/*     */ public final class Index<K, V>
/*     */ {
/*     */   private final Map<K, V> keyToValue;
/*     */   private final Map<V, K> valueToKey;
/*     */   
/*     */   private Index(Map<K, V> keyToValue, Map<V, K> valueToKey) {
/*  50 */     this.keyToValue = keyToValue;
/*  51 */     this.valueToKey = valueToKey;
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
/*     */   public static <K, V extends Enum<V>> Index<K, V> create(Class<V> type, @NotNull Function<? super V, ? extends K> keyFunction) {
/*  65 */     return create(type, keyFunction, type.getEnumConstants());
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
/*     */   @SafeVarargs
/*     */   @NotNull
/*     */   public static <K, V extends Enum<V>> Index<K, V> create(Class<V> type, @NotNull Function<? super V, ? extends K> keyFunction, @NotNull V... values) {
/*  82 */     return create(values, length -> new EnumMap<>(type), keyFunction);
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
/*     */   @SafeVarargs
/*     */   @NotNull
/*     */   public static <K, V> Index<K, V> create(@NotNull Function<? super V, ? extends K> keyFunction, @NotNull V... values) {
/*  98 */     return create(values, HashMap::new, keyFunction);
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
/*     */   public static <K, V> Index<K, V> create(@NotNull Function<? super V, ? extends K> keyFunction, @NotNull List<V> constants) {
/* 112 */     return create(constants, HashMap::new, keyFunction);
/*     */   }
/*     */   @NotNull
/*     */   private static <K, V> Index<K, V> create(V[] values, IntFunction<Map<V, K>> valueToKeyFactory, @NotNull Function<? super V, ? extends K> keyFunction) {
/* 116 */     return create(Arrays.asList(values), valueToKeyFactory, keyFunction);
/*     */   }
/*     */   @NotNull
/*     */   private static <K, V> Index<K, V> create(List<V> values, IntFunction<Map<V, K>> valueToKeyFactory, @NotNull Function<? super V, ? extends K> keyFunction) {
/* 120 */     int length = values.size();
/* 121 */     Map<K, V> keyToValue = new HashMap<>(length);
/* 122 */     Map<V, K> valueToKey = valueToKeyFactory.apply(length);
/* 123 */     for (int i = 0; i < length; i++) {
/* 124 */       V value = values.get(i);
/* 125 */       K key = keyFunction.apply(value);
/* 126 */       if (keyToValue.putIfAbsent(key, value) != null) {
/* 127 */         throw new IllegalStateException(String.format("Key %s already mapped to value %s", new Object[] { key, keyToValue.get(key) }));
/*     */       }
/* 129 */       if (valueToKey.putIfAbsent(value, key) != null) {
/* 130 */         throw new IllegalStateException(String.format("Value %s already mapped to key %s", new Object[] { value, valueToKey.get(value) }));
/*     */       }
/*     */     } 
/* 133 */     return new Index<>(Collections.unmodifiableMap(keyToValue), Collections.unmodifiableMap(valueToKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Set<K> keys() {
/* 143 */     return Collections.unmodifiableSet(this.keyToValue.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public K key(@NotNull V value) {
/* 154 */     return this.valueToKey.get(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Set<V> values() {
/* 164 */     return Collections.unmodifiableSet(this.valueToKey.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V value(@NotNull K key) {
/* 175 */     return this.keyToValue.get(key);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventur\\util\Index.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */