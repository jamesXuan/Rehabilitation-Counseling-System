var MessageType = {
    REQUEST: 0,
    RESPONSE: 1,
    NOTIFY: 2,
    ACK: 3
};

var MessageEnum = {
    LOGIN: 0,
    SELF_MESSAGE: 1,
    OTHER_MESSAGE: 2,
    SYSTEM_MESSAGE: 3,
    APPLY_MATCH: 4,
    ENTER_WAITING: 5,
    ENTER_SUCCESS: 6,
    EXIT: 7,
    EXIT_WAITING: 8,
    EXIT_SUCCESS: 9,
    HEARTBEAT: 10,
    STATE: 11,
    EXIT_APPLY_MATCH: 12,
    ALREAD_ENTER_SUCCESS: 13,
    SELF_PRIVATE_MESSAGE: 14,
    OTHER_PRIVATE_MESSAGE: 15,
    TYPING: 16,
    ATTENTION: 17,
    READ_MESSAGE: 18,
    FEEDBACK_MESSAGE: 19,
    FEEDBACK_REPLY_MESSAGE: 20
};

var ChatStatusEnum = {
    INIT: 1,
    WAIT: 2,
    CHATING: 3
};

var _initSocket = function(onOpen, onMessage, onReconnect) {
    if (_myToken() == null) {
        return;
    }
    var heartbeat = function() {
        var myToken = _myToken();
        if (myToken == null) {
            return "";
        }
        return JSON.stringify({memberToken: myToken, operate: MessageEnum.HEARTBEAT, type: MessageType.REQUEST});
    };
    window.websocketHeartbeatJs = new WebsocketHeartbeatJs({
        url: ws_url,
        pingTimeout: 2000,
        pongTimeout: 3000,
        pingMsg: heartbeat,
        beforeConnect: function() {
            var myToken = _myToken();
            if (myToken == null) {
                return false;
            }
            return true;
        }
    });

    websocketHeartbeatJs.onopen = function () {
        if (onOpen) {
            onOpen();
        }
        websocketHeartbeatJs.send(heartbeat());
    }
    websocketHeartbeatJs.onmessage = function (e) {
        if (onMessage) {
            onMessage(e);
        }
    }
    websocketHeartbeatJs.onreconnect = function () {
        if (onReconnect) {
            onReconnect();
        }
    }
}