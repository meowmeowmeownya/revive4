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
/*    */ import java.sql.Time;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
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
/*    */ 
/*    */ final class SqlTimeTypeAdapter
/*    */   extends TypeAdapter<Time>
/*    */ {
/* 41 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 44 */         return (typeToken.getRawType() == Time.class) ? new SqlTimeTypeAdapter() : null;
/*    */       }
/*    */     };
/*    */   
/* 48 */   private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Time read(JsonReader in) throws IOException {
/* 54 */     if (in.peek() == JsonToken.NULL) {
/* 55 */       in.nextNull();
/* 56 */       return null;
/*    */     } 
/*    */     try {
/* 59 */       Date date = this.format.parse(in.nextString());
/* 60 */       return new Time(date.getTime());
/* 61 */     } catch (ParseException e) {
/* 62 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized void write(JsonWriter out, Time value) throws IOException {
/* 67 */     out.value((value == null) ? null : this.format.format(value));
/*    */   }
/*    */   
/*    */   private SqlTimeTypeAdapter() {}
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\sql\SqlTimeTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */