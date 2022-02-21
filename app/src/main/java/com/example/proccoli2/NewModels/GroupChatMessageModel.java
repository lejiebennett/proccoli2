package com.example.proccoli2.NewModels;

import java.io.Serializable;

public class GroupChatMessageModel implements Serializable {
    @DocumentID String messageId;
    final String message;
    final double createdAt;
    final String senderUid;
    final String senderEmail;

    public GroupChatMessageModel(String messageId, String message, double createdAt, String senderUid, String senderEmail){
        this.messageId = messageId;
        this.message = message;
        this.createdAt = createdAt;
        this.senderUid = senderUid;
        this.senderEmail = senderEmail;
    }


}
/*
import Foundation
        import FirebaseFirestoreSwift

        struct groupChatMessageModel:Codable {
@DocumentID var messageId:String?
        let message:String
        let createdAt:Double
        let senderUid:String
        let senderEmail:String
        }

 */

