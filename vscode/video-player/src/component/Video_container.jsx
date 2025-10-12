import React from "react";
import Sidebar from "./sidebar";
import VideoPlayer from "./VideoPlayer";

function Video_container() {
  return (
    <div className="flex bg-green-400 h-fit">
      <Sidebar />
      <VideoPlayer />
    </div>
  );
}

export default Video_container;
