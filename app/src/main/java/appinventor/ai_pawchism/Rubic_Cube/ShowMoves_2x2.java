package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShowMoves_2x2 {

    private static  int[] moves2x2 = {R.drawable._2x2_l,R.drawable._2x2_l_prim,R.drawable._2x2_r,R.drawable._2x2_r_prim,R.drawable._2x2_f,
            R.drawable._2x2_f_prim,R.drawable._2x2_b,R.drawable._2x2_b_prim,R.drawable._2x2_d,R.drawable._2x2_d_prim,R.drawable._2x2_u,R.drawable._2x2_u_prim};
    private LinearLayout.LayoutParams params, layoutParams;

    private void setParamsForMove(){

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,20,10,20);
        layoutParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

    }

    private ImageView getNewSingleMove(String move, Context context){
        int mv=0;
        switch (move){
            case "l": mv=0; break;
            case "l'": mv=1; break;
            case "r": mv=2; break;
            case "r'": mv=3; break;
            case "f": mv=4; break;
            case "f'": mv=5; break;
            case "b": mv=6; break;
            case "b'": mv=7; break;
            case "d": mv=8; break;
            case "d'": mv=9; break;
            case "u": mv=10; break;
            case "u'": mv=11; break;
            case "v": mv=12; break;
        }

        ImageView newMove = new ImageView(context);
        if (mv==12){
            newMove.setImageResource(moves2x2[0]);
            newMove.setAdjustViewBounds(true);
            newMove.setLayoutParams(params);
            newMove.setLayoutParams(layoutParams);
            newMove.setVisibility(View.INVISIBLE);
        } else {
            newMove.setImageResource(moves2x2[mv]);
            newMove.setAdjustViewBounds(true);
            newMove.setLayoutParams(params);
            newMove.setLayoutParams(layoutParams);
        }
        return newMove;
    }

    public LinearLayout movesAllCases(Context context, String...moves){

        setParamsForMove();
        LinearLayout case_child_moves_1 = new LinearLayout(context);
        case_child_moves_1.setLayoutParams(params);
        LinearLayout case_child_moves_2 = new LinearLayout(context);
        case_child_moves_2.setLayoutParams(params);
        LinearLayout case_child_moves_3 = new LinearLayout(context);
        case_child_moves_3.setLayoutParams(params);
        LinearLayout case_child_moves_4 = new LinearLayout(context);
        case_child_moves_4.setLayoutParams(params);
        LinearLayout case_child_moves_5 = new LinearLayout(context);
        case_child_moves_5.setLayoutParams(params);

        case_child_moves_1.addView(getNewSingleMove(moves[0], context));
        case_child_moves_1.addView(getNewSingleMove(moves[1], context));
        case_child_moves_1.addView(getNewSingleMove(moves[2], context));

        case_child_moves_2.addView(getNewSingleMove(moves[3], context));
        case_child_moves_2.addView(getNewSingleMove(moves[4], context));
        case_child_moves_2.addView(getNewSingleMove(moves[5], context));

        case_child_moves_3.addView(getNewSingleMove(moves[6], context));
        case_child_moves_3.addView(getNewSingleMove(moves[7], context));
        case_child_moves_3.addView(getNewSingleMove(moves[8], context));



        final LinearLayout parentLayoutMoves = new LinearLayout(context);
        parentLayoutMoves.setOrientation(LinearLayout.VERTICAL);
        parentLayoutMoves.setVisibility(View.GONE);
        parentLayoutMoves.addView(case_child_moves_1);
        parentLayoutMoves.addView(case_child_moves_2);
        parentLayoutMoves.addView(case_child_moves_3);

        if (moves.length>9){
            case_child_moves_4.addView(getNewSingleMove(moves[9], context));
            case_child_moves_4.addView(getNewSingleMove(moves[10], context));
            case_child_moves_4.addView(getNewSingleMove(moves[11], context));
            parentLayoutMoves.addView(case_child_moves_4);
        }

        if (moves.length>12){
            case_child_moves_5.addView(getNewSingleMove(moves[12], context));
            case_child_moves_5.addView(getNewSingleMove(moves[13], context));
            case_child_moves_5.addView(getNewSingleMove(moves[14], context));
            parentLayoutMoves.addView(case_child_moves_5);
        }

        //**** Divider - black line
        /*
        View line = new View(context);
        ViewGroup.LayoutParams lineParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lineParams.height = 4;
        line.setLayoutParams(lineParams);
        line.setBackgroundColor(Color.BLACK);
        */
        //****

        //linearLayout.addView(parentLayoutMoves);
        //linearLayout.addView(line);

        return parentLayoutMoves;


    }
}
