package Resource;

import java.util.ListResourceBundle;

public class ResourceSpanish extends ListResourceBundle {
    public static final Object[][] contents = {
            {"no_rights", "No tiene permisos para usar este comando."},
            {"object_added", "El objeto se agregó con éxito."},
            {"went_wrong", "Algo salió mal."},
            {"object_updated", "El objeto se ha actualizado correctamente."},
            {"impossible_to_update", "No se puede actualizar el objeto."},
            {"deletion_completed", "La eliminación se completó correctamente."},
            {"impossible_to_delete", "No se puede eliminar."},
            {"not_minimal", "El objeto no es mínimo en la colección."},
            {"no_account", "No existe tal cuenta."},
            {"wrong_password", "Contraseña incorrecta."},
            {"success", "¡Éxito!"},
            {"new_account", "La nueva cuenta se ha registrado correctamente."},
            {"already_exists", "Esa cuenta ya existe."}
    };

    public Object[][] getContents() {
        return contents;
    }
}
