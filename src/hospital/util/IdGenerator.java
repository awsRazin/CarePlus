package hospital.util;

import java.util.List;

public final class IdGenerator {
    private IdGenerator() { }
    public static String next(String file, String prefix) {
        int highest = 0;
        for (String[] row : CsvUtil.read(file)) {
            if (row.length > 0 && row[0].startsWith(prefix)) try {
                highest = Math.max(highest, Integer.parseInt(row[0].substring(prefix.length())));
            } catch (NumberFormatException ignored) { }
        }
        return prefix + String.format("%03d", highest + 1);
    }
}
