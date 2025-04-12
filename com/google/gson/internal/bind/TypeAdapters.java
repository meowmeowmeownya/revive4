/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonIOException;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import com.google.gson.internal.LazilyParsedNumber;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Calendar;
/*     */ import java.util.Currency;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeAdapters
/*     */ {
/*     */   private TypeAdapters() {
/*  66 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*  70 */   public static final TypeAdapter<Class> CLASS = (new TypeAdapter<Class>()
/*     */     {
/*     */       public void write(JsonWriter out, Class value) throws IOException {
/*  73 */         throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value
/*  74 */             .getName() + ". Forgot to register a type adapter?");
/*     */       }
/*     */       
/*     */       public Class read(JsonReader in) throws IOException {
/*  78 */         throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
/*     */       }
/*  81 */     }).nullSafe();
/*     */   
/*  83 */   public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
/*     */   
/*  85 */   public static final TypeAdapter<BitSet> BIT_SET = (new TypeAdapter<BitSet>() {
/*     */       public BitSet read(JsonReader in) throws IOException {
/*  87 */         BitSet bitset = new BitSet();
/*  88 */         in.beginArray();
/*  89 */         int i = 0;
/*  90 */         JsonToken tokenType = in.peek();
/*  91 */         while (tokenType != JsonToken.END_ARRAY) {
/*     */           boolean set; String stringValue;
/*  93 */           switch (tokenType) {
/*     */             case NUMBER:
/*  95 */               set = (in.nextInt() != 0);
/*     */               break;
/*     */             case BOOLEAN:
/*  98 */               set = in.nextBoolean();
/*     */               break;
/*     */             case STRING:
/* 101 */               stringValue = in.nextString();
/*     */               try {
/* 103 */                 set = (Integer.parseInt(stringValue) != 0);
/* 104 */               } catch (NumberFormatException e) {
/* 105 */                 throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + stringValue);
/*     */               } 
/*     */               break;
/*     */             
/*     */             default:
/* 110 */               throw new JsonSyntaxException("Invalid bitset value type: " + tokenType);
/*     */           } 
/* 112 */           if (set) {
/* 113 */             bitset.set(i);
/*     */           }
/* 115 */           i++;
/* 116 */           tokenType = in.peek();
/*     */         } 
/* 118 */         in.endArray();
/* 119 */         return bitset;
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, BitSet src) throws IOException {
/* 123 */         out.beginArray();
/* 124 */         for (int i = 0, length = src.length(); i < length; i++) {
/* 125 */           int value = src.get(i) ? 1 : 0;
/* 126 */           out.value(value);
/*     */         } 
/* 128 */         out.endArray();
/*     */       }
/* 130 */     }).nullSafe();
/*     */   
/* 132 */   public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
/*     */   
/* 134 */   public static final TypeAdapter<Boolean> BOOLEAN = new TypeAdapter<Boolean>()
/*     */     {
/*     */       public Boolean read(JsonReader in) throws IOException {
/* 137 */         JsonToken peek = in.peek();
/* 138 */         if (peek == JsonToken.NULL) {
/* 139 */           in.nextNull();
/* 140 */           return null;
/* 141 */         }  if (peek == JsonToken.STRING)
/*     */         {
/* 143 */           return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
/*     */         }
/* 145 */         return Boolean.valueOf(in.nextBoolean());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 149 */         out.value(value);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
/*     */       public Boolean read(JsonReader in) throws IOException {
/* 159 */         if (in.peek() == JsonToken.NULL) {
/* 160 */           in.nextNull();
/* 161 */           return null;
/*     */         } 
/* 163 */         return Boolean.valueOf(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 167 */         out.value((value == null) ? "null" : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 172 */   public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(boolean.class, (Class)Boolean.class, (TypeAdapter)BOOLEAN);
/*     */   
/* 174 */   public static final TypeAdapter<Number> BYTE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 177 */         if (in.peek() == JsonToken.NULL) {
/* 178 */           in.nextNull();
/* 179 */           return null;
/*     */         } 
/*     */         try {
/* 182 */           int intValue = in.nextInt();
/* 183 */           return Byte.valueOf((byte)intValue);
/* 184 */         } catch (NumberFormatException e) {
/* 185 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 190 */         out.value(value);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 195 */   public static final TypeAdapterFactory BYTE_FACTORY = newFactory(byte.class, (Class)Byte.class, (TypeAdapter)BYTE);
/*     */   
/* 197 */   public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 200 */         if (in.peek() == JsonToken.NULL) {
/* 201 */           in.nextNull();
/* 202 */           return null;
/*     */         } 
/*     */         try {
/* 205 */           return Short.valueOf((short)in.nextInt());
/* 206 */         } catch (NumberFormatException e) {
/* 207 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 212 */         out.value(value);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 217 */   public static final TypeAdapterFactory SHORT_FACTORY = newFactory(short.class, (Class)Short.class, (TypeAdapter)SHORT);
/*     */   
/* 219 */   public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 222 */         if (in.peek() == JsonToken.NULL) {
/* 223 */           in.nextNull();
/* 224 */           return null;
/*     */         } 
/*     */         try {
/* 227 */           return Integer.valueOf(in.nextInt());
/* 228 */         } catch (NumberFormatException e) {
/* 229 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 234 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 238 */   public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(int.class, (Class)Integer.class, (TypeAdapter)INTEGER);
/*     */   
/* 240 */   public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = (new TypeAdapter<AtomicInteger>() {
/*     */       public AtomicInteger read(JsonReader in) throws IOException {
/*     */         try {
/* 243 */           return new AtomicInteger(in.nextInt());
/* 244 */         } catch (NumberFormatException e) {
/* 245 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       public void write(JsonWriter out, AtomicInteger value) throws IOException {
/* 249 */         out.value(value.get());
/*     */       }
/* 251 */     }).nullSafe();
/*     */   
/* 253 */   public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
/*     */   
/* 255 */   public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = (new TypeAdapter<AtomicBoolean>() {
/*     */       public AtomicBoolean read(JsonReader in) throws IOException {
/* 257 */         return new AtomicBoolean(in.nextBoolean());
/*     */       }
/*     */       public void write(JsonWriter out, AtomicBoolean value) throws IOException {
/* 260 */         out.value(value.get());
/*     */       }
/* 262 */     }).nullSafe();
/*     */   
/* 264 */   public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
/*     */   
/* 266 */   public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = (new TypeAdapter<AtomicIntegerArray>() {
/*     */       public AtomicIntegerArray read(JsonReader in) throws IOException {
/* 268 */         List<Integer> list = new ArrayList<Integer>();
/* 269 */         in.beginArray();
/* 270 */         while (in.hasNext()) {
/*     */           try {
/* 272 */             int integer = in.nextInt();
/* 273 */             list.add(Integer.valueOf(integer));
/* 274 */           } catch (NumberFormatException e) {
/* 275 */             throw new JsonSyntaxException(e);
/*     */           } 
/*     */         } 
/* 278 */         in.endArray();
/* 279 */         int length = list.size();
/* 280 */         AtomicIntegerArray array = new AtomicIntegerArray(length);
/* 281 */         for (int i = 0; i < length; i++) {
/* 282 */           array.set(i, ((Integer)list.get(i)).intValue());
/*     */         }
/* 284 */         return array;
/*     */       }
/*     */       public void write(JsonWriter out, AtomicIntegerArray value) throws IOException {
/* 287 */         out.beginArray();
/* 288 */         for (int i = 0, length = value.length(); i < length; i++) {
/* 289 */           out.value(value.get(i));
/*     */         }
/* 291 */         out.endArray();
/*     */       }
/* 293 */     }).nullSafe();
/*     */   
/* 295 */   public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
/*     */   
/* 297 */   public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 300 */         if (in.peek() == JsonToken.NULL) {
/* 301 */           in.nextNull();
/* 302 */           return null;
/*     */         } 
/*     */         try {
/* 305 */           return Long.valueOf(in.nextLong());
/* 306 */         } catch (NumberFormatException e) {
/* 307 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 312 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 316 */   public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 319 */         if (in.peek() == JsonToken.NULL) {
/* 320 */           in.nextNull();
/* 321 */           return null;
/*     */         } 
/* 323 */         return Float.valueOf((float)in.nextDouble());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 327 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 331 */   public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 334 */         if (in.peek() == JsonToken.NULL) {
/* 335 */           in.nextNull();
/* 336 */           return null;
/*     */         } 
/* 338 */         return Double.valueOf(in.nextDouble());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 342 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 346 */   public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>()
/*     */     {
/*     */       public Character read(JsonReader in) throws IOException {
/* 349 */         if (in.peek() == JsonToken.NULL) {
/* 350 */           in.nextNull();
/* 351 */           return null;
/*     */         } 
/* 353 */         String str = in.nextString();
/* 354 */         if (str.length() != 1) {
/* 355 */           throw new JsonSyntaxException("Expecting character, got: " + str);
/*     */         }
/* 357 */         return Character.valueOf(str.charAt(0));
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Character value) throws IOException {
/* 361 */         out.value((value == null) ? null : String.valueOf(value));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 366 */   public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(char.class, (Class)Character.class, (TypeAdapter)CHARACTER);
/*     */   
/* 368 */   public static final TypeAdapter<String> STRING = new TypeAdapter<String>()
/*     */     {
/*     */       public String read(JsonReader in) throws IOException {
/* 371 */         JsonToken peek = in.peek();
/* 372 */         if (peek == JsonToken.NULL) {
/* 373 */           in.nextNull();
/* 374 */           return null;
/*     */         } 
/*     */         
/* 377 */         if (peek == JsonToken.BOOLEAN) {
/* 378 */           return Boolean.toString(in.nextBoolean());
/*     */         }
/* 380 */         return in.nextString();
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, String value) throws IOException {
/* 384 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 388 */   public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
/*     */       public BigDecimal read(JsonReader in) throws IOException {
/* 390 */         if (in.peek() == JsonToken.NULL) {
/* 391 */           in.nextNull();
/* 392 */           return null;
/*     */         } 
/*     */         try {
/* 395 */           return new BigDecimal(in.nextString());
/* 396 */         } catch (NumberFormatException e) {
/* 397 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, BigDecimal value) throws IOException {
/* 402 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 406 */   public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>() {
/*     */       public BigInteger read(JsonReader in) throws IOException {
/* 408 */         if (in.peek() == JsonToken.NULL) {
/* 409 */           in.nextNull();
/* 410 */           return null;
/*     */         } 
/*     */         try {
/* 413 */           return new BigInteger(in.nextString());
/* 414 */         } catch (NumberFormatException e) {
/* 415 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, BigInteger value) throws IOException {
/* 420 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 424 */   public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
/*     */   
/* 426 */   public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>()
/*     */     {
/*     */       public StringBuilder read(JsonReader in) throws IOException {
/* 429 */         if (in.peek() == JsonToken.NULL) {
/* 430 */           in.nextNull();
/* 431 */           return null;
/*     */         } 
/* 433 */         return new StringBuilder(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, StringBuilder value) throws IOException {
/* 437 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 442 */   public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
/*     */   
/* 444 */   public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>()
/*     */     {
/*     */       public StringBuffer read(JsonReader in) throws IOException {
/* 447 */         if (in.peek() == JsonToken.NULL) {
/* 448 */           in.nextNull();
/* 449 */           return null;
/*     */         } 
/* 451 */         return new StringBuffer(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, StringBuffer value) throws IOException {
/* 455 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 460 */   public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
/*     */   
/* 462 */   public static final TypeAdapter<URL> URL = new TypeAdapter<URL>()
/*     */     {
/*     */       public URL read(JsonReader in) throws IOException {
/* 465 */         if (in.peek() == JsonToken.NULL) {
/* 466 */           in.nextNull();
/* 467 */           return null;
/*     */         } 
/* 469 */         String nextString = in.nextString();
/* 470 */         return "null".equals(nextString) ? null : new URL(nextString);
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, URL value) throws IOException {
/* 474 */         out.value((value == null) ? null : value.toExternalForm());
/*     */       }
/*     */     };
/*     */   
/* 478 */   public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
/*     */   
/* 480 */   public static final TypeAdapter<URI> URI = new TypeAdapter<URI>()
/*     */     {
/*     */       public URI read(JsonReader in) throws IOException {
/* 483 */         if (in.peek() == JsonToken.NULL) {
/* 484 */           in.nextNull();
/* 485 */           return null;
/*     */         } 
/*     */         try {
/* 488 */           String nextString = in.nextString();
/* 489 */           return "null".equals(nextString) ? null : new URI(nextString);
/* 490 */         } catch (URISyntaxException e) {
/* 491 */           throw new JsonIOException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, URI value) throws IOException {
/* 496 */         out.value((value == null) ? null : value.toASCIIString());
/*     */       }
/*     */     };
/*     */   
/* 500 */   public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
/*     */   
/* 502 */   public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>()
/*     */     {
/*     */       public InetAddress read(JsonReader in) throws IOException {
/* 505 */         if (in.peek() == JsonToken.NULL) {
/* 506 */           in.nextNull();
/* 507 */           return null;
/*     */         } 
/*     */         
/* 510 */         return InetAddress.getByName(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, InetAddress value) throws IOException {
/* 514 */         out.value((value == null) ? null : value.getHostAddress());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 519 */   public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
/*     */   
/* 521 */   public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>()
/*     */     {
/*     */       public UUID read(JsonReader in) throws IOException {
/* 524 */         if (in.peek() == JsonToken.NULL) {
/* 525 */           in.nextNull();
/* 526 */           return null;
/*     */         } 
/* 528 */         return UUID.fromString(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, UUID value) throws IOException {
/* 532 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 536 */   public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);
/*     */   
/* 538 */   public static final TypeAdapter<Currency> CURRENCY = (new TypeAdapter<Currency>()
/*     */     {
/*     */       public Currency read(JsonReader in) throws IOException {
/* 541 */         return Currency.getInstance(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Currency value) throws IOException {
/* 545 */         out.value(value.getCurrencyCode());
/*     */       }
/* 547 */     }).nullSafe();
/* 548 */   public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
/*     */   
/* 550 */   public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>()
/*     */     {
/*     */       private static final String YEAR = "year";
/*     */       private static final String MONTH = "month";
/*     */       private static final String DAY_OF_MONTH = "dayOfMonth";
/*     */       private static final String HOUR_OF_DAY = "hourOfDay";
/*     */       private static final String MINUTE = "minute";
/*     */       private static final String SECOND = "second";
/*     */       
/*     */       public Calendar read(JsonReader in) throws IOException {
/* 560 */         if (in.peek() == JsonToken.NULL) {
/* 561 */           in.nextNull();
/* 562 */           return null;
/*     */         } 
/* 564 */         in.beginObject();
/* 565 */         int year = 0;
/* 566 */         int month = 0;
/* 567 */         int dayOfMonth = 0;
/* 568 */         int hourOfDay = 0;
/* 569 */         int minute = 0;
/* 570 */         int second = 0;
/* 571 */         while (in.peek() != JsonToken.END_OBJECT) {
/* 572 */           String name = in.nextName();
/* 573 */           int value = in.nextInt();
/* 574 */           if ("year".equals(name)) {
/* 575 */             year = value; continue;
/* 576 */           }  if ("month".equals(name)) {
/* 577 */             month = value; continue;
/* 578 */           }  if ("dayOfMonth".equals(name)) {
/* 579 */             dayOfMonth = value; continue;
/* 580 */           }  if ("hourOfDay".equals(name)) {
/* 581 */             hourOfDay = value; continue;
/* 582 */           }  if ("minute".equals(name)) {
/* 583 */             minute = value; continue;
/* 584 */           }  if ("second".equals(name)) {
/* 585 */             second = value;
/*     */           }
/*     */         } 
/* 588 */         in.endObject();
/* 589 */         return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Calendar value) throws IOException {
/* 594 */         if (value == null) {
/* 595 */           out.nullValue();
/*     */           return;
/*     */         } 
/* 598 */         out.beginObject();
/* 599 */         out.name("year");
/* 600 */         out.value(value.get(1));
/* 601 */         out.name("month");
/* 602 */         out.value(value.get(2));
/* 603 */         out.name("dayOfMonth");
/* 604 */         out.value(value.get(5));
/* 605 */         out.name("hourOfDay");
/* 606 */         out.value(value.get(11));
/* 607 */         out.name("minute");
/* 608 */         out.value(value.get(12));
/* 609 */         out.name("second");
/* 610 */         out.value(value.get(13));
/* 611 */         out.endObject();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 616 */   public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, (Class)GregorianCalendar.class, CALENDAR);
/*     */   
/* 618 */   public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>()
/*     */     {
/*     */       public Locale read(JsonReader in) throws IOException {
/* 621 */         if (in.peek() == JsonToken.NULL) {
/* 622 */           in.nextNull();
/* 623 */           return null;
/*     */         } 
/* 625 */         String locale = in.nextString();
/* 626 */         StringTokenizer tokenizer = new StringTokenizer(locale, "_");
/* 627 */         String language = null;
/* 628 */         String country = null;
/* 629 */         String variant = null;
/* 630 */         if (tokenizer.hasMoreElements()) {
/* 631 */           language = tokenizer.nextToken();
/*     */         }
/* 633 */         if (tokenizer.hasMoreElements()) {
/* 634 */           country = tokenizer.nextToken();
/*     */         }
/* 636 */         if (tokenizer.hasMoreElements()) {
/* 637 */           variant = tokenizer.nextToken();
/*     */         }
/* 639 */         if (country == null && variant == null)
/* 640 */           return new Locale(language); 
/* 641 */         if (variant == null) {
/* 642 */           return new Locale(language, country);
/*     */         }
/* 644 */         return new Locale(language, country, variant);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Locale value) throws IOException {
/* 649 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 653 */   public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
/*     */   
/* 655 */   public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>() { public JsonElement read(JsonReader in) throws IOException { String number; JsonArray array;
/*     */         JsonObject object;
/* 657 */         if (in instanceof JsonTreeReader) {
/* 658 */           return ((JsonTreeReader)in).nextJsonElement();
/*     */         }
/*     */         
/* 661 */         switch (in.peek()) {
/*     */           case STRING:
/* 663 */             return (JsonElement)new JsonPrimitive(in.nextString());
/*     */           case NUMBER:
/* 665 */             number = in.nextString();
/* 666 */             return (JsonElement)new JsonPrimitive((Number)new LazilyParsedNumber(number));
/*     */           case BOOLEAN:
/* 668 */             return (JsonElement)new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
/*     */           case NULL:
/* 670 */             in.nextNull();
/* 671 */             return (JsonElement)JsonNull.INSTANCE;
/*     */           case BEGIN_ARRAY:
/* 673 */             array = new JsonArray();
/* 674 */             in.beginArray();
/* 675 */             while (in.hasNext()) {
/* 676 */               array.add(read(in));
/*     */             }
/* 678 */             in.endArray();
/* 679 */             return (JsonElement)array;
/*     */           case BEGIN_OBJECT:
/* 681 */             object = new JsonObject();
/* 682 */             in.beginObject();
/* 683 */             while (in.hasNext()) {
/* 684 */               object.add(in.nextName(), read(in));
/*     */             }
/* 686 */             in.endObject();
/* 687 */             return (JsonElement)object;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 693 */         throw new IllegalArgumentException(); }
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, JsonElement value) throws IOException {
/* 698 */         if (value == null || value.isJsonNull()) {
/* 699 */           out.nullValue();
/* 700 */         } else if (value.isJsonPrimitive()) {
/* 701 */           JsonPrimitive primitive = value.getAsJsonPrimitive();
/* 702 */           if (primitive.isNumber()) {
/* 703 */             out.value(primitive.getAsNumber());
/* 704 */           } else if (primitive.isBoolean()) {
/* 705 */             out.value(primitive.getAsBoolean());
/*     */           } else {
/* 707 */             out.value(primitive.getAsString());
/*     */           }
/*     */         
/* 710 */         } else if (value.isJsonArray()) {
/* 711 */           out.beginArray();
/* 712 */           for (JsonElement e : value.getAsJsonArray()) {
/* 713 */             write(out, e);
/*     */           }
/* 715 */           out.endArray();
/*     */         }
/* 717 */         else if (value.isJsonObject()) {
/* 718 */           out.beginObject();
/* 719 */           for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)value.getAsJsonObject().entrySet()) {
/* 720 */             out.name(e.getKey());
/* 721 */             write(out, e.getValue());
/*     */           } 
/* 723 */           out.endObject();
/*     */         } else {
/*     */           
/* 726 */           throw new IllegalArgumentException("Couldn't write " + value.getClass());
/*     */         } 
/*     */       } }
/*     */   ;
/*     */ 
/*     */   
/* 732 */   public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
/*     */   
/*     */   private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
/* 735 */     private final Map<String, T> nameToConstant = new HashMap<String, T>();
/* 736 */     private final Map<T, String> constantToName = new HashMap<T, String>();
/*     */     
/*     */     public EnumTypeAdapter(Class<T> classOfT) {
/*     */       try {
/* 740 */         for (Field field : classOfT.getDeclaredFields()) {
/* 741 */           if (field.isEnumConstant())
/*     */           
/*     */           { 
/* 744 */             AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */                   public Void run() {
/* 746 */                     field.setAccessible(true);
/* 747 */                     return null;
/*     */                   }
/*     */                 });
/*     */             
/* 751 */             Enum enum_ = (Enum)field.get(null);
/* 752 */             String name = enum_.name();
/* 753 */             SerializedName annotation = field.<SerializedName>getAnnotation(SerializedName.class);
/* 754 */             if (annotation != null) {
/* 755 */               name = annotation.value();
/* 756 */               for (String alternate : annotation.alternate()) {
/* 757 */                 this.nameToConstant.put(alternate, (T)enum_);
/*     */               }
/*     */             } 
/* 760 */             this.nameToConstant.put(name, (T)enum_);
/* 761 */             this.constantToName.put((T)enum_, name); } 
/*     */         } 
/* 763 */       } catch (IllegalAccessException e) {
/* 764 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */     public T read(JsonReader in) throws IOException {
/* 768 */       if (in.peek() == JsonToken.NULL) {
/* 769 */         in.nextNull();
/* 770 */         return null;
/*     */       } 
/* 772 */       return this.nameToConstant.get(in.nextString());
/*     */     }
/*     */     
/*     */     public void write(JsonWriter out, T value) throws IOException {
/* 776 */       out.value((value == null) ? null : this.constantToName.get(value));
/*     */     }
/*     */   }
/*     */   
/* 780 */   public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 783 */         Class<? super T> rawType = typeToken.getRawType();
/* 784 */         if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
/* 785 */           return null;
/*     */         }
/* 787 */         if (!rawType.isEnum()) {
/* 788 */           rawType = rawType.getSuperclass();
/*     */         }
/* 790 */         return new TypeAdapters.EnumTypeAdapter<T>(rawType);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 796 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 799 */           return typeToken.equals(type) ? typeAdapter : null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 806 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 809 */           return (typeToken.getRawType() == type) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 812 */           return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
/* 819 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 822 */           Class<? super T> rawType = typeToken.getRawType();
/* 823 */           return (rawType == unboxed || rawType == boxed) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 826 */           return "Factory[type=" + boxed.getName() + "+" + unboxed
/* 827 */             .getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
/* 834 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 837 */           Class<? super T> rawType = typeToken.getRawType();
/* 838 */           return (rawType == base || rawType == sub) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 841 */           return "Factory[type=" + base.getName() + "+" + sub
/* 842 */             .getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T1> TypeAdapterFactory newTypeHierarchyFactory(final Class<T1> clazz, final TypeAdapter<T1> typeAdapter) {
/* 853 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T2> TypeAdapter<T2> create(Gson gson, TypeToken<T2> typeToken) {
/* 856 */           final Class<? super T2> requestedType = typeToken.getRawType();
/* 857 */           if (!clazz.isAssignableFrom(requestedType)) {
/* 858 */             return null;
/*     */           }
/* 860 */           return new TypeAdapter<T1>() {
/*     */               public void write(JsonWriter out, T1 value) throws IOException {
/* 862 */                 typeAdapter.write(out, value);
/*     */               }
/*     */               
/*     */               public T1 read(JsonReader in) throws IOException {
/* 866 */                 T1 result = (T1)typeAdapter.read(in);
/* 867 */                 if (result != null && !requestedType.isInstance(result)) {
/* 868 */                   throw new JsonSyntaxException("Expected a " + requestedType.getName() + " but was " + result
/* 869 */                       .getClass().getName());
/*     */                 }
/* 871 */                 return result;
/*     */               }
/*     */             };
/*     */         }
/*     */         public String toString() {
/* 876 */           return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\bind\TypeAdapters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */