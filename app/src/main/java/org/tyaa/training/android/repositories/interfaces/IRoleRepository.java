package org.tyaa.training.android.repositories.interfaces;

import org.tyaa.training.android.handlers.IResultHandler;
import org.tyaa.training.android.models.RoleModel;

import java.util.List;

/**
 * Абстракция репозитория ролей
 * */
public interface IRoleRepository {
    /**
     * Получить описание всех вариантов ролей пользователей в виде строки сырых данных
     * */
    void getRoles(IResultHandler<List<RoleModel>> handler);
}
