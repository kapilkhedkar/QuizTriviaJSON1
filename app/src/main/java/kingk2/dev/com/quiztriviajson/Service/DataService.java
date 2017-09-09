package kingk2.dev.com.quiztriviajson.Service;

import java.util.ArrayList;

import kingk2.dev.com.quiztriviajson.Activities.MainActivity;
import kingk2.dev.com.quiztriviajson.Model.Question;

/**
 * Created by Kapil on 08-09-2017.
 */

public class DataService
{
    private static DataService ourInstance = new DataService();

    public static DataService getInstance() {
        return ourInstance;
    }

    private DataService() {
    }


    public ArrayList<Question> getQuestions()
    {

        ArrayList<Question> list = new ArrayList<>();


        /*for(int i=0;i<MainActivity.qnList.size();i++)
        {
            list.add(new Question(MainActivity.qnList.get(i).getQuestion(),MainActivity.qnList.get(i).getCorrectAnswer()));
        }*/

        list.add(new Question("What's my name?","Kapil"));
        list.add(new Question("What do I like?","Android"));


        return list;

/*



        return MainActivity.qnList;*/


    }

}
