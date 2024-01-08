import http from '@/services/httpService';
import axios from 'axios';

export const apiGetMonthCalendar = async () => {
  try {
    return await http.get('/v2/auth');
    // setTimeout(() => {
    //   return [
    //     {
    //       id: 0,
    //       title: '1_이벤트',
    //       allDay: true,
    //       start: new Date('2023-12-13'),
    //       end: new Date('2023-12-13'),
    //     },
    //     {
    //       id: 1,
    //       title: '2_이벤트',
    //       allDay: true,
    //       start: new Date('2023-12-13'),
    //       end: new Date('2023-12-13'),
    //     },
    //   ];
    // }, 300);
  } catch (e) {}
};

export const apiGetAttendance = async (data: null) => {
  try {
    return http.get('/api/v1/attendance/attend');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
