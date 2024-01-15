'use client';

import { Calendar, DateLocalizer, luxonLocalizer } from 'react-big-calendar';
import { DateTime } from 'luxon';
import styles from './CalendarMain.module.css';
import { Fragment, useCallback, useEffect, useState } from 'react';
import { FilterQueryParamType, useGetMonthCalendar } from '@/hooks/useCalendar';
import { ListType, useCalendarStore } from '@/store/useCalendar';
import useDidMountEffect from '@/utils/useDidMountEffect';
import Toolbar from '@/components/calendar/Toolbar';
import CustomDateHeader from '@/components/calendar/CustomDateHeader';
import List from '@/components/calendar/List';
import React from 'react';
import Header from '@/components/Header';
import { useTokenStore } from '@/store/useLogin';
import ClubListFilter from '@/components/popups/ClubListFilter';
import HeaderCellContent from '@/components/calendar/HeaderCellContent';

const CalendarMain = () => {
  DateTime.local().setLocale('ko-KR');
  const { accessToken } = useTokenStore();
  const localize: DateLocalizer = luxonLocalizer(DateTime);
  const [month, setMonth] = useState<number | null>(null);
  const { isLoading } = useGetMonthCalendar(month);
  const calendarStore = useCalendarStore();
  const [isFilterPopup, setIsFilterPopup] = useState(false);
  const [myEvents, setMyEvents] = useState<ListType[] | []>([]);
  const [listFilterQuery, setListFilterQuery] = useState<
    {} | FilterQueryParamType
  >({});

  useEffect(() => {
    setMonth(2);
    test();
  }, []);

  /* TODO: 토큰을 어떻게 하느냐에 따라서 달라짐... */

  /*  useEffect(() => {
    if (!!accessToken) {
      setMonth(2);
      test();
    }
  }, [accessToken]);*/

  const test = () => {
    calendarStore.add([
      {
        id: 0,
        title: '등록된 이벤트',
        allDay: true,
        start: new Date('2024-01-10'),
        end: new Date('2024-01-10'),
        colorEvento: 'red',
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
  };
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

  /**
   * @function
   * 필터 설정하는 팝업 open / close
   */
  const handleFilterPopupState = useCallback((e: boolean) => {
    setIsFilterPopup(e);
  }, []);

  /**
   * @function
   * 리스트 필터 팝업 적용하기 버튼 클릭 후
   */
  function handleSetFilterApplyData(filterData: FilterQueryParamType) {
    setListFilterQuery(filterData);
    setIsFilterPopup(false);
  }

  /**
   * @function
   * 캘린더에 이벤트가 있고, 그 이벤트에 다른 색으로 표시하고 싶을때..
   */
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
    <Fragment>
      <main>
        {/*<Header />*/}
        {calendarStore.calendarList.length === 0 && (
          <div className={styles.loadingText}>로딩중</div>
        )}
        {calendarStore.calendarList.length !== 0 && (
          <div className={styles.calendarWrap}>
            <Calendar
              className={styles.calendar}
              localizer={localize}
              components={{
                toolbar: Toolbar,
                month: {
                  header: HeaderCellContent,
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
        <List
          listFilterQueryData={listFilterQuery}
          popupState={handleFilterPopupState}
        />
      </main>
      {isFilterPopup && (
        <ClubListFilter
          listFilterQueryData={listFilterQuery}
          popupState={handleFilterPopupState}
          popupApplyData={handleSetFilterApplyData}
        />
      )}
    </Fragment>
  );
};

export default CalendarMain;
