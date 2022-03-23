package com.med.medreminder.firebase;

import java.util.List;

public interface FirebaseDisplayMedFriendsDelegate {
    void successToDisplayMedFriend(List<String> emails);
    void failedToDisplayMedFriend(String msg);
}
