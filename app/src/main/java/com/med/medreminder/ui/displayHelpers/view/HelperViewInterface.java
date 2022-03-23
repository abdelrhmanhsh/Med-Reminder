package com.med.medreminder.ui.displayHelpers.view;

import java.util.List;

public interface HelperViewInterface {
    void successToDisplayRequests(List<String> helper_email);
    void failedToDisplayRequests(String msg);
}
