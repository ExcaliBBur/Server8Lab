package Resource;

import java.util.ListResourceBundle;

public class ResourcePortuguese extends ListResourceBundle {
    public static final Object[][] contents = {
            {"no_rights", "Você não tem permissão para usar este comando."},
            {"object_added", "O objeto foi adicionado com sucesso."},
            {"went_wrong", "Algo deu errado."},
            {"object_updated", "O objeto foi atualizado com sucesso."},
            {"impossible_to_update", "Não é possível atualizar o objeto."},
            {"deletion_completed", "A desinstalação foi concluída com êxito."},
            {"impossible_to_delete", "Não é possível excluir."},
            {"not_minimal", "O objeto não é mínimo na coleção."},
            {"no_account", "Essa conta não existe."},
            {"wrong_password", "Senha errada."},
            {"success", "Sucesso!"},
            {"new_account", "A nova conta foi registrada com sucesso."},
            {"already_exists", "Essa conta já existe."}
    };

    public Object[][] getContents() {
        return contents;
    }
}
