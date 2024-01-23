import { create } from 'zustand';

export interface ClubListItemType {
  id: number;
  title: string;
  date: string;
  status: string;
  images: string;
  personNumber: number;
  time: string;
}

interface ClubListState {
  clubList: ClubListItemType[];
  updateList: (list: ClubListItemType[]) => void;
  remove: () => void;
}

const useClubListStore = create<ClubListState>((set, get) => ({
  clubList: [],
  updateList: (list) => {
    set(() => ({
      clubList: [...list],
    }));
  },
  remove: () => {
    set(() => ({ clubList: [] }));
  },
}));

export { useClubListStore };
