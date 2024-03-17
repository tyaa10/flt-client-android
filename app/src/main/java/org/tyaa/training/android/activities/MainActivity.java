package org.tyaa.training.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.tyaa.training.android.R;
import org.tyaa.training.android.adapters.RoleAdapter;
import org.tyaa.training.android.handlers.IResultHandler;
import org.tyaa.training.android.models.RoleModel;
import org.tyaa.training.android.repositories.RoleRepository;
import org.tyaa.training.android.repositories.interfaces.IRoleRepository;
import org.tyaa.training.android.utils.UIActionsRunner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private final IRoleRepository roleRepository = new RoleRepository();
    private List<RoleModel> roles;
    private RoleAdapter roleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // пустой список моделей ролей
        roles = new ArrayList<>();
        // создание адаптера, инициализированного: 1) ссылкой на текущую Activity,
        // от имени которой будут выполняться действия с экраном,
        // 2) идентификатором разметки пункта визуального списка,
        // 3) ссылкой на пустой список моделей ролей
        roleAdapter = new RoleAdapter(this, R.layout.activity_main_list_item, roles);
        // подключение настроенного адаптера к текущей Activity
        this.setListAdapter(roleAdapter);
        showRoles();
    }

    /**
     * Отображение всех возможных ролей пользователей на экране в виде списка
     * */
    private void showRoles() {
        // вызов метода получения всех возможных ролей пользователей
        // с дальнейшим их выводом на экран в виде списка
        roleRepository.getRoles(new IResultHandler<List<RoleModel>>() {
            @Override
            public void onSuccess(List<RoleModel> result) {
                if(roles.size() > 0) {
                    roles.clear();
                }
                // копирование списка ролей, полученных от сервера, в пустой список, подключённый к адаптеру
                roles.addAll(result);
                // запуск работы адаптера
                UIActionsRunner.run(roleAdapter::notifyDataSetChanged);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.println(Log.ERROR, "Ошибка", errorMessage);
                UIActionsRunner.run(() -> Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show());
            }
        });
    }
}