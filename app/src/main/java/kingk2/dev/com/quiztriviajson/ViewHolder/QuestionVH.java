package kingk2.dev.com.quiztriviajson.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kingk2.dev.com.quiztriviajson.Adapter.QuestionAdapter;
import kingk2.dev.com.quiztriviajson.Model.Question;
import kingk2.dev.com.quiztriviajson.R;

/**
 * Created by Kapil on 08-09-2017.
 */

public class QuestionVH extends RecyclerView.ViewHolder
{
    private TextView tvQuestion;
    private Button btnTrue,btnFalse;

    public static String choosenAnswer = "";

    public QuestionVH(View itemView)
    {
        super(itemView);


        this.tvQuestion = (TextView)itemView.findViewById(R.id.tvQuestion);
        this.btnTrue = (Button)itemView.findViewById(R.id.btnTrue);
        this.btnFalse = (Button)itemView.findViewById(R.id.btnFalse);



        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnTrue.setBackgroundResource(R.color.myBlue);
                btnTrue.setTextColor(v.getResources().getColor(R.color.white));

                btnFalse.setBackgroundResource(0);
                btnFalse.setTextColor(v.getResources().getColor(R.color.myBlue));

                int position = Integer.parseInt(btnTrue.getTag().toString());

                choosenAnswer = "True";
                QuestionAdapter.questionResponses.add(position, choosenAnswer);

            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnFalse.setBackgroundResource(R.color.myBlue);
                btnFalse.setTextColor(v.getResources().getColor(R.color.white));

                btnTrue.setBackgroundResource(0);
                btnTrue.setTextColor(v.getResources().getColor(R.color.myBlue));

                int position = Integer.parseInt(btnTrue.getTag().toString());

                choosenAnswer = "False";
                QuestionAdapter.questionResponses.add(position, choosenAnswer);
            }
        });



    }

    public void updateUI(Question question, int position)
    {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvQuestion.setText(Html.fromHtml(question.getQuestion(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvQuestion.setText(Html.fromHtml(question.getQuestion()));
        }
        btnFalse.setTag(position);
        btnTrue.setTag(position);

    }

}
