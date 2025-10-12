// import axios from "axios";
// import React, { useEffect } from "react";
//import UseStore from "../store/GeneralStore";
import { useEffect } from "react";
import UseStore from "../store/UseStore";
import Thumbnail from "./Thumbnail";

function Sidebar() {
  const { thumbnails, addthumbnail } = UseStore();
  console.log(addthumbnail);

  useEffect(() => {
    const readStream = async () => {
      const response = await fetch(
        "http://localhost:8080/api/movie/start-thumbnail-stream"
      );
      const reader = response.body.getReader();
      const decoder = new TextDecoder("utf-8");
      let buffer = "";

      while (true) {
        const { done, value } = await reader.read();
        if (done) break;

        buffer += decoder.decode(value, { stream: true });
        const parts = buffer.split("\n");
        buffer = parts.pop();

        for (const chunk of parts) {
          if (chunk.trim()) {
            const obj = JSON.parse(chunk);
            addthumbnail(obj);
            console.log(obj);
          }
        }
      }
    };
    readStream();
  }, []);
  return (
    <div className="w-[20%] bg-black h-dvh overflow-auto scrollbar-none">
      <div className="title w-full h-[5%] text-[20px] rounded-[10px] mt-4 bg-violet-800 text-center mb-4 text-white italic font-bold ">
        <i className="fa-solid fa-film fa-beat-fade text-white pe-3"></i>
        video-playlist
      </div>
      {thumbnails?.map((thumbnail, key) => (
        <Thumbnail key={key} obj={thumbnail} />
      ))}
    </div>
  );
}

export default Sidebar;
