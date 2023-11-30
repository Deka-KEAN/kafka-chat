import SockJsClient from 'react-stomp';
import { LoginForm } from './component/loginForm';
import { Messages } from './component/messages';
import { chatAPI } from './component/chatAPI';
import { useState } from 'react';
import Input from './component/Input';

const Socket_url="http://localhost:8080/ws-chat";

function randomColor() {
  return '#' + Math.floor(Math.random() * 0xFFFFFF).toString(16);
}

function App() {
  const [messages,setMessages]=useState([]);
  const [user,setUser]=useState(null);

  const handleLoginSubmit = (val) =>{
    console.log(val," logged in!!")
    setUser({
      username:val,
      color:randomColor()
    });
  }
  let onMessageReceived = (msg) => {
    console.log("Message Recieved",msg);
    setMessages(messages.concat(msg));
  }
  let onSendMessage = (messageText) => {
    chatAPI.sendMessage(user.username,messageText).then(res=>{
      console.log("Sent ",res);
    }).catch(err=>{
      console.log("Error Occured!!" , err);
    })
  }
  return (
    <div className="App">
      {!!user ?
        <>
          <SockJsClient
            url={Socket_url}
            topics={['/topic/group']}
            onConnect={console.log("Connected!!")}
            onDisconnect={console.log("Disconnected!")}
            onMessage={msg => onMessageReceived(msg)}
            debug={false}
          />
          <Messages
            messages={messages}
            currentUser={user}
          />
          <Input onSendMessage={onSendMessage} />
        </>
         :
        <LoginForm onSubmit={handleLoginSubmit}/>

      }

    </div>
  );
}

export default App;
