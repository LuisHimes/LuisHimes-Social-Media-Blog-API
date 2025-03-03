package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message postMessage(Message message){
        if(message.getMessage_text()!=null && message.getMessage_text().length() <= 255 && message.getMessage_text().length() > 0 && accountDAO.getAccount(message.getPosted_by()) != null){
            return messageDAO.postMessage(message);
        }
        return null;
    }

    public Message getMessagebyid(int id){
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessagebyid(int id){
        Message message = messageDAO.getMessageById(id);
        if(message != null){
            return messageDAO.deleteMessage(message);
        }
        return null;
    }

    public Message updateMessage(int id, String msg){
        Message message = messageDAO.getMessageById(id);
        if(message != null && msg.length() > 0 && msg.length() <= 255 && msg != null){
            message.setMessage_text(msg);
            return messageDAO.updateMessageText(message);
        }
        return null;
    }

    public List<Message> getMessagesFromUser(int accId){
        return messageDAO.getMessagesByUser(accId);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
}
