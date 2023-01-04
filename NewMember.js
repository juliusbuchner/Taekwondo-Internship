import React from "react";
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';
import './NewMember.css';


const NewMember = (props) => {
 

  const {register, handleSubmit, reset , formState : {errors}} = useForm();

  const handleSave = props.handleSave;

  const navigate = useNavigate();

    
  return (
   
    <div>
      {props.showMember ? 
      <div className="container">
        <div className=" text-center mt-5 ">
          <h1>Skapa Medlem</h1>
        </div>

        <div className="row ">
          <div className="col-lg-10 mx-auto">
            <div className="card mt-2 mx-auto p-4 bg-light">
              <div className="card-body bg-light">
                <div className="container">
                  <form id="contact-form" role="form" onSubmit={handleSubmit(handleSave)} >
                  
                    <div className="controls">
                      <div className="row">
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="firstName">1. Förnamn *</label>
                            <input
                              id="firstName"
                              type="text"
                              name="firstName"
                              className="form-control"
                              placeholder="Ange ditt Förnamn"
                              {...register("firstName", {required: true, minLength: 2, pattern: /^([a-zA-Z '-]+)$/i })}
                            />
                            {errors.firstName && <span className="text-danger">
                              Förnamn är inte giltigt.</span>}
                          </div>
                        </div>
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="lastName">2. Efternamn *</label>
                            <input
                              id="lastName"
                              type="text"
                              name="lastName"
                              className="form-control"
                              placeholder="Ange ditt Efternamn"
                              {...register("lastName", {required: true, minLength: 2, pattern: /^([a-zA-Z '-]+)$/i})}
                            />
                            {errors.lastName && <span className="text-danger">
                              Efternamn är inte giltigt.</span>}
                          </div>
                        </div>
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="socialSecurityNumber"> 3. Personnummer * </label>
                            <input
                              id="socialSecurityNumber"
                              type="text"
                              name="socialSecurityNumber"
                              className="form-control"
                              placeholder="YYMMDD-xxxx"
                              {...register("socialSecurityNumber", {required: true, pattern: /^\d{2}([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9]|[3][0-1])-\d{4}$/i})}
                            />
                            {errors.socialSecurityNumber && <span className="text-danger">
                             Personnummer är inte giltigt.</span>}
                          </div>
                        </div>
                      </div>
                      <br />

                      <div className="row">
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="phoneNumber">4. Telefon *</label>
                            <input
                              id="phoneNumber"
                              type="tel"
                              name="phoneNumber"
                              className="form-control"
                              placeholder="Ange ditt Telefon"
                              {...register("phoneNumber", {required: true, pattern: /(^1300\d{6}$)|(^1800|1900|1902\d{6}$)|(^0[2|3|7|8]{1}[0-9]{8}$)|(^13\d{4}$)|(^04\d{2,3}\d{6}$)/i})}
                            />
                            {errors.phoneNumber && <span className="text-danger">
                            Telefonnummer är inte giltigt.</span>}
                          </div>
                        </div>
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="parentName">5. Föräldernamn *</label>
                            <input
                              id="parentName"
                              type="text"
                              name="parentName"
                              className="form-control"
                              placeholder="Ange ditt Föräldernamn"
                              {...register("parentName", {required: true, pattern: /^([a-zA-Z]+[\'\,\.\-]?[a-zA-Z ]*)+[ ]([a-zA-Z]+[\'\,\.\-]?[a-zA-Z ]+)+$/i})}
                            />
                            {errors.parentName && <span className="text-danger">
                            Föräldernamn är inte giltigt.</span>}
                          </div>
                        </div>
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="parentPhone">6. FörälderTelefon *</label>
                            <input
                              id="parentPhone"
                              type="text"
                              name="parentPhone"
                              className="form-control"
                              placeholder="Ange ditt FörälderTelefon"
                              {...register("parentPhone", {required: true,  pattern: /(^1300\d{6}$)|(^1800|1900|1902\d{6}$)|(^0[2|3|7|8]{1}[0-9]{8}$)|(^13\d{4}$)|(^04\d{2,3}\d{6}$)/i})}
                            />
                            {errors.parentPhone && <span className="text-danger">
                              Telefonnummer är inte giltigt.</span>}
                          </div>
                        </div>
                      </div>
                      <br />

                      <div className="row">
                        <div className="col-md-4">
                          <div className="form-group">
                            <label htmlFor="email">7. E-post *</label>
                            <input
                              id="email"
                              type="email"
                              name="email"
                              className="form-control"
                              placeholder="Ange ditt E-post"
                              {...register("email", {required: true, pattern: /^\w+[\w-\.]*\@\w+((-\w+)|(\w*))\.[a-z]{2,3}$/i})}
                            />
                            {errors.email && <span className="text-danger">
                            E-post är inte giltigt.</span>}
                          </div>
                        </div>
                      </div>
                      <br />

                      <div className="row">
                        <div className="form-group form-check mb-3"> 
                          <p className="mb-0 me-4">Tillåtelse av fotografering/Sociala medier: </p>

                            <div className="form-check form-check-inline mb-0 me-4">
                              <input className="form-check-input" type="radio" name="permissionPhoto" id="permissionPhoto1" 
                              value={true}  {...register("permissionPhoto")} />
                              <label className="form-check-label" htmlFor="permissionPhoto1">Ja</label>
                            </div>

                            <div className="form-check form-check-inline mb-0 me-4">
                              <input className="form-check-input" type="radio" name="permissionPhoto" id="permissionPhoto2" 
                              value={false}  {...register("permissionPhoto")} />
                              <label className="form-check-label" htmlFor="permissionPhoto2">Nej</label>
                            </div>
                        </div>
                      </div>

                      <div className="row d-md-flex justify-content-md-end">
                        <div className="col-md-3 d-md-flex justify-content-md-en">
                          <button
                            className="btn btn-success btn-md custom"
                            type="submit"
                          >
                            Skapa medlem
                          </button>

                        </div>
                        <div className="col-md-3 d-md-flex justify-content-md-end">
                          <button
                            className="btn btn-danger btn-md custom "
                            type="button"
                            onClick={() => reset()}
                          >
                            Avbryt
                          </button>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <br />
         
      </div> 
       : null}
      
    </div>
    
  );
};

export default NewMember;
