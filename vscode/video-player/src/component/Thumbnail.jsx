import axios from "axios";
import React, { useEffect } from "react";
import { useStore } from "zustand";

function Thumbnail() {
  const { Thumbnail, addThumbnail } = useStore();
  useEffect(() => {
    const readStream = async () => {
      const response = await fetch(
        "http://localhost:8080/api/output-service/start-thumbnail-stream"
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
            addThumbnail(obj);
            console.log("Received chunk:", obj);
          }
        }
      }
    };

    readStream();
  }, []);

  return (
    <div>
      {Thumbnail.map((item, index) => (
        <div key={index} className="w-full h-[20%] border border-black m-0 p-0">
          <img className="w-screen h-[20%]" src={item.url} alt={item.name} />
          <p className="p-0 m-0">{item.name}</p>
        </div>
      ))}
      <div className="w-full h-[20%] border border-black m-0 p-0">
        <img className="w-screen h-[20%]" src="" alt="" />
        <p className="p-0 m-0"></p>
      </div>
    </div>
  );
}

export default Thumbnail;
