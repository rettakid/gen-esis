package za.co.rettakid.common.genesis.common;

import za.co.rettakid.common.genesis.enums.NamingStd;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class Utilz {

    public static String convertUnderscoreCaseTo(String string, NamingStd namingStd) {
        while (string.contains("_")) {
            String toUpperPart = string.substring(string.indexOf('_'), string.indexOf('_') + 2);
            string = string.replaceFirst(toUpperPart, String.valueOf(toUpperPart.toUpperCase().charAt(1)));
        }
        switch (namingStd) {
            case PARCEL:
                string = String.format("%s%s", string.toUpperCase().charAt(0), string.substring(1, string.length()));
                break;
        }
        return string;
    }

    public static String convertToUnderscoreCase(String string, NamingStd namingStd) {
        switch (namingStd) {
            case PARCEL:
                string = String.format("%s%s", string.toLowerCase().charAt(0), string.substring(1, string.length()));
                break;
        }
        Matcher matchDbName = Pattern.compile("([A-Z])").matcher(string);
        while (matchDbName.find()) {
            string = string.replace(matchDbName.group(), "_" + matchDbName.group().toLowerCase());
        }
        return string.toUpperCase();
    }

    /*TODO refractor into more methods*/
    public static Collection<String> addAll(Collection<String> array, String... arrayString) {
        array.addAll(Arrays.asList(arrayString));
        return array;
    }

    /*TODO refractor into more methods*/
    public static Set<String> createImports(Set<String> imports, String... arrayString) {
        imports.addAll(Arrays.asList(arrayString));
        Set<String> returnImports = new HashSet<>();
        for (String anImport : imports) {
            if (!anImport.contains("import "))   {
                anImport = "import " + anImport;
                if (anImport.substring(anImport.length() - 1).equals("."))   {
                    anImport = anImport + "*;";
                } else  {
                    anImport = anImport + ".*;";
                }
            }

            if (!anImport.contains(";"))   {
                anImport += ";";
            }
            returnImports.add(anImport);
        }
        imports = returnImports;
        return imports;
    }

}
