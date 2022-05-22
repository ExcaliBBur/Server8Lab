package Resource;

import java.util.ListResourceBundle;

public class ResourceRussian extends ListResourceBundle {
    public static final Object[][] contents = {
            {"no_rights", "У Вас нет прав для использования этой команды."},
            {"object_added", "Объект был успешно добавлен."},
            {"went_wrong", "Что-то пошло не так."},
            {"object_updated", "Объект был успешно обновлён."},
            {"impossible_to_update", "Невозможно обновить объект."},
            {"deletion_completed", "Удаление успешно завершено."},
            {"impossible_to_delete", "Удаление невозможно."},
            {"not_minimal", "Объект не минимальный в коллекции."},
            {"no_account", "Такого аккаунта не существует."},
            {"wrong_password", "Неправильный пароль."},
            {"success", "Успех!"},
            {"new_account", "Новый аккаунт был успешно зарегистрирован."},
            {"already_exists", "Такой аккаунт уже существует."}
    };

    public Object[][] getContents() {
        return contents;
    }
}
