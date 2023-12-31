import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:8080/api/',
});

export const chatAPI = {
    getMessages: (groupId) => {
        console.log('Calling get messages from API');
        return api.get(`messages/${groupId}`);
    },

    sendMessage: (username, text) => {
        let msg = {
            sender: username,
            content: text
        }
        return api.post(`send`, msg);
    }
}
