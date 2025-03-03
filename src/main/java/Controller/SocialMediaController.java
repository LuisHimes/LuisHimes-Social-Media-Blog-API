package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        } else{
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.login(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        } else{
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.postMessage(message);
        if(addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
        } else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageIdHandler(Context context){
        int message_id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.getMessagebyid(message_id);
        if(message != null){
            context.json(message);
        } else {
            context.status(200);
        }
        
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException { 
        int message_id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.deleteMessagebyid(message_id);
        if(message != null){
            context.json(message);
        } else {
            context.status(200);
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.valueOf(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message.getMessage_text());
        if(updatedMessage != null){
            context.json(updatedMessage);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesFromUserHandler(Context context)throws JsonProcessingException{
        int account_id = Integer.valueOf(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesFromUser(account_id);
        if(messages != null){
            context.json(messages);
        } else {
            context.status(200);
        }
    }
}