function openDialog(client){
    window.myDialog.show();
    document.getElementById('phone').innerText = client;
}

function closeDialog(){
    window.myDialog.close();
}