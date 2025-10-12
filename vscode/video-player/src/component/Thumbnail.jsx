// import axios from "axios";
// import React, { useEffect } from "react";
import UseStore from "../store/UseStore";

function Thumbnail({ obj }) {
  const { setMovieName } = UseStore();
  console.log("value print from thumbnail component : " + obj.img);

  function setNewMoviename(newmovieName) {
    console.log("selected movie name is " + newmovieName);
    setMovieName(newmovieName);
  }
  return (
    <div
      id="playlist"
      className="w-full flex h-[25%] flex-col gap-1 rounded-2xl mt-2"
      onClick={() => setNewMoviename(obj.name)}
    >
      <div className="w-full h-[75%]">
        <img
          className="h-full w-full"
          src={`data:image/jpeg;base64,${obj.img}`}
          alt=""
        />
      </div>
      <p className="text-center h-[15%] rounded-[10px] text-[12px] break-words text-white">
        {obj.name}
      </p>
    </div>
  );
}

export default Thumbnail;
