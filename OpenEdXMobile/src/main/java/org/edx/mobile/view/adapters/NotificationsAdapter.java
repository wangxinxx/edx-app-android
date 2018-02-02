package org.edx.mobile.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import org.edx.mobile.core.IEdxEnvironment;
import org.edx.mobile.model.api.Notification;

public class NotificationsAdapter extends BaseListAdapter<Notification> {
    public NotificationsAdapter(Context context, IEdxEnvironment environment) {
        super(context, CourseCardViewHolder.LAYOUT, environment);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public BaseViewHolder getTag(View convertView) {
        return null;
    }

    @Override
    public void render(BaseViewHolder tag, Notification model) {

    }
}
