package org.edx.mobile.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.edx.mobile.R;
import org.edx.mobile.databinding.FragmentNotificationsListBinding;
import org.edx.mobile.interfaces.RefreshListener;
import org.edx.mobile.logger.Logger;
import org.edx.mobile.view.adapters.BaseListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationsListFragment
        extends OfflineSupportBaseFragment
        implements RefreshListener {

    private final Logger logger = new Logger(getClass().getSimpleName());
    private FragmentNotificationsListBinding binding;
    private String testJsonStr = "{notifications: [{title: Notification 1, message: Hello 1}, {title: Notification 2, message: Hello 2}]}";
    private String[] notificationsList = {"Notification 1", "Notification 2"};
    private String[] notificationMessages = {"Hello 1", "Hello 2"};
    private SimpleAdapter simpleAdapter = null;
    private String[] from = {"title", "message"};
    private int[] to = {R.id.notification_title, R.id.notification_content};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
//
//        for (int i=0; i < notificationsList.length;i++) {
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("title", notificationsList[i]);
//            hashMap.put("message", notificationMessages[i]);
//            arrayList.add(hashMap);
//        }

//        simpleAdapter = new SimpleAdapter(
//                getContext(), arrayList, R.layout.row_notification_list, from, to
//        );

    }

    private void updateSimpleAdapter(JSONObject notificationJson){
        JSONArray notificationsJsonList = null;
        try{
            notificationsJsonList = notificationJson.getJSONArray("notifications");
        } catch (JSONException e) {
            logger.error(e);
        }
        if (notificationsJsonList != null) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            for (int i=0; i < notificationsJsonList.length();i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject currJson;
                try {
                    currJson = notificationsJsonList.getJSONObject(i);

                    if (currJson != null) {
                        hashMap.put("title", currJson.getString("title"));
                        hashMap.put("message", currJson.getString("message"));
                        arrayList.add(hashMap);
                    }
                } catch (JSONException e){
                    logger.error(e);
                }

            }
            simpleAdapter = new SimpleAdapter(
                    getContext(), arrayList, R.layout.row_notification_list, from, to
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        JSONObject notificationJson = new JSONObject();
        try {
            JSONArray notificationList = new JSONArray();
            for (int i=0; i < notificationsList.length;i++) {
                JSONObject jsonData = new JSONObject();
                jsonData.put("title", notificationsList[i]);
                jsonData.put("message", notificationMessages[i]);
                notificationList.put(jsonData);
            }
            notificationJson.put("notifications", notificationList);
        } catch (JSONException e) {
            logger.error(e);
        }
        updateSimpleAdapter(notificationJson);

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_notifications_list,
                container,
                false
        );
        binding.swipeContainer.setColorSchemeResources(R.color.edx_brand_primary_accent,
                R.color.edx_brand_gray_x_back, R.color.edx_brand_gray_x_back,
                R.color.edx_brand_gray_x_back);
        binding.notificationList.addHeaderView(new View(getContext()), null, false);
        binding.notificationList.addFooterView(new View(getContext()), null, false);
        binding.notificationList.setAdapter(simpleAdapter);
//        binding.notificationList.setOnItemClickListener(simpleAdapter);
        return binding.getRoot();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected boolean isShowingFullScreenError() {
        return false;
    }
}
