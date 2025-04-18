/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonIOException;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.internal.bind.TypeAdapters;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.google.gson.stream.MalformedJsonException;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Streams
/*     */ {
/*     */   private Streams() {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parse(JsonReader reader) throws JsonParseException {
/*  44 */     boolean isEmpty = true;
/*     */     try {
/*  46 */       reader.peek();
/*  47 */       isEmpty = false;
/*  48 */       return (JsonElement)TypeAdapters.JSON_ELEMENT.read(reader);
/*  49 */     } catch (EOFException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  54 */       if (isEmpty) {
/*  55 */         return (JsonElement)JsonNull.INSTANCE;
/*     */       }
/*     */       
/*  58 */       throw new JsonSyntaxException(e);
/*  59 */     } catch (MalformedJsonException e) {
/*  60 */       throw new JsonSyntaxException(e);
/*  61 */     } catch (IOException e) {
/*  62 */       throw new JsonIOException(e);
/*  63 */     } catch (NumberFormatException e) {
/*  64 */       throw new JsonSyntaxException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(JsonElement element, JsonWriter writer) throws IOException {
/*  72 */     TypeAdapters.JSON_ELEMENT.write(writer, element);
/*     */   }
/*     */   
/*     */   public static Writer writerForAppendable(Appendable appendable) {
/*  76 */     return (appendable instanceof Writer) ? (Writer)appendable : new AppendableWriter(appendable);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class AppendableWriter
/*     */     extends Writer
/*     */   {
/*     */     private final Appendable appendable;
/*     */     
/*  85 */     private final CurrentWrite currentWrite = new CurrentWrite();
/*     */     
/*     */     AppendableWriter(Appendable appendable) {
/*  88 */       this.appendable = appendable;
/*     */     }
/*     */     
/*     */     public void write(char[] chars, int offset, int length) throws IOException {
/*  92 */       this.currentWrite.chars = chars;
/*  93 */       this.appendable.append(this.currentWrite, offset, offset + length);
/*     */     }
/*     */     
/*     */     public void write(int i) throws IOException {
/*  97 */       this.appendable.append((char)i);
/*     */     }
/*     */     
/*     */     public void flush() {}
/*     */     
/*     */     public void close() {}
/*     */     
/*     */     static class CurrentWrite
/*     */       implements CharSequence {
/*     */       char[] chars;
/*     */       
/*     */       public int length() {
/* 109 */         return this.chars.length;
/*     */       }
/*     */       public char charAt(int i) {
/* 112 */         return this.chars[i];
/*     */       }
/*     */       public CharSequence subSequence(int start, int end) {
/* 115 */         return new String(this.chars, start, end - start);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\Streams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */