package com.codefish.android.taskmanager.component.userListView;

import com.codefish.android.taskmanager.model.FollowerBean;

/**
 * Created by abedch on 5/26/2016.
 */
public interface IFollowerCallBack {
     void itemClicked(FollowerBean followerBean);
     void followerRemoved(FollowerBean followerBean);


}
