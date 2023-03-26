import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ChatServerImpl extends ChatServiceGrpc.ChatServiceImplBase  {
    Map<String, ArrayList<MyClient>> channelList = new ConcurrentHashMap<>();
    Map<String, ArrayList<ChannelMessage>> channelMessage = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(ChatServerImpl.class.getName());

    private String showHelp(){
        StringBuilder printMessage = new StringBuilder();
        String seperator = "-";
        printMessage.append("Please enter one of the following option along with suitable parameter\n");
        printMessage.append("Please mention your values at places <> \n");
        printMessage.append(seperator.repeat(50));
        printMessage.append("\nnick <nickname> [To assign nickname]\n");
        printMessage.append("list  [To show active channels and total users]\n");
        printMessage.append("join <channel> [To join a given channel]\n");
        printMessage.append("leave <channel> [To leave a given channel]\n");
        printMessage.append("post <channel> <message> [To post a message in the channel\n");

        return printMessage.toString();
    }
    @Override
    public void help(Chat.ClientChannelAction  req, StreamObserver<Chat.ServerChannelActionMessage> response){
        Chat.ServerChannelActionMessage help = Chat.ServerChannelActionMessage.newBuilder()
                .setMessage(showHelp())
                .build();
        response.onNext(help);
        response.onCompleted();

    }

    public boolean addToChannel(String clientId, String channelName, StreamObserver<Chat.ServerChannelActionMessage> response){

        if(channelList.containsKey(channelName)) {
            ArrayList<MyClient> clients = channelList.get(channelName);
            boolean clientExist = clients.isEmpty() ? false : checkClientExist(clientId, clients);
            if(!clientExist){
                clients.add(new MyClient(clientId, response));
                channelList.put(channelName, clients);
                return true;
            }
        }

        else{
            ArrayList<MyClient> clients = new ArrayList<MyClient>();
            clients.add(new MyClient(clientId, response));
            channelList.put(channelName, clients);
            return true;
        }
        return false;
    }

    private boolean checkClientExist(String clientId, ArrayList<MyClient> clients){
        boolean clientExist = false;
        for (MyClient myclient : clients) {
            if(myclient.clientID.equals(clientId)){
                clientExist = true;
                break;
            }
        }
        return clientExist;
    }

    public String showChannelsAndClients(){
        StringBuilder output = new StringBuilder();
        output.append("Channel\t Total Clients\n");
        output.append("-".repeat(50));

        for(Map.Entry<String, ArrayList<MyClient>> channel : channelList.entrySet()){
            output.append(String.format("\n%s\t\t%s",channel.getKey(), channel.getValue().size()));
        }
        return output.toString();
    }
    @Override
    public void list(Chat.ClientChannelAction  req, StreamObserver<Chat.ServerChannelActionMessage> response){
        Chat.ServerChannelActionMessage clientsChannels = Chat.ServerChannelActionMessage.newBuilder().setMessage(showChannelsAndClients()).build();
        response.onNext(clientsChannels);
        response.onCompleted();
    }

    @Override
    public void join(Chat.ClientChannelAction request, StreamObserver<Chat.ServerChannelActionMessage>response){
        String channelName = request.getChannelName();
        String clientId = request.getClientId();
        String nickName = request.getNickName();
        boolean isAdded = addToChannel(clientId,channelName, response);
        String mess = String.format(" %s already part of %s", clientId, channelName);
        if (isAdded)
            mess = clientId+" " +"joined"+" "+channelName;
        Chat.ServerChannelActionMessage joinMessage = Chat.ServerChannelActionMessage.newBuilder().setMessage(mess).build();
        response.onNext(joinMessage);
        response.onCompleted();
    }

    @Override
    public void leave(Chat.ClientChannelAction request, StreamObserver<Chat.ServerChannelActionMessage>response){
        String channelName = request.getChannelName();
        String clientId = request.getClientId();
        String nickName = request.getNickName();
        String leaveChannel = leaveChannel(clientId, channelName);
        Chat.ServerChannelActionMessage leaveMessage = Chat.ServerChannelActionMessage.newBuilder().setMessage(leaveChannel).build();
        response.onNext(leaveMessage);
        response.onCompleted();
    }

    public String leaveChannel(String clientId, String channelName){
        if(channelList.containsKey(channelName)){
            ArrayList<MyClient> clients = channelList.get(channelName);
            MyClient myclientexist = null;
            for (MyClient myclient : clients) {
                if(myclient.clientID.equals(clientId)){
                    myclientexist = myclient;
                    break;
                }
            }
             if(myclientexist != null){
                 clients.remove(myclientexist);
                 channelList.put(channelName, clients);
                 return "you are successfully removed from channel " + channelName;
             }
             else{
                 System.out.println("Not part of channel");
             }

        }
        return "You are not part of the channel";
    }

    public void post(Chat.ClientPostMessage request, StreamObserver<Chat.ServerChannelActionMessage> response){
        String channelName = request.getChannelName();
        String clientId = request.getClientId();
        String nickName = request.getNickName();
        String content = request.getContent();
        String message = String.format("[#%s:%s]: %s", channelName, nickName, content);
        if(this.channelList.containsKey(channelName)){
            ArrayList<MyClient> myClients = this.channelList.get(channelName);
            boolean clientExists = false;
            if(myClients != null || !myClients.isEmpty())
                clientExists = checkClientExist(clientId, myClients);

            if (clientExists){
                if(this.channelMessage.containsKey(channelName))
                    this.channelMessage.get(channelName).add(new ChannelMessage(message, clientId));
                else{
                    ArrayList<ChannelMessage> newChannelMessage = new ArrayList<ChannelMessage>();
                    newChannelMessage.add(new ChannelMessage(message, clientId));
                    this.channelMessage.put(channelName, newChannelMessage);
                }
            }
            else {
                message = String.format("You are not part of the channel %s. Please join before posting", channelName);
            }
        }
        else{
            message = String.format("You are not part of the channel %s. Please join before posting", channelName);
        }

        Chat.ServerChannelActionMessage postMessage = Chat.ServerChannelActionMessage.newBuilder().setMessage(message).build();

        response.onNext(postMessage);
        response.onCompleted();
    }

    public void receive(Chat.ClientChannelAction  request, StreamObserver<Chat.ServerChannelActionMessage> response){
        String clientId = request.getClientId();
        ServerCallStreamObserver<Chat.ServerChannelActionMessage> clientCallObserver =
                (ServerCallStreamObserver<Chat.ServerChannelActionMessage>) response;
        clientCallObserver.setOnCancelHandler(()->{
            for (String channelName:this.channelList.keySet()) {
                leaveChannel(clientId, channelName);
                logger.info(clientId+" left the server and removed from channel "+ channelName);
            }

        });

        String outMessage = sendMessage(clientId);
        if(outMessage == null || outMessage.isBlank() || outMessage.isEmpty())
            response.onCompleted();
        else{
            Chat.ServerChannelActionMessage sendMessage = Chat.ServerChannelActionMessage.newBuilder()
                    .setMessage(outMessage)
                    .build();
            response.onNext(sendMessage);
            response.onCompleted();
        }

    }

    private String sendMessage(String clientId){
        StringBuilder sendMessageOut = new StringBuilder();
        for (String channel: this.channelMessage.keySet()) {
            ArrayList<MyClient> myClients = this.channelList.get(channel);
            boolean isPartofChannel = myClients.isEmpty() ? false : checkClientExist(clientId, myClients);
            if (isPartofChannel){
                for(ChannelMessage channelMessage1: this.channelMessage.get(channel)){
                    if (!channelMessage1.postedToClient.contains(clientId)){
                        channelMessage1.postedToClient.add(clientId);
                        return channelMessage1.message;
                    }
                }
            }
        }
        return sendMessageOut.toString();
    }

    private class MyClient{
        private String clientID;
        private StreamObserver<Chat.ServerChannelActionMessage> response;

        public MyClient(String clientID, StreamObserver<Chat.ServerChannelActionMessage> response){
            this.clientID = clientID;
            this.response = response;
        }
    }

    private class ChannelMessage{
        public ArrayList<String> postedToClient = new ArrayList<String>();
        public String message;

        public ChannelMessage(String message, String clientId){
            this.message = message;
            this.postedToClient.add(clientId);
        }
    }

}
