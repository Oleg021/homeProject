package com.nix.vyrvykhvost;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {
    private List<Request> requests = new ArrayList<>();
    private RequestRepository() {}
    private static volatile RequestRepository instance;

    public static RequestRepository getInstance() {
        if (instance == null) {
            instance = new RequestRepository();
        }
        return instance;
    }

    public void add(Request request){
        requests.add(request);
    }

    public List<Request> getAll(){
        return requests;
    }
}
