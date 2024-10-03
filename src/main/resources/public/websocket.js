let socket = null;
let input_box = null;

function start() {
    console.log("Hello, world!");
    input_box = document.getElementById("msg");

    const PATH = `/ws`;
    console.log(PATH);
    socket = new WebSocket(PATH);
    socket.addEventListener("message", (event) => {
        let msg = event.data;
        console.log(`Message: ${msg}`);
        document.getElementById("wsout").innerText += msg + "\n";
    });
    socket.addEventListener("close", (event) => {
        console.log("Disconnected");
    })
    socket.addEventListener("error", (event) => {
        console.log(`Error: ${event}`);
    });
    input_box.addEventListener("keypress", (event) => {
        if (event.key == "Enter") {
            sendMessage();
        }
    });
}
function sendMessage() {
    if (socket.readyState == WebSocket.OPEN) {
        let value = input_box.value;
        socket.send(value);
        console.log(`Sent message ${value}`);
        input_box.value = "";
    } else {
        console.log(socket.readyState);
    }
}
console.log("Sanity check 2");
window.onload = start;