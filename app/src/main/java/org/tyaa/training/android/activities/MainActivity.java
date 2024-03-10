package org.tyaa.training.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.tyaa.training.android.R;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final IRoleRepository roleRepository = new RoleRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRoles();
    }

    /**
     * Отображение всех возможных ролей пользователей в выводе консоли
     * */
    private void showRoles() {
        // вызов метода получения всех возможных ролей пользователей
        // с дальнейшим их выводом в консоль
        roleRepository.getRoles(new IResultHandler<List<RoleModel>>() {
            @Override
            public void onSuccess(List<RoleModel> result) {
                    TextView rolesJson = findViewById(R.id.rolesJson);
                    UIActionsRunner.run(() -> rolesJson.setText(""));
                    for (int i = 0; i < result.size(); i++) {
                        final int index = i;
                        Log.println(Log.DEBUG, "Роли", String.format("Роль #%s: %s", result.get(index).id, result.get(index).name));
                        UIActionsRunner.run(() -> rolesJson.append(result.get(index).name + "; "));
                    }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.println(Log.ERROR, "Ошибка", errorMessage);
                UIActionsRunner.run(() -> Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show());
            }
        });
    }
}