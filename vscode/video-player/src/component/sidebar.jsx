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
            console.log(`name is ${obj.name}`);
            console.log(`id is ${obj.id}`);
            console.log(`img is ${obj.img}`);
            addthumbnail(obj);
            console.log(obj);
          }
        }
      }
    };
    readStream();
  }, []);
  return (
    <div className="w-[25%] bg-black h-dvh overflow-auto scrollbar-none">
      <div className="title w-full h-[4%] border-3 text-[17px] rounded-[10px] bg-white text-center italic font-extralight ">
        video-playlist
      </div>
      {thumbnails?.map((thumbnail, key) => (
        <Thumbnail key={key} obj={thumbnail} />
      ))}
    </div>
  );
}

export default Sidebar;
