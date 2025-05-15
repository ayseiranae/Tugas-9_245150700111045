import java.text.NumberFormat;
import java.util.Locale;

class Type {
    public static void center(String text) {
        int tengah = (50 - text.length()) / 2;
        for (int i = 0; i < tengah; i++) {
            System.out.print(" ");
        }
        System.out.println(text);
    }    
    
class RpF {
        public static final Locale RUPIAH = new Locale.Builder().setLanguage("id").setRegion("ID").build();
        public static final NumberFormat IND = NumberFormat.getCurrencyInstance(RUPIAH);
    }
}