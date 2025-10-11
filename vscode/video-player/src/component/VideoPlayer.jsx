import axios from "axios";
import React, { useEffect, useState } from "react";

function VideoPlayer() {
  const [videourl, setVideourl] = useState();
  function getMovie(moviename) {
    console.log("selected movie name is : " + moviename);
    axios
      .get("http://localhost:8080/api/output-service/start-stream/" + moviename,{responseType:"blob"})
      .then((data) => {
        const blob = new Blob([data], { type: "video/mp4" });
        const url = URL.createObjectURL(blob);
        console.log("creating url is :  " + url);
        setVideourl(url);
      })
      .catch((error) => {
        console.log(error);
      });
  }
    useEffect(() => {
      getMovie("vb");
    }, []);
  return (
    <div className="w-[80%] h-screen bg-amber-500">
      <video className="w-full h-full" src={videourl} controls autoPlay></video>
    </div>
  );
}

export default VideoPlayer;
