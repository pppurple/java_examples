import java.text.BreakIterator;

public class Main {
    public static void main(String[] args) {
        // 👨‍👨‍👦
        final String str = "\uD83D\uDC68\u200D\uD83D\uDC68\u200D\uD83D\uDC66";

        System.out.println(str);
        System.out.println("length: " + str.length());
        System.out.println("BreakIterator(java): " + getGraphemeLength(str));
        System.out.println("BreakIterator(ICU): " + getGraphemeLengthWithIcu(str));
        System.out.println("java13+: " + str.split("\\b{g}").length);

        // a + 🏻
        final String aAndSkinTone = "a" + "\uD83C\uDFFB";
        System.out.println(aAndSkinTone);
        System.out.println("length: " + aAndSkinTone.length());
        System.out.println("BreakIterator(java): " + getGraphemeLength(aAndSkinTone));
        System.out.println("BreakIterator(ICU): " + getGraphemeLengthWithIcu(aAndSkinTone));
        System.out.println("java13+: " + aAndSkinTone.split("\\b{g}").length);
    }

    public static int getGraphemeLength(String value) {
        final BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(value);
        int count = 0;
        while (it.next() != BreakIterator.DONE) {
            count++;
        }
        return count;
    }

    public static int getGraphemeLengthWithIcu(String value) {
        final com.ibm.icu.text.BreakIterator it = com.ibm.icu.text.BreakIterator.getCharacterInstance();
        it.setText(value);
        int count = 0;
        while (it.next() != BreakIterator.DONE) {
            count++;
        }
        return count;
    }
}
