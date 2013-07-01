package de.galimov.datagen.basic;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class NameLists {
    public static final List<String> FINNISH_LAST_NAME = asList("Korhonen", "Virtanen", "Mäkinen", "Nieminen", "Mäkelä", "Hämäläinen", "Laine",
            "Heikkinen", "Koskinen", "Järvinen", "Lehtonen", "Lehtinen", "Saarinen", "Salminen", "Heinonen", "Niemi", "Heikkilä", "Salonen",
            "Kinnunen", "Turunen", "Salo", "Laitinen", "Tuominen", "Rantanen", "Karjalainen", "Jokinen", "Mattila", "Savolainen", "Lahtinen",
            "Ahonen");

    public static final List<String> FINNISH_MALE_FIRST_NAME = asList("Elias", "Onni", "Eetu", "Leo", "Aleksi", "Leevi", "Veeti", "Joona", "Niilo",
            "Oliver", "Lauri", "Eino", "Eemeli", "Matias", "Lenni", "Mikael", "Viljami", "Jimi", "Akseli", "Julius", "Joel", "Rasmus", "Väinö",
            "Juho", "Vilho", "Valtteri", "Niklas", "Aapo", "Aatu", "Otto", "Daniel", "Aaro", "Konsta", "Peetu", "Peetu", "Eeli", "Miro", "Anton");

    public static final List<String> FINNISH_FEMALE_FIRST_NAME = asList("Sofia", "Venla", "Aada", "Emma", "Aino", "Ella", "Emilia", "Iida", "Siiri",
            "Anni", "Helmi", "Sara", "Veera", "Vilma", "Elli", "Elsa", "Lotta", "Olivia", "Viivi", "Kerttu", "Amanda", "Nella", "Iina", "Enni",
            "Emmi", "Lilja", "Pihla", "Oona", "Alisa", "Minea", "Ronja", "Milla", "Sanni", "Linnea", "Pinja", "Ilona", "Ellen", "Nelli", "Nea");

    public static final List<String> FINNISH_FIRST_NAME;

    static {
        FINNISH_FIRST_NAME = new ArrayList<String>(FINNISH_MALE_FIRST_NAME.size() + FINNISH_FEMALE_FIRST_NAME.size());
        FINNISH_FEMALE_FIRST_NAME.addAll(FINNISH_MALE_FIRST_NAME);
        FINNISH_FEMALE_FIRST_NAME.addAll(FINNISH_FEMALE_FIRST_NAME);
    }
}
