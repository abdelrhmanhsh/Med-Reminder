package com.med.medreminder.ui.displayMedFriends.presenter;

import com.med.medreminder.firebase.FirebaseDisplayMedFriendsDelegate;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.displayMedFriends.view.DisplayMedFriendViewInterface;

import java.util.List;

public class DisplayMedFriendPresenter implements DisplayMedFriendPresenterInterface, FirebaseDisplayMedFriendsDelegate {

    RepositoryInterface repo;
    DisplayMedFriendViewInterface displayMedFriendViewInterface;

    public DisplayMedFriendPresenter(RepositoryInterface repo, DisplayMedFriendViewInterface displayMedFriendViewInterface) {
        this.repo = repo;
        this.displayMedFriendViewInterface = displayMedFriendViewInterface;
    }

    @Override
    public void successToDisplayMedFriend(List<String> emails) {
        displayMedFriendViewInterface.successToDisplayMedFriend(emails);
    }

    @Override
    public void failedToDisplayMedFriend(String msg) {
        displayMedFriendViewInterface.failedToDisplayMedFriend(msg);
    }

    @Override
    public void acceptedRequests(String myEmail) {
        repo.acceptedRequests(myEmail,this);
    }
}
