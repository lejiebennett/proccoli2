package com.example.proccoli2.NewModels;

import java.io.Serializable;

public class SearchUserModel implements Serializable {
    @DocumentID String uid;
    String email;
    String userName;

    public SearchUserModel(String uid, String email, String userName){
        this.uid=uid;
        this.email=email;
        this.userName = userName;
    }
}

/*
import Foundation
import FirebaseFirestoreSwift
struct searchUserModel:Decodable {
	@DocumentID var uid:String?
	let email:String
	let userName:String
}
 */