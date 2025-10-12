import { create } from "zustand";
import Thumbnail from "../component/Thumbnail";

const useStore = create((set) => ({
  moviename: "vb",
  Thumbnail: [{}],
  addThumbnail: (newThumbnail) =>
    set((state) => ({ Thumbnail: [...state.Thumbnail, newThumbnail] })),

  setMoviename: (name) => set({ moviename: name }),
}));

export default useStore;
