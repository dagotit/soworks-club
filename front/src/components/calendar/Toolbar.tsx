import styles from '../../app/(calendar)/calendar/CalendarMain.module.css';
import React, { ReactElement, useEffect, useState } from 'react';
import { DateTime } from 'luxon';
import useDidMountEffect from '@/utils/useDidMountEffect';
const Toolbar = (props: any) => {
  const { date } = props;
  const week = ['일', '월', '화', '수', '목', '금', '토'];
  const [month, setMonth] = useState(DateTime.now().month);
  const [selectDate, setSelectDate] = useState(
    DateTime.now().toFormat('yyyy-MM'),
  );

  /**
   * @function
   * 월 이동 버튼
   */
  function navigate(action: string) {
    props.onNavigate(action);
    if (action === 'TODAY') {
      const clickDate = DateTime.now().toFormat('yyyy-MM');
      setSelectDate(clickDate);
    }
    if (action === 'PREV') {
      const clickDate = DateTime.fromJSDate(date)
        .plus({ month: -1 })
        .toFormat('yyyy-MM');
      setSelectDate(clickDate);
    }
    if (action === 'NEXT') {
      const clickDate = DateTime.fromJSDate(date)
        .plus({ month: 1 })
        .toFormat('yyyy-MM');
      setSelectDate(clickDate);
    }
  }

  function onViews(action: any) {
    props.onView(action);
  }

  /**
   * @function
   * input onChange 로 이동 시
   * */
  function handleMonthChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (!e.currentTarget.value) {
      return;
    }
    setSelectDate(e.currentTarget.value);
    props.onNavigate(null, new Date(e.currentTarget.value));
  }

  return (
    <div className={`${styles.rbcToolbar} rbc-toolbar`}>
      <span className={`${styles.rbcBtnGroup} rbc-btn-group`}>
        <span className={`${styles.rbcToolbarLabel} rbc-toolbar-label`}>
          <label className={styles.inputLable}>
            <input
              type="month"
              className={styles.inputMonth}
              value={selectDate}
              onKeyDown={(e) => e.preventDefault()}
              onChange={handleMonthChange}
            />
            {/*<span>*/}
            {props.view === 'month' &&
              `${date.getFullYear()}년 ${date.getMonth() + 1}월`}
            {props.view === 'week' &&
              `${date.getFullYear()}년 ${date.getMonth() + 1}월`}
            {props.view === 'day' &&
              `${date.getMonth() + 1}월 ${date.getDate()}일 ${
                week[date.getDay()]
              }요일`}
            {/*</span>*/}
          </label>
        </span>
        <button onClick={navigate.bind(null, 'PREV')}>&#10094;</button>
        <button onClick={navigate.bind(null, 'NEXT')}>&#10095;</button>
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
