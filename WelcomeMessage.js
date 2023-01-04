import React from 'react';
import {useNavigate} from 'react-router-dom';
import './WelcomeMessage.css'


const WelcomeMessage = (props) => {

 
    const navigate = useNavigate();

    const close = props.closePopup;
   
    return (
        <div >
 
            {props.show ? 
            <div className='app'> 
            <div className="login-form">           
            <div className="title">{props.text.title}</div>
            <div className='content'>{props.text.message}</div>
            <br/>            
            <button className="btn btn-danger button-close " onClick={close} >OK</button>
            </div>
            </div>
          
             : null}

        </div>
    
    );
};

export default WelcomeMessage;