import { create } from "zustand";

const UseStore = create((set) => ({
  thumbnails: [],
  addthumbnail: (newthumbnails) =>
    set((state) => ({
      thumbnails: [...state.thumbnails, newthumbnails],
    })),
  moviename: "",
  setMovieName: (newname) =>
    set((state) => ({
      moviename: newname,
    })),
}));
export default UseStore;
