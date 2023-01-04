import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import CrudDemo from './CrudDemo';
import ExtraClass from './ExtraClass';
import Register from './Register';
import WelcomeMessage from './WelcomeMessage';

const AppRouter = () => {
    return (
        <div>
          <Router>           
              <Routes>
                  {/* <Route exact path="/" element={< FirstPage/>} />  */}
                <Route path="/nymedlem" element={<CrudDemo />} />
                <Route path="/valkomna" element={<WelcomeMessage/>}/>
                <Route path="/" element={<ExtraClass />} />
                <Route path="/passregistrering" element={<Register />} />
              </Routes>
                
              <FirstPage/> 
                
          </Router>
        </div>
    );
};

const FirstPage = () =>{
  return(
    <>
    
    <div className="container px-lg-4">
          <div className="row justify-content-center">
            <div className="col-md-3 d-md-flex justify-content-md-start">

              <Link
                className="btn btn-primary style"
                type="button"
                to={"/"}
              >
                Skapa extrapass
              </Link>
            </div>
            <div className="col-md-3 d-md-flex justify-content-md-center">
              <Link
                className="btn btn-primary style"
                type="button"
                to={"passregistrering"}
              >
                Passregistrering
              </Link>
            </div>
            
            <div className="col-md-3 d-md-flex justify-content-md-end">
              <Link
                className="btn btn-primary style" 
                type="button" 
                to={"nymedlem"}         
              >
                Ny medlem
              </Link> 
            </div>
          </div>
        </div>
    </>
  )
}

export default AppRouter;