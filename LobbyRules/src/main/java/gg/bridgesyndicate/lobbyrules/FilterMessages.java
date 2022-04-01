package gg.bridgesyndicate.lobbyrules;

public class FilterMessages {

    public static boolean containsBlacklistedWords(String message){
        message = modifyMessage(message); // to avoid bypassing filter attempts

        String[] blacklistedWords = fetchBlacklistedWords();
        int blacklistedWordsFound = 0;
        for (String wordChecked : blacklistedWords) {
            if (message.contains(wordChecked)) {
                blacklistedWordsFound++;
            }
        }
        return blacklistedWordsFound > 0;
    }

    private static String modifyMessage(String message) {
        message = message.replaceAll("1", "i") // remove leetspeak
                .replaceAll("!", "i")
                .replaceAll("3", "e")
                .replaceAll("4", "a")
                .replaceAll("@", "a")
                .replaceAll("5", "s")
                .replaceAll("7", "t")
                .replaceAll("0", "o")
                .replaceAll("9", "g");
        message = message.toLowerCase().replaceAll("[^a-zA-Z]", ""); // ignore non letters
        return message.replaceAll(" ", ""); // ignore spaces
    }

    private static String[] fetchBlacklistedWords(){
        return new String[]{
                "nigg",
                "fag"};
    }

}
