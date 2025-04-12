/*    */ package com.google.gson.internal.sql;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Date;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SqlDateTypeAdapter
/*    */   extends TypeAdapter<Date>
/*    */ {
/* 39 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 42 */         return (typeToken.getRawType() == Date.class) ? 
/* 43 */           new SqlDateTypeAdapter() : null;
/*    */       }
/*    */     };
/*    */   
/* 47 */   private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Date read(JsonReader in) throws IOException {
/* 54 */     if (in.peek() == JsonToken.NULL) {
/* 55 */       in.nextNull();
/* 56 */       return null;
/*    */     } 
/*    */     try {
/* 59 */       long utilDate = this.format.parse(in.nextString()).getTime();
/* 60 */       return new Date(utilDate);
/* 61 */     } catch (ParseException e) {
/* 62 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void write(JsonWriter out, Date value) throws IOException {
/* 68 */     out.value((value == null) ? null : this.format.format(value));
/*    */   }
/*    */   
/*    */   private SqlDateTypeAdapter() {}
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\sql\SqlDateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */