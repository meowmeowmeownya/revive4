/*      */ package com.google.gson;
/*      */ 
/*      */ import com.google.gson.internal.ConstructorConstructor;
/*      */ import com.google.gson.internal.Excluder;
/*      */ import com.google.gson.internal.Primitives;
/*      */ import com.google.gson.internal.Streams;
/*      */ import com.google.gson.internal.bind.ArrayTypeAdapter;
/*      */ import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.DateTypeAdapter;
/*      */ import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.JsonTreeReader;
/*      */ import com.google.gson.internal.bind.JsonTreeWriter;
/*      */ import com.google.gson.internal.bind.MapTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.NumberTypeAdapter;
/*      */ import com.google.gson.internal.bind.ObjectTypeAdapter;
/*      */ import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
/*      */ import com.google.gson.internal.bind.TypeAdapters;
/*      */ import com.google.gson.internal.sql.SqlTypesSupport;
/*      */ import com.google.gson.reflect.TypeToken;
/*      */ import com.google.gson.stream.JsonReader;
/*      */ import com.google.gson.stream.JsonToken;
/*      */ import com.google.gson.stream.JsonWriter;
/*      */ import com.google.gson.stream.MalformedJsonException;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Type;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Gson
/*      */ {
/*      */   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
/*      */   static final boolean DEFAULT_LENIENT = false;
/*      */   static final boolean DEFAULT_PRETTY_PRINT = false;
/*      */   static final boolean DEFAULT_ESCAPE_HTML = true;
/*      */   static final boolean DEFAULT_SERIALIZE_NULLS = false;
/*      */   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
/*      */   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
/*  114 */   private static final TypeToken<?> NULL_KEY_SURROGATE = TypeToken.get(Object.class);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>>();
/*      */ 
/*      */   
/*  127 */   private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<TypeToken<?>, TypeAdapter<?>>();
/*      */ 
/*      */   
/*      */   private final ConstructorConstructor constructorConstructor;
/*      */ 
/*      */   
/*      */   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
/*      */ 
/*      */   
/*      */   final List<TypeAdapterFactory> factories;
/*      */ 
/*      */   
/*      */   final Excluder excluder;
/*      */ 
/*      */   
/*      */   final FieldNamingStrategy fieldNamingStrategy;
/*      */ 
/*      */   
/*      */   final Map<Type, InstanceCreator<?>> instanceCreators;
/*      */ 
/*      */   
/*      */   final boolean serializeNulls;
/*      */ 
/*      */   
/*      */   final boolean complexMapKeySerialization;
/*      */ 
/*      */   
/*      */   final boolean generateNonExecutableJson;
/*      */ 
/*      */   
/*      */   final boolean htmlSafe;
/*      */ 
/*      */   
/*      */   final boolean prettyPrinting;
/*      */ 
/*      */   
/*      */   final boolean lenient;
/*      */ 
/*      */   
/*      */   final boolean serializeSpecialFloatingPointValues;
/*      */ 
/*      */   
/*      */   final String datePattern;
/*      */ 
/*      */   
/*      */   final int dateStyle;
/*      */   
/*      */   final int timeStyle;
/*      */   
/*      */   final LongSerializationPolicy longSerializationPolicy;
/*      */   
/*      */   final List<TypeAdapterFactory> builderFactories;
/*      */   
/*      */   final List<TypeAdapterFactory> builderHierarchyFactories;
/*      */   
/*      */   final ToNumberStrategy objectToNumberStrategy;
/*      */   
/*      */   final ToNumberStrategy numberToNumberStrategy;
/*      */ 
/*      */   
/*      */   public Gson() {
/*  188 */     this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, 
/*  189 */         Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, null, 2, 2, 
/*      */ 
/*      */ 
/*      */         
/*  193 */         Collections.emptyList(), Collections.emptyList(), 
/*  194 */         Collections.emptyList(), ToNumberPolicy.DOUBLE, ToNumberPolicy.LAZILY_PARSED_NUMBER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting, boolean lenient, boolean serializeSpecialFloatingPointValues, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> builderFactories, List<TypeAdapterFactory> builderHierarchyFactories, List<TypeAdapterFactory> factoriesToBeAdded, ToNumberStrategy objectToNumberStrategy, ToNumberStrategy numberToNumberStrategy) {
/*  206 */     this.excluder = excluder;
/*  207 */     this.fieldNamingStrategy = fieldNamingStrategy;
/*  208 */     this.instanceCreators = instanceCreators;
/*  209 */     this.constructorConstructor = new ConstructorConstructor(instanceCreators);
/*  210 */     this.serializeNulls = serializeNulls;
/*  211 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*  212 */     this.generateNonExecutableJson = generateNonExecutableGson;
/*  213 */     this.htmlSafe = htmlSafe;
/*  214 */     this.prettyPrinting = prettyPrinting;
/*  215 */     this.lenient = lenient;
/*  216 */     this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
/*  217 */     this.longSerializationPolicy = longSerializationPolicy;
/*  218 */     this.datePattern = datePattern;
/*  219 */     this.dateStyle = dateStyle;
/*  220 */     this.timeStyle = timeStyle;
/*  221 */     this.builderFactories = builderFactories;
/*  222 */     this.builderHierarchyFactories = builderHierarchyFactories;
/*  223 */     this.objectToNumberStrategy = objectToNumberStrategy;
/*  224 */     this.numberToNumberStrategy = numberToNumberStrategy;
/*      */     
/*  226 */     List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
/*      */ 
/*      */     
/*  229 */     factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
/*  230 */     factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
/*      */ 
/*      */     
/*  233 */     factories.add(excluder);
/*      */ 
/*      */     
/*  236 */     factories.addAll(factoriesToBeAdded);
/*      */ 
/*      */     
/*  239 */     factories.add(TypeAdapters.STRING_FACTORY);
/*  240 */     factories.add(TypeAdapters.INTEGER_FACTORY);
/*  241 */     factories.add(TypeAdapters.BOOLEAN_FACTORY);
/*  242 */     factories.add(TypeAdapters.BYTE_FACTORY);
/*  243 */     factories.add(TypeAdapters.SHORT_FACTORY);
/*  244 */     TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
/*  245 */     factories.add(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
/*  246 */     factories.add(TypeAdapters.newFactory(double.class, Double.class, 
/*  247 */           doubleAdapter(serializeSpecialFloatingPointValues)));
/*  248 */     factories.add(TypeAdapters.newFactory(float.class, Float.class, 
/*  249 */           floatAdapter(serializeSpecialFloatingPointValues)));
/*  250 */     factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
/*  251 */     factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
/*  252 */     factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
/*  253 */     factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
/*  254 */     factories.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
/*  255 */     factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
/*  256 */     factories.add(TypeAdapters.CHARACTER_FACTORY);
/*  257 */     factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
/*  258 */     factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
/*  259 */     factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
/*  260 */     factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
/*  261 */     factories.add(TypeAdapters.URL_FACTORY);
/*  262 */     factories.add(TypeAdapters.URI_FACTORY);
/*  263 */     factories.add(TypeAdapters.UUID_FACTORY);
/*  264 */     factories.add(TypeAdapters.CURRENCY_FACTORY);
/*  265 */     factories.add(TypeAdapters.LOCALE_FACTORY);
/*  266 */     factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
/*  267 */     factories.add(TypeAdapters.BIT_SET_FACTORY);
/*  268 */     factories.add(DateTypeAdapter.FACTORY);
/*  269 */     factories.add(TypeAdapters.CALENDAR_FACTORY);
/*      */     
/*  271 */     if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
/*  272 */       factories.add(SqlTypesSupport.TIME_FACTORY);
/*  273 */       factories.add(SqlTypesSupport.DATE_FACTORY);
/*  274 */       factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
/*      */     } 
/*      */     
/*  277 */     factories.add(ArrayTypeAdapter.FACTORY);
/*  278 */     factories.add(TypeAdapters.CLASS_FACTORY);
/*      */ 
/*      */     
/*  281 */     factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
/*  282 */     factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
/*  283 */     this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
/*  284 */     factories.add(this.jsonAdapterFactory);
/*  285 */     factories.add(TypeAdapters.ENUM_FACTORY);
/*  286 */     factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory));
/*      */ 
/*      */     
/*  289 */     this.factories = Collections.unmodifiableList(factories);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GsonBuilder newBuilder() {
/*  299 */     return new GsonBuilder(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Excluder excluder() {
/*  308 */     return this.excluder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldNamingStrategy fieldNamingStrategy() {
/*  317 */     return this.fieldNamingStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean serializeNulls() {
/*  327 */     return this.serializeNulls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean htmlSafe() {
/*  337 */     return this.htmlSafe;
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
/*  341 */     if (serializeSpecialFloatingPointValues) {
/*  342 */       return TypeAdapters.DOUBLE;
/*      */     }
/*  344 */     return new TypeAdapter<Number>() {
/*      */         public Double read(JsonReader in) throws IOException {
/*  346 */           if (in.peek() == JsonToken.NULL) {
/*  347 */             in.nextNull();
/*  348 */             return null;
/*      */           } 
/*  350 */           return Double.valueOf(in.nextDouble());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  353 */           if (value == null) {
/*  354 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  357 */           double doubleValue = value.doubleValue();
/*  358 */           Gson.checkValidFloatingPoint(doubleValue);
/*  359 */           out.value(value);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
/*  365 */     if (serializeSpecialFloatingPointValues) {
/*  366 */       return TypeAdapters.FLOAT;
/*      */     }
/*  368 */     return new TypeAdapter<Number>() {
/*      */         public Float read(JsonReader in) throws IOException {
/*  370 */           if (in.peek() == JsonToken.NULL) {
/*  371 */             in.nextNull();
/*  372 */             return null;
/*      */           } 
/*  374 */           return Float.valueOf((float)in.nextDouble());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  377 */           if (value == null) {
/*  378 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  381 */           float floatValue = value.floatValue();
/*  382 */           Gson.checkValidFloatingPoint(floatValue);
/*  383 */           out.value(value);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   static void checkValidFloatingPoint(double value) {
/*  389 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/*  390 */       throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
/*  397 */     if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
/*  398 */       return TypeAdapters.LONG;
/*      */     }
/*  400 */     return new TypeAdapter<Number>() {
/*      */         public Number read(JsonReader in) throws IOException {
/*  402 */           if (in.peek() == JsonToken.NULL) {
/*  403 */             in.nextNull();
/*  404 */             return null;
/*      */           } 
/*  406 */           return Long.valueOf(in.nextLong());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  409 */           if (value == null) {
/*  410 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  413 */           out.value(value.toString());
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
/*  419 */     return (new TypeAdapter<AtomicLong>() {
/*      */         public void write(JsonWriter out, AtomicLong value) throws IOException {
/*  421 */           longAdapter.write(out, Long.valueOf(value.get()));
/*      */         }
/*      */         public AtomicLong read(JsonReader in) throws IOException {
/*  424 */           Number value = longAdapter.read(in);
/*  425 */           return new AtomicLong(value.longValue());
/*      */         }
/*  427 */       }).nullSafe();
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
/*  431 */     return (new TypeAdapter<AtomicLongArray>() {
/*      */         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
/*  433 */           out.beginArray();
/*  434 */           for (int i = 0, length = value.length(); i < length; i++) {
/*  435 */             longAdapter.write(out, Long.valueOf(value.get(i)));
/*      */           }
/*  437 */           out.endArray();
/*      */         }
/*      */         public AtomicLongArray read(JsonReader in) throws IOException {
/*  440 */           List<Long> list = new ArrayList<Long>();
/*  441 */           in.beginArray();
/*  442 */           while (in.hasNext()) {
/*  443 */             long value = ((Number)longAdapter.read(in)).longValue();
/*  444 */             list.add(Long.valueOf(value));
/*      */           } 
/*  446 */           in.endArray();
/*  447 */           int length = list.size();
/*  448 */           AtomicLongArray array = new AtomicLongArray(length);
/*  449 */           for (int i = 0; i < length; i++) {
/*  450 */             array.set(i, ((Long)list.get(i)).longValue());
/*      */           }
/*  452 */           return array;
/*      */         }
/*  454 */       }).nullSafe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
/*  465 */     TypeAdapter<?> cached = this.typeTokenCache.get((type == null) ? NULL_KEY_SURROGATE : type);
/*  466 */     if (cached != null) {
/*  467 */       return (TypeAdapter)cached;
/*      */     }
/*      */     
/*  470 */     Map<TypeToken<?>, FutureTypeAdapter<?>> threadCalls = this.calls.get();
/*  471 */     boolean requiresThreadLocalCleanup = false;
/*  472 */     if (threadCalls == null) {
/*  473 */       threadCalls = new HashMap<TypeToken<?>, FutureTypeAdapter<?>>();
/*  474 */       this.calls.set(threadCalls);
/*  475 */       requiresThreadLocalCleanup = true;
/*      */     } 
/*      */ 
/*      */     
/*  479 */     FutureTypeAdapter<T> ongoingCall = (FutureTypeAdapter<T>)threadCalls.get(type);
/*  480 */     if (ongoingCall != null) {
/*  481 */       return ongoingCall;
/*      */     }
/*      */     
/*      */     try {
/*  485 */       FutureTypeAdapter<T> call = new FutureTypeAdapter<T>();
/*  486 */       threadCalls.put(type, call);
/*      */       
/*  488 */       for (TypeAdapterFactory factory : this.factories) {
/*  489 */         TypeAdapter<T> candidate = factory.create(this, type);
/*  490 */         if (candidate != null) {
/*  491 */           call.setDelegate(candidate);
/*  492 */           this.typeTokenCache.put(type, candidate);
/*  493 */           return candidate;
/*      */         } 
/*      */       } 
/*  496 */       throw new IllegalArgumentException("GSON (2.8.9) cannot handle " + type);
/*      */     } finally {
/*  498 */       threadCalls.remove(type);
/*      */       
/*  500 */       if (requiresThreadLocalCleanup) {
/*  501 */         this.calls.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
/*      */     JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory;
/*  559 */     if (!this.factories.contains(skipPast)) {
/*  560 */       jsonAdapterAnnotationTypeAdapterFactory = this.jsonAdapterFactory;
/*      */     }
/*      */     
/*  563 */     boolean skipPastFound = false;
/*  564 */     for (TypeAdapterFactory factory : this.factories) {
/*  565 */       if (!skipPastFound) {
/*  566 */         if (factory == jsonAdapterAnnotationTypeAdapterFactory) {
/*  567 */           skipPastFound = true;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  572 */       TypeAdapter<T> candidate = factory.create(this, type);
/*  573 */       if (candidate != null) {
/*  574 */         return candidate;
/*      */       }
/*      */     } 
/*  577 */     throw new IllegalArgumentException("GSON cannot serialize " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(Class<T> type) {
/*  587 */     return getAdapter(TypeToken.get(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonElement toJsonTree(Object src) {
/*  604 */     if (src == null) {
/*  605 */       return JsonNull.INSTANCE;
/*      */     }
/*  607 */     return toJsonTree(src, src.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonElement toJsonTree(Object src, Type typeOfSrc) {
/*  627 */     JsonTreeWriter writer = new JsonTreeWriter();
/*  628 */     toJson(src, typeOfSrc, (JsonWriter)writer);
/*  629 */     return writer.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(Object src) {
/*  646 */     if (src == null) {
/*  647 */       return toJson(JsonNull.INSTANCE);
/*      */     }
/*  649 */     return toJson(src, src.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(Object src, Type typeOfSrc) {
/*  668 */     StringWriter writer = new StringWriter();
/*  669 */     toJson(src, typeOfSrc, writer);
/*  670 */     return writer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Appendable writer) throws JsonIOException {
/*  688 */     if (src != null) {
/*  689 */       toJson(src, src.getClass(), writer);
/*      */     } else {
/*  691 */       toJson(JsonNull.INSTANCE, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
/*      */     try {
/*  713 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  714 */       toJson(src, typeOfSrc, jsonWriter);
/*  715 */     } catch (IOException e) {
/*  716 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
/*  727 */     TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
/*  728 */     boolean oldLenient = writer.isLenient();
/*  729 */     writer.setLenient(true);
/*  730 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  731 */     writer.setHtmlSafe(this.htmlSafe);
/*  732 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*  733 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  735 */       adapter.write(writer, src);
/*  736 */     } catch (IOException e) {
/*  737 */       throw new JsonIOException(e);
/*  738 */     } catch (AssertionError e) {
/*  739 */       AssertionError error = new AssertionError("AssertionError (GSON 2.8.9): " + e.getMessage());
/*  740 */       error.initCause(e);
/*  741 */       throw error;
/*      */     } finally {
/*  743 */       writer.setLenient(oldLenient);
/*  744 */       writer.setHtmlSafe(oldHtmlSafe);
/*  745 */       writer.setSerializeNulls(oldSerializeNulls);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(JsonElement jsonElement) {
/*  757 */     StringWriter writer = new StringWriter();
/*  758 */     toJson(jsonElement, writer);
/*  759 */     return writer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
/*      */     try {
/*  772 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  773 */       toJson(jsonElement, jsonWriter);
/*  774 */     } catch (IOException e) {
/*  775 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonWriter newJsonWriter(Writer writer) throws IOException {
/*  783 */     if (this.generateNonExecutableJson) {
/*  784 */       writer.write(")]}'\n");
/*      */     }
/*  786 */     JsonWriter jsonWriter = new JsonWriter(writer);
/*  787 */     if (this.prettyPrinting) {
/*  788 */       jsonWriter.setIndent("  ");
/*      */     }
/*  790 */     jsonWriter.setSerializeNulls(this.serializeNulls);
/*  791 */     return jsonWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonReader newJsonReader(Reader reader) {
/*  798 */     JsonReader jsonReader = new JsonReader(reader);
/*  799 */     jsonReader.setLenient(this.lenient);
/*  800 */     return jsonReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
/*  808 */     boolean oldLenient = writer.isLenient();
/*  809 */     writer.setLenient(true);
/*  810 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  811 */     writer.setHtmlSafe(this.htmlSafe);
/*  812 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*  813 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  815 */       Streams.write(jsonElement, writer);
/*  816 */     } catch (IOException e) {
/*  817 */       throw new JsonIOException(e);
/*  818 */     } catch (AssertionError e) {
/*  819 */       AssertionError error = new AssertionError("AssertionError (GSON 2.8.9): " + e.getMessage());
/*  820 */       error.initCause(e);
/*  821 */       throw error;
/*      */     } finally {
/*  823 */       writer.setLenient(oldLenient);
/*  824 */       writer.setHtmlSafe(oldHtmlSafe);
/*  825 */       writer.setSerializeNulls(oldSerializeNulls);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
/*  848 */     Object object = fromJson(json, classOfT);
/*  849 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
/*  873 */     if (json == null) {
/*  874 */       return null;
/*      */     }
/*  876 */     StringReader reader = new StringReader(json);
/*  877 */     T target = fromJson(reader, typeOfT);
/*  878 */     return target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
/*  900 */     JsonReader jsonReader = newJsonReader(json);
/*  901 */     Object object = fromJson(jsonReader, classOfT);
/*  902 */     assertFullConsumption(object, jsonReader);
/*  903 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/*  927 */     JsonReader jsonReader = newJsonReader(json);
/*  928 */     T object = fromJson(jsonReader, typeOfT);
/*  929 */     assertFullConsumption(object, jsonReader);
/*  930 */     return object;
/*      */   }
/*      */   
/*      */   private static void assertFullConsumption(Object obj, JsonReader reader) {
/*      */     try {
/*  935 */       if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
/*  936 */         throw new JsonIOException("JSON document was not fully consumed.");
/*      */       }
/*  938 */     } catch (MalformedJsonException e) {
/*  939 */       throw new JsonSyntaxException(e);
/*  940 */     } catch (IOException e) {
/*  941 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/*  955 */     boolean isEmpty = true;
/*  956 */     boolean oldLenient = reader.isLenient();
/*  957 */     reader.setLenient(true);
/*      */     try {
/*  959 */       reader.peek();
/*  960 */       isEmpty = false;
/*  961 */       TypeToken<T> typeToken = TypeToken.get(typeOfT);
/*  962 */       TypeAdapter<T> typeAdapter = getAdapter(typeToken);
/*  963 */       T object = typeAdapter.read(reader);
/*  964 */       return object;
/*  965 */     } catch (EOFException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  970 */       if (isEmpty) {
/*  971 */         return null;
/*      */       }
/*  973 */       throw new JsonSyntaxException(e);
/*  974 */     } catch (IllegalStateException e) {
/*  975 */       throw new JsonSyntaxException(e);
/*  976 */     } catch (IOException e) {
/*      */       
/*  978 */       throw new JsonSyntaxException(e);
/*  979 */     } catch (AssertionError e) {
/*  980 */       AssertionError error = new AssertionError("AssertionError (GSON 2.8.9): " + e.getMessage());
/*  981 */       error.initCause(e);
/*  982 */       throw error;
/*      */     } finally {
/*  984 */       reader.setLenient(oldLenient);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
/* 1006 */     Object object = fromJson(json, classOfT);
/* 1007 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
/* 1031 */     if (json == null) {
/* 1032 */       return null;
/*      */     }
/* 1034 */     return fromJson((JsonReader)new JsonTreeReader(json), typeOfT);
/*      */   }
/*      */   
/*      */   static class FutureTypeAdapter<T> extends TypeAdapter<T> {
/*      */     private TypeAdapter<T> delegate;
/*      */     
/*      */     public void setDelegate(TypeAdapter<T> typeAdapter) {
/* 1041 */       if (this.delegate != null) {
/* 1042 */         throw new AssertionError();
/*      */       }
/* 1044 */       this.delegate = typeAdapter;
/*      */     }
/*      */     
/*      */     public T read(JsonReader in) throws IOException {
/* 1048 */       if (this.delegate == null) {
/* 1049 */         throw new IllegalStateException();
/*      */       }
/* 1051 */       return this.delegate.read(in);
/*      */     }
/*      */     
/*      */     public void write(JsonWriter out, T value) throws IOException {
/* 1055 */       if (this.delegate == null) {
/* 1056 */         throw new IllegalStateException();
/*      */       }
/* 1058 */       this.delegate.write(out, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1064 */     return "{serializeNulls:" + this.serializeNulls + 
/* 1065 */       ",factories:" + 
/* 1066 */       this.factories + ",instanceCreators:" + 
/* 1067 */       this.constructorConstructor + "}";
/*      */   }
/*      */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\Gson.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */