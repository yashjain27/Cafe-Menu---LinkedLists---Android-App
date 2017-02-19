package tech.ceece.hw_2_214_linkedlists;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrintMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_menu);

        TextView textView = new TextView(this);
        textView.setTextSize(10);
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setText(Statics.toBarista.toString(1) + Statics.toBarista1.toString(2));

        ViewGroup layout = (ViewGroup) findViewById(R.id.print_menu);
        layout.addView(textView);
    }
}
