/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.math.BigDecimal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LazilyParsedNumber
/*     */   extends Number
/*     */ {
/*     */   private final String value;
/*     */   
/*     */   public LazilyParsedNumber(String value) {
/*  34 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*     */     try {
/*  40 */       return Integer.parseInt(this.value);
/*  41 */     } catch (NumberFormatException e) {
/*     */       try {
/*  43 */         return (int)Long.parseLong(this.value);
/*  44 */       } catch (NumberFormatException nfe) {
/*  45 */         return (new BigDecimal(this.value)).intValue();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*     */     try {
/*  53 */       return Long.parseLong(this.value);
/*  54 */     } catch (NumberFormatException e) {
/*  55 */       return (new BigDecimal(this.value)).longValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float floatValue() {
/*  61 */     return Float.parseFloat(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  66 */     return Double.parseDouble(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  71 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/*  80 */     return new BigDecimal(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/*  85 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     if (this == obj) {
/*  96 */       return true;
/*     */     }
/*  98 */     if (obj instanceof LazilyParsedNumber) {
/*  99 */       LazilyParsedNumber other = (LazilyParsedNumber)obj;
/* 100 */       return (this.value == other.value || this.value.equals(other.value));
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\LazilyParsedNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */