package tech.ceece.hw_2_214_linkedlists;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Varibles
    int server;
    String choice="";

    //OrderList objects
    OrderList barista1 = new OrderList();
    OrderList barista2 = new OrderList();
    OrderList[] barista = {barista1, barista2};

    //Order object
    Order clipboard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateListView();
        registerClickCallBack();
    }

    //Populating List view options for the menu app
    private void populateListView() {
        //Create list of items
        String[] menuChoices = {"Order", "Print Order Details", "Extra Credit", "Cursor Options", "Quit"};

        //Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, menuChoices) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                ViewGroup.LayoutParams params = view.getLayoutParams();

                params.height = 100;
                view.setLayoutParams(params);

                return view;
            }
        };

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    //Call back method for the list view, listening for a click in the list
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.listView);

        final Intent intent = new Intent(this, OrderMenu.class);
        final Intent intent1 = new Intent(this, PrintMenu.class);

        assert list != null;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {                    //Order placement
                    startActivityForResult(intent, 1);
                } else if (position == 1) {             //Print Menu
                    Statics.toBarista = barista[0];
                    Statics.toBarista1 = barista[1];
                    startActivity(intent1);
                } else if (position == 2) {             //Extra credit functions
                    //Create a Dialog
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.layout);
                    dialog.show();

                    //String of choices for extra credit options
                    final String[] choices = {"Reverse"};

                    //Create Number Picker objects for id.np1 and id.np2
                    final NumberPicker p1 = (NumberPicker) dialog.findViewById(R.id.np1);
                    final NumberPicker p2 = (NumberPicker) dialog.findViewById(R.id.np2);
                    p1.setMaxValue(2);
                    p1.setMinValue(1);
                    p2.setMaxValue(0);
                    p2.setMinValue(0);
                    p2.setDisplayedValues(choices);
                    p1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    p2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    //Button
                    Button b1 = (Button) dialog.findViewById(R.id.button2);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Get value for which Barista you want
                            server = p1.getValue();

                            //Get value for cursor option
                            int pos = p2.getValue();
                            choice = choices[pos];

                            switch (choice) {
                                case "Reverse":
                                    try {
                                        barista[server - 1] = barista[server - 1].reverseList();
                                    } catch (NullPointerException e) {
                                        Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                /*
                                case "Merge":
                                    //Merge method
                                    break;
                                    */
                            }
                            dialog.dismiss();
                        }
                    });

                } else if (position == 3) {             //Cursor Options
                    //Create a Dialog
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.layout);
                    dialog.show();

                    //String of choices for cursor options
                    final String[] choices = {"Forward", "Backward", "To Head", "To tail", "Remove", "Cut", "Paste"};

                    //Create Number Picker objects for id.np1 and id.np2
                    final NumberPicker p1 = (NumberPicker) dialog.findViewById(R.id.np1);
                    final NumberPicker p2 = (NumberPicker) dialog.findViewById(R.id.np2);
                    p1.setMaxValue(2);
                    p1.setMinValue(1);
                    p2.setMaxValue(6);
                    p2.setMinValue(0);
                    p2.setDisplayedValues(choices);
                    p1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    p2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                    //Button
                    Button b1 = (Button) dialog.findViewById(R.id.button2);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Get value for which Barista you want
                            server = p1.getValue();

                            //Get value for cursor option
                            int pos = p2.getValue();
                            choice = choices[pos];

                            //Switch statement to choose cursor option selected
                            switch (choice) {
                                case "Forward":
                                    try {
                                        barista[server-1].cursorForward(); //Move cursor forward
                                    } catch (EndOfListException e) {
                                        Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case "Backward":
                                    try {
                                        barista[server-1].cursorBackward(); //Move cursor backwards.
                                    } catch (EndOfListException e) {
                                        Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case "To Head":
                                    barista[server-1].resetCursorToHead(); //Cursor to head
                                    break;
                                case "To tail":
                                    barista[server-1].cursorToTail(); //Cursor to tail
                                    break;
                                case "Remove":
                                    try {
                                        barista[server-1].removeCursor(); //Node pointed by cursor is removed
                                    } catch (EndOfListException e) {
                                        Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case "Cut":
                                    try {
                                        clipboard = barista[server - 1].removeCursor();
                                        if(clipboard != null)
                                            Toast.makeText(MainActivity.this, clipboard.getOrder() + " is in clipboard.", Toast.LENGTH_SHORT).show();
                                    } catch (EndOfListException e) {
                                        Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case "Paste":
                                    if(clipboard != null) {
                                        try {
                                            barista[server - 1].insertAfterCursor(clipboard);
                                        } catch (IllegalArgumentException e) {
                                            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                        clipboard = null;
                                    }else Toast.makeText(MainActivity.this, "Nothing to paste", Toast.LENGTH_SHORT).show();
                            }

                            //Dismiss Dialog
                            dialog.dismiss();
                        }
                    });

                } else {                                                    //Exit
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                    a_builder.setMessage("Are you sure you want to exit?");
                    a_builder.setCancelable(true);

                    //Positive button
                    a_builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    //Negative button
                    a_builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Quit");
                    alert.show();


                }
            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(MainActivity.this, "Order added to " + data.getStringExtra("choice"), Toast.LENGTH_SHORT).show();

                server = data.getIntExtra("barista",0);

                //Create an Order object
                Order order = new Order(data.getStringExtra("drink"), data.getStringExtra("requests"), data.getDoubleExtra("price",0));

                switch (data.getStringExtra("choice")){
                    case "Front of List":
                        try {
                            barista[server - 1].addNewHead(order);
                        }catch (IllegalArgumentException e){
                            System.out.println(e);
                        }
                        break;
                    case "Back of List":
                        try {
                            barista[server-1].appendToTail(order);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e);
                        }
                        break;
                    case "After Cursor":
                        try {
                            barista[server - 1].insertAfterCursor(order);
                        }catch (IllegalArgumentException e) {
                            System.out.println(e);
                        }
                        break;
                    case "After Similar Order":
                        try {
                            barista[server-1].listSearch(order);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e);
                        } catch ( EndOfListException e){
                            System.out.println(e + ", order placed at the bottom");
                            barista[server-1].appendToTail(order);
                        }
                        break;
                    default:
                        try {
                            barista[server-1].appendToTail(order);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e);
                        }
                        break;
                }

            }
        }
    }

}

