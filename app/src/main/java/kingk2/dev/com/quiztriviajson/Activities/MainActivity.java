package kingk2.dev.com.quiztriviajson.Activities;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kingk2.dev.com.quiztriviajson.Adapter.QuestionAdapter;
import kingk2.dev.com.quiztriviajson.Model.Question;
import kingk2.dev.com.quiztriviajson.R;
import kingk2.dev.com.quiztriviajson.Service.DataService;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://opentdb.com/api.php?amount=10&category=15&difficulty=easy&type=boolean";


    RecyclerView recyclerView;

    CardView card_submit_quiz;

    public static ArrayList<Question> qnList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        qnList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_questions);
        recyclerView.setHasFixedSize(true);

        loadQuestionList();

        /*QuestionAdapter adapter;

        adapter = new QuestionAdapter(DataService.getInstance().getQuestions());
        recyclerView.setAdapter(adapter);*/

        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        card_submit_quiz = (CardView)findViewById(R.id.card_submit_quiz);

        card_submit_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.score_display_popup,null);

                TextView tvScore = (TextView)mView.findViewById(R.id.tvScore);
                tvScore.setText(QuestionAdapter.getUserScore()+"/"+QuestionAdapter.questions.size());

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });









    }



    private void loadQuestionList() {
        /*//getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*//hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);*/


                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray resultArray = obj.getJSONArray("results");


                            for (int i = 0; i < resultArray.length(); i++) {

                                JSONObject qnObject = resultArray.getJSONObject(i);

                                Question qn = new Question(qnObject.getString("question"),qnObject.getString("correct_answer"));

                                qnList.add(qn);

                                //Log.d(""+i,qnObject.getString("correct_answer"));

                            }

                            //creating custom adapter object
                            QuestionAdapter adapter = new QuestionAdapter(qnList);

                            //adding the adapter to listview
                            recyclerView.setAdapter(adapter);







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);
    }


    public static interface ClickListener
    {
        public void onClick(View view, int position);
        public void onLongClick(View view,int position);
    }
}
class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private MainActivity.ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final MainActivity.ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }



    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
