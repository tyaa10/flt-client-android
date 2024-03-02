package org.tyaa.training.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.tyaa.training.android.R;
import org.tyaa.training.android.handlers.IResultHandler;
import org.tyaa.training.android.repositories.RoleRepository;
import org.tyaa.training.android.repositories.interfaces.IRoleRepository;
import org.tyaa.training.android.utils.UIActionsRunner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

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
        roleRepository.getRoles(new IResultHandler<>() {
            @Override
            public void onSuccess(String result) {
                // Log.println(Log.DEBUG, "Роли", result);
                UIActionsRunner.run(() -> Toast.makeText(MainActivity.this, "Роли " + result, Toast.LENGTH_LONG)
                        .show());
            }

            @Override
            public void onFailure(String errorMessage) {
                // Log.println(Log.ERROR, "Ошибка", errorMessage);
                UIActionsRunner.run(() -> Toast.makeText(MainActivity.this, "Ошибка " + errorMessage, Toast.LENGTH_LONG)
                        .show());
            }
        });
    }
}