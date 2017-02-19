package tech.ceece.hw_2_214_linkedlists;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.InputMismatchException;

public class OrderMenu extends AppCompatActivity {
    String price, drink, specialRequest, choice="";
    double drinkPrice;
    private EditText editText, editText1, editText2; //EditText objects
    private TextView textView, textView1;
    int server = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText2);
        editText2 = (EditText) findViewById(R.id.editText3);
        textView = (TextView) findViewById(R.id.textView5);
        textView1 = (TextView) findViewById(R.id.textView6);
    }

    public void enter(View view){
        Intent returnIntent = new Intent();

            try {
                //Drink name
                drink = editText.getText().toString(); //type public static string drink
                if(drink.equals("")) throw new InputMismatchException("Please enter a drink name");
                else returnIntent.putExtra("drink",drink);

                //Special requests
                specialRequest = editText1.getText().toString();
                returnIntent.putExtra("requests", specialRequest);

                //Price
                price = editText2.getText().toString();
                drinkPrice = Double.parseDouble(price);
                returnIntent.putExtra("price",drinkPrice);

                //Server
                if(server != 0)
                    returnIntent.putExtra("barista", server);
                else
                    throw new InputMismatchException("Please select a server");

                //Order placement
                returnIntent.putExtra("choice",choice);

                //Return previous Activity
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            } catch (InputMismatchException ex){
                Toast.makeText(OrderMenu.this, ""+ ex, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException ex){
                Toast.makeText(OrderMenu.this, "Invalid input for price.", Toast.LENGTH_SHORT).show();
            }

    }

    //Dialog for server
    public void onSelectBarista(View v){
        //Create dialog
        final Dialog d = new Dialog(OrderMenu.this);
        d.setContentView(R.layout.dialog);

        //Number picker
        final NumberPicker noPicker = (NumberPicker) d.findViewById(R.id.numberPicker2) ;
        noPicker.setMinValue(1);
        noPicker.setMaxValue(2);
        noPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        noPicker.setWrapSelectorWheel(true);

        //Choose barista
        Button b1 = (Button) d.findViewById(R.id.onBarista);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Server
                server = noPicker.getValue();
                textView.setText("" + server);
                d.dismiss();

            }
        });
        d.show();
    }


    public void onPlacement(View v){
        //String of choices for the placement
        final String[] choices = {"Front of List", "Back of List", "After Cursor", "After Similar Order"};

        //Dialog box
        final Dialog dialog = new Dialog(OrderMenu.this);
        dialog.setTitle("Place order: ");
        dialog.setContentView(R.layout.dialog2);

        //Number picker
        final NumberPicker posPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker3);
        posPicker.setMinValue(0);
        posPicker.setMaxValue(3);
        posPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        posPicker.setDisplayedValues(choices);


        //Place order at a position
        Button b1 = (Button) dialog.findViewById(R.id.onPosition);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Position of the order
                int pos = posPicker.getValue();
                choice = choices[pos];
                textView1.setText("" + choice);
                dialog.dismiss();
            }

        });
        dialog.show();
    }


}
