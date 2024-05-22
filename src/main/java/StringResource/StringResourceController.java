package main.java.StringResource;

import main.java.Controll.Controller;

import java.util.List;

public final class StringResourceController {
    private StringResourceController(){}
    public static final String FALSE = "false";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String WRONG_PLAYER = "Nem te vagy a soron következő játékos!";
    public static final String WRONG_ACTION = "Sikertelen művelet";
    public static final String INVALID_ACTION = "A műveletet nem lehet végrehajtani";
    public static final String GOOD_ACTION = "Sikeres művelet";
    public static final String WATER = "water";
    public static final String RANDOM_ON = "A véletlen események be lettek kapcsolva.";
    public static String stringBuilder(List<?> collection){
        StringBuilder builder = new StringBuilder("null");
        if(collection != null) {
            for (int i = 0; i < collection.size(); i++) {
                if (i == 0) builder.delete(0,4);
                builder.append(Controller.objectReverseNames.get(collection.get(i)));
                if (i != collection.size() - 1) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }
}