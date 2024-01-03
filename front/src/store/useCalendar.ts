import { create } from 'zustand';

export interface ListType {
  id: number;
  title: string;
  allDay: boolean;
  start: any;
  end: any;
  attendanceDate?: any;
  colorEvento?: string;
  color?: string;
  attendance?: boolean;
}

interface CalendarState {
  calendarList: ListType[];
  add: (list: ListType[]) => void;
  remove: () => void;
}

const useCalendarStore = create<CalendarState>((set, get) => ({
  calendarList: [],
  add: (list) => {
    set(() => ({ calendarList: [...list] }));
  },
  remove: () => {
    set(() => ({ calendarList: [] }));
  },
}));

export { useCalendarStore };
