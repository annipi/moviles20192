package com.example.hells.androidtic_tac_toe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView mDebugTextView;
    private boolean mGameOver;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTextView = (TextView) findViewById(R.id.information);
        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }
    private void startNewGame(){
        this.mGameOver = false;
        mGame.clearBoard();

        for(int i = 0; i < mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mInfoTextView.setText(R.string.first_human);
        }
    }

    private void setMove(char player, int location){
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0,200,0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200,0,0));
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location){
            this.location = location;
        }

        public void onClick(View view){
            if(mBoardButtons[location].isEnabled() && mGameOver != true) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                //if no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1) {
                    mGameOver = true;
                    mInfoTextView.setText(R.string.result_tie);
                } else if (winner == 2) {
                    mGameOver = true;
                    mInfoTextView.setText(R.string.result_human_wins);
                } else {
                    mGameOver = true;
                    mInfoTextView.setText(R.string.result_computer_wins);
                }
            }
        }
    }

    protected Dialog onCreateDialog(int id){
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id){
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);

                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)
                };

                int selected = 0;
                TicTacToeGame.DifficultyLevel selectedDif = mGame.getDifficultyLevel();
                if(selectedDif == TicTacToeGame.DifficultyLevel.Easy)
                    selected = 0;
                else if(selectedDif == TicTacToeGame.DifficultyLevel.Harder)
                    selected = 1;
                else
                    selected = 2;
                builder.setSingleChoiceItems(levels, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int itemSelected) {
                        dialog.dismiss();
                        if(itemSelected == 0 )
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                        else if(itemSelected == 1)
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                        else
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

                        Toast.makeText(getApplicationContext(), levels[itemSelected], Toast.LENGTH_SHORT).show();
                    }
                });
                dialog = builder.create();
                break;
            case DIALOG_QUIT_ID:
                builder.setMessage(R.string.quit_question)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AndroidTicTacToeActivity.this.finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null);
                dialog = builder.create();;
                builder.setCancelable(false);
                break;
        }
        return dialog;
    }
}
