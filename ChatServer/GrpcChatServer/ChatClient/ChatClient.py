import grpc
import chat_pb2
import chat_pb2_grpc
import sys
import logging
import asyncio

class ChatClient():
    clientId = None
    nickName = None
    address = None
    ERROR_FORMAT = '%(user)-1s %(message)s'
    

    def __init__(self,clientId, port) -> None:
        self.clientId = clientId
        self.address = f'localhost:{port}'
            
    
    def getHelp(self,stub):
        req = chat_pb2.ClientChannelAction(nickName = self.nickName, channelName = '', clientId = self.clientId)
        response = stub.help(req)
        for res in response:
            logging.basicConfig(level=logging.INFO)
            logging.info(res.message)
    
    def setNickName(self, nickName):
         
        logging.basicConfig(level=logging.INFO)
        logging.info(f'Nick name changed from {self.nickName} to {nickName}')
        self.nickName = nickName

    def hasNickName(self):
        if self.nickName is None or len(self.nickName) == 0:
            return False
        
        return True


    def joinChannel(self, stub, channelName):
        if self.hasNickName():
            request = chat_pb2.ClientChannelAction(nickName = self.nickName, channelName = channelName, clientId = self.clientId)
            response = stub.join(request)
            for res in response:
                logging.basicConfig(level=logging.INFO)
                logging.info(res.message)

        else:
            logging.error('OOH!, you need to set nick name to join the channel')


    def postMessage(self, stub, channelName, message):
        request = chat_pb2.ClientPostMessage(nickName = self.nickName, channelName = channelName, clientId = self.clientId, content = message)
        response = stub.post(request)
        for res in response:
            logging.basicConfig(level=logging.INFO)
            logging.info(res.message)
                
    
    def getList(self,stub):
        req = chat_pb2.ClientChannelAction(nickName = self.nickName, channelName = '', clientId = self.clientId)
        response = stub.list(req)
        for res in response:
            logging.basicConfig(level=logging.INFO)
            logging.info('\n'+ res.message)

    def leaveChannel(self, stub, channelName):
        if self.hasNickName():
            req = chat_pb2.ClientChannelAction(nickName = self.nickName, channelName = channelName, clientId = self.clientId)
            response = stub.leave(req)
            for res in response:
                logging.basicConfig(level=logging.INFO)
                logging.info(res.message)

        else:
            logging.error('OOH!, you need to set nick name and join the channel to leave the channel')

    def getServerChannelMessage(self,stub):
        req = chat_pb2.ClientChannelAction(nickName = self.nickName, channelName = '', clientId = self.clientId)
        response = stub.receive(req)
        for res in response:
            logging.basicConfig(level=logging.INFO)
            logging.info(res.message)
        

    async def run_user_input(self, stub):
        while True:
            awaitUserInput = await asyncio.get_event_loop().run_in_executor(None, input, )
            userInput = awaitUserInput.split()
            if userInput[0].lower() == 'help':
                self.getHelp(stub)

            elif userInput[0].lower() == 'nick':
                if len(userInput) < 2:
                    logging.basicConfig(level=logging.INFO)
                    logging.info('Please provide the name along with nick')
                    continue
                self.setNickName(', '.join(userInput[1:]))

            elif userInput[0].lower() == 'join':
                if len(userInput) < 2:
                    logging.basicConfig(level=logging.INFO)
                    logging.info('Please provide the channel along with join')
                    continue
                self.joinChannel(stub,userInput[1])

            elif userInput[0].lower() == 'post':
                if len(userInput) < 3:
                    logging.basicConfig(level=logging.INFO)
                    logging.info('Please provide the channel followed by content along with post')
                    continue
                self.postMessage(stub, userInput[1], ' '.join(userInput[2:]))

            elif userInput[0].lower() == 'leave':
                if len(userInput) < 2:
                    logging.basicConfig(level=logging.INFO)
                    logging.info('Please provide the channel along with leave')
                    continue
                self.leaveChannel(stub, userInput[1])

            elif userInput[0].lower() == 'list':
                self.getList(stub)
            
            else :
                logging.warning("OOO! incorrect input , please type help for the correct")

    async def get_message_from_server(self,stub):
        while True:
            self.getServerChannelMessage(stub)
            await asyncio.sleep(1)

    async def run(self):
        with grpc.insecure_channel(self.address) as channel:
            print("You are connected to server, please start chatting. You may begin by typing help")
            stub = chat_pb2_grpc.ChatServiceStub(channel)
            run_tasks = [self.run_user_input(stub), self.get_message_from_server(stub)]
            await asyncio.gather(*run_tasks)

if __name__ == '__main__':
    args = sys.argv[1:]
    clientId = args[0]
    port = args[1]
    chatClient = ChatClient(clientId, port)
    asyncio.run(chatClient.run())