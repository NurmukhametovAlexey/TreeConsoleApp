import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TreeEmulator {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        printDir(path, new ArrayList<PseudoGraphics>());
    }

    private static void printDir(String path, List<PseudoGraphics> parentLines) {

        File file = new File(path);
        if (!file.exists()) {
            System.err.println("No such file: " + file.toString());
            return;
        }

        for (int i = 0; i < parentLines.size(); i++) {
            PseudoGraphics graphics = parentLines.get(i);
            System.out.print(graphics.getSymbols());
            if (graphics.equals(PseudoGraphics.TRIPLE_CONNECTOR))
                parentLines.set(i, PseudoGraphics.VERT_LINE);
            else if (graphics.equals(PseudoGraphics.DOWN_RIGHT_CONNECTOR))
                parentLines.set(i, PseudoGraphics.EMPTY_SPACE);
        }

        System.out.println(file.getName());

        String[] dirs = file.list();

        if (dirs == null || dirs.length == 0)
            return;

        for (int i = 0; i < dirs.length - 1; i++) {
            List<PseudoGraphics> parentLinesIntermediate = new ArrayList<>(parentLines);
            parentLinesIntermediate.add(PseudoGraphics.TRIPLE_CONNECTOR);
            printDir(path + "\\" +  dirs[i], parentLinesIntermediate);
        }

        List<PseudoGraphics> parentLinesLast = new ArrayList<>(parentLines);
        parentLinesLast.add(PseudoGraphics.DOWN_RIGHT_CONNECTOR);
        printDir(path + "\\" + dirs[dirs.length - 1], parentLinesLast);
    }

    private enum PseudoGraphics {
        EMPTY_SPACE (new char[]{' ', ' ', ' ', ' '}),
        TRIPLE_CONNECTOR (new char[]{0x251C, 0x2500, 0x2500, 0x2500}),
        DOWN_RIGHT_CONNECTOR (new char[]{0x2514, 0x2500, 0x2500, 0x2500}),
        VERT_LINE (new char[]{0x2502, ' ', ' ', ' '});

        private char[] symbols;

        PseudoGraphics(char[] symbols) {
            this.symbols = symbols;
        }
        char[] getSymbols() {
            return symbols;
        }
    }
}