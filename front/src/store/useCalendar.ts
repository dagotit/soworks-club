import { create } from 'zustand';

export interface ListType {
  id: number; // id
  title: string; // 모임 제목
  allDay: boolean; // 종일
  start: any; // 모임 시작일 new Date('2023-12-13') 이렇게 했지만 YYYY-MM-DD만
  end: any; // 모임 종료일
  attendanceDate?: any; // 출석체크한날
  colorEvento?: string; // 모임 배경색
  color?: string; // 모임 글자색
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
