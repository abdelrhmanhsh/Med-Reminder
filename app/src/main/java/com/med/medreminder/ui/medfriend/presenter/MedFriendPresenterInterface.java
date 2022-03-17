package com.med.medreminder.ui.medfriend.presenter;

public interface MedFriendPresenterInterface {
    void addRequestsToFirestore(String email,String name,String status,String helper_email);
}
