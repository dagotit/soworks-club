import { create } from 'zustand';

export interface ClubListItemType {
  id: number;
  title: string;
  date: string;
  status: string;
}

interface ClubListState {
  clubList: ClubListItemType[];
  updateList: (list: ClubListItemType[]) => void;
}

const useClubListStore = create<ClubListState>((set, get) => ({
  clubList: [],
  updateList: (list) => {
    set(() => ({
      clubList: [...list],
    }));
  },
}));

export { useClubListStore };
