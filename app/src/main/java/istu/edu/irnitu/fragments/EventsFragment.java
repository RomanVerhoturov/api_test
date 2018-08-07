package istu.edu.irnitu.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import istu.edu.irnitu.IOPackage.InternetController;
import istu.edu.irnitu.R;
import istu.edu.irnitu.adapters.EventsAdapter;
import istu.edu.irnitu.dataModels.EventsModel;

public class EventsFragment extends AbstractTabFragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;
    ArrayList<EventsModel> mEventsArrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    EventsAdapter mEventsAdapter;
    private TextView errorTV;
    private View errorView;
    Button retryButton;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment getInstance(Context context, String cookies) {
        Bundle args = new Bundle();
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        fragment.setActivityContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_events));
        fragment.setCookiesLogin(cookies);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        errorView = view.findViewById(R.id.errorViewNews);
        errorTV = (TextView) view.findViewById(R.id.news_errorTV);
        retryButton = (Button) view.findViewById(R.id.buttonRetryNews);
        retryButton.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.recyclerViewNews);
        if (InternetController.hasConnection(getContext())) {

            showProgress();
            MyTask mt = new MyTask();
            mt.execute();
        } else {
            showError(true, R.string.error_nodata);
        }
        return view;
    }

    private void showError(final boolean show, int textError) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        if (show) {
                Toast.makeText(getContext(), textError, Toast.LENGTH_LONG).show();

                errorTV.setText(textError);
                errorView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {errorView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
                mRecyclerView.setVisibility(View.GONE);
        } else {
            errorView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {errorView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            }
        }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEventsAdapter = new EventsAdapter(getContext(), mEventsArrayList);
        mRecyclerView.setAdapter(mEventsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mEventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        errorTV.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        showProgress();
        MyTask mt = new MyTask();
        mt.execute();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class MyTask extends AsyncTask<Void, Void, ArrayList<EventsModel>> {

        ContentValues values = new ContentValues();
        int id = 0;

        @Override
        protected ArrayList<EventsModel> doInBackground(Void... voids) {
            ArrayList<EventsModel> list = new ArrayList<>();
            Document doc = null;
            try {
                doc = Jsoup.connect("https://fgbou-vo-irkutskiy-natsio.timepad.ru/events/all/page/1/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc != null) {
                Elements mainElements = doc.getElementsByClass("t-card t-card_event t-card_event-public  ");
                for (Element elements : mainElements) {
                    id++;
                    String name = elements.getElementsByClass("__c").text();
                    String description = elements.getElementsByClass("t-description").text();
                    String link = elements.getElementsByClass("__c").html();
                    String date = elements.getElementsByClass("t-card_event__info").text();
                    int a = name.indexOf("Иркутск");
                    int b = link.indexOf("href=\"");
                    int c = link.indexOf("\" target");
                    link = link.substring(b+6, c);
                    name = name.substring(0, a);
                    list.add(new EventsModel(name, description, date, link));


//                        values.put(NewsEntry.COLUMN_NAME_NEWS, text);
//                        values.put(NewsEntry.COLUMN_LINK_NEWS, s);
//                        dbWriter.insert(NewsEntry.TABLE_NAME, null, values);
                 //   }

                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<EventsModel> list) {
            super.onPostExecute(list);
            for (int i = 0; i < list.size(); i++) {
                mEventsArrayList.add(new EventsModel(list.get(i).getName(), list.get(i).getDescription(),
                        list.get(i).getDate(), list.get(i).getUrl()));
            }
            showData();
            hideProgress();
        }
    }
    public void showProgress() {
        progressDialog = ProgressDialog.show(getContext(), "",
                getString(R.string.text_progress_dialog));
    }

    //Скрытие прогресс диалога
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
