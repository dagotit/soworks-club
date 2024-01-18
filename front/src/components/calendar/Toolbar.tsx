import styles from '../../app/(calendar)/calendar/CalendarMain.module.css';
import { useState } from 'react';
import { DateTime } from 'luxon';
const Toolbar = (props: any) => {
  const { date } = props;
  const week = ['일', '월', '화', '수', '목', '금', '토'];
  const [month, setMonth] = useState(DateTime.now().month);

  /**
   * @function
   * 월 이동 버튼
   */
  const navigate = (action: string) => {
    props.onNavigate(action);
  };

  const onViews = (action: any) => {
    props.onView(action);
  };

  return (
    <div className={`${styles.rbcToolbar} rbc-toolbar`}>
      <span className={`${styles.rbcBtnGroup} rbc-btn-group`}>
        <span className={`${styles.rbcToolbarLabel} rbc-toolbar-label`}>
          {props.view === 'month' &&
            `${date.getFullYear()}년 ${date.getMonth() + 1}월`}
          {props.view === 'week' &&
            `${date.getFullYear()}년 ${date.getMonth() + 1}월`}
          {props.view === 'day' &&
            `${date.getMonth() + 1}월 ${date.getDate()}일 ${
              week[date.getDay()]
            }요일`}
        </span>
        <button onClick={navigate.bind(null, 'PREV')}>↑</button>
        <button onClick={navigate.bind(null, 'NEXT')}>↓</button>
      </span>

      <button onClick={navigate.bind(null, 'TODAY')}>{month}월</button>
      {/*<span className="rbc-btn-group">
        <button onClick={onViews.bind(null, 'month')}>월</button>
        <button onClick={onViews.bind(null, 'week')}>주</button>
        <button onClick={onViews.bind(null, 'day')}>일</button>
        <button onClick={onViews.bind(null, 'agenda')}>아젠다</button>
      </span>*/}
    </div>
  );
};

export default Toolbar;
