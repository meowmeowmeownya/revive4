package com.google.gson;

import com.google.gson.stream.JsonReader;
import java.io.IOException;

public interface ToNumberStrategy {
  Number readNumber(JsonReader paramJsonReader) throws IOException;
}


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\com\google\gson\ToNumberStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */