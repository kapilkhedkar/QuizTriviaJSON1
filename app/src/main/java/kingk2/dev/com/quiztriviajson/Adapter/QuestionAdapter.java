package kingk2.dev.com.quiztriviajson.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kingk2.dev.com.quiztriviajson.Activities.MainActivity;
import kingk2.dev.com.quiztriviajson.Model.Question;
import kingk2.dev.com.quiztriviajson.R;
import kingk2.dev.com.quiztriviajson.ViewHolder.QuestionVH;

/**
 * Created by Kapil on 08-09-2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionVH>
{

    public static ArrayList<Question> questions;
    public static ArrayList<String> questionResponses = new ArrayList<>();

    public QuestionAdapter(ArrayList<Question> questions) {
        this.questions = questions;
        //initialize
        //loop questions
        for(int i=0; i<questions.size(); i++)
        {
            questionResponses.add(i, "");
        }
    }

    @Override
    public QuestionVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View qnItemCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_item,parent,false);


        return new QuestionVH(qnItemCard);



    }

    @Override
    public void onBindViewHolder(QuestionVH holder, int position) {


        final Question qnItem = questions.get(position);
        holder.updateUI(qnItem, position);

        /*if(holder.choosenAnswer.equalsIgnoreCase(questions.get(position).getCorrectAnswer()))
        {
            MainActivity.score++;
        }*/


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    /*public ArrayList<String> getAllAnswers()
    {
        return questionResponses;
    }*/

    public static int getUserScore()
    {
        int score = 0;

        for(int i=0; i<questions.size(); i++)
        {
            String correctAnswer = questions.get(i).getCorrectAnswer();
            String givenAnswer = questionResponses.get(i);

            if(givenAnswer.equalsIgnoreCase(correctAnswer))
            {
                score++;
            }
        }
        return score;
    }
}
