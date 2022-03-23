package com.med.medreminder.ui.displayMedFriends.view;

import com.med.medreminder.firebase.FirebaseDisplayMedFriendsDelegate;

import java.util.List;

public interface DisplayMedFriendViewInterface {
    void successToDisplayMedFriend(List<String> emails);
    void failedToDisplayMedFriend(String msg);

}
