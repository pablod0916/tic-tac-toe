package com.disgrow.www.tictactoebypablof;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe extends AppCompatActivity implements View.OnClickListener {

    private int widthScreen;
    private int heightScreen;
    private Drawable ex1, ex2, ex3, ex4, ex5, ex6;
    private Drawable circle1, circle2, circle3, circle4, circle5, circle6;
    private Drawable lineVertical, lineHorizontal, lineDiagonal1, lineDiagonal2;
    private ImageView imPos1, imPos2, imPos3, imPos4, imPos5, imPos6, imPos7, imPos8, imPos9;

    static final String EQUIS = "Equis";
    static final String CIRCLE = "Circle";
    static final String EMPTY = "Empty";

    static final String PLAYER_1 = "PERSON";
    static final String PLAYER_2 = "COMPUTER";

    static final int NO_RESULT = -1;
    static final int WIN = 1;
    static final int NO_LOSE = 2;

    private Player player1;
    private Player player2;

    private ArrayList<Position> arrayPos = new ArrayList<Position>();
    private int sizeCell;
    private int sizeImage;
    private Typeface font;
    private RelativeLayout rlBlackShadow;
    private LinearLayout llPlayerOneElection;
    private TextView tvTurn;
    private TextView tvThinking;
    private boolean gameOver;
    private boolean playAvailable = true;
    private ImageView imLineWin;
    private int movementCont = 0;
    private Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This line of code keeps portrait screen orientation*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.tic_tac_toe);

        /*I get the width and height of the screen to build all the
        views dynamically with the size screen of the device*/

        widthScreen = Methods.getWidthScreen(this);
        heightScreen = Methods.getHeightScreen(this, getResources());
        font = Typeface.createFromAsset(getAssets(), "font.otf");

        sizeCell = w(30);
        sizeImage = w(18);

        loadPieces();   // This method loads all the Drawables that will be used in the activity
        iniGrid();      // This method initializes the grid of tic tac toe
        iniMatriz();    // This method initializes an ArrayList of the objects "Position", to handle each element of the matrix and its current state.
        iniPlayerElection();  //  This method initializes the set of views that allow the player to a choice between X or O

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSubTitle = findViewById(R.id.tvSubTitle);
        tvTurn = findViewById(R.id.tvTurn);
        tvThinking = findViewById(R.id.tvThinking);
        btnTryAgain = findViewById(R.id.btnTryAgain);

        Methods.setTextViewProperties(tvTitle, w(10), getResources().getColor(R.color.black), font, getString(R.string.tic_tac_toe_challenge), 0, 0, 0, h(1));
        Methods.setTextViewProperties(tvSubTitle, w(6), getResources().getColor(R.color.dark_gray), font, getString(R.string.message2), 0, 0, 0, h(7));
        Methods.setTextViewProperties(tvTurn, w(10), getResources().getColor(R.color.black), font, "", 0, h(7), 0, 0);
        Methods.setTextViewProperties(tvThinking, w(7), getResources().getColor(R.color.dark_gray), font, "", 0, h(2), 0, 0);
        Methods.setParamsView(btnTryAgain,w(50),h(7));

        btnTryAgain.setTypeface(font);
        btnTryAgain.setTextSize(TypedValue.COMPLEX_UNIT_PX, w(7));
        Methods.setMargenes(btnTryAgain,0,h(2),0,0);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });

    }

    private void tryAgain() {
        startActivity(new Intent(getApplicationContext(),TicTacToe.class));
        finish();
    }

    private void iniPlayerElection() {

        rlBlackShadow = findViewById(R.id.rlBlackShadow);
        llPlayerOneElection = findViewById(R.id.llPlayerOneElection);
        TextView tvPlayerOneElection = findViewById(R.id.tvPlayerOneElection);

        RelativeLayout rlPlayerOneElectionO = findViewById(R.id.rlPlayerOneElectionO);
        RelativeLayout rlPlayerOneElectionX = findViewById(R.id.rlPlayerOneElectionX);

        ImageView imPlayerOneElectionO = findViewById(R.id.imPlayerOneElectionO);
        ImageView imPlayerOneElectionX = findViewById(R.id.imPlayerOneElectionX);

        Methods.setParamsView(llPlayerOneElection, w(80), 0);

        Methods.setTextViewProperties(
                tvPlayerOneElection,
                w(5),
                getResources().getColor(R.color.black),
                font,
                getString(R.string.quetions1),
                w(10),
                h(4),
                w(10),
                h(4)
        );

        Methods.setParamsView(rlPlayerOneElectionO, w(40), w(40), 0, 0, 0, h(4));
        Methods.setParamsView(rlPlayerOneElectionX, w(40), w(40), 0, 0, 0, h(4));

        imPlayerOneElectionO.setImageBitmap(Methods.drawableToBitmap(circle5, w(25), w(25)));
        imPlayerOneElectionX.setImageBitmap(Methods.drawableToBitmap(ex6, w(25), w(25)));

        rlBlackShadow.setOnClickListener(this);
        rlPlayerOneElectionO.setOnClickListener(this);
        rlPlayerOneElectionX.setOnClickListener(this);
    }

    private void iniGrid() {

        RelativeLayout rlPos1 = findViewById(R.id.rlPos1);
        RelativeLayout rlPos2 = findViewById(R.id.rlPos2);
        RelativeLayout rlPos3 = findViewById(R.id.rlPos3);
        RelativeLayout rlPos4 = findViewById(R.id.rlPos4);
        RelativeLayout rlPos5 = findViewById(R.id.rlPos5);
        RelativeLayout rlPos6 = findViewById(R.id.rlPos6);
        RelativeLayout rlPos7 = findViewById(R.id.rlPos7);
        RelativeLayout rlPos8 = findViewById(R.id.rlPos8);
        RelativeLayout rlPos9 = findViewById(R.id.rlPos9);

        Methods.setParamsView(rlPos1, sizeCell, sizeCell);
        Methods.setParamsView(rlPos2, sizeCell, sizeCell);
        Methods.setParamsView(rlPos3, sizeCell, sizeCell);
        Methods.setParamsView(rlPos4, sizeCell, sizeCell);
        Methods.setParamsView(rlPos5, sizeCell, sizeCell);
        Methods.setParamsView(rlPos6, sizeCell, sizeCell);
        Methods.setParamsView(rlPos7, sizeCell, sizeCell);
        Methods.setParamsView(rlPos8, sizeCell, sizeCell);
        Methods.setParamsView(rlPos9, sizeCell, sizeCell);

        rlPos1.setOnClickListener(this);
        rlPos2.setOnClickListener(this);
        rlPos3.setOnClickListener(this);
        rlPos4.setOnClickListener(this);
        rlPos5.setOnClickListener(this);
        rlPos6.setOnClickListener(this);
        rlPos7.setOnClickListener(this);
        rlPos8.setOnClickListener(this);
        rlPos9.setOnClickListener(this);

        imPos1 = findViewById(R.id.imPos1);
        imPos2 = findViewById(R.id.imPos2);
        imPos3 = findViewById(R.id.imPos3);
        imPos4 = findViewById(R.id.imPos4);
        imPos5 = findViewById(R.id.imPos5);
        imPos6 = findViewById(R.id.imPos6);
        imPos7 = findViewById(R.id.imPos7);
        imPos8 = findViewById(R.id.imPos8);
        imPos9 = findViewById(R.id.imPos9);

        imLineWin = findViewById(R.id.lineWin);

    }

    private void iniMatriz() {
        arrayPos.add(new Position(imPos1, 0, EMPTY));
        arrayPos.add(new Position(imPos2, 1, EMPTY));
        arrayPos.add(new Position(imPos3, 2, EMPTY));
        arrayPos.add(new Position(imPos4, 3, EMPTY));
        arrayPos.add(new Position(imPos5, 4, EMPTY));
        arrayPos.add(new Position(imPos6, 5, EMPTY));
        arrayPos.add(new Position(imPos7, 6, EMPTY));
        arrayPos.add(new Position(imPos8, 7, EMPTY));
        arrayPos.add(new Position(imPos9, 8, EMPTY));
    }

    private void loadPieces() {

        ex1 = this.getResources().getDrawable(R.drawable.ic_ex1);
        ex2 = this.getResources().getDrawable(R.drawable.ic_ex2);
        ex3 = this.getResources().getDrawable(R.drawable.ic_ex3);
        ex4 = this.getResources().getDrawable(R.drawable.ic_ex4);
        ex5 = this.getResources().getDrawable(R.drawable.ic_ex5);
        ex6 = this.getResources().getDrawable(R.drawable.ic_ex6);

        circle1 = this.getResources().getDrawable(R.drawable.ic_circle1);
        circle2 = this.getResources().getDrawable(R.drawable.ic_circle2);
        circle3 = this.getResources().getDrawable(R.drawable.ic_circle3);
        circle4 = this.getResources().getDrawable(R.drawable.ic_circle4);
        circle5 = this.getResources().getDrawable(R.drawable.ic_circle5);
        circle6 = this.getResources().getDrawable(R.drawable.ic_circle6);

        lineHorizontal = this.getResources().getDrawable(R.drawable.ic_line_horizontal);
        lineVertical = this.getResources().getDrawable(R.drawable.ic_line_vertical);
        lineDiagonal1 = this.getResources().getDrawable(R.drawable.ic_line_diag1);
        lineDiagonal2 = this.getResources().getDrawable(R.drawable.ic_line_diag2);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rlPos1) {
            doValidation(0);
        } else if (v.getId() == R.id.rlPos2) {
            doValidation(1);
        } else if (v.getId() == R.id.rlPos3) {
            doValidation(2);
        } else if (v.getId() == R.id.rlPos4) {
            doValidation(3);
        } else if (v.getId() == R.id.rlPos5) {
            doValidation(4);
        } else if (v.getId() == R.id.rlPos6) {
            doValidation(5);
        } else if (v.getId() == R.id.rlPos7) {
            doValidation(6);
        } else if (v.getId() == R.id.rlPos8) {
            doValidation(7);
        } else if (v.getId() == R.id.rlPos9) {
            doValidation(8);
        } else if (v.getId() == R.id.rlBlackShadow) {

        } else if (v.getId() == R.id.rlPlayerOneElectionO) {
            choice(1);
        } else if (v.getId() == R.id.rlPlayerOneElectionX) {
            choice(2);
        }

    }

    private void choice(int i) {

        if (i == 1) {
            player1 = new Player(1, PLAYER_1, CIRCLE, true);
            player2 = new Player(2, PLAYER_2, EQUIS, false);
        } else if (i == 2) {
            player1 = new Player(1, PLAYER_1, EQUIS, true);
            player2 = new Player(2, PLAYER_2, CIRCLE, false);
        }

        rlBlackShadow.setVisibility(View.GONE);
        llPlayerOneElection.setVisibility(View.GONE);
        tvTurn.setText(getText(R.string.turn_person));
        tvThinking.setText(getText(R.string.you_are_thinking));
    }

    private void doValidation(int i) {
        /*This validation is to make sure, the position selected is empty*/
        if (arrayPos.get(i).getState().equals(EMPTY) && playAvailable && !gameOver) {
            changePositionState(i);
        }
    }

    private void changePositionState(int i) {
        /*It updates the state of each position of the grid*/
        if (player1.isCurrentTurn()) {
            arrayPos.get(i).setState(player1.getExOrCircle());
            Drawable drawable = getDrawable(player1.getExOrCircle(), getRandomNumberInRange(1, 6));
            arrayPos.get(i).getImage().setImageBitmap(Methods.drawableToBitmap(drawable, sizeImage, sizeImage));
            changeCurrentTurn(player1.getId());
        } else {
            arrayPos.get(i).setState(player2.getExOrCircle());
            Drawable drawable = getDrawable(player2.getExOrCircle(), getRandomNumberInRange(1, 6));
            arrayPos.get(i).getImage().setImageBitmap(Methods.drawableToBitmap(drawable, sizeImage, sizeImage));
            changeCurrentTurn(player2.getId());
        }

        /*After every move, the system has to make sure if there is a winner*/
        scannerWinner();

    }

    private void scannerWinner() {

        movementCont ++;

        String s = "";

        if (player1.isCurrentTurn()) {
            s = player2.getExOrCircle();
        } else {
            s = player1.getExOrCircle();
        }

        String st1 = "0,1,2";
        String st2 = "3,4,5";
        String st3 = "6,7,8";
        String st4 = "0,3,6";
        String st5 = "1,4,7";
        String st6 = "2,5,8";
        String st7 = "0,4,8";
        String st8 = "2,4,6";

        if (scannerString(s, st1)) {
            int height = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineHorizontal, w(80), height));
            Methods.setMargenes(imLineWin, w(5), w(15) - (height / 2), 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st2)) {
            int height = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineHorizontal, w(80), height));
            Methods.setMargenes(imLineWin, w(5), w(45) - (height / 2), 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st3)) {
            int height = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineHorizontal, w(80), height));
            Methods.setMargenes(imLineWin, w(5), w(75) - (height / 2), 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st4)) {
            int widht = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineVertical, widht, w(80)));
            Methods.setMargenes(imLineWin, w(15) - (widht / 2), 0, 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st5)) {
            int widht = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineVertical, widht, w(80)));
            Methods.setMargenes(imLineWin, w(45) - (widht / 2), 0, 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st6)) {
            int widht = (int) (w(80) * 0.14375f);
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineVertical, widht, w(80)));
            Methods.setMargenes(imLineWin, w(75) - (widht / 2), 0, 0, 0);
            gameIsOver(s);
        } else if (scannerString(s, st7)) {
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineDiagonal1, w(80), w(80)));
            gameIsOver(s);
        } else if (scannerString(s, st8)) {
            imLineWin.setImageBitmap(Methods.drawableToBitmap(lineDiagonal2, w(80), w(80)));
            Methods.setMargenes(imLineWin,w(10),0,0,0);
            gameIsOver(s);
        }else{
            if(movementCont == 9){
                tvTurn.setText(R.string.tie_message);
                tvThinking.setVisibility(View.GONE);
                btnTryAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    private void gameIsOver(String s) {

        /*When the system found a winner provide
        the user the option to play again*/

        if(s.equals(player2.isCurrentTurn())){
            tvTurn.setText(R.string.you_win);
            tvThinking.setVisibility(View.GONE);
            btnTryAgain.setVisibility(View.VISIBLE);
        }else{
            tvTurn.setText(R.string.you_lost);
            tvThinking.setVisibility(View.GONE);
            btnTryAgain.setVisibility(View.VISIBLE);
        }
        gameOver = true;
    }

    private boolean scannerString(String s, String str) {

        int cont = 0;
        String array[] = str.split(",");
        for (String string : array) {
            int i = Integer.parseInt(string);
            if (s.equals(arrayPos.get(i).getState())) {
                cont++;
            }
        }

        if (cont == 3)
            return true;
        else
            return false;

    }

    private void changeCurrentTurn(int id) {

        if (id == 1) {
            player1.setCurrentTurn(false);
            player2.setCurrentTurn(true);
            tvTurn.setText(getText(R.string.turn_machine));
            tvThinking.setText(getText(R.string.machine_is_thinking));
            playAvailable = false;
            delay(getRandomNumberInRange(1, 3));
        } else if (id == 2) {
            player1.setCurrentTurn(true);
            player2.setCurrentTurn(false);
            tvTurn.setText(getText(R.string.turn_person));
            tvThinking.setText(getText(R.string.you_are_thinking));
            playAvailable = true;
        }

    }

    private void delay(int i) {

        /*This delay tries to represent the machine is thinking its next
        key movement, it could take 500, 1000, 1500 milliseconds chosen
        randomly to seems more realistic.*/

        Runnable r = new Runnable() {
            @Override
            public void run() {
                artificialIntelligence();
            }
        };

        int milliseconds = 0;

        if (i == 1) {
            milliseconds = 500;
        } else if (i == 2) {
            milliseconds = 1000;
        } else if (i == 3) {
            milliseconds = 1500;
        }

        Handler h = new Handler();
        h.postDelayed(r, milliseconds);

    }


    private void artificialIntelligence() {

        /*This is the algorithm created to
        make unbeatable the machine*/

        boolean w = win();
        if (!w) {
            boolean nl = notLose();
            if (!nl) {
                findNeighbor();
            }
        }

    }

    private boolean win() {

        int sh = scannerHorizontal(WIN);
        int sv = scannerVertical(WIN);
        int sd = scannerDiagonal(WIN);

        if (sh != NO_RESULT) {
            changePositionState(sh);
            return true;
        } else if (sv != NO_RESULT) {
            changePositionState(sv);
            return true;
        } else if (sd != NO_RESULT) {
            changePositionState(sd);
            return true;
        }

        return false;

    }

    private boolean notLose() {

        int sh = scannerHorizontal(NO_LOSE);
        int sv = scannerVertical(NO_LOSE);
        int sd = scannerDiagonal(NO_LOSE);

        if (sh != NO_RESULT) {
            changePositionState(sh);
            return true;
        } else if (sv != NO_RESULT) {
            changePositionState(sv);
            return true;
        } else if (sd != NO_RESULT) {
            changePositionState(sd);
            return true;
        }

        return false;

    }

    private void findNeighbor() {

        String s = player2.getExOrCircle();
        int id = NO_RESULT;

        for (Position p : arrayPos) {
            if (p.getState().equals(s)) {
                id = p.getPosition();
            }
        }

        if (id != NO_RESULT) {
            String neighbors = getNeighbors(id);

            if (!neighbors.equals("")) {
                String arraNeig[] = neighbors.split(",");
                int rand;
                for (int i = 0; i < arraNeig.length; i++) {
                    rand = getRandomNumberInRange(0, (arraNeig.length - 1));
                    if (arrayPos.get(Integer.parseInt(arraNeig[rand])).getState().equals(EMPTY)) {
                        changePositionState(Integer.parseInt(arraNeig[rand]));
                        break;
                    } else if (arrayPos.get(i).getState().equals(EMPTY)) {
                        changePositionState(i);
                        break;
                    }
                }
            }

        } else {
            doFirstMovement();
        }

    }

    private String getNeighbors(int id) {

        if (id == 0) {
            return "1,2,3,6,4,8";
        } else if (id == 1) {
            return "0,2,4,7";
        } else if (id == 2) {
            return "0,1,5,8,4,6";
        } else if (id == 3) {
            return "0,6,4,5";
        } else if (id == 4) {
            return "1,7,3,5,0,8,2,6";
        } else if (id == 5) {
            return "3,4,2,8";
        } else if (id == 6) {
            return "0,3,7,8,4,2";
        } else if (id == 7) {
            return "1,4,6,8";
        } else if (id == 8) {
            return "2,5,6,7,0,4";
        }

        return "";
    }

    private void doFirstMovement() {

        String movPossible = "";

        if (arrayPos.get(0).getState().equals(player1.getExOrCircle()) || arrayPos.get(2).getState().equals(player1.getExOrCircle()) || arrayPos.get(6).getState().equals(player1.getExOrCircle()) || arrayPos.get(8).getState().equals(player1.getExOrCircle())) {
            movPossible = "1,4,7";
        } else if (arrayPos.get(4).getState().equals(player1.getExOrCircle())) {
            movPossible = "0,2,6,8";
        } else if (arrayPos.get(1).getState().equals(player1.getExOrCircle()) || arrayPos.get(3).getState().equals(player1.getExOrCircle()) || arrayPos.get(5).getState().equals(player1.getExOrCircle()) || arrayPos.get(7).getState().equals(player1.getExOrCircle())) {
            movPossible = "0,2,4,6,8";
        }

        String arrayMp[] = movPossible.split(",");
        int rand = getRandomNumberInRange(0, ((arrayMp.length) - 1));
        int i = Integer.parseInt(arrayMp[rand]);
        changePositionState(i);

    }

    private int scannerHorizontal(int i) {

        String s = "";

        if (i == WIN) {
            s = player2.getExOrCircle();
        } else if (i == NO_LOSE) {
            s = player1.getExOrCircle();
        }

        if (arrayPos.get(0).getState().equals(s) && arrayPos.get(1).getState().equals(s)) {
            if (arrayPos.get(2).getState().equals(EMPTY))
                return 2;
        }

        if (arrayPos.get(1).getState().equals(s) && arrayPos.get(2).getState().equals(s)) {
            if (arrayPos.get(0).getState().equals(EMPTY))
                return 0;
        }

        if (arrayPos.get(2).getState().equals(s) && arrayPos.get(0).getState().equals(s)) {
            if (arrayPos.get(1).getState().equals(EMPTY))
                return 1;
        }

        //----------------------------------------------------//

        if (arrayPos.get(3).getState().equals(s) && arrayPos.get(4).getState().equals(s)) {
            if (arrayPos.get(5).getState().equals(EMPTY))
                return 5;
        }

        if (arrayPos.get(4).getState().equals(s) && arrayPos.get(5).getState().equals(s)) {
            if (arrayPos.get(3).getState().equals(EMPTY))
                return 3;
        }

        if (arrayPos.get(5).getState().equals(s) && arrayPos.get(3).getState().equals(s)) {
            if (arrayPos.get(4).getState().equals(EMPTY))
                return 4;
        }

        //----------------------------------------------------//

        if (arrayPos.get(6).getState().equals(s) && arrayPos.get(7).getState().equals(s)) {
            if (arrayPos.get(8).getState().equals(EMPTY))
                return 8;
        }

        if (arrayPos.get(7).getState().equals(s) && arrayPos.get(8).getState().equals(s)) {
            if (arrayPos.get(6).getState().equals(EMPTY))
                return 6;
        }

        if (arrayPos.get(8).getState().equals(s) && arrayPos.get(6).getState().equals(s)) {
            if (arrayPos.get(7).getState().equals(EMPTY))
                return 7;
        }

        return NO_RESULT;

    }

    private int scannerVertical(int i) {

        String s = "";

        if (i == WIN) {
            s = player2.getExOrCircle();
        } else if (i == NO_LOSE) {
            s = player1.getExOrCircle();
        }

        if (arrayPos.get(0).getState().equals(s) && arrayPos.get(3).getState().equals(s)) {
            if (arrayPos.get(6).getState().equals(EMPTY))
                return 6;
        }

        if (arrayPos.get(3).getState().equals(s) && arrayPos.get(6).getState().equals(s)) {
            if (arrayPos.get(0).getState().equals(EMPTY))
                return 0;
        }

        if (arrayPos.get(6).getState().equals(s) && arrayPos.get(0).getState().equals(s)) {
            if (arrayPos.get(3).getState().equals(EMPTY))
                return 3;
        }

        //----------------------------------------------------//

        if (arrayPos.get(1).getState().equals(s) && arrayPos.get(4).getState().equals(s)) {
            if (arrayPos.get(7).getState().equals(EMPTY))
                return 7;
        }

        if (arrayPos.get(4).getState().equals(s) && arrayPos.get(7).getState().equals(s)) {
            if (arrayPos.get(1).getState().equals(EMPTY))
                return 1;
        }

        if (arrayPos.get(7).getState().equals(s) && arrayPos.get(1).getState().equals(s)) {
            if (arrayPos.get(4).getState().equals(EMPTY))
                return 4;
        }

        //----------------------------------------------------//

        if (arrayPos.get(2).getState().equals(s) && arrayPos.get(5).getState().equals(s)) {
            if (arrayPos.get(8).getState().equals(EMPTY))
                return 8;
        }

        if (arrayPos.get(5).getState().equals(s) && arrayPos.get(8).getState().equals(s)) {
            if (arrayPos.get(2).getState().equals(EMPTY))
                return 2;
        }

        if (arrayPos.get(8).getState().equals(s) && arrayPos.get(2).getState().equals(s)) {
            if (arrayPos.get(5).getState().equals(EMPTY))
                return 5;
        }

        return NO_RESULT;

    }

    private int scannerDiagonal(int i) {

        String s = "";

        if (i == WIN) {
            s = player2.getExOrCircle();
        } else if (i == NO_LOSE) {
            s = player1.getExOrCircle();
        }

        if (arrayPos.get(0).getState().equals(s) && arrayPos.get(4).getState().equals(s)) {
            if (arrayPos.get(8).getState().equals(EMPTY))
                return 8;
        }

        if (arrayPos.get(4).getState().equals(s) && arrayPos.get(8).getState().equals(s)) {
            if (arrayPos.get(0).getState().equals(EMPTY))
                return 0;
        }

        if (arrayPos.get(8).getState().equals(s) && arrayPos.get(0).getState().equals(s)) {
            if (arrayPos.get(4).getState().equals(EMPTY))
                return 4;
        }

        //----------------------------------------------------//

        if (arrayPos.get(2).getState().equals(s) && arrayPos.get(4).getState().equals(s)) {
            if (arrayPos.get(6).getState().equals(EMPTY))
                return 6;
        }

        if (arrayPos.get(4).getState().equals(s) && arrayPos.get(6).getState().equals(s)) {
            if (arrayPos.get(2).getState().equals(EMPTY))
                return 2;
        }

        if (arrayPos.get(6).getState().equals(s) && arrayPos.get(2).getState().equals(s)) {
            if (arrayPos.get(4).getState().equals(EMPTY))
                return 4;
        }

        return NO_RESULT;

    }

    private Drawable getDrawable(String str, int randon) {
        if (str.equals(EQUIS)) {
            if (randon == 1)
                return ex1;
            else if (randon == 2)
                return ex2;
            else if (randon == 3)
                return ex3;
            else if (randon == 4)
                return ex4;
            else if (randon == 5)
                return ex5;
            else if (randon == 6)
                return ex6;
        } else if (str.equals(CIRCLE)) {
            if (randon == 1)
                return circle1;
            else if (randon == 2)
                return circle2;
            else if (randon == 3)
                return circle3;
            else if (randon == 4)
                return circle4;
            else if (randon == 5)
                return circle5;
            else if (randon == 6)
                return circle6;
        }
        return null;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public int w(float percent) {
        return (int) (widthScreen * (percent / 100));
    }

    public int h(float percent) {
        return (int) (heightScreen * (percent / 100));
    }
}
