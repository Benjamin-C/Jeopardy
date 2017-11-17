unsigned long next = 0;
int r = 0;
int y = 0;
int g = 0;
int b = 0;

void setup() {
    pinMode(9, INPUT_PULLUP);
    pinMode(10, INPUT_PULLUP);
    pinMode(11, INPUT_PULLUP);
    pinMode(12, INPUT_PULLUP);
    Serial.begin(250000);
}

void loop() {
   if(digitalRead(9) == LOW) {
    if(r <= 0) {
      Serial.println("9");
    }
    r = 3;
   } 
   if(y <= 0 && digitalRead(10) == LOW) {
     if(y <= 0) {
      Serial.println("a");
    }
    y = 3;
   } 
   if(g <= 0 && digitalRead(11) == LOW) {
     if(g <= 0) {
      Serial.println("b");
    }
    g = 3;
   } 
   if(b <= 0 && digitalRead(12) == LOW) {
     if(b <= 0) {
      Serial.println("c");
    }
    b = 3;
   }
   if(next < millis()) {
     if(r > -1) {
       r--;
     }
     if(y > -1) {
       y--;
     }
     if(g > -1) {
       g--;
     }
     if(b > -1) {
       b--;
     }
     next = millis() + 1;
   }
}
