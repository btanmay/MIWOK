package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//Find the view that shows the numbers category
        TextView numbers = (TextView) findViewById(R.id.numbers);
//set a onClicklistener on that view
        numbers.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                //create a new intent to open {@link NumbersActivity}
                Intent numbersIntent = new Intent(MainActivity.this,NumbersActivity.class);
                //start the new activity
                startActivity(numbersIntent);
            }
        });
//family
        TextView family = (TextView) findViewById(R.id.family);

        family.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent familyIntent = new Intent(MainActivity.this,FamilyActivity.class);

                startActivity(familyIntent);
            }
        });

//colors
        TextView colors = (TextView) findViewById(R.id.colors);

        colors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent colorsIntent = new Intent(MainActivity.this,ColorsActivity.class);

                startActivity(colorsIntent);
            }
        });
//phrases

        //Find the view that shows the phrases category
        TextView phrases = (TextView) findViewById(R.id.phrases);

        //set a clicklistener on that view
        phrases.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //create a new intent to open the{@link PhrasesActivity}
                Intent phrasesIntent = new Intent(MainActivity.this,PhrasesActivity.class);
                //start the new activity
                startActivity(phrasesIntent);
            }
        });

    }


}
