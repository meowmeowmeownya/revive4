/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.ToNumberPolicy;
/*     */ import com.google.gson.ToNumberStrategy;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.LinkedTreeMap;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectTypeAdapter
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  43 */   private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory((ToNumberStrategy)ToNumberPolicy.DOUBLE);
/*     */   
/*     */   private final Gson gson;
/*     */   private final ToNumberStrategy toNumberStrategy;
/*     */   
/*     */   private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
/*  49 */     this.gson = gson;
/*  50 */     this.toNumberStrategy = toNumberStrategy;
/*     */   }
/*     */   
/*     */   private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
/*  54 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/*  57 */           if (type.getRawType() == Object.class) {
/*  58 */             return new ObjectTypeAdapter(gson, toNumberStrategy);
/*     */           }
/*  60 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
/*  66 */     if (toNumberStrategy == ToNumberPolicy.DOUBLE) {
/*  67 */       return DOUBLE_FACTORY;
/*     */     }
/*  69 */     return newFactory(toNumberStrategy);
/*     */   }
/*     */   public Object read(JsonReader in) throws IOException {
/*     */     List<Object> list;
/*     */     LinkedTreeMap<String, Object> linkedTreeMap;
/*  74 */     JsonToken token = in.peek();
/*  75 */     switch (token) {
/*     */       case BEGIN_ARRAY:
/*  77 */         list = new ArrayList();
/*  78 */         in.beginArray();
/*  79 */         while (in.hasNext()) {
/*  80 */           list.add(read(in));
/*     */         }
/*  82 */         in.endArray();
/*  83 */         return list;
/*     */       
/*     */       case BEGIN_OBJECT:
/*  86 */         linkedTreeMap = new LinkedTreeMap();
/*  87 */         in.beginObject();
/*  88 */         while (in.hasNext()) {
/*  89 */           linkedTreeMap.put(in.nextName(), read(in));
/*     */         }
/*  91 */         in.endObject();
/*  92 */         return linkedTreeMap;
/*     */       
/*     */       case STRING:
/*  95 */         return in.nextString();
/*     */       
/*     */       case NUMBER:
/*  98 */         return this.toNumberStrategy.readNumber(in);
/*     */       
/*     */       case BOOLEAN:
/* 101 */         return Boolean.valueOf(in.nextBoolean());
/*     */       
/*     */       case NULL:
/* 104 */         in.nextNull();
/* 105 */         return null;
/*     */     } 
/*     */     
/* 108 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object value) throws IOException {
/* 114 */     if (value == null) {
/* 115 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 119 */     TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
/* 120 */     if (typeAdapter instanceof ObjectTypeAdapter) {
/* 121 */       out.beginObject();
/* 122 */       out.endObject();
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     typeAdapter.write(out, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\bind\ObjectTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */