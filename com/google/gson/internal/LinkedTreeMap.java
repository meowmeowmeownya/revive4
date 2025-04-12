/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LinkedTreeMap<K, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable
/*     */ {
/*  43 */   private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
/*     */       public int compare(Comparable<Comparable> a, Comparable b) {
/*  45 */         return a.compareTo(b);
/*     */       }
/*     */     };
/*     */   
/*     */   Comparator<? super K> comparator;
/*     */   Node<K, V> root;
/*  51 */   int size = 0;
/*  52 */   int modCount = 0;
/*     */ 
/*     */   
/*  55 */   final Node<K, V> header = new Node<K, V>();
/*     */   
/*     */   private EntrySet entrySet;
/*     */   
/*     */   private KeySet keySet;
/*     */ 
/*     */   
/*     */   public LinkedTreeMap() {
/*  63 */     this((Comparator)NATURAL_ORDER);
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
/*     */   public LinkedTreeMap(Comparator<? super K> comparator) {
/*  75 */     this
/*     */       
/*  77 */       .comparator = (comparator != null) ? comparator : (Comparator)NATURAL_ORDER;
/*     */   }
/*     */   
/*     */   public int size() {
/*  81 */     return this.size;
/*     */   }
/*     */   
/*     */   public V get(Object key) {
/*  85 */     Node<K, V> node = findByObject(key);
/*  86 */     return (node != null) ? node.value : null;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  90 */     return (findByObject(key) != null);
/*     */   }
/*     */   
/*     */   public V put(K key, V value) {
/*  94 */     if (key == null) {
/*  95 */       throw new NullPointerException("key == null");
/*     */     }
/*  97 */     Node<K, V> created = find(key, true);
/*  98 */     V result = created.value;
/*  99 */     created.value = value;
/* 100 */     return result;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 104 */     this.root = null;
/* 105 */     this.size = 0;
/* 106 */     this.modCount++;
/*     */ 
/*     */     
/* 109 */     Node<K, V> header = this.header;
/* 110 */     header.next = header.prev = header;
/*     */   }
/*     */   
/*     */   public V remove(Object key) {
/* 114 */     Node<K, V> node = removeInternalByKey(key);
/* 115 */     return (node != null) ? node.value : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node<K, V> find(K key, boolean create) {
/*     */     Node<K, V> created;
/* 125 */     Comparator<? super K> comparator = this.comparator;
/* 126 */     Node<K, V> nearest = this.root;
/* 127 */     int comparison = 0;
/*     */     
/* 129 */     if (nearest != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       Comparable<Object> comparableKey = (comparator == NATURAL_ORDER) ? (Comparable<Object>)key : null;
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 139 */         comparison = (comparableKey != null) ? comparableKey.compareTo(nearest.key) : comparator.compare(key, nearest.key);
/*     */ 
/*     */         
/* 142 */         if (comparison == 0) {
/* 143 */           return nearest;
/*     */         }
/*     */ 
/*     */         
/* 147 */         Node<K, V> child = (comparison < 0) ? nearest.left : nearest.right;
/* 148 */         if (child == null) {
/*     */           break;
/*     */         }
/*     */         
/* 152 */         nearest = child;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 157 */     if (!create) {
/* 158 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 162 */     Node<K, V> header = this.header;
/*     */     
/* 164 */     if (nearest == null) {
/*     */       
/* 166 */       if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
/* 167 */         throw new ClassCastException(key.getClass().getName() + " is not Comparable");
/*     */       }
/* 169 */       created = new Node<K, V>(nearest, key, header, header.prev);
/* 170 */       this.root = created;
/*     */     } else {
/* 172 */       created = new Node<K, V>(nearest, key, header, header.prev);
/* 173 */       if (comparison < 0) {
/* 174 */         nearest.left = created;
/*     */       } else {
/* 176 */         nearest.right = created;
/*     */       } 
/* 178 */       rebalance(nearest, true);
/*     */     } 
/* 180 */     this.size++;
/* 181 */     this.modCount++;
/*     */     
/* 183 */     return created;
/*     */   }
/*     */ 
/*     */   
/*     */   Node<K, V> findByObject(Object key) {
/*     */     try {
/* 189 */       return (key != null) ? find((K)key, false) : null;
/* 190 */     } catch (ClassCastException e) {
/* 191 */       return null;
/*     */     } 
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
/*     */   Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
/* 205 */     Node<K, V> mine = findByObject(entry.getKey());
/* 206 */     boolean valuesEqual = (mine != null && equal(mine.value, entry.getValue()));
/* 207 */     return valuesEqual ? mine : null;
/*     */   }
/*     */   
/*     */   private boolean equal(Object a, Object b) {
/* 211 */     return (a == b || (a != null && a.equals(b)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeInternal(Node<K, V> node, boolean unlink) {
/* 221 */     if (unlink) {
/* 222 */       node.prev.next = node.next;
/* 223 */       node.next.prev = node.prev;
/*     */     } 
/*     */     
/* 226 */     Node<K, V> left = node.left;
/* 227 */     Node<K, V> right = node.right;
/* 228 */     Node<K, V> originalParent = node.parent;
/* 229 */     if (left != null && right != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 240 */       Node<K, V> adjacent = (left.height > right.height) ? left.last() : right.first();
/* 241 */       removeInternal(adjacent, false);
/*     */       
/* 243 */       int leftHeight = 0;
/* 244 */       left = node.left;
/* 245 */       if (left != null) {
/* 246 */         leftHeight = left.height;
/* 247 */         adjacent.left = left;
/* 248 */         left.parent = adjacent;
/* 249 */         node.left = null;
/*     */       } 
/*     */       
/* 252 */       int rightHeight = 0;
/* 253 */       right = node.right;
/* 254 */       if (right != null) {
/* 255 */         rightHeight = right.height;
/* 256 */         adjacent.right = right;
/* 257 */         right.parent = adjacent;
/* 258 */         node.right = null;
/*     */       } 
/*     */       
/* 261 */       adjacent.height = Math.max(leftHeight, rightHeight) + 1;
/* 262 */       replaceInParent(node, adjacent); return;
/*     */     } 
/* 264 */     if (left != null) {
/* 265 */       replaceInParent(node, left);
/* 266 */       node.left = null;
/* 267 */     } else if (right != null) {
/* 268 */       replaceInParent(node, right);
/* 269 */       node.right = null;
/*     */     } else {
/* 271 */       replaceInParent(node, null);
/*     */     } 
/*     */     
/* 274 */     rebalance(originalParent, false);
/* 275 */     this.size--;
/* 276 */     this.modCount++;
/*     */   }
/*     */   
/*     */   Node<K, V> removeInternalByKey(Object key) {
/* 280 */     Node<K, V> node = findByObject(key);
/* 281 */     if (node != null) {
/* 282 */       removeInternal(node, true);
/*     */     }
/* 284 */     return node;
/*     */   }
/*     */   
/*     */   private void replaceInParent(Node<K, V> node, Node<K, V> replacement) {
/* 288 */     Node<K, V> parent = node.parent;
/* 289 */     node.parent = null;
/* 290 */     if (replacement != null) {
/* 291 */       replacement.parent = parent;
/*     */     }
/*     */     
/* 294 */     if (parent != null) {
/* 295 */       if (parent.left == node) {
/* 296 */         parent.left = replacement;
/*     */       } else {
/* 298 */         assert parent.right == node;
/* 299 */         parent.right = replacement;
/*     */       } 
/*     */     } else {
/* 302 */       this.root = replacement;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebalance(Node<K, V> unbalanced, boolean insert) {
/* 314 */     for (Node<K, V> node = unbalanced; node != null; node = node.parent) {
/* 315 */       Node<K, V> left = node.left;
/* 316 */       Node<K, V> right = node.right;
/* 317 */       int leftHeight = (left != null) ? left.height : 0;
/* 318 */       int rightHeight = (right != null) ? right.height : 0;
/*     */       
/* 320 */       int delta = leftHeight - rightHeight;
/* 321 */       if (delta == -2) {
/* 322 */         Node<K, V> rightLeft = right.left;
/* 323 */         Node<K, V> rightRight = right.right;
/* 324 */         int rightRightHeight = (rightRight != null) ? rightRight.height : 0;
/* 325 */         int rightLeftHeight = (rightLeft != null) ? rightLeft.height : 0;
/*     */         
/* 327 */         int rightDelta = rightLeftHeight - rightRightHeight;
/* 328 */         if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
/* 329 */           rotateLeft(node);
/*     */         } else {
/* 331 */           assert rightDelta == 1;
/* 332 */           rotateRight(right);
/* 333 */           rotateLeft(node);
/*     */         } 
/* 335 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 339 */       else if (delta == 2) {
/* 340 */         Node<K, V> leftLeft = left.left;
/* 341 */         Node<K, V> leftRight = left.right;
/* 342 */         int leftRightHeight = (leftRight != null) ? leftRight.height : 0;
/* 343 */         int leftLeftHeight = (leftLeft != null) ? leftLeft.height : 0;
/*     */         
/* 345 */         int leftDelta = leftLeftHeight - leftRightHeight;
/* 346 */         if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
/* 347 */           rotateRight(node);
/*     */         } else {
/* 349 */           assert leftDelta == -1;
/* 350 */           rotateLeft(left);
/* 351 */           rotateRight(node);
/*     */         } 
/* 353 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 357 */       else if (delta == 0) {
/* 358 */         node.height = leftHeight + 1;
/* 359 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       } else {
/*     */         
/* 364 */         assert delta == -1 || delta == 1;
/* 365 */         node.height = Math.max(leftHeight, rightHeight) + 1;
/* 366 */         if (!insert) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateLeft(Node<K, V> root) {
/* 377 */     Node<K, V> left = root.left;
/* 378 */     Node<K, V> pivot = root.right;
/* 379 */     Node<K, V> pivotLeft = pivot.left;
/* 380 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 383 */     root.right = pivotLeft;
/* 384 */     if (pivotLeft != null) {
/* 385 */       pivotLeft.parent = root;
/*     */     }
/*     */     
/* 388 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 391 */     pivot.left = root;
/* 392 */     root.parent = pivot;
/*     */ 
/*     */     
/* 395 */     root.height = Math.max((left != null) ? left.height : 0, 
/* 396 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/* 397 */     pivot.height = Math.max(root.height, 
/* 398 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateRight(Node<K, V> root) {
/* 405 */     Node<K, V> pivot = root.left;
/* 406 */     Node<K, V> right = root.right;
/* 407 */     Node<K, V> pivotLeft = pivot.left;
/* 408 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 411 */     root.left = pivotRight;
/* 412 */     if (pivotRight != null) {
/* 413 */       pivotRight.parent = root;
/*     */     }
/*     */     
/* 416 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 419 */     pivot.right = root;
/* 420 */     root.parent = pivot;
/*     */ 
/*     */     
/* 423 */     root.height = Math.max((right != null) ? right.height : 0, 
/* 424 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/* 425 */     pivot.height = Math.max(root.height, 
/* 426 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 433 */     EntrySet result = this.entrySet;
/* 434 */     return (result != null) ? result : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 438 */     KeySet result = this.keySet;
/* 439 */     return (result != null) ? result : (this.keySet = new KeySet());
/*     */   }
/*     */   
/*     */   static final class Node<K, V>
/*     */     implements Map.Entry<K, V> {
/*     */     Node<K, V> parent;
/*     */     Node<K, V> left;
/*     */     Node<K, V> right;
/*     */     Node<K, V> next;
/*     */     Node<K, V> prev;
/*     */     final K key;
/*     */     V value;
/*     */     int height;
/*     */     
/*     */     Node() {
/* 454 */       this.key = null;
/* 455 */       this.next = this.prev = this;
/*     */     }
/*     */ 
/*     */     
/*     */     Node(Node<K, V> parent, K key, Node<K, V> next, Node<K, V> prev) {
/* 460 */       this.parent = parent;
/* 461 */       this.key = key;
/* 462 */       this.height = 1;
/* 463 */       this.next = next;
/* 464 */       this.prev = prev;
/* 465 */       prev.next = this;
/* 466 */       next.prev = this;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 470 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 474 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V value) {
/* 478 */       V oldValue = this.value;
/* 479 */       this.value = value;
/* 480 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 485 */       if (o instanceof Map.Entry) {
/* 486 */         Map.Entry other = (Map.Entry)o;
/* 487 */         return (((this.key == null) ? (other.getKey() == null) : this.key.equals(other.getKey())) && ((this.value == null) ? (other
/* 488 */           .getValue() == null) : this.value.equals(other.getValue())));
/*     */       } 
/* 490 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 494 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ (
/* 495 */         (this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 499 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> first() {
/* 506 */       Node<K, V> node = this;
/* 507 */       Node<K, V> child = node.left;
/* 508 */       while (child != null) {
/* 509 */         node = child;
/* 510 */         child = node.left;
/*     */       } 
/* 512 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> last() {
/* 519 */       Node<K, V> node = this;
/* 520 */       Node<K, V> child = node.right;
/* 521 */       while (child != null) {
/* 522 */         node = child;
/* 523 */         child = node.right;
/*     */       } 
/* 525 */       return node;
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
/* 530 */     LinkedTreeMap.Node<K, V> next = LinkedTreeMap.this.header.next;
/* 531 */     LinkedTreeMap.Node<K, V> lastReturned = null;
/* 532 */     int expectedModCount = LinkedTreeMap.this.modCount;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 538 */       return (this.next != LinkedTreeMap.this.header);
/*     */     }
/*     */     
/*     */     final LinkedTreeMap.Node<K, V> nextNode() {
/* 542 */       LinkedTreeMap.Node<K, V> e = this.next;
/* 543 */       if (e == LinkedTreeMap.this.header) {
/* 544 */         throw new NoSuchElementException();
/*     */       }
/* 546 */       if (LinkedTreeMap.this.modCount != this.expectedModCount) {
/* 547 */         throw new ConcurrentModificationException();
/*     */       }
/* 549 */       this.next = e.next;
/* 550 */       return this.lastReturned = e;
/*     */     }
/*     */     
/*     */     public final void remove() {
/* 554 */       if (this.lastReturned == null) {
/* 555 */         throw new IllegalStateException();
/*     */       }
/* 557 */       LinkedTreeMap.this.removeInternal(this.lastReturned, true);
/* 558 */       this.lastReturned = null;
/* 559 */       this.expectedModCount = LinkedTreeMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   class EntrySet extends AbstractSet<Map.Entry<K, V>> {
/*     */     public int size() {
/* 565 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 569 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>() {
/*     */           public Map.Entry<K, V> next() {
/* 571 */             return nextNode();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 577 */       return (o instanceof Map.Entry && LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o) != null);
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 581 */       if (!(o instanceof Map.Entry)) {
/* 582 */         return false;
/*     */       }
/*     */       
/* 585 */       LinkedTreeMap.Node<K, V> node = LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o);
/* 586 */       if (node == null) {
/* 587 */         return false;
/*     */       }
/* 589 */       LinkedTreeMap.this.removeInternal(node, true);
/* 590 */       return true;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 594 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final class KeySet extends AbstractSet<K> {
/*     */     public int size() {
/* 600 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<K> iterator() {
/* 604 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>() {
/*     */           public K next() {
/* 606 */             return (nextNode()).key;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 612 */       return LinkedTreeMap.this.containsKey(o);
/*     */     }
/*     */     
/*     */     public boolean remove(Object key) {
/* 616 */       return (LinkedTreeMap.this.removeInternalByKey(key) != null);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 620 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 631 */     return new LinkedHashMap<K, V>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 636 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\internal\LinkedTreeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */