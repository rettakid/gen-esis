package za.co.rettakid.common.genesis.common;

import za.co.rettakid.common.genesis.enums.NamingStd;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class Utilz {

    public static String convertTo(String string, NamingStd namingStd) {
        switch (namingStd) {
            case PARCEL:
                string = String.format("%s%s", string.toUpperCase().charAt(0), string.substring(1, string.length()));
                break;
        }
        while (string.contains("_")) {
            String toUpperPart = string.substring(string.indexOf('_'), string.indexOf('_') + 2);
            string = string.replaceFirst(toUpperPart, String.valueOf(toUpperPart.toUpperCase().charAt(1)));
        }
        return string;
    }

}
