package org.padrewin.welcomehead.Utils;

public enum ImageChar
{
BLOCK('█'),
DARK_SHADE('▓'),
MEDIUM_SHADE('▒'),
LIGHT_SHADE('░');
  private char c;

  ImageChar(char c) {
this.c = c;
  }

  public char getChar() {
return this.c;
  }
}
