/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.;
/*     */ import com.google.gson.internal.JavaVersion;
/*     */ import com.google.gson.internal.PreJava9DateFormatProvider;
/*     */ import com.google.gson.internal.bind.util.ISO8601Utils;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultDateTypeAdapter<T extends Date>
/*     */   extends TypeAdapter<T>
/*     */ {
/*     */   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
/*     */   private final DateType<T> dateType;
/*     */   
/*     */   public static abstract class DateType<T extends Date>
/*     */   {
/*  52 */     public static final DateType<Date> DATE = new DateType<Date>(Date.class) {
/*     */         protected Date deserialize(Date date) {
/*  54 */           return date;
/*     */         }
/*     */       };
/*     */     
/*     */     private final Class<T> dateClass;
/*     */     
/*     */     protected DateType(Class<T> dateClass) {
/*  61 */       this.dateClass = dateClass;
/*     */     }
/*     */     
/*     */     protected abstract T deserialize(Date param1Date);
/*     */     
/*     */     private final TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> adapter) {
/*  67 */       return TypeAdapters.newFactory(this.dateClass, adapter);
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(String datePattern) {
/*  71 */       return createFactory(new DefaultDateTypeAdapter<T>(this, datePattern));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(int style) {
/*  75 */       return createFactory(new DefaultDateTypeAdapter<T>(this, style));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(int dateStyle, int timeStyle) {
/*  79 */       return createFactory(new DefaultDateTypeAdapter<T>(this, dateStyle, timeStyle));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createDefaultsAdapterFactory() {
/*  83 */       return createFactory(new DefaultDateTypeAdapter<T>(this, 2, 2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, String datePattern) {
/*  96 */     this.dateType = (DateType<T>).Gson.Preconditions.checkNotNull(dateType);
/*  97 */     this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
/*  98 */     if (!Locale.getDefault().equals(Locale.US)) {
/*  99 */       this.dateFormats.add(new SimpleDateFormat(datePattern));
/*     */     }
/*     */   }
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, int style) {
/* 104 */     this.dateType = (DateType<T>).Gson.Preconditions.checkNotNull(dateType);
/* 105 */     this.dateFormats.add(DateFormat.getDateInstance(style, Locale.US));
/* 106 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 107 */       this.dateFormats.add(DateFormat.getDateInstance(style));
/*     */     }
/* 109 */     if (JavaVersion.isJava9OrLater()) {
/* 110 */       this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(style));
/*     */     }
/*     */   }
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, int dateStyle, int timeStyle) {
/* 115 */     this.dateType = (DateType<T>).Gson.Preconditions.checkNotNull(dateType);
/* 116 */     this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
/* 117 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 118 */       this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
/*     */     }
/* 120 */     if (JavaVersion.isJava9OrLater()) {
/* 121 */       this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(dateStyle, timeStyle));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Date value) throws IOException {
/* 129 */     if (value == null) {
/* 130 */       out.nullValue();
/*     */       return;
/*     */     } 
/* 133 */     synchronized (this.dateFormats) {
/* 134 */       String dateFormatAsString = ((DateFormat)this.dateFormats.get(0)).format(value);
/* 135 */       out.value(dateFormatAsString);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/* 141 */     if (in.peek() == JsonToken.NULL) {
/* 142 */       in.nextNull();
/* 143 */       return null;
/*     */     } 
/* 145 */     Date date = deserializeToDate(in.nextString());
/* 146 */     return this.dateType.deserialize(date);
/*     */   }
/*     */   
/*     */   private Date deserializeToDate(String s) {
/* 150 */     synchronized (this.dateFormats) {
/* 151 */       for (DateFormat dateFormat : this.dateFormats) {
/*     */         try {
/* 153 */           return dateFormat.parse(s);
/* 154 */         } catch (ParseException parseException) {}
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 159 */       return ISO8601Utils.parse(s, new ParsePosition(0));
/* 160 */     } catch (ParseException e) {
/* 161 */       throw new JsonSyntaxException(s, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     DateFormat defaultFormat = this.dateFormats.get(0);
/* 168 */     if (defaultFormat instanceof SimpleDateFormat) {
/* 169 */       return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)defaultFormat).toPattern() + ')';
/*     */     }
/* 171 */     return "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\bind\DefaultDateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */