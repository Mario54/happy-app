package com.mariolamontagne.happy.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;

public class StatisticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.graphContainer);

        // init example series data
        ArrayList<HappyEntry> list = HappyEntryLab.get(getActivity()).getEntries();
        GraphViewSeries exampleSeries = new GraphViewSeries(list.toArray(new HappyEntry[list.size()]));

        GraphView graphView = new LineGraphView(getActivity(), "GraphViewDemo");
        graphView.getGraphViewStyle().setNumVerticalLabels(5);
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                return String.valueOf((int) value);
            }
        });
        graphView.addSeries(exampleSeries); // data
        layout.addView(graphView);

        return v;
    }
}
