
import React, { useState } from 'react';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

export const LoginForm = ({ onSubmit }) => {
    const [username,setUsername]=useState();
    const handleChange = (e) => {
        setUsername(e.target.value);
    }
    let handleSubmit = () =>{
        onSubmit(username);
    }
    return (
        <div>
            <TextField 
                label="Enter your Username"
                placeholder="username"
                onChange={handleChange}
                margin="normal"
                onKeyDown={
                        e=>{
                            if(e.key==="Enter"){
                                handleSubmit();
                            }
                        }
                    }
            />
            <br/>
            <Button variant="contained" color="primary" onClick={handleSubmit}>
                Login
            </Button>
        </div>
    );

}