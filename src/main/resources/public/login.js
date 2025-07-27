function test(){
    
    let username = document.getElementById("username").value

    document.cookie = "username="+username
    console.log("it worked")
}
