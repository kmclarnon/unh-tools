module tk.quasar.unhtools {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.jsoup;
  requires com.google.common;
  requires org.controlsfx.controls;
  requires com.fasterxml.jackson.dataformat.xml;
  requires com.fasterxml.jackson.dataformat.csv;
  requires com.fasterxml.jackson.datatype.jdk8;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires hubspot.style;
  requires immutable.collection.encodings;
  requires immutables.exceptions;
  requires annotations;
  requires static value;

  opens tk.quasar.unhtools to javafx.fxml;
  opens tk.quasar.unhtools.parser.models to com.fasterxml.jackson.databind;
  exports tk.quasar.unhtools;
}