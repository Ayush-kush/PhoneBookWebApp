contacts_data = [];

async function getAllContacts(){

    const response = await fetch("http://localhost:8080/contact/getall")
    console.log(response);

    if(response.status === 200){
        response_body = JSON.parse(await response.text())
        console.log(response_body);
        contacts_data=response_body;
        process_contacts_data();
    }

}

document.getElementById("sort").onchange = () => {

    console.log("Update type button was clicked");
}

document.getElementById("addForm").onsubmit =async (event) =>{

    event.preventDefault();
    let contact=new Object();
    contact.mobileNumber = document.getElementById('mobile').value;
    contact.name = document.getElementById('cname').value;
    
    contact.email = document.getElementById('Email').value;
    contact.type = document.getElementById('Ctype').value;
    contact.url = document.getElementById('url').value;

    console.log(contact);

    const response = await fetch("http://localhost:8080/contact/add",{
        method:'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body:JSON.stringify(contact)
        });

        if(response.status === 201){
            alert(`Contact successfully added ${contact}`)
        }
        else{
            alert(await response.text());
        }

}


document.getElementById("updateNameForm").onsubmit = async (event) =>{
   
    event.preventDefault();

    mobileNumber =document.getElementById('umobile').value;
    updatedName = document.getElementById('uname').value;

    const response = await fetch(`http://localhost:8080/contact/update/${mobileNumber}`,{
        method:'PUT',
        headers: {
            'Content-Type':'application/json'
        },
        body: updatedName
        });

        if(response.status === 202){
            alert("Contact Name Updated")
        }
        else{
            alert(await response.text());
        }
        

    console.log("Update Name button was clicked");
}

document.getElementById("updateEmailForm").onsubmit = async (event) =>{
   
    event.preventDefault();

    mobileNumber =document.getElementById('uEmobile').value;
    updatedEmail = document.getElementById('uEmail').value;

    const response = await fetch(`http://localhost:8080/contact/update/email/${mobileNumber}`,{
        method:'PUT',
        headers: {
            'Content-Type':'application/json'
        },
        body: updatedEmail
        });

        if(response.status === 202){
            alert("Contact Email Updated")
        }
        else{
            alert(await response.text());
        }
        

    console.log("Update Email button was clicked");
}


document.getElementById("updateTypeForm").onsubmit =async (event) =>{
    event.preventDefault();

    mobileNumber =document.getElementById('utmobile').value;
    updatedType = document.getElementById('uType').value;
    const data = {
        type:updatedType
    }
    const response = await fetch(`http://localhost:8080/contact/update/type/${mobileNumber}`,{
        method:'PUT',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(data)
        });

        if(response.status === 202){
            alert("Contact Type Updated")
        }
        else{
            alert(await response.text());
        }
        
    console.log("Update Type was clicked");
}

document.getElementById("deleteForm").onsubmit =async (event) =>{
    event.preventDefault();
    console.log("Delete Contact button was clicked");

    var delmobile = document.getElementById('dmobile').value;

    const response = await fetch(`http://localhost:8080/contact/delete/${delmobile}`,{
        method:'DELETE'
        });


}

document.getElementById("sort").onchange = async()=>{

    var sortdata = document.getElementById('sort').value;
    const response = await fetch(`http://localhost:8080/contact/sort?property=${sortdata}`);
   
    if(response.status === 200){
        var jsonBody = JSON.parse(await response.text());
        contacts_data = jsonBody;
        process_contacts_data();
    }

}
function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

document.getElementById("searchForm").onsubmit = async(event)=>{
    event.preventDefault();
    console.log(`Searching...`);
    
    let key = document.getElementById('smobile').value;
    if(isNumeric(key)){
        const response = await fetch(`http://localhost:8080/contact/getcontact/${key}`,
        )
        console.log(response);
        if(response.status === 200){
            response_body = JSON.parse(await response.text())
            console.log(response_body);
            contacts_data=response_body;
            process_contacts_data();
        }
    }
    else if(typeof key === 'string'){
    const response = await fetch(`http://localhost:8080/contact/getname/${key}`,
    )
    console.log(response);
    if(response.status === 200){
        response_body = JSON.parse(await response.text())
        console.log(response_body);
        contacts_data=response_body;
        process_contacts_data();
    }
    else if(response.status === 404){
        alert("Contact with name"+key+"not found");
    }
}
    // else{
    //     const response = await fetch(`http://localhost:8080/contact/getname/${key}`,
    //     )
    //     console.log(response);
    //     if(response.status === 200){
    //         response_body = JSON.parse(await response.text())
    //         console.log(response_body);
    //         contacts_data=response_body;
    //         process_contacts_data();
    //     }
    // }
    
}

function process_contacts_data(){
    var table= document.getElementById("cTable");
    // console.log(table);
    var body = table.getElementsByTagName('tbody')[0];
    // console.log(body);
    body.innerHTML = "";
    if(Array.isArray(contacts_data)){
        for (let i = 0; i < contacts_data.length; i++) {
            add_contact_row(contacts_data[i]);            
        }
    }
    else{
        add_contact_row(contacts_data);
    }
}

function add_contact_row(contact){
    var table= document.getElementById("cTable");

    // console.log(table);
    var body = table.getElementsByTagName("tbody")[0];
    // console.log(body);
    var row = body.insertRow();
    var mobile = row.insertCell(0);
    var name = row.insertCell(1);
    var email = row.insertCell(2);
    var type = row.insertCell(3);
    var url = row.insertCell(4);

    mobile.innerHTML = contact.mobileNumber
    name.innerHTML = contact.name
    email.innerHTML = contact.email
    type.innerHTML = contact.type
    url.innerHTML = contact.url
}