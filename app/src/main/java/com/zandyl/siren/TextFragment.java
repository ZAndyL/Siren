package com.zandyl.siren;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by stevedavis on 15-06-13.
 */
public class TextFragment extends Fragment {

    EditText minputText;
    String input;
    public final static String INPUT_KEY = "input";



    public TextFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View textView = inflater.inflate(R.layout.fragment_text, container, false);

        Button talkButton = (Button)textView.findViewById(R.id.Talk);
        talkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talkButton();
            }
        });

        minputText = (EditText)textView.findViewById(R.id.inputText);

        final EditText editText = (EditText)textView.findViewById(R.id.inputText);

        final RelativeLayout rLayout = (RelativeLayout)textView.findViewById(R.id.relativeLayout);
        rLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (editText.isFocused()) {
                        Rect outRect = new Rect();
                        editText.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            editText.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
        return textView;
    }

    public void talkButton() {
        input = minputText.getText().toString();

        if (input.equals("")) {
            minputText.setError("Please enter some text");
            return;
        }

        Intent intent = new Intent(getActivity(), DisplayActivity.class);
        intent.putExtra(INPUT_KEY, input);
        startActivity(intent);

    }


}
