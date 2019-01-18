package fr.wcs.testullotitlebarhidden;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.library.PulseView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tvCoordX;
    private ImageView ivVibration;
    private ImageView ivColor;
    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    private static final String IMAGE_VIEW2_TAG = "color Logo";

    private float eventX;
    private String eventXString;
    private boolean isRight;
    PulseView pv;

    private boolean isColorSelected;
    private boolean isVibrationSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        implementEvents();
        isRight = false;
        isColorSelected = false;
        isVibrationSelected = false;

        pv = (PulseView)findViewById(R.id.pv);




        pv.startPulse();
        pv.setPulseSpawnPeriod(1000);
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {

        //textView.setTag(TEXT_VIEW_TAG);
        ivVibration = (ImageView) findViewById(R.id.image_view);
        ivVibration.setTag(IMAGE_VIEW_TAG);

        ivColor = (ImageView) findViewById(R.id.ivColor);
        ivColor.setTag(IMAGE_VIEW2_TAG);

        //button.setTag(BUTTON_VIEW_TAG);
    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged
      //  textView.setOnLongClickListener(this);
        ivVibration.setOnLongClickListener(this);
       // button.setOnLongClickListener(this);
        ivColor.setOnLongClickListener(this);

        //add or remove any layout view that you don't want to accept dragged view
        findViewById(R.id.bottom_layout).setOnDragListener(this);
        findViewById(R.id.left_layout).setOnDragListener(this);
        findViewById(R.id.right_layout).setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

        // Create a new ClipData.Item from the ImageView object's tag
        if (view == ivColor) isColorSelected =true;
        if (view == ivVibration) isVibrationSelected = true;


        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //  view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                /////////////////////////////////////////////
              //  view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();


                // Displays a message containing the dragged data.
               // Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();


//                if (event.getX()>300) vibration = true;
//                else vibration =false;
//                Toast.makeText(this, Float.toString(event.getX()), Toast.LENGTH_SHORT).show();


               // Toast.makeText(this, eventXString, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                //Faut inverser... Allez savoir pourquoi
//                if (owner == findViewById(R.id.left_layout)) vibration = true;
//                else vibration = false;
                owner.removeView(v);//remove the dragged view
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                if (container == findViewById(R.id.right_layout)) isRight = true;
                else isRight = false;
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE



                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult()) {

                   // Toast.makeText(this, event.getLocalState().toString(), Toast.LENGTH_SHORT).show();
                    if (isColorSelected && isRight)
                    {

                        pv.setPulseColor(Color.parseColor("#ff1a1a"));
                        //pv.setIconRes(Color.parseColor("#ff1a1a"));


//                        VectorMasterView heartVector = (VectorMasterView)
//                                findViewById(R.id.pulseCircle);
//
//// find the correct path using name
//                        PathModel outline = heartVector.getPathModelByName("outline");
//
//// set the stroke color
//                        outline.setStrokeColor(Color.parseColor("#ff1a1a"));

                    }

                    if(isColorSelected && !isRight)  pv.setPulseColor(Color.parseColor("#4682B4"));
                   // pv.setIconRes(Color.parseColor("#4682B4"));

                    //Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                    if (isVibrationSelected && isRight) {

                        if (Build.VERSION.SDK_INT >= 26) {
                            ((Vibrator) getSystemService(VIBRATOR_SERVICE))
                                    .vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
                        }

                    } else {

                       // Toast.makeText(this, "vibration desactivated", Toast.LENGTH_SHORT).show();
                    }

                }

                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();

                isVibrationSelected=false;
                isColorSelected=false;
                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


}
