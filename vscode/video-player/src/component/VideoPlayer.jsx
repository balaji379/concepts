//import axios from "axios";
import UseStore from "../store/UseStore";

function VideoPlayer() {
  const { moviename } = UseStore();
  return (
    <div className="w-[75%] h-screen bg-black">
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
