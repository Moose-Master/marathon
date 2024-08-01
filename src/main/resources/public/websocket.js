let socket = null;

function start() {
    console.log("Hello, world!");

    const PATH = `/ws`;
    console.log(PATH);
    socket = new WebSocket(PATH);
    socket.addEventListener("message", (event) => {
        let msg = event.data;
        console.log(`Message: ${msg}`);
        document.getElementById("wsout").innerText += msg + "\n";
    });

    const node = document.getElementById("msg");
    node.addEventListener("keyup", function (event) {
        if (event.key === "Enter") {
            updateWebsocket()
        }
    });
}

function updateWebsocket() {
    if(socket.readyState == WebSocket.CLOSED) {
        console.log("Disconnected");
    } else if (socket.readyState == WebSocket.OPEN) {
        socket.send(document.getElementById("msg").value);
        document.getElementById("msg").value = "";
    } else {
        console.log(socket.readyState);
    }
}

window.onload = start;