import http from '@/services/httpService';

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
