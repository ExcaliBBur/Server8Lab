package Resource;

import java.util.ListResourceBundle;

public class ResourceHungarian extends ListResourceBundle {
    public static final Object[][] contents = {
            {"no_rights", "Nincs joga használni ezt a parancsot."},
            {"object_added", "Az objektum sikeresen hozzá lett adva."},
            {"went_wrong", "Valami rosszul sült el."},
            {"object_updated", "Az objektum sikeresen frissült."},
            {"impossible_to_update", "Az objektum nem frissíthető."},
            {"deletion_completed", "A Törlés sikeresen befejeződött."},
            {"impossible_to_delete", "Törlés nem lehetséges."},
            {"not_minimal", "Az objektum nem minimális a gyűjteményben."},
            {"no_account", "Nincs ilyen számla."},
            {"wrong_password", "Helytelen jelszó."},
            {"success", "Siker!"},
            {"new_account", "Az Új fiók sikeresen regisztrálva lett."},
            {"already_exists", "Ilyen fiók már létezik."}
    };

    public Object[][] getContents() {
        return contents;
    }
}
