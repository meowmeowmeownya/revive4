/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
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
/*     */ public final class JsonTreeReader
/*     */   extends JsonReader
/*     */ {
/*  39 */   private static final Reader UNREADABLE_READER = new Reader() {
/*     */       public int read(char[] buffer, int offset, int count) throws IOException {
/*  41 */         throw new AssertionError();
/*     */       }
/*     */       public void close() throws IOException {
/*  44 */         throw new AssertionError();
/*     */       }
/*     */     };
/*  47 */   private static final Object SENTINEL_CLOSED = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private Object[] stack = new Object[32];
/*  53 */   private int stackSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private String[] pathNames = new String[32];
/*  64 */   private int[] pathIndices = new int[32];
/*     */   
/*     */   public JsonTreeReader(JsonElement element) {
/*  67 */     super(UNREADABLE_READER);
/*  68 */     push(element);
/*     */   }
/*     */   
/*     */   public void beginArray() throws IOException {
/*  72 */     expect(JsonToken.BEGIN_ARRAY);
/*  73 */     JsonArray array = (JsonArray)peekStack();
/*  74 */     push(array.iterator());
/*  75 */     this.pathIndices[this.stackSize - 1] = 0;
/*     */   }
/*     */   
/*     */   public void endArray() throws IOException {
/*  79 */     expect(JsonToken.END_ARRAY);
/*  80 */     popStack();
/*  81 */     popStack();
/*  82 */     if (this.stackSize > 0) {
/*  83 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public void beginObject() throws IOException {
/*  88 */     expect(JsonToken.BEGIN_OBJECT);
/*  89 */     JsonObject object = (JsonObject)peekStack();
/*  90 */     push(object.entrySet().iterator());
/*     */   }
/*     */   
/*     */   public void endObject() throws IOException {
/*  94 */     expect(JsonToken.END_OBJECT);
/*  95 */     popStack();
/*  96 */     popStack();
/*  97 */     if (this.stackSize > 0) {
/*  98 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasNext() throws IOException {
/* 103 */     JsonToken token = peek();
/* 104 */     return (token != JsonToken.END_OBJECT && token != JsonToken.END_ARRAY);
/*     */   }
/*     */   
/*     */   public JsonToken peek() throws IOException {
/* 108 */     if (this.stackSize == 0) {
/* 109 */       return JsonToken.END_DOCUMENT;
/*     */     }
/*     */     
/* 112 */     Object o = peekStack();
/* 113 */     if (o instanceof Iterator) {
/* 114 */       boolean isObject = this.stack[this.stackSize - 2] instanceof JsonObject;
/* 115 */       Iterator<?> iterator = (Iterator)o;
/* 116 */       if (iterator.hasNext()) {
/* 117 */         if (isObject) {
/* 118 */           return JsonToken.NAME;
/*     */         }
/* 120 */         push(iterator.next());
/* 121 */         return peek();
/*     */       } 
/*     */       
/* 124 */       return isObject ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
/*     */     } 
/* 126 */     if (o instanceof JsonObject)
/* 127 */       return JsonToken.BEGIN_OBJECT; 
/* 128 */     if (o instanceof JsonArray)
/* 129 */       return JsonToken.BEGIN_ARRAY; 
/* 130 */     if (o instanceof JsonPrimitive) {
/* 131 */       JsonPrimitive primitive = (JsonPrimitive)o;
/* 132 */       if (primitive.isString())
/* 133 */         return JsonToken.STRING; 
/* 134 */       if (primitive.isBoolean())
/* 135 */         return JsonToken.BOOLEAN; 
/* 136 */       if (primitive.isNumber()) {
/* 137 */         return JsonToken.NUMBER;
/*     */       }
/* 139 */       throw new AssertionError();
/*     */     } 
/* 141 */     if (o instanceof com.google.gson.JsonNull)
/* 142 */       return JsonToken.NULL; 
/* 143 */     if (o == SENTINEL_CLOSED) {
/* 144 */       throw new IllegalStateException("JsonReader is closed");
/*     */     }
/* 146 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   
/*     */   private Object peekStack() {
/* 151 */     return this.stack[this.stackSize - 1];
/*     */   }
/*     */   
/*     */   private Object popStack() {
/* 155 */     Object result = this.stack[--this.stackSize];
/* 156 */     this.stack[this.stackSize] = null;
/* 157 */     return result;
/*     */   }
/*     */   
/*     */   private void expect(JsonToken expected) throws IOException {
/* 161 */     if (peek() != expected) {
/* 162 */       throw new IllegalStateException("Expected " + expected + " but was " + 
/* 163 */           peek() + locationString());
/*     */     }
/*     */   }
/*     */   
/*     */   public String nextName() throws IOException {
/* 168 */     expect(JsonToken.NAME);
/* 169 */     Iterator<?> i = (Iterator)peekStack();
/* 170 */     Map.Entry<?, ?> entry = (Map.Entry<?, ?>)i.next();
/* 171 */     String result = (String)entry.getKey();
/* 172 */     this.pathNames[this.stackSize - 1] = result;
/* 173 */     push(entry.getValue());
/* 174 */     return result;
/*     */   }
/*     */   
/*     */   public String nextString() throws IOException {
/* 178 */     JsonToken token = peek();
/* 179 */     if (token != JsonToken.STRING && token != JsonToken.NUMBER) {
/* 180 */       throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + token + 
/* 181 */           locationString());
/*     */     }
/* 183 */     String result = ((JsonPrimitive)popStack()).getAsString();
/* 184 */     if (this.stackSize > 0) {
/* 185 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 187 */     return result;
/*     */   }
/*     */   
/*     */   public boolean nextBoolean() throws IOException {
/* 191 */     expect(JsonToken.BOOLEAN);
/* 192 */     boolean result = ((JsonPrimitive)popStack()).getAsBoolean();
/* 193 */     if (this.stackSize > 0) {
/* 194 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 196 */     return result;
/*     */   }
/*     */   
/*     */   public void nextNull() throws IOException {
/* 200 */     expect(JsonToken.NULL);
/* 201 */     popStack();
/* 202 */     if (this.stackSize > 0) {
/* 203 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public double nextDouble() throws IOException {
/* 208 */     JsonToken token = peek();
/* 209 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 210 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 211 */           locationString());
/*     */     }
/* 213 */     double result = ((JsonPrimitive)peekStack()).getAsDouble();
/* 214 */     if (!isLenient() && (Double.isNaN(result) || Double.isInfinite(result))) {
/* 215 */       throw new NumberFormatException("JSON forbids NaN and infinities: " + result);
/*     */     }
/* 217 */     popStack();
/* 218 */     if (this.stackSize > 0) {
/* 219 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 221 */     return result;
/*     */   }
/*     */   
/*     */   public long nextLong() throws IOException {
/* 225 */     JsonToken token = peek();
/* 226 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 227 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 228 */           locationString());
/*     */     }
/* 230 */     long result = ((JsonPrimitive)peekStack()).getAsLong();
/* 231 */     popStack();
/* 232 */     if (this.stackSize > 0) {
/* 233 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 235 */     return result;
/*     */   }
/*     */   
/*     */   public int nextInt() throws IOException {
/* 239 */     JsonToken token = peek();
/* 240 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 241 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 242 */           locationString());
/*     */     }
/* 244 */     int result = ((JsonPrimitive)peekStack()).getAsInt();
/* 245 */     popStack();
/* 246 */     if (this.stackSize > 0) {
/* 247 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 249 */     return result;
/*     */   }
/*     */   
/*     */   JsonElement nextJsonElement() throws IOException {
/* 253 */     JsonToken peeked = peek();
/* 254 */     if (peeked == JsonToken.NAME || peeked == JsonToken.END_ARRAY || peeked == JsonToken.END_OBJECT || peeked == JsonToken.END_DOCUMENT)
/*     */     {
/*     */ 
/*     */       
/* 258 */       throw new IllegalStateException("Unexpected " + peeked + " when reading a JsonElement.");
/*     */     }
/* 260 */     JsonElement element = (JsonElement)peekStack();
/* 261 */     skipValue();
/* 262 */     return element;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 266 */     this.stack = new Object[] { SENTINEL_CLOSED };
/* 267 */     this.stackSize = 1;
/*     */   }
/*     */   
/*     */   public void skipValue() throws IOException {
/* 271 */     if (peek() == JsonToken.NAME) {
/* 272 */       nextName();
/* 273 */       this.pathNames[this.stackSize - 2] = "null";
/*     */     } else {
/* 275 */       popStack();
/* 276 */       if (this.stackSize > 0) {
/* 277 */         this.pathNames[this.stackSize - 1] = "null";
/*     */       }
/*     */     } 
/* 280 */     if (this.stackSize > 0) {
/* 281 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 286 */     return getClass().getSimpleName() + locationString();
/*     */   }
/*     */   
/*     */   public void promoteNameToValue() throws IOException {
/* 290 */     expect(JsonToken.NAME);
/* 291 */     Iterator<?> i = (Iterator)peekStack();
/* 292 */     Map.Entry<?, ?> entry = (Map.Entry<?, ?>)i.next();
/* 293 */     push(entry.getValue());
/* 294 */     push(new JsonPrimitive((String)entry.getKey()));
/*     */   }
/*     */   
/*     */   private void push(Object newTop) {
/* 298 */     if (this.stackSize == this.stack.length) {
/* 299 */       int newLength = this.stackSize * 2;
/* 300 */       this.stack = Arrays.copyOf(this.stack, newLength);
/* 301 */       this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
/* 302 */       this.pathNames = Arrays.<String>copyOf(this.pathNames, newLength);
/*     */     } 
/* 304 */     this.stack[this.stackSize++] = newTop;
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 308 */     StringBuilder result = (new StringBuilder()).append('$');
/* 309 */     for (int i = 0; i < this.stackSize; i++) {
/* 310 */       if (this.stack[i] instanceof JsonArray) {
/* 311 */         if (++i < this.stackSize && this.stack[i] instanceof Iterator) {
/* 312 */           result.append('[').append(this.pathIndices[i]).append(']');
/*     */         }
/* 314 */       } else if (this.stack[i] instanceof JsonObject && 
/* 315 */         ++i < this.stackSize && this.stack[i] instanceof Iterator) {
/* 316 */         result.append('.');
/* 317 */         if (this.pathNames[i] != null) {
/* 318 */           result.append(this.pathNames[i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     return result.toString();
/*     */   }
/*     */   
/*     */   private String locationString() {
/* 327 */     return " at path " + getPath();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\bind\JsonTreeReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */