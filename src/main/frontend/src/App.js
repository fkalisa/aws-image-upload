import './App.css';
import axios from "axios";
import {useState,useEffect, useCallback }  from 'react' ;
import {useDropzone} from "react-dropzone";

const UserProfile = ()=>{

    const [userProfiles, setUserProfiles]= useState([]);
  const fetchUserProfiles = () =>{
    axios.get("http://localhost:8080/api/vi/user-profile")
        .then(res => {
          const data = res.data;
            console.log(data);

            setUserProfiles(data);
        })
  }
    useEffect(() =>{
        fetchUserProfiles();
    }, []);


    function Dropzone({userProfileId}) {
        const onDrop = useCallback(acceptedFiles => {
            const file =acceptedFiles[0];
            console.log(file);
            console.log(userProfileId);

            const formData = new FormData();
            formData.append("file", file);

            //
            axios.post(`http://localhost:8080/api/vi/user-profile/${userProfileId}/image/upload`,
                formData,
                {
                    headers:{
                        "Content-Type": "multipart/form-data",
                        'Access-Control-Allow-Origin' : '*',
                        'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',

                    }
                }).then(()=>{
                    console.log("file uploading")

            }).catch(err=> {
                console.log(err)
                }

            );;
        }, [])
        const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

        return (
            <div {...getRootProps()}>
                <input {...getInputProps()} />
                {
                    isDragActive ?
                        <p>Drop the files here ...</p> :
                        <p>Drag 'n' drop some files here, or click to select files</p>
                }
            </div>
        )
    }
  return userProfiles.map((userProfile, index) =>{
      return (
          <div key={index}>
              {userProfile.userProfileId ?
                  (<img
                      src={`http://localhost:8080/api/vi/user-profile/${userProfile.userProfileId}/image/download`}
                  />)
                  :
                  null }
              <h1>{userProfile.username}</h1>
              <p>{userProfile.userProfileId}</p>
              <Dropzone {...userProfile}/>

          </div>
      );
  });
}



function App() {
  return (
    <div className="App">
      <UserProfile />
    </div>
  );
}

export default App;
