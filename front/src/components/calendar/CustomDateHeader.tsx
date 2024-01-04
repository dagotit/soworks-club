import { useCalendarStore } from '@/store/useCalendar';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { DateTime, Interval } from 'luxon';
import { useState } from 'react';

const CustomDateHeader = (props: any) => {
  const { calendarList } = useCalendarStore();
  const [highlightDate, setHighlightData] = useState<any[] | []>([]);
  // console.log('props:', props);
  const attendance = calendarList.find((event) => {
    if (!event.attendanceDate) {
      return;
    }
    const interval = Interval.fromDateTimes(
      DateTime.local(
        event.attendanceDate.getFullYear(),
        event.attendanceDate.getMonth() + 1,
        event.attendanceDate.getDate(),
      ),
      DateTime.local(
        event.attendanceDate.getFullYear(),
        event.attendanceDate.getMonth() + 1,
        event.attendanceDate.getDate() + 1,
      ),
    );
    let dateTimeToCheck = DateTime.local(
      props.date.getFullYear(),
      props.date.getMonth() + 1,
      props.date.getDate(),
    );

    return interval.contains(dateTimeToCheck);
  });

  return (
    <div>
      <button className="rbc-button-link" role="cell">
        <div className={`${attendance ? 'attendance' : ''}`}>{props.label}</div>
      </button>
      {/* 음력날짜를 추가로 표기 */}
      {/*<div>{date}</div>*/}
    </div>
  );
};

export default CustomDateHeader;
