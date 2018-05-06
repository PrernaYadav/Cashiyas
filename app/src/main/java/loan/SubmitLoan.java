package loan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

/**
 * Created by JKB-2 on 4/1/2016.
 */
public class SubmitLoan extends Activity {
    Button loansbmt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_loan);
        loansbmt = (Button) findViewById(R.id.button_loansbmt);
        loansbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SubmitLoan.this, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
            }
        });
    }
}
