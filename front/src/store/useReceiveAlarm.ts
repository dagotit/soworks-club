import { create } from 'zustand';

type AlarmItem = {
  id : number;
  title: string
  content: string;
  readYn: 'N' | 'Y'
}
interface ReceiveAlarmState {
  list: AlarmItem[];
  isNew: boolean;
  setList: (list: AlarmItem[]) => void;
  updateReadYN: (id: number) => void;
}

const useReceiveAlarmStore = create<ReceiveAlarmState>((set, get) => ({
  list: [],
  isNew: false,
  setList: (list) => {
    set({ list: list })
  },
  updateReadYN: (id: number) => {
    const updateList: AlarmItem[] = get().list.map((item) => {
      if (item.id === id && item.readYn === 'N') {
        return { ...item, readYn: 'Y' }
      }
      return item
    });

    set(() => ({ list: [...updateList] }));
  }
}))

export {useReceiveAlarmStore}