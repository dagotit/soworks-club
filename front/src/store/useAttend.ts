import { create } from 'zustand';
import { DateTime } from 'luxon';

interface AttendState {
  isAttend: boolean; // 출첵 완료
  attendDay: any; // 출석체크한 날짜
  todayAttend: () => void;
  attendDayCheck: () => void;
}

const useAttendStore = create<AttendState>((set, get) => ({
  isAttend: false,
  attendDay: null,
  todayAttend: () => {
    // 출석체크 시 하루동안 출석 api 호출 방지를 위한 메서드
    const attendDay = get().attendDay;
    if (!attendDay) {
      set(() => ({
        isAttend: true,
        attendDay: DateTime.now(),
      }));
    }
  },
  attendDayCheck: () => {
    // 하루 지났는지 체크하는 메서드
    const attendDay = get().attendDay;
    if (!attendDay) {
      return;
    }
    const today = DateTime.now();
    if (
      today.year >= attendDay.year &&
      today.month >= attendDay.month &&
      today.day > attendDay.day
    ) {
      set(() => ({
        isAttend: false,
        attendDay: null,
      }));
    }
  },
}));

export { useAttendStore };
