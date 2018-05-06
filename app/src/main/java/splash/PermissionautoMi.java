package splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bounside.mj.cashiya.R;

import acunt.SignIn;

/**
 * Created by Neeraj Sain on 11/4/2016.
 */

public class PermissionautoMi extends Activity {

    Button okl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_auto);
        okl = (Button) findViewById(R.id.movetonext1);

        okl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermissionautoMi.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
    }
}
