//import axios from "axios";
import { useStore } from "zustand";

function VideoPlayer() {
  const{moviename} = useStore();
  return (
    <div className="w-[75%] h-screen bg-amber-500">
      <video
        className="w-full h-full"
        src={`http://localhost:8080/api/movie/${moviename}`}
        controls
        autoPlay
        loop
      ></video>
    </div>
  );
}

export default VideoPlayer;
