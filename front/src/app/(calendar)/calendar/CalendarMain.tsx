'use client';

import { Calendar, DateLocalizer, luxonLocalizer } from 'react-big-calendar';
import { DateTime } from 'luxon';
import styles from './CalendarMain.module.css';
import { Fragment, useCallback, useEffect, useState } from 'react';
import {
  CalendarParamType,
  FilterQueryParamType,
  useGetCalendarQuerys,
} from '@/hooks/useCalendar';
import { ListType, useCalendarStore } from '@/store/useCalendar';
import useDidMountEffect from '@/utils/useDidMountEffect';
import Toolbar from '@/components/calendar/Toolbar';
import CustomDateHeader from '@/components/calendar/CustomDateHeader';
import List from '@/components/calendar/List';
import Header from '@/components/Header';
import ClubListFilter from '@/components/popups/ClubListFilter';
import HeaderCellContent from '@/components/calendar/HeaderCellContent';
import { useRouter } from 'next/navigation';
import { useClubListStore } from '@/store/useClubList';

const DEFAULTSTARTDAY = DateTime.now().toISODate(); // 모임리스트 조회 시작일
const DEFAULTENDDAY = `${DateTime.now().toFormat('yyyy-MM')}-${new Date(
  DateTime.now().year,
  DateTime.now().month,
  0,
).getDate()}`; // 모임리스트 조회 종료일
const CalendarMain = () => {
  const router = useRouter(); // 라우터
  DateTime.local().setLocale('ko-KR'); // 캘린더 한국시간 기준으로 설정
  const localize: DateLocalizer = luxonLocalizer(DateTime); // 달력 데이터 setting
  const [calendarDate, setCalendarDate] = useState(DateTime.now().toJSDate());
  const [calendarQuery, setCalendarQuery] = useState<CalendarParamType>({
    stYear: DateTime.now().year,
    endYear: DateTime.now().year,
    stMonth: DateTime.now().month,
    endMonth: DateTime.now().month,
    joinOnly: false, // 내가 가입한 모임
    makeOnly: false, // 내가 만든 모임
    statusNotDone: true, // 모집중인 모임
  });
  const [isFilterPopup, setIsFilterPopup] = useState(false); // 필터 팝업 open & close 여부
  const [listFilterQuery, setListFilterQuery] = useState<FilterQueryParamType>({
    isAll: true,
    startDate: DEFAULTSTARTDAY,
    endDate: DEFAULTENDDAY,
    isAttendClub: false,
    isCreateClub: false,
    statusClub: true,
  }); // 필터 옵션

  const calendarStore = useCalendarStore(); // 캘린더 데이터 담기
  const clubListStore = useClubListStore(); //  모임 리스트 데이터 스토어에 담기

  // api
  const apiCalendarQuerys = useGetCalendarQuerys(
    calendarQuery,
    listFilterQuery,
  );

  // 사용 x
  const [myEvents, setMyEvents] = useState<ListType[] | []>([]);

  /**
   * @function
   * 캘린더 api 데이터 스토어에 setting
   * 모임 리스트 api 데이터 스토어에 setting
   */
  useEffect(() => {
    if (!!apiCalendarQuerys[0].data) {
      // 캘린더 api 데이터 스토어에 set
      /**
       *  "id": "1",
       *  "groupId": "1",
       *  "title": "댄스동아리",
       *  "allDay": null,
       *  "start": "2024-01-04",
       *  "end": "2024-01-04",
       *  "attendanceDate": null,
       *  "colorEvento": "",
       *  "color": ""
       */
      let calendarList = [];
      // @ts-ignore
      if (apiCalendarQuerys[0].data.respBody.length === 0) {
        calendarStore.remove();
      }
      // @ts-ignore
      if (apiCalendarQuerys[0].data.respBody.length > 0) {
        // @ts-ignore
        calendarList = apiCalendarQuerys[0].data.respBody.map((item: any) => {
          const itemData: ListType = {
            id: item.id,
            title: item.title,
            allDay: false,
            start: new Date(item.start),
            end: new Date(item.end),
            colorEvento: 'green',
            color: 'white',
          };
          if (item.attendanceDate) {
            itemData.attendanceDate = new Date(item.attendanceDate);
          }
          return itemData;
        });
        calendarStore.add(calendarList);
      }
    }

    if (!!apiCalendarQuerys[1].data) {
      // 모임 리스트 api 데이터 스토어에 setting
      /**
       * api 데이터
       * "groupId": 1,
       * "category": "취미",
       * "name": "댄스동아리",
       * "memberId": 1,
       * "description": "방송댄스배우기",
       * "status": "WAITING",
       * "groupImage": "https://storage.googleapis.com/dagachi-image-bucket/default/group_default.png",
       * "strStartDate": "20240127",
       * "strStartTime": "1900",
       * "strEndDate": "20240127",
       * "strEndTime": "2200",
       * "groupMaxNum": null,
       * "groupJoinNum": 1,
       * "masterYn": "Y"
       * */
      let groupList = [];
      // @ts-ignore
      if (apiCalendarQuerys[1].data.respBody.length === 0) {
        clubListStore.remove();
      }
      // @ts-ignore
      if (apiCalendarQuerys[1].data.respBody.length > 0) {
        // @ts-ignore
        groupList = apiCalendarQuerys[1].data.respBody.map((item: any) => {
          return {
            id: item.groupId,
            title: item.name,
            date: DateTime.fromFormat(item.strStartDate, 'yyyyMMdd').toFormat(
              'yyyy-MM-dd',
            ),
            status: item.status,
            personNumber: `참여: ${item.groupJoinNum}명`,
            images: item.groupImage,
            time: DateTime.fromFormat(item.strStartTime, 'HHmm').toFormat('t'),
          };
        });
        clubListStore.updateList(groupList);
      }
    }
  }, [apiCalendarQuerys[0].data, apiCalendarQuerys[1].data]);

  /**
   * @function
   * 등록된 이벤트 클릭했을 때
   */
  function onClickScheduler(item: ListType) {
    router.push(`/group/detail/${item.id}`);
  }

  function handlerViewChange(val: any) {
    // 월 단위로 고정
    console.log(val);
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
  const handleSetFilterApplyData = useCallback(
    (filterData: FilterQueryParamType) => {
      setListFilterQuery(filterData); // 모임 조회 리스트
      filterCalendar(filterData); // 캘린더
      setIsFilterPopup(false); // 팝업 닫기
    },
    [],
  );

  /**
   * @function
   * 캘린더에 이벤트가 있고, 그 이벤트에 다른 색으로 표시하고 싶을때..
   * 사용 x, 삭제 x
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

  /**
   * @function
   * 다른 월 클릭 시 이벤트
   */
  const handlerMonth = useCallback((e: any) => {
    setCalendarDate(e); // 캘린더 date 컨트롤
    const clickDay = DateTime.fromJSDate(e).toFormat('yyyy-MM-dd');
    const month = DateTime.fromJSDate(e).toFormat('M');
    const year = DateTime.fromJSDate(e).toFormat('yyyy');

    // 캘린더 파라미터 변경
    setCalendarQuery({
      ...calendarQuery,
      stYear: Number(year),
      endYear: Number(year),
      stMonth: Number(month),
      endMonth: Number(month),
    });

    // 캘린더 내용 바뀌면 리스트의 날짜도 변경되어야함..?
    setListFilterQuery({
      ...listFilterQuery,
      startDate: clickDay,
      endDate: clickDay,
    });
  }, []);

  /**
   * @function
   * 필터 조회시 캘린더 파라미터 setting
   */
  function filterCalendar(data: FilterQueryParamType) {
    setCalendarDate(
      DateTime.fromFormat(data.startDate, 'yyyy-MM-dd').toJSDate(),
    ); // 캘린더 정보 변경
    const year = DateTime.fromFormat(data.startDate, 'yyyy-MM-dd').year;
    const month = DateTime.fromFormat(data.startDate, 'yyyy-MM-dd').month;

    setCalendarQuery({
      stYear: year,
      endYear: year,
      stMonth: month,
      endMonth: month,
      joinOnly: data.isAttendClub, // 내가 가입한 모임
      makeOnly: data.isCreateClub, // 내가 만든 모임
      statusNotDone: data.statusClub, // 모집중인 모임
    });
  }

  return (
    <Fragment>
      <main>
        <Header />

        {apiCalendarQuerys[0].isLoading && (
          <div className={styles.loadingText}>
            <div className={styles.loader}></div>

            <div
              className={`${styles.loaderSection} ${styles.sectionLeft}`}
            ></div>
            <div
              className={`${styles.loaderSection} ${styles.sectionRight}`}
            ></div>
          </div>
        )}

        <div className={styles.calendarWrap}>
          <Calendar
            className={styles.calendar}
            localizer={localize}
            date={calendarDate}
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
            onNavigate={handlerMonth}
            onView={handlerViewChange}
            style={{ height: 500 }}
          />
        </div>

        <List popupState={handleFilterPopupState} />
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
