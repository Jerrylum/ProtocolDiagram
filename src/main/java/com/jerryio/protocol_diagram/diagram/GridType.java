package com.jerryio.protocol_diagram.diagram;

public enum GridType {
  EMPTY        ('\u0000'),
  HORIZONTAL   ('\u0001'),
  VERTICAL     ('\u0002'),
  TOP_RIGHT    ('\u0003'),
  BOTTOM_RIGHT ('\u0004'),
  BOTTOM_LEFT  ('\u0005'),
  TOP_LEFT     ('\u0006'),
  NON_TOP      ('\u0007'),
  NON_RIGHT    ('\u0008'),
  NON_BOTTOM   ('\u0009'),
  NON_LEFT     ('\u0010'),
  ALL          ('\u0011');

  private final char index;

  GridType(char index) {
    this.index = index;
  }

  public char toChar() {
    return this.index;
  }

}
