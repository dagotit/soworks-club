'use client';

import { Calendar, DateLocalizer, luxonLocalizer } from 'react-big-calendar';
import { DateTime } from 'luxon';
import styles from './CalendarMain.module.css';
import { useState } from 'react';
import { useGetMonthCalendar } from '@/hooks/useCalendar';
import { ListType, useCalendarStore } from '@/store/useCalendar';
import useDidMountEffect from '@/utils/useDidMountEffect';
import Toolbar from '@/components/calendar/Toolbar';
import CustomDateHeader from '@/components/calendar/CustomDateHeader';
import List from '@/components/calendar/List';
import React from 'react';

const CalendarMain = () => {
  DateTime.local().setLocale('ko-KR');
  const localize: DateLocalizer = luxonLocalizer(DateTime);
  const { isLoading, isFetching, data, isError, error, refetch } =
    useGetMonthCalendar();
  const calendarStore = useCalendarStore();
  useDidMountEffect(() => {
    calendarStore.add([
      {
        id: 0,
        title: '등록된 이벤트',
        allDay: true,
        start: new Date('2023-12-13'),
        end: new Date('2023-12-13'),
        colorEvento: 'green',
        color: 'white',
      },
      {
        id: 1,
        title: '등록된 이벤트',
        allDay: false,
        start: new Date('2024-01-13'),
        end: new Date('2024-01-13'),
        attendanceDate: new Date('2024-01-13'),
        colorEvento: 'green',
        color: 'white',
      },
    ]);
  }, [data]);
  const [myEvents, setMyEvents] = useState<ListType[] | []>([]);

  /**
   * @function
   * 등록된 이벤트 클릭했을 때
   */
  function onClickScheduler(item: ListType) {
    console.log('click', item);
  }

  function handlerViewChange(val: any) {
    // 월 단위로 고정
  }

  function handlerPropsGetter(myEvents: any) {
    const backgroundColor = myEvents.colorEvento
      ? myEvents.colorEvento
      : 'blue';
    const color = myEvents.color ? myEvents.color : 'blue';
    return {
      style: { backgroundColor, color },
    };
  }

  return (
    <main>
      {isLoading && <div className={styles.loadingText}>로딩중</div>}
      {!isLoading && (
        <div>
          <Calendar
            className={styles.calendar}
            localizer={localize}
            components={{
              toolbar: Toolbar,
              month: {
                dateHeader: CustomDateHeader,
              },
            }}
            events={calendarStore.calendarList || []}
            startAccessor="start"
            endAccessor="end"
            onSelectEvent={onClickScheduler}
            view={'month'}
            views={['month']}
            eventPropGetter={handlerPropsGetter}
            onView={handlerViewChange}
            style={{ height: 500 }}
          />
        </div>
      )}
      {!isLoading && <List />}
    </main>
  );
};

export default CalendarMain;
