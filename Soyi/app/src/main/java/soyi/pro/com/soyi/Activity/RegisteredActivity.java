package soyi.pro.com.soyi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.apkfuns.logutils.LogUtils;

import soyi.pro.com.soyi.Logic.LogicJumpTo;
import soyi.pro.com.soyi.R;
import soyi.pro.com.soyi.Tools.SpUtils;
import soyi.pro.com.soyi.Tools.ToastUtils;

public class RegisteredActivity extends Activity implements View.OnClickListener {
    final static String TAG = RegisteredActivity.class.getName();
    private long exitTime = 0;
    Button loginButton;
    TextView findPwdTextView, RegTextView;
    ImageView KeyImage;
    EditText userPassword, userName;
    ToastUtils toastUtils;
    SpUtils spUtils;

    LogicJumpTo logicJumpTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LogUtils.d("--->RegisteredActivity onCreate");

        findId();
        init();
        setClickListener();
    }

    private void init() {
        logicJumpTo = LogicJumpTo.getInstance();

        loginButton.setText("注册账号^.^");
        RegTextView.setText("去登陆界面");
        findPwdTextView.setText("王亟亟");
        KeyImage.setImageResource(R.drawable.icon);
    }

    private void findId() {
        toastUtils = ToastUtils.getInstance();
        spUtils = SpUtils.getInstance();

        loginButton = (Button) findViewById(R.id.loginButton);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userName = (EditText) findViewById(R.id.userName);
        findPwdTextView = (TextView) findViewById(R.id.findPwdTextView);
        KeyImage = (ImageView) findViewById(R.id.KeyImage);
        RegTextView = (TextView) findViewById(R.id.RegTextView);
    }

    private void setClickListener() {
        loginButton.setOnClickListener(this);
        RegTextView.setOnClickListener(this);
    }

    private void RegisteredLogic() {
        if (checkEditText(userName, 6) && checkEditText(userPassword, 6)) {
            spUtils.putString(this, "userName", userName.getText().toString().trim());
            spUtils.putString(this, "userPassword", userPassword.getText().toString().trim());
            LogUtils.d("userName:  " + userName.getText().toString().trim() + " userPassword:  " + userPassword.getText().toString().trim());
            logicJumpTo.LoginToHomeActivity(RegisteredActivity.this, HomeActivity.class, userName.getText().toString().trim());
        } else {
            userName.setText("");
            userPassword.setText("");
            toastUtils.show(this, "输入内容不合逻辑", true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("--->RegisteredActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("--->RegisteredActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("--->RegisteredActivity onDestroy");
    }

    /*判断输入内容是否符合要求
    * @editText :需要进行校验的对象
    * @editTextSize :需要进行校验的文字长度
    * @return :true为合格，false为不合格
    * */
    private boolean checkEditText(EditText editText, int editTextSize) {
        String eText = editText.getText().toString().trim();
        if (editTextSize > 0) {
            if (eText.length() >= editTextSize & eText != null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (eText.length() >= 0 & eText != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //返回桌面
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                RegisteredLogic();
                break;
            case R.id.RegTextView:
                logicJumpTo.noValueJump(RegisteredActivity.this, LoginActivity.class);
                break;
        }
    }
}
