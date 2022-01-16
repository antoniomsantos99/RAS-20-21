package Model;

import java.util.Locale;
import java.util.ResourceBundle;

public class Idioma {

    Locale locale= new Locale("en", "US");

    ResourceBundle labels = ResourceBundle.getBundle("Languages.Example_EN", locale);

    System.out.println(labels.getString());

}
