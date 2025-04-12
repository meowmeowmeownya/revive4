/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.gson.internal.;
/*     */ import com.google.gson.internal.Excluder;
/*     */ import com.google.gson.internal.bind.DefaultDateTypeAdapter;
/*     */ import com.google.gson.internal.bind.TreeTypeAdapter;
/*     */ import com.google.gson.internal.bind.TypeAdapters;
/*     */ import com.google.gson.internal.sql.SqlTypesSupport;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GsonBuilder
/*     */ {
/*  80 */   private Excluder excluder = Excluder.DEFAULT;
/*  81 */   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
/*  82 */   private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
/*  83 */   private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<Type, InstanceCreator<?>>();
/*     */   
/*  85 */   private final List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
/*     */   
/*  87 */   private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>();
/*     */   private boolean serializeNulls = false;
/*     */   private String datePattern;
/*  90 */   private int dateStyle = 2;
/*  91 */   private int timeStyle = 2;
/*     */   private boolean complexMapKeySerialization = false;
/*     */   private boolean serializeSpecialFloatingPointValues = false;
/*     */   private boolean escapeHtmlChars = true;
/*     */   private boolean prettyPrinting = false;
/*     */   private boolean generateNonExecutableJson = false;
/*     */   private boolean lenient = false;
/*  98 */   private ToNumberStrategy objectToNumberStrategy = ToNumberPolicy.DOUBLE;
/*  99 */   private ToNumberStrategy numberToNumberStrategy = ToNumberPolicy.LAZILY_PARSED_NUMBER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GsonBuilder(Gson gson) {
/* 117 */     this.excluder = gson.excluder;
/* 118 */     this.fieldNamingPolicy = gson.fieldNamingStrategy;
/* 119 */     this.instanceCreators.putAll(gson.instanceCreators);
/* 120 */     this.serializeNulls = gson.serializeNulls;
/* 121 */     this.complexMapKeySerialization = gson.complexMapKeySerialization;
/* 122 */     this.generateNonExecutableJson = gson.generateNonExecutableJson;
/* 123 */     this.escapeHtmlChars = gson.htmlSafe;
/* 124 */     this.prettyPrinting = gson.prettyPrinting;
/* 125 */     this.lenient = gson.lenient;
/* 126 */     this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
/* 127 */     this.longSerializationPolicy = gson.longSerializationPolicy;
/* 128 */     this.datePattern = gson.datePattern;
/* 129 */     this.dateStyle = gson.dateStyle;
/* 130 */     this.timeStyle = gson.timeStyle;
/* 131 */     this.factories.addAll(gson.builderFactories);
/* 132 */     this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
/* 133 */     this.objectToNumberStrategy = gson.objectToNumberStrategy;
/* 134 */     this.numberToNumberStrategy = gson.numberToNumberStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setVersion(double ignoreVersionsAfter) {
/* 145 */     this.excluder = this.excluder.withVersion(ignoreVersionsAfter);
/* 146 */     return this;
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
/*     */   public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
/* 161 */     this.excluder = this.excluder.withModifiers(modifiers);
/* 162 */     return this;
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
/*     */   public GsonBuilder generateNonExecutableJson() {
/* 175 */     this.generateNonExecutableJson = true;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
/* 186 */     this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder serializeNulls() {
/* 198 */     this.serializeNulls = true;
/* 199 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder enableComplexMapKeySerialization() {
/* 279 */     this.complexMapKeySerialization = true;
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder disableInnerClassSerialization() {
/* 290 */     this.excluder = this.excluder.disableInnerClassSerialization();
/* 291 */     return this;
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
/*     */   public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
/* 303 */     this.longSerializationPolicy = serializationPolicy;
/* 304 */     return this;
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
/*     */   public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
/* 316 */     this.fieldNamingPolicy = namingConvention;
/* 317 */     return this;
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
/*     */   public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
/* 329 */     this.fieldNamingPolicy = fieldNamingStrategy;
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setObjectToNumberStrategy(ToNumberStrategy objectToNumberStrategy) {
/* 341 */     this.objectToNumberStrategy = objectToNumberStrategy;
/* 342 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setNumberToNumberStrategy(ToNumberStrategy numberToNumberStrategy) {
/* 353 */     this.numberToNumberStrategy = numberToNumberStrategy;
/* 354 */     return this;
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
/*     */   public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
/* 368 */     for (ExclusionStrategy strategy : strategies) {
/* 369 */       this.excluder = this.excluder.withExclusionStrategy(strategy, true, true);
/*     */     }
/* 371 */     return this;
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
/*     */   public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
/* 387 */     this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
/* 388 */     return this;
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
/*     */   public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
/* 404 */     this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
/* 405 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setPrettyPrinting() {
/* 415 */     this.prettyPrinting = true;
/* 416 */     return this;
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
/*     */   public GsonBuilder setLenient() {
/* 428 */     this.lenient = true;
/* 429 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder disableHtmlEscaping() {
/* 440 */     this.escapeHtmlChars = false;
/* 441 */     return this;
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
/*     */   public GsonBuilder setDateFormat(String pattern) {
/* 462 */     this.datePattern = pattern;
/* 463 */     return this;
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
/*     */   public GsonBuilder setDateFormat(int style) {
/* 481 */     this.dateStyle = style;
/* 482 */     this.datePattern = null;
/* 483 */     return this;
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
/*     */   public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
/* 502 */     this.dateStyle = dateStyle;
/* 503 */     this.timeStyle = timeStyle;
/* 504 */     this.datePattern = null;
/* 505 */     return this;
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
/*     */   public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
/* 526 */     .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof InstanceCreator || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */ 
/*     */     
/* 530 */     if (typeAdapter instanceof InstanceCreator) {
/* 531 */       this.instanceCreators.put(type, (InstanceCreator)typeAdapter);
/*     */     }
/* 533 */     if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
/* 534 */       TypeToken<?> typeToken = TypeToken.get(type);
/* 535 */       this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
/*     */     } 
/* 537 */     if (typeAdapter instanceof TypeAdapter) {
/* 538 */       this.factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)typeAdapter));
/*     */     }
/* 540 */     return this;
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
/*     */   public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
/* 552 */     this.factories.add(factory);
/* 553 */     return this;
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
/*     */   public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
/* 572 */     .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */     
/* 575 */     if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer) {
/* 576 */       this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter));
/*     */     }
/* 578 */     if (typeAdapter instanceof TypeAdapter) {
/* 579 */       this.factories.add(TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter)typeAdapter));
/*     */     }
/* 581 */     return this;
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
/*     */   public GsonBuilder serializeSpecialFloatingPointValues() {
/* 605 */     this.serializeSpecialFloatingPointValues = true;
/* 606 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gson create() {
/* 616 */     List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>(this.factories.size() + this.hierarchyFactories.size() + 3);
/* 617 */     factories.addAll(this.factories);
/* 618 */     Collections.reverse(factories);
/*     */     
/* 620 */     List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>(this.hierarchyFactories);
/* 621 */     Collections.reverse(hierarchyFactories);
/* 622 */     factories.addAll(hierarchyFactories);
/*     */     
/* 624 */     addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
/*     */     
/* 626 */     return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, this.factories, this.hierarchyFactories, factories, this.objectToNumberStrategy, this.numberToNumberStrategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> factories) {
/*     */     TypeAdapterFactory dateAdapterFactory;
/* 637 */     boolean sqlTypesSupported = SqlTypesSupport.SUPPORTS_SQL_TYPES;
/* 638 */     TypeAdapterFactory sqlTimestampAdapterFactory = null;
/* 639 */     TypeAdapterFactory sqlDateAdapterFactory = null;
/*     */     
/* 641 */     if (datePattern != null && !datePattern.trim().isEmpty()) {
/* 642 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(datePattern);
/*     */       
/* 644 */       if (sqlTypesSupported) {
/* 645 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(datePattern);
/* 646 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(datePattern);
/*     */       } 
/* 648 */     } else if (dateStyle != 2 && timeStyle != 2) {
/* 649 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(dateStyle, timeStyle);
/*     */       
/* 651 */       if (sqlTypesSupported) {
/* 652 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/* 653 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */       } 
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 659 */     factories.add(dateAdapterFactory);
/* 660 */     if (sqlTypesSupported) {
/* 661 */       factories.add(sqlTimestampAdapterFactory);
/* 662 */       factories.add(sqlDateAdapterFactory);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\GsonBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */