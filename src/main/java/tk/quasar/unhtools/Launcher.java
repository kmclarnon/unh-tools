package tk.quasar.unhtools;

public class Launcher {
  public static void main(String[] args) {
    // Avoid the idiotic JPMS restrictions
    Application.launch(args);
  }
}
