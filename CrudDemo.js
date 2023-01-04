import React, { useState } from 'react';
import axios from 'axios';
import './WelcomeMessage.css';
import NewMember from './NewMember';
import WelcomeMessage from './WelcomeMessage';


const CrudDemo = () => {

     
    const API_URL = "http://localhost:8080/participant";

    const [show, setShow] = useState(false)

    const [messages, setMessages] = useState({title:'',message:''})

    const [showMember, setShowMember] = useState(true)
    
                          
    const savePerson = async (data) => {

        console.log("DATA:", data);
        await axios.post(API_URL, data).then(
            (response) => {

                console.log(response);

                if (response.status === 201) {
                    console.log(`API: (Save) Request was executed Successfully!`);
                  
                    // todo: call welcome message api
                    axios.get('http://localhost:8080/message/welcome').then(res => {

                        if (res.status === 200) {
                            console.log(`API: (Get) Request was executed Successfully!`);
                            const messageApiResponse = {title: 'Welcome!', message: res.data.messageContent};
                            setShowMember(false)
                            setShow(true)                      
                            setMessages(messageApiResponse)
                       
                        }

                    });
                  
    
                } else if (response.status === 400){
                    
                   
                }else {
                    console.error(`API: Request was executed with status code ${response.status}`);
                }

            }
        ).catch((error) => {
            console.warn(`API: Request Encounter an Error ${error}`);
        })
        
    }

    
    return (
        <div>
             
             <WelcomeMessage show={show} text={messages} closePopup={() => setShow(false)}/> 
             <NewMember handleSave={savePerson} showMember={showMember}/>           
          
        </div>
    );
};

export default CrudDemo;