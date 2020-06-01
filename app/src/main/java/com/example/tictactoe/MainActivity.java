package com.example.tictactoe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final GridLayout Grid = findViewById(R.id.Grid);

        final int[][] winPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6,}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

        final Player red = new Player();
        red.setId("red");

        final Player yellow = new Player();
        yellow.setId("yellow");


        for (int i=0; i<Grid.getChildCount(); i++) {
            final int j = i;
            final ImageView child = (ImageView) Grid.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getCurrentPlayer(red, yellow) == yellow) {
                        child.setImageResource(R.drawable.red);
                        red.setTurnCount(red.getTurnCount()+1);
                        red.setPlayerPositions(j);
                    } else {
                        child.setImageResource(R.drawable.yellow);
                        yellow.setTurnCount(yellow.getTurnCount()+1);
                        yellow.setPlayerPositions(j);
                    }
                    v.animate().alpha(1f);

                    if (CheckWin(winPositions, getCurrentPlayer(red, yellow))){
                        announceWinner(getCurrentPlayer(red, yellow).getId());
                        resetGame(Grid, red, yellow);
                    };

                    if ((red.getTurnCount()+yellow.getTurnCount())==9) {
                        Toast.makeText(getApplicationContext(),"draw",Toast.LENGTH_SHORT).show();
                        resetGame(Grid,red,yellow);
                    }


                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext(), "hey", Toast.LENGTH_SHORT).show();
        int id = item.getItemId();

        if (id == R.id.action_name) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Player {
        private String id;
        private int turnCount = 0;
        private List<Integer> playerPositions = new ArrayList<>();

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setTurnCount(int turnCount) {
            this.turnCount = turnCount;
        }

        public int getTurnCount() {
            return turnCount;
        }

        public void setPlayerPositions(Integer i) {
            this.playerPositions.add(i);
        }

        public List<Integer> getPlayerPositions() {
            return playerPositions;
        }

        public void resetPlayer () {
            turnCount = 0;
            playerPositions.clear();
        }
    }

    public boolean CheckWin (int[][] winPositions, Player player) {
        for (int i=0; i<winPositions.length; i++) {
            if (isIncluded(winPositions[i], player.getPlayerPositions())) {
                return true;
            }
        }

        return false;
    }

    public Player getCurrentPlayer(Player red, Player yellow) {
        if (red.getTurnCount()==yellow.getTurnCount()) {
            return yellow;
        } else return red;
    }

    public void announceWinner(String id) {
        Toast.makeText(getApplicationContext(), "player "+id+" wins",Toast.LENGTH_LONG).show();

    }

    public boolean isIncluded(int[] position, List<Integer> list) {

        int c=0;
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (position[i] == list.get(j)) {
                    c++;
                }
            }
            if (c == 3) {
                return true;
            }
        }

        return false;
    }

    public void resetGame(GridLayout Grid, Player red, Player yellow) {
        for (int i=0; i<Grid.getChildCount(); i++) {
            Grid.getChildAt(i).animate().alpha(0f);
        }
        red.resetPlayer();
        yellow.resetPlayer();

    }

}