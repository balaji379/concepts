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
      className="w-full cursor-pointer flex h-[25%] flex-col  rounded-2xl mt-2 transform transition-transform duration-500 ease-in-out hover:scale-120"
      onClick={() => setNewMoviename(obj.name)}
    >
      <div className="w-full h-[75%]">
        <img
          className="h-full w-full p-2.5 rounded-2xl"
          src={`data:image/jpeg;base64,${obj.img}`}
          alt=""
        />
      </div>
      <p className="text-center underline h-fit rounded-[10px] text-[12px] break-words text-violet-800">
        {obj.name}
      </p>
    </div>
  );
}

export default Thumbnail;
